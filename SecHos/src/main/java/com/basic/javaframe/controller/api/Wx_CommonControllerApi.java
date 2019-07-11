package com.basic.javaframe.controller.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.basic.javaframe.common.WebSocket.WebSocketServer;
import com.basic.javaframe.common.customclass.PassToken;
import com.basic.javaframe.common.enumresource.DelFlagEnum;
import com.basic.javaframe.common.enumresource.PatientEnum;
import com.basic.javaframe.common.enumresource.PatientStatusEnum;
import com.basic.javaframe.common.enumresource.RecordStatusEnum;
import com.basic.javaframe.common.enumresource.SexEnum;
import com.basic.javaframe.common.exception.MyException;
import com.basic.javaframe.common.utils.AmountUtils;
import com.basic.javaframe.common.utils.DateUtil;
import com.basic.javaframe.common.utils.R;
import com.basic.javaframe.common.utils.XMLUtil;
import com.basic.javaframe.controller.BaseController;
import com.basic.javaframe.entity.Frame_Config;
import com.basic.javaframe.entity.SecHos_Outpatient;
import com.basic.javaframe.entity.SecHos_Patient;
import com.basic.javaframe.entity.SecHos_hospitalized;
import com.basic.javaframe.entity.Sechos_Rechargerecord;
import com.basic.javaframe.service.Frame_ConfigService;
import com.basic.javaframe.service.RedisService;
import com.basic.javaframe.service.SecHos_OutpatientService;
import com.basic.javaframe.service.SecHos_PatientService;
import com.basic.javaframe.service.SecHos_hospitalizedService;
import com.basic.javaframe.service.Sechos_RechargerecordService;
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
	
	Sechos_RechargerecordService rechargerecordService;
	
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
						if (array.size() == 0) {
							return R.error("未查询到对应门诊用户信息");
						}
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
						pa.setPatientStatus(PatientStatusEnum.OUTPATIENT.getCode());
						patientService.update(pa);
						
//						//删除之前绑定的门诊记录
//						List<SecHos_Outpatient> hoslist = pa.getOutpatients();
//						if (hoslist != null && hoslist.size() != 0) {
//							List<String> strlist = new ArrayList<>();
//							for (int i = 0; i < hoslist.size(); i++) {
//								String rowGuid = hoslist.get(i).getRowGuid();
//								strlist.add(rowGuid);
//							}
//							String[] rowGuids = strlist.toArray(new String[strlist.size()]);
//							outpatientService.deleteBatch(rowGuids);
//						}
//						
//						for (int i = 0; i < array.size(); i++) {
//							JSONObject obj = array.getJSONObject(i);
//							SecHos_Outpatient ho = new SecHos_Outpatient();
//							ho.setDelFlag(DelFlagEnum.NDELFLAG.getCode());
//							ho.setCreateTime(DateUtil.changeDate(new Date()));
//							String uuid = java.util.UUID.randomUUID().toString();
//							ho.setRowGuid(uuid);
//							ho.setMedicalNumberMZ(obj.getString("blh"));
//							ho.setPatidMZ(obj.getString("patid"));
//							ho.setPatientRowGuidMZ(pa.getRowGuid());
//							outpatientService.save(ho);
//						}
						
						//门诊只需要查一个医保类的 一个自费类的
						return R.ok("查询门诊成功").put("data", array);
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
						if (array.size() == 0) {
							return R.error("未查询到相应住院患者信息");
						}
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
						pa.setPatientStatus(PatientStatusEnum.HOSPATIENT.getCode());
						patientService.update(pa);
						
//						//删除之前绑定的住院记录
//						List<SecHos_hospitalized> hoslist = pa.getHospitalizedList();
//						if (hoslist != null && hoslist.size() != 0) {
//							List<String> strlist = new ArrayList<>();
//							for (int i = 0; i < hoslist.size(); i++) {
//								String rowGuid = hoslist.get(i).getRowGuid();
//								strlist.add(rowGuid);
//							}
//							String[] rowGuids = strlist.toArray(new String[strlist.size()]);
//							hospitalService.deleteBatch(rowGuids);
//						}
//						
//						for (int i = 0; i < array.size(); i++) {
//							JSONObject obj = array.getJSONObject(i);
//							SecHos_hospitalized ho = new SecHos_hospitalized();
//							ho.setDelFlag(DelFlagEnum.NDELFLAG.getCode());
//							ho.setCreateTime(DateUtil.changeDate(new Date()));
//							String uuid = java.util.UUID.randomUUID().toString();
//							ho.setRowGuid(uuid);
//							ho.setHospitalizedStatus(Integer.valueOf(obj.getString("zyzt")));
//							ho.setMedicalNumber(obj.getString("blh"));
//							ho.setPatid(obj.getString("patid"));
//							ho.setPatientRowGuid(pa.getRowGuid());
//							hospitalService.save(ho);
//						}
						return R.ok("绑定成功").put("data", array);
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
		 * 患者建档
		 * <p>Title: bindingPatient</p>  	
		 * <p>Description: </p>
		 * @author hero  
		 * @return
		 */
		@ApiOperation(value="患者建档")
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
					pa.setPatientStatus(PatientStatusEnum.OUTPATIENT.getCode());
					patientService.update(pa);
					
