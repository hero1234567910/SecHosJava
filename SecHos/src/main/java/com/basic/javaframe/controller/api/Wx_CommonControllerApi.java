package com.basic.javaframe.controller.api;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.basic.javaframe.common.WebSocket.WebSocketServer;
import com.basic.javaframe.common.customclass.PassToken;
import com.basic.javaframe.common.enumresource.DelFlagEnum;
import com.basic.javaframe.common.enumresource.PatientEnum;
import com.basic.javaframe.common.enumresource.SexEnum;
import com.basic.javaframe.common.exception.MyException;
import com.basic.javaframe.common.utils.DateUtil;
import com.basic.javaframe.common.utils.R;
import com.basic.javaframe.controller.BaseController;
import com.basic.javaframe.entity.SecHos_Outpatient;
import com.basic.javaframe.entity.SecHos_Patient;
import com.basic.javaframe.entity.SecHos_hospitalized;
import com.basic.javaframe.service.Frame_ConfigService;
import com.basic.javaframe.service.RedisService;
import com.basic.javaframe.service.SecHos_OutpatientService;
import com.basic.javaframe.service.SecHos_PatientService;
import com.basic.javaframe.service.SecHos_hospitalizedService;
import com.basic.javaframe.service.api.Wx_CommonServiceIApi;

import io.swagger.annotations.ApiOperation;


@RestController
@CrossOrigin
@RequestMapping(value="wx/common")
public class Wx_CommonControllerApi extends BaseController{
	
	@Value(value = "${wx.api.appid}")
	public String appid;
	
	@Autowired
	Wx_CommonServiceIApi wx_CommonServiceApi;
	
	@Autowired
	RedisService redisService;
	
	@Autowired
	WebSocketServer socketServer;
	
	@Autowired
	Frame_ConfigService configService;
	
	@Autowired
	SecHos_PatientService patientService;
	
	@Autowired
	SecHos_hospitalizedService hospitalService;
	
	@Autowired
	SecHos_OutpatientService outpatientService;
	
	/**
	 * 获取网页授权token，openid
	 * <p>Title: code2Token</p>  
	 * <p>Description: </p>
	 * @author hero  
	 * @param code
	 * @return
	 */
	@PassToken
	@RequestMapping(value="/code2Token",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	@ResponseBody
	public R code2Token(@RequestBody String code){
		System.out.println(code);
		String result = wx_CommonServiceApi.code2Token(code);
		
		JSONObject jsonObject = JSONObject.parseObject(result);
		if (jsonObject.containsKey("errcode")) {
			String errcode = jsonObject.getString("errcode");
			return R.error("获取网页授权用户信息异常,errcode为"+errcode);
		}
		//获取openid,微信昵称，头像
		String openid = jsonObject.getString("openid");
		String nickname = jsonObject.getString("nickname");
		String headimgurl = jsonObject.getString("headimgurl");
		
		//根据openid查询是否有该用户,没有则生成一条新用户，有则返回该用户信息
		SecHos_Patient pa = patientService.getPatientByOpenid(openid);
		if (pa == null) {
			pa = new SecHos_Patient();
			pa.setDelFlag(DelFlagEnum.NDELFLAG.getCode());
			pa.setCreateTime(DateUtil.changeDate(new Date()));
			String uuid = java.util.UUID.randomUUID().toString();
			pa.setRowGuid(uuid);
			pa.setOpenid(openid);
			pa.setHeadImgUrl(headimgurl);
			patientService.save(pa);
			return R.ok().put("data", pa);
		}
		return R.ok().put("data", pa);	
	}
	
		/**
	     * 解析微信发来的请求（XML）
	     * 
	     * @param request
	     * @return
	     * @throws Exception
     	*/
		@SuppressWarnings("unchecked")
		public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {
	        // 将解析结果存储在HashMap中
	        Map<String, String> map = new HashMap<String, String>();
	
	        // 从request中取得输入流
	        InputStream inputStream = request.getInputStream();
	        // 读取输入流
	        SAXReader reader = new SAXReader();
	        Document document = reader.read(inputStream);
	        // 得到xml根元素
	        Element root = document.getRootElement();
	        // 得到根元素的所有子节点
	        List<Element> elementList = root.elements();
	
	        // 遍历所有子节点
	        for (Element e : elementList)
	            map.put(e.getName(), e.getText());
	
	        // 释放资源
	        inputStream.close();
	        inputStream = null;
	        return map;
		}
		
		/**
		 * 查询患者信息
		 * <p>Title: checkPatient</p>  
		 * <p>Description: </p>
		 * @author hero  
		 * @return
		 */
		@ResponseBody
		@RequestMapping(value="/checkPatient",produces="application/json;charset=utf-8",method=RequestMethod.POST)
		@ApiOperation(value="查询患者信息")
		public R checkPatient(@RequestBody Map<String, String> params){
			checkParams(params, "hzxm");
			checkParams(params, "zjh");
			checkParams(params, "action");
			checkParams(params, "openid");
			if (PatientEnum.OUTPATIENT.getCode().equals(params.get("action"))) {
				//查询门诊患者
				String result = wx_CommonServiceApi.selectOutPatient(params);

			    //解析结果
				JSONObject jsonObject = JSONObject.parseObject(result);
				if (jsonObject.containsKey("success")) {
					boolean resu = jsonObject.getBoolean("success");
					if (resu) {
						//查询成功
						JSONArray array = jsonObject.getJSONArray("patinfos");
						//默认取第一个
						JSONObject json = array.getJSONObject(0);
						//直接绑定患者信息
						SecHos_Patient pa = patientService.getPatientByOpenid(params.get("openid"));
						if (pa == null) {
							return R.error("未找到相关用户");
						}
						pa.setPatientAddress(json.getString("lxdz"));
						pa.setPatientBirth(DateUtil.changeStrToDate3(json.getString("birth")));
						pa.setPatientIdcard(json.getString("zjh"));
						pa.setPatientMobile(json.getString("lxdh"));
						pa.setPatientName(json.getString("hzxm"));
						pa.setPatientSex((json.getString("sex") == SexEnum.MALE.getValue())?SexEnum.MALE.getCode():SexEnum.FEMALE.getCode());
						patientService.update(pa);
						
						//删除之前绑定的门诊记录
						List<SecHos_Outpatient> hoslist = pa.getOutpatients();
						if (hoslist != null && hoslist.size() != 0) {
							List<String> strlist = new ArrayList<>();
							for (int i = 0; i < hoslist.size(); i++) {
								String rowGuid = hoslist.get(i).getRowGuid();
								strlist.add(rowGuid);
							}
							String[] rowGuids = strlist.toArray(new String[strlist.size()]);
							outpatientService.deleteBatch(rowGuids);
						}
						
						for (int i = 0; i < array.size(); i++) {
							JSONObject obj = array.getJSONObject(i);
							SecHos_Outpatient ho = new SecHos_Outpatient();
							ho.setDelFlag(DelFlagEnum.NDELFLAG.getCode());
							ho.setCreateTime(DateUtil.changeDate(new Date()));
							String uuid = java.util.UUID.randomUUID().toString();
							ho.setRowGuid(uuid);
							ho.setMedicalNumberMZ(obj.getString("blh"));
							ho.setPatidMZ(obj.getString("patid"));
							ho.setPatientRowGuidMZ(pa.getRowGuid());
							outpatientService.save(ho);
						}
						return R.ok("绑定成功").put("data", pa);
					}else{
						//查询失败
						return R.error("未查询到门诊患者");
					}
				}else{
					return R.error("查询门诊患者信息接口异常");
				}
				
			}
			if (PatientEnum.HOSPITALIZED.getCode().equals(params.get("action"))) {
//				//住院患者
				String result = wx_CommonServiceApi.selectHospitalized(params);
				//解析结果
				JSONObject jsonObject = JSONObject.parseObject(result);
				if (jsonObject.containsKey("success")) {
					boolean resu = jsonObject.getBoolean("success");
					if (resu) {
						//查询成功
						JSONArray array = jsonObject.getJSONArray("patinfos");
						//默认取第一个
						JSONObject json = array.getJSONObject(0);
						//直接绑定患者信息
						SecHos_Patient pa = patientService.getPatientByOpenid(params.get("openid"));
						if (pa == null) {
							return R.error("未找到相关用户");
						}
						pa.setPatientAddress(json.getString("lxdz"));
						pa.setPatientBirth(DateUtil.changeStrToDate3(json.getString("birth")));
						pa.setPatientIdcard(json.getString("zjh"));
						pa.setPatientMobile(json.getString("lxdh"));
						pa.setPatientName(json.getString("hzxm"));
						pa.setPatientSex((json.getString("sex") == SexEnum.MALE.getValue())?SexEnum.MALE.getCode():SexEnum.FEMALE.getCode());
						patientService.update(pa);
						
						//删除之前绑定的住院记录
						List<SecHos_hospitalized> hoslist = pa.getHospitalizedList();
						if (hoslist != null && hoslist.size() != 0) {
							List<String> strlist = new ArrayList<>();
							for (int i = 0; i < hoslist.size(); i++) {
								String rowGuid = hoslist.get(i).getRowGuid();
								strlist.add(rowGuid);
							}
							String[] rowGuids = strlist.toArray(new String[strlist.size()]);
							hospitalService.deleteBatch(rowGuids);
						}
						
						for (int i = 0; i < array.size(); i++) {
							JSONObject obj = array.getJSONObject(i);
							SecHos_hospitalized ho = new SecHos_hospitalized();
							ho.setDelFlag(DelFlagEnum.NDELFLAG.getCode());
							ho.setCreateTime(DateUtil.changeDate(new Date()));
							String uuid = java.util.UUID.randomUUID().toString();
							ho.setRowGuid(uuid);
							ho.setHospitalizedStatus(Integer.valueOf(obj.getString("zyzt")));
							ho.setMedicalNumber(obj.getString("blh"));
							ho.setPatid(obj.getString("patid"));
							ho.setPatientRowGuid(pa.getRowGuid());
							hospitalService.save(ho);
						}
						return R.ok("绑定成功").put("data", pa);
					}else{
						//查询失败
						return R.error("未查询到住院患者，住院患者无法建档");
					}
				}else{
					return R.error("查询住院患者信息接口异常");
				}
			}
			return R.error("action参数错误");
		}
		
		/**
		 * 绑定患者
		 * <p>Title: bindingPatient</p>  	
		 * <p>Description: </p>
		 * @author hero  
		 * @return
		 */
		@ApiOperation(value="绑定患者")
		@ResponseBody
		@RequestMapping(value="/bingdingPatient",produces="application/json;charset=utf-8",method=RequestMethod.POST)
		public R bindingPatient(@RequestBody Map<String, String> params){
			checkParams(params, "openid");
			checkParams(params, "hzxm");
			checkParams(params, "sex");
			checkParams(params, "zjh");
			checkParams(params, "birth");
			checkParams(params, "lxdz");
			checkParams(params, "lxdh");
			if (params.get("action") != null) {
				if (PatientEnum.OUTPATIENT.getCode().equals(params.get("action"))) {
					//门诊患者
					String result = wx_CommonServiceApi.bingdingOutPatient(params);
					//解析结果
					JSONObject jsonObject = JSONObject.parseObject(result);
					if (jsonObject.containsKey("success")) {
						boolean res = jsonObject.getBoolean("success");
						if (res) {
							//建档成功
							
							//直接绑定患者信息
							SecHos_Patient pa = patientService.getPatientByOpenid(params.get("openid"));
							if (pa == null) {
								return R.error("未找到相关用户");
							}
							pa.setPatientAddress(params.get("lxdz"));
							pa.setPatientBirth(DateUtil.changeStrToDate3(params.get("birth")));
							pa.setPatientIdcard(params.get("zjh"));
							pa.setPatientMobile(params.get("lxdh"));
							pa.setPatientName(params.get("hzxm"));
							pa.setPatientSex((params.get("sex") == SexEnum.MALE.getValue())?SexEnum.MALE.getCode():SexEnum.FEMALE.getCode());
							patientService.update(pa);
							
							//删除之前绑定的门诊记录
							List<SecHos_Outpatient> hoslist = pa.getOutpatients();
							if (hoslist != null && hoslist.size() != 0) {
								List<String> strlist = new ArrayList<>();
								for (int i = 0; i < hoslist.size(); i++) {
									String rowGuid = hoslist.get(i).getRowGuid();
									strlist.add(rowGuid);
								}
								String[] rowGuids = strlist.toArray(new String[strlist.size()]);
								outpatientService.deleteBatch(rowGuids);
							}
							
							return R.ok("绑定成功").put("data",pa);
						}else{
							//建档失败
							return R.error(jsonObject.getString("message"));
						}
					}else{
						return R.error("门诊建档接口异常");
					}
					
				}
			}
			
			return R.ok();
		}
		
		/**
		 * 预交金充值
		 * <p>Title: advancePay</p>  
		 * <p>Description: </p>
		 * @author hero  
		 * @return
		 */
		@ApiOperation(value="预交金充值")
		@ResponseBody
		@RequestMapping(value="/advancePay",produces="application/json;charset=utf-8",method=RequestMethod.POST)
		public R advancePay(@RequestBody Map<String, String> params){
			checkParams(params, "hzxm");
//			params.put("jzlsh", value);
			//预交金预充值
			String result = wx_CommonServiceApi.beforehandPay(params);
			JSONObject json = JSONObject.parseObject(result);
			if (json.getBoolean("success")) {
				params.put("hisddh", json.getString("hisddh"));
				params.put("yjlsh", json.getString("yjlsh"));
				//预交金充值
				String res = wx_CommonServiceApi.advancePay(params);
				JSONObject obj = JSONObject.parseObject(res);
				if (obj.getBoolean("success")) {
					return R.ok();
				}else{
					return R.error(obj.getString("message"));
				}
			}else{
				return R.error(json.getString("message"));
			}
		}
		
		/**
		 * 验证参数
		 * <p>Title: checkParams</p>  
		 * <p>Description: </p>
		 * @author hero  
		 * @return
		 */
		public void checkParams(Map<String, String> params,String param){
			if (params.get(param) == null || "".equals(params.get(param))) {
				throw new MyException("缺失参数"+param);
			}
		}
	
}	