//					//删除之前绑定的门诊记录
//					List<SecHos_Outpatient> hoslist = pa.getOutpatients();
//					if (hoslist != null && hoslist.size() != 0) {
//						List<String> strlist = new ArrayList<>();
//						for (int i = 0; i < hoslist.size(); i++) {
//							String rowGuid = hoslist.get(i).getRowGuid();
//							strlist.add(rowGuid);
//						}
//						String[] rowGuids = strlist.toArray(new String[strlist.size()]);
//						outpatientService.deleteBatch(rowGuids);
//					}
					
					return R.ok("绑定成功");
				}else{
					//建档失败
					return R.error(jsonObject.getString("message"));
				}
			}else{
				return R.error("门诊建档接口异常");
			}
					
		}
		
		/**
		 * 绑定用户身份证和姓名
		 * <p>Title: bindIdcard</p>  
		 * <p>Description: </p>
		 * @author hero  
		 * @return
		 */
		@RequestMapping(value="/bindInfo",produces="application/json;charset=utf-8",method=RequestMethod.PUT)
		public R bindIdcard(@RequestBody Map<String, String> params){
			checkParams(params, "hzxm");
			checkParams(params, "zjh");
			checkParams(params, "openid");
			SecHos_Patient pa = patientService.getPatientByOpenid(params.get("openid"));
			if (pa == null) {
				return R.error("绑定身份证异常");
			}
			pa.setPatientIdcard(params.get("zjh"));
			pa.setPatientName(params.get("hzxm"));
			patientService.update(pa);
			return R.ok().put("绑定身份信息成功", pa);
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
			checkParams(params, "patid");
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
		 * 查询就诊记录(获取就诊流水号)
		 * <p>Title: getJzlsh</p>  
		 * <p>Description: </p>
		 * @author hero  
		 * @return
		 */
		@ApiOperation(value="获取就诊流水号")
		@ResponseBody
		@RequestMapping(value="/getJzlsh",produces="application/json;charset=utf-8",method=RequestMethod.POST)
		public R getJzlsh(@RequestBody Map<String, String> params){
			checkParams(params, "hzxm");
			checkParams(params, "patid");
			String result = wx_CommonServiceApi.getJzlsh(params);
			JSONObject json = JSONObject.parseObject(result);
			if (json.getBoolean("success")) {
				JSONArray arr = json.getJSONArray("zyjls");
				if (arr.size() == 0) {
					return R.error("未查到相关记录");
				}
				return R.ok().put("data", arr.getJSONObject(0));
			}else{
				return R.error(json.getString("message"));
			}
		}
		
		
		/**
		 * 获取检查报告列表
		 * <p>Title: getReportList</p>  
		 * <p>Description: </p>
		 * @author hero  
		 * @return
		 */
		@ApiOperation(value="获取检查报告列表")
		@ResponseBody
		@RequestMapping(value="/getReportList",produces="application/json;charset=utf-8",method=RequestMethod.POST)
		public R getReportList(@RequestBody Map<String, String> params){
			checkParams(params, "hzxm");
			checkParams(params, "patid");
			checkParams(params, "jzlb");
			checkParams(params, "ksrq");
			checkParams(params, "jsrq");
			String result = wx_CommonServiceApi.getReportListByPatid(params);
			JSONObject json = JSONObject.parseObject(result);
			if (json.getBoolean("success")) {
				return R.ok().put("data", json.getJSONArray("risReports"));
			}else{
				return R.error(json.getString("message"));
			}
		}
		
		/**
		 * 获取详细报告
		 * <p>Title: getReportDetail</p>  
		 * <p>Description: </p>
		 * @author hero  
		 * @return
		 */
		@ApiOperation(value="获取详细报告")
		@ResponseBody
		@RequestMapping(value="/getReportDetail",produces="application/json;charset=utf-8",method=RequestMethod.POST)
		public R getReportDetail(@RequestBody Map<String, String> params){
			checkParams(params, "bgdh");
			checkParams(params, "bglbdm");
			String result = wx_CommonServiceApi.getReportDetail(params);
			JSONObject json = JSONObject.parseObject(result);
			if (json.getBoolean("success")) {
				return R.ok().put("data", json.getJSONArray("risResults"));
			}else{
				return R.error(json.getString("message"));
			}
		}
		
		/**
		 * 获取实验检查报告列表
		 * <p>Title: getLabReportList</p>  
		 * <p>Description: </p>
		 * @author hero  
		 * @return
		 */
		@ApiOperation(value="获取实验检查报告列表")
		@ResponseBody
		@RequestMapping(value="/getLabReportList",produces="application/json;charset=utf-8",method=RequestMethod.POST)
		public R getLabReportList(@RequestBody Map<String, String> params){
			checkParams(params, "hzxm");
			checkParams(params, "patid");
			checkParams(params, "jzlb");
			checkParams(params, "ksrq");
			checkParams(params, "jsrq");
			String result = wx_CommonServiceApi.getLabReportListByPatid(params);
			JSONObject json = JSONObject.parseObject(result);
			if (json.getBoolean("success")) {
				return R.ok().put("data", json.getJSONArray("lisReports"));
			}else{
				return R.error(json.getString("message"));
			}
		}
		
		/**
		 * 获取实验详细报告
		 * <p>Title: getReportDetail</p>  
		 * <p>Description: </p>
		 * @author hero  
		 * @return
		 */
		@ApiOperation(value="获取详细报告")
		@ResponseBody
		@RequestMapping(value="/getLabReportDetail",produces="application/json;charset=utf-8",method=RequestMethod.POST)
		public R getLabReportDetail(@RequestBody Map<String, String> params){
			checkParams(params, "bgdh");
			checkParams(params, "bglbdm");
			String result = wx_CommonServiceApi.getLabReportDetail(params);
			JSONObject json = JSONObject.parseObject(result);
			if (json.getBoolean("success")) {
				return R.ok().put("data", json.getJSONArray("lisResults"));
			}else{
				return R.error(json.getString("message"));
			}
		}
		
		/**
		 * 获取预交金汇总信息
		 * <p>Title: getSummary</p>  
		 * <p>Description: </p>
		 * @author hero  
		 * @return
		 */
		@ApiOperation(value="获取预交金汇总信息")
		@ResponseBody
		@RequestMapping(value="/getSummary",produces="application/json;charset=utf-8",method=RequestMethod.POST)
		public R getSummary(@RequestBody Map<String, String> params){
			checkParams(params, "hzxm");
			checkParams(params, "jzlsh");
			String result = wx_CommonServiceApi.getSummary(params);
			JSONObject json = JSONObject.parseObject(result);
			if (json.getBoolean("success")) {
				JSONArray arr = json.getJSONArray("zyyjjhzs");
				if (arr.size() == 0) {
					return R.error("未查到相关记录");
				}
				return R.ok().put("data", arr.getJSONObject(0));
			}else{
				return R.error(json.getString("message"));
			}
		}
		
		/**
		 * 获取预交金详细信息
		 * <p>Title: getAdvanceDetail</p>  
		 * <p>Description: </p>
		 * @author hero  
		 * @return
		 */
		@ApiOperation(value="获取预交金详细信息")
		@ResponseBody
		@RequestMapping(value="/getAdvanceDetail",produces="application/json;charset=utf-8",method=RequestMethod.POST)
		public R getAdvanceDetail(@RequestBody Map<String, String> params){
			checkParams(params, "jzlsh");
			checkParams(params, "hzxm");
			String result = wx_CommonServiceApi.getAdvanceDetail(params);
			JSONObject json = JSONObject.parseObject(result);
			if (json.getBoolean("success")) {
				JSONArray arr = json.getJSONArray("zyyjjhzs");
				if (arr.size() == 0) {
					return R.error("未查到相关记录");
				}
				return R.ok().put("data", arr.getJSONObject(0));
			}else{
				return R.error(json.getString("message"));
			}
		}

		/**
		 * 获取预约出诊科室信息
		 * <p>Title: getAppointRoomInfo</p>
		 * <p>Description: </p>
		 * @author wzl
		 * @return
		 */
		@ApiOperation(value="获取预约出诊科室信息")
		@ResponseBody
		@RequestMapping(value="/getAppointRoomInfo",produces="application/json;charset=utf-8",method=RequestMethod.POST)
		public R getAppointRoomInfo(@RequestBody Map<String, String> params){
			checkParams(params, "ksrq");
			checkParams(params, "jsrq");
			String result =  wx_CommonServiceApi.getAppointRoomInfo(params);
			JSONObject json = JSONObject.parseObject(result);
			if (json.getBoolean("success")) {
				JSONArray arr = json.getJSONArray("czksxxs");
				if (arr.size() == 0) {
					return R.error("未查到相关记录");
				}
				return R.ok().put("data", arr.getJSONObject(0));
			}else{
				return R.error(json.getString("message"));
			}
		}

	/**
	 * 获取预约出诊医生信息
	 * <p>Title: getAppointDoctorInfo</p>
	 * <p>Description: </p>
	 * @author wzl
	 * @return
	 */
	@ApiOperation(value="获取预约出诊医生信息")
	@ResponseBody
	@RequestMapping(value="/getAppointDoctorInfo",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R getAppointDoctorInfo(@RequestBody Map<String, String> params){
		checkParams(params, "ksrq");
		checkParams(params, "jsrq");
		checkParams(params, "ksdm");
		String result =  wx_CommonServiceApi.getAppointDoctorInfo(params);
		JSONObject json = JSONObject.parseObject(result);
		if (json.getBoolean("success")) {
			JSONArray arr = json.getJSONArray("czysxxs");
			if (arr.size() == 0) {
				return R.error("未查到相关记录");
			}
			return R.ok().put("data", arr.getJSONObject(0));
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
		
		/**
		 * 统一下单接口
		 * <p>Title: placeOrder</p>  
		 * <p>Description: </p>
		 * @author hero  
		 * @return
		 */
		@Transactional
		@PassToken
		@ResponseBody
		@RequestMapping(value="/placeOrder",produces="application/json;charset=utf-8",method=RequestMethod.POST)
		public R placeOrder(@RequestBody Map<String, String> params){
			checkParams(params, "openid");
			checkParams(params, "yjMoney");
			checkParams(params, "patientGuid");
			checkParams(params, "patid");
			checkParams(params, "patientName");
			//单位分
			int money = Integer.valueOf(AmountUtils.changeY2F(params.get("yjMoney"))); 

			//商户订单号
	        String out_trade_no = System.currentTimeMillis()+wx_CommonServiceApi.getRandomStringByLength(7);
			
	        String xml = null;
			try {
				xml = wx_CommonServiceApi.placeOrder(money,out_trade_no,params.get("openid"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			if (xml == "") {
				//如果返回结果为空，说明调用接口异常
				return R.error("调用下单接口失败");
			}
			
			//解析xml
			Map<String, String> map = new HashMap<>();
			map = XMLUtil.xml2Map(xml);
			//对返回结果进行解析
			String return_code = map.get("return_code");
			if (return_code == null || return_code == "") {
				return R.error("return_code为空");
			}
			if ("FAIL".equals(return_code)) {
				return R.error(map.get("return_msg"));
			}
			if ("FAIL".equals(map.get("result_code"))) {
				return R.error("错误代码："+map.get("err_code")+" 错误原因："+map.get("err_code_des"));
			}
			
			
			//数据库创建订单
			Sechos_Rechargerecord order = new Sechos_Rechargerecord();
			String orderGuid = UUID.randomUUID().toString();
			order.setCreateTime(DateUtil.changeDate(new Date()));
			order.setRowGuid(orderGuid);
			order.setDelFlag(DelFlagEnum.NDELFLAG.getCode());
			order.setMerchantNumber(out_trade_no);
			order.setRecordStatus(RecordStatusEnum.READYHANDLE.getCode());
			order.setPatientRowGuid(params.get("patientGuid"));
			order.setPatid(params.get("patid"));
			order.setPatientName(params.get("patientName"));
			//转decimal
			BigDecimal number = new BigDecimal(params.get("yjMoney"));
			order.setPayMoney(number);
			rechargerecordService.save(order);
			
			//准备数据返回
			SortedMap<Object, Object> obj =
	                new TreeMap<Object, Object>();
			obj.put("appId",appid);
			obj.put("timeStamp",System.currentTimeMillis()/1000);
			obj.put("nonceStr",wx_CommonServiceApi.getRandomStringByLength(32));
			obj.put("package","prepay_id="+map.get("prepay_id"));
			obj.put("signType", "MD5");
			obj.put("paySign",wx_CommonServiceApi.createSign(obj));
			return R.ok("下单成功").put("data", obj);
		}
		
		/**
		 * 回调接口
		 * <p>Title: wx_callback</p>  
		 * <p>Description: </p>
		 * @author hero
		 */
		@ResponseBody
		@RequestMapping(value="/wx_callback",produces="application/json;charset=utf-8",method=RequestMethod.POST)
		public String wx_callback(HttpServletRequest request){
			
			//xml转map
			Map<String, String> hashmap = new HashMap<>();
			try {
				hashmap = parseXml(request);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			logger.info("微信回调接口参数》》》"+JSONObject.toJSONString(hashmap));
			
			if ("FAIL".equals(hashmap.get("return_code"))) {
				throw new MyException("微信回调异常，异常信息》》"+hashmap.get("return_msg"));
			}
			
			SortedMap<Object, Object> sm =
	                new TreeMap<Object, Object>(hashmap);
			
			System.out.println("sm>>>>"+JSONObject.toJSONString(sm));
			//验签
			String sign = wx_CommonServiceApi.createSign(sm);
			if (!hashmap.get("sign").equals(sign)) {
				throw new MyException("验签失败，sign比对不同");
			}
			//校验返回的订单金额是否与商户侧的订单金额一致
			int orderMoney = Integer.valueOf(hashmap.get("total_fee"));//单位分
			//根据商户订单号查询该订单金额
			Sechos_Rechargerecord rechargerecord = rechargerecordService.queryByOrderNumber(hashmap.get("out_trade_no"));
			if (rechargerecord == null) {
				throw new MyException("未查询到对应记录");
			}
			if (rechargerecord.getRecordStatus() != RecordStatusEnum.READYHANDLE.getCode()) {
				sm.clear();
				sm.put("return_code", "SUCCESS");
				sm.put("return_msg", "this infomation is already deal");
				
				String xmlWx = XMLUtil.mapToXml(sm, true);
				logger.info("返回给微信的xml为"+xmlWx);
			}
			//int单位分转decimal并和回调金额比较
			int money = Integer.valueOf(AmountUtils.changeY2F(rechargerecord.getPayMoney().toString()));
			if (orderMoney != money) {
				logger.info("回调金额为"+orderMoney+"  数据库已存入订单金额为"+money+"(实际已元为单位存储)");
				//报错，提示金额不一致，做下一步处理...
				sm.clear();
				sm.put("return_code", "FAIL");
				sm.put("return_msg", "金额不一致");
				String xmlwx = XMLUtil.mapToXml(sm, true);
				logger.info("返回给微信的xml为"+xmlwx);
				return xmlwx;
			}
			//调用充值预交金接口
			Map<String, String> yjParams = new HashMap<>();
			yjParams.put("hzxm", rechargerecord.getPatientName());
			yjParams.put("patid", rechargerecord.getPatid());
			//预交金预充值
			String result = wx_CommonServiceApi.beforehandPay(yjParams);
			JSONObject json = JSONObject.parseObject(result);
			if (json.getBoolean("success")) {
				yjParams.put("hisddh", json.getString("hisddh"));
				yjParams.put("yjlsh", json.getString("yjlsh"));
//				yjParams.put("ptlsh", rechargerecord.getMerchantNumber());
				yjParams.put("zfje", rechargerecord.getPayMoney().toString());
				yjParams.put("zflsh",hashmap.get("hashmap"));
				yjParams.put("zfsj", hashmap.get("time_end"));
				
				//预交金充值
				String res = wx_CommonServiceApi.advancePay(yjParams);
				JSONObject obj = JSONObject.parseObject(res);
				if (obj.getBoolean("success")) {
				}else{
					logger.info(obj.getString("message"));
					sm.clear();
					sm.put("return_code", "FAIL");
					sm.put("return_msg", "业务异常");
					String xmlwx = XMLUtil.mapToXml(sm, true);
					logger.info("返回给微信的xml为"+xmlwx);
					return xmlwx;
				}
			}else{
				logger.info(json.getString("message"));
				sm.clear();
				sm.put("return_code", "FAIL");
				sm.put("return_msg", "业务异常");
				String xmlwx = XMLUtil.mapToXml(sm, true);
				logger.info("返回给微信的xml为"+xmlwx);
				return xmlwx;
			}
			
			
			//若前面都通过，更新订单状态并返回正确信息给微信
			Sechos_Rechargerecord hosOrder = new Sechos_Rechargerecord();
			hosOrder.setRowGuid(rechargerecord.getRowGuid());
			hosOrder.setRecordStatus(RecordStatusEnum.ALREADYHANDLE.getCode());
			rechargerecordService.update(hosOrder);
			
			sm.clear();
			sm.put("return_code", "SUCCESS");
			sm.put("return_msg", "OK");
			
			String xmlWx = XMLUtil.mapToXml(sm, true);
			logger.info("返回给微信的xml为"+xmlWx);
			
			return xmlWx;
		}
	
}	
