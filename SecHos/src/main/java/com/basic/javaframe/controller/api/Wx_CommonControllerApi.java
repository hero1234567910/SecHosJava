package com.basic.javaframe.controller.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;

import org.apache.commons.lang.ArrayUtils;
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
import com.basic.javaframe.Thread.InsertOaUsersThread;
import com.basic.javaframe.common.WebSocket.WebSocketServer;
import com.basic.javaframe.common.customclass.PassToken;
import com.basic.javaframe.common.enumresource.DelFlagEnum;
import com.basic.javaframe.common.enumresource.PatientEnum;
import com.basic.javaframe.common.enumresource.PatientStatusEnum;
import com.basic.javaframe.common.enumresource.PayTypeEnum;
import com.basic.javaframe.common.enumresource.RecordStatusEnum;
import com.basic.javaframe.common.enumresource.SexEnum;
import com.basic.javaframe.common.exception.MyException;
import com.basic.javaframe.common.utils.AmountUtils;
import com.basic.javaframe.common.utils.DateUtil;
import com.basic.javaframe.common.utils.R;
import com.basic.javaframe.common.utils.SignUtil;
import com.basic.javaframe.common.utils.XMLUtil;
import com.basic.javaframe.controller.BaseController;
import com.basic.javaframe.entity.Frame_Config;
import com.basic.javaframe.entity.Frame_User;
import com.basic.javaframe.entity.SecHos_Outpatient;
import com.basic.javaframe.entity.SecHos_Patient;
import com.basic.javaframe.entity.SecHos_hospitalized;
import com.basic.javaframe.entity.SechosDrug;
import com.basic.javaframe.entity.Sechos_PopuPerson;
import com.basic.javaframe.entity.Sechos_Rechargerecord;
import com.basic.javaframe.service.Frame_ConfigService;
import com.basic.javaframe.service.Frame_UserService;
import com.basic.javaframe.service.RedisService;
import com.basic.javaframe.service.SecHos_OutpatientService;
import com.basic.javaframe.service.SecHos_PatientService;
import com.basic.javaframe.service.SecHos_hospitalizedService;
import com.basic.javaframe.service.SechosDrugService;
import com.basic.javaframe.service.Sechos_PopuPersonService;
import com.basic.javaframe.service.Sechos_RechargerecordService;
import com.basic.javaframe.service.api.Wx_CommonServiceIApi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value="微信通用接口")
@RestController
@CrossOrigin
@RequestMapping(value="wx/common")
public class Wx_CommonControllerApi extends BaseController{
	
	@Value(value = "${wx.api.appid}")
	public String appid;
	
	@Value(value = "${wn.ksrq}")
	public String ksrq;
	
	@Value(value = "${wn.jsrq}")
	public String jsrq;
	
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
	
	@Autowired
	Sechos_RechargerecordService rechargerecordService;
	
	@Autowired
	SechosDrugService sechosDrugService;
	
	@Autowired
	Frame_UserService frame_UserService;
	
	@Autowired
	Sechos_PopuPersonService popuPersonService;
	
	/**
	 * 二院服务器配置
	 * <p>Title: zjgService</p>  
	 * <p>Description: </p>
	 * @author hero  
	 * @return
	 */
	@PassToken
	@RequestMapping(value="/zjgService",method=RequestMethod.GET)
	public String zjgService(HttpServletRequest request){
		
		logger.info("推送接口");
		InputStream ins = null;
		String remess = "";
		try {
			ins = request.getInputStream();
	        byte[] rebyte = XMLUtil.readStream(ins);  
	        remess = new String(rebyte);  
	        
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if (remess.equals("")) {
			return "";
		}
		
		JSONObject jsonObject = JSONObject.parseObject(remess);
		
		//获取来自用户的用户名
		String FromUserName = jsonObject.getString("FromUserName");
		//获取来自用户的创建时间
		String CreateTime = jsonObject.getString("CreateTime");
		//事件
		String event = jsonObject.getString("Event");
		if ("subscribe".equals(event)) {
			//订阅事件
			String ev = jsonObject.getString("EventKey");
			if (ev != null && ev.startsWith("qrscene_")) {
				//以qrscene_开头说明是新用户关注
				//获取openId，并根据openid获取用户信息(注意是否为第一次关注)
				
				//获取推广人guid
				String promotersGuid = ev.substring(8);
				
				String fromUserOpenId = jsonObject.getString("FromUserName");
				SecHos_Patient patient = patientService.getPatientByOpenid(fromUserOpenId);
				if (patient == null) {
					//说明该用户从未登录过平台 直接登记+1
					
					Sechos_PopuPerson person = new Sechos_PopuPerson();
		        	person.setRowGuid(UUID.randomUUID().toString());
		        	person.setCreateTime(new Date());
		        	person.setPopuPersonOpenId(fromUserOpenId);
		        	
		        	//推广人姓名和guid
		        	person.setPromotersGuid(promotersGuid);
		        	popuPersonService.save(person);
					
				}else{
					//说明之前有过登录
					Sechos_PopuPerson person = popuPersonService.getByPopuPersonOpenId(fromUserOpenId);
					if (person == null) {
						//说明没有登记过 直接登记+1
						Sechos_PopuPerson p = new Sechos_PopuPerson();
			        	p.setRowGuid(UUID.randomUUID().toString());
			        	p.setCreateTime(new Date());
			        	p.setPopuPersonOpenId(fromUserOpenId);
			        	
			        	//推广人姓名和guid
			        	p.setPromotersGuid(promotersGuid);
			        	popuPersonService.save(p);
						
					}
					
					
				}
				
				
			}
		}
		
		
		// 微信加密签名
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 随机字符串
        String echostr = request.getParameter("echostr");
        
        System.out.println(signature+"   "+timestamp);
        
        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        if (SignUtil.checkSignature(signature, timestamp, nonce)) {
            return echostr;
        }else{
        	return "";
        }
	}
	
	
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
		JSONObject jsonobj = wx_CommonServiceApi.code2Token(code);
		
		JSONObject jsonObject = JSONObject.parseObject(jsonobj.getString("resultUser"));
		if (jsonObject.containsKey("errcode")) {
			String errcode = jsonObject.getString("errcode");
			return R.error("获取网页授权用户信息异常,errcode为"+errcode).put("data", errcode);
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
			pa.setAccessToken(jsonobj.getString("access_token"));
			pa.setRefreshToken(jsonobj.getString("refresh_token"));
			patientService.save(pa);
			return R.ok().put("data", pa);
		}
		pa.setAccessToken(jsonobj.getString("access_token"));
		pa.setRefreshToken(jsonobj.getString("refresh_token"));
		logger.info(pa.toString());
		return R.ok().put("data", pa);	
	}
	
	/**
	 * 获取网页授权token，openid
	 * <p>Title: code2Token</p>  
	 * <p>Description: </p>
	 * @author hero  
	 * @param code
	 * @return
	 */
	@PassToken
	@RequestMapping(value="/code2TokenDoctor",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	@ResponseBody
	public R code2TokenDoctor(@RequestBody String code,HttpServletRequest request){
		System.out.println(code);
		JSONObject jsonobj = wx_CommonServiceApi.code2Token(code);
		
		JSONObject jsonObject = JSONObject.parseObject(jsonobj.getString("resultUser"));
		if (jsonObject.containsKey("errcode")) {
			String errcode = jsonObject.getString("errcode");
			return R.error("获取网页授权用户信息异常,errcode为"+errcode).put("data", errcode);
		}
		//获取openid,微信昵称，头像
		String openid = jsonObject.getString("openid");
		String nickname = jsonObject.getString("nickname");
		String headimgurl = jsonObject.getString("headimgurl");
		
		HttpSession session = request.getSession();
		session.setAttribute("accessToken", jsonobj.getString("access_token"));
		session.setAttribute("refreshToken", jsonobj.getString("refresh_token"));
		
		return R.ok().put("data", jsonobj.getString("access_token"));
	}
	
//	/**
//	 * 获取二维码
//	 * <p>Title: getDoctorPic</p>  
//	 * <p>Description: </p>
//	 * @author hero  
//	 * @return
//	 */
//	@PassToken
//	@RequestMapping(value="/getDoctorPic",produces="application/json;charset=utf-8",method=RequestMethod.GET)
//	@ResponseBody
//	public R getDoctorPic(HttpServletRequest request){
//		
//		HttpSession session = request.getSession();
//		String accessToken = (String) session.getAttribute("accessToken");
//		String refreshToken = (String) session.getAttribute("refreshToken");
//		
//		String res = wx_CommonServiceApi.getDoctorPic(accessToken);
//		JSONObject jsonObject = JSONObject.parseObject(res);
//		
//		if(jsonObject.containsKey("errcode")){
//			//微信出错
//			logger.info(jsonObject.toJSONString());
//			String errcode = jsonObject.getString("errcode");
//			if (errcode.equals("40001")) {
//				//token过期 刷新token再试
//				Map<String, String> params = new HashMap<String, String>();
//				params.put("refresh_token", refreshToken);
//				String result = wx_CommonServiceApi.refreshToken(params);
//				JSONObject json = JSONObject.parseObject(result);
//				if (json.containsKey("errcode")) {
//					String err = json.getString("errcode");
//					if ("42002".equals(err)) {
//						//重新获取token
////						JSONObject jsonobj = wx_CommonServiceApi.code2Token(params.get("code"));
////						logger.info("重新获取token成功"+jsonobj.toJSONString());
////						return R.ok().put("data", jsonobj);
//					}
//					
//				}else{
//					session.setAttribute("accessToken", json.getString("access_token"));
//					session.setAttribute("refreshToken", json.getString("refresh_token"));
//					//再次获取
//					String r = wx_CommonServiceApi.getDoctorPic((String)session.getAttribute("accessToken"));
//					JSONObject j = JSONObject.parseObject(r);
//					return R.ok().put("data", j.getString("url"));
//				}
//			}else{
//				return R.error("系统出错,请联系管理员");
//			}
//		}
//		
//		
//		return R.ok().put("data", jsonObject.getString("url"));
//	}
	
	/**
	 * 获取二维码
	 * <p>Title: getDoctorPic</p>  
	 * <p>Description: </p>
	 * @author hero  
	 * @return
	 */
	@PassToken
	@RequestMapping(value="/getDoctorPic",produces="application/json;charset=utf-8",method=RequestMethod.GET)
	@ResponseBody
	public R getDoctorPic(HttpServletRequest request){
		//获取token
		
		String resObj = wx_CommonServiceApi.getToken();
		JSONObject json = JSONObject.parseObject(resObj);
		
		if (json.containsKey("errcode")) {
			return R.error("系统异常");
		}
		HttpSession session = request.getSession();
		session.setAttribute("wxToken", json.getString("access_token"));
		session.setAttribute("wxTokenExpires", json.getString("expires_in"));
		
		String accessToken = (String) session.getAttribute("wxToken");
		String popuPersonGuid = request.getParameter("popuPersonGuid");
		
		String res = wx_CommonServiceApi.getDoctorPic(accessToken,popuPersonGuid);
		JSONObject jsonObject = JSONObject.parseObject(res);
		
		if(jsonObject.containsKey("errcode")){
			//微信出错
			logger.info(jsonObject.toJSONString());
			String errcode = jsonObject.getString("errcode");
			return R.error("系统异常");
		}
		
		//根据ticket换取二维码
		String rString = wx_CommonServiceApi.getByTicket(jsonObject.getString("ticket"));
		System.out.println("根据ticket换取结果为"+rString);
		return R.ok().put("data", rString);
	}
	
	/**
	 * 获取用户信息
	 * <p>Title: getUserByToken</p>  
	 * <p>Description: </p>
	 * @author hero  
	 * @param params
	 * @return
	 */
	@PassToken
	@RequestMapping(value="/getUserByToken",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	@ResponseBody
	public R getUserByToken(@RequestBody Map<String, String> params){
		checkParams(params, "openid");
		checkParams(params, "access_token");
		checkParams(params, "refresh_token");
		String result = wx_CommonServiceApi.getWxUserByToken(params);
		JSONObject jsonObject = JSONObject.parseObject(result);
		if (jsonObject.containsKey("errcode")) {
			String errcode = jsonObject.getString("errcode");
			if ("40001".equals(errcode)) {
				//重新获取
				JSONObject jsonobj = wx_CommonServiceApi.code2Token(params.get("code"));
				JSONObject Object = JSONObject.parseObject(jsonobj.getString("resultUser"));
				logger.info("4001》》》 重新获取token成功"+jsonobj.toJSONString());
				//
				//获取openid,微信昵称，头像
				String openid = Object.getString("openid");
				String nickname = Object.getString("nickname");
				String headimgurl = Object.getString("headimgurl");
				
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
					pa.setAccessToken(jsonobj.getString("access_token"));
					pa.setRefreshToken(jsonobj.getString("refresh_token"));
					patientService.save(pa);
					return R.ok().put("data", pa);
				}
				pa.setAccessToken(jsonobj.getString("access_token"));
				pa.setRefreshToken(jsonobj.getString("refresh_token"));
				logger.info(pa.toString());
				return R.ok().put("data", pa);	
			}
			return R.error("获取网页授权用户信息异常,errcode为"+errcode).put("data", errcode);
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
			pa.setAccessToken(params.get("access_token"));
			pa.setRefreshToken(params.get("refresh_token"));
			patientService.save(pa);
			return R.ok().put("data", pa);
		}
		pa.setAccessToken(params.get("access_token"));
		pa.setRefreshToken(params.get("refresh_token"));
		return R.ok().put("data", pa);	
	}
	
	/**
	 * 刷新token
	 * <p>Title: refreshToken</p>  
	 * <p>Description: </p>
	 * @author hero  
	 * @param params
	 * @return
	 */
	@PassToken
	@RequestMapping(value="/refreshToken",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	@ResponseBody
	public R refreshToken(@RequestBody Map<String, String> params){
		checkParams(params, "refresh_token");
		String result = wx_CommonServiceApi.refreshToken(params);
		JSONObject jsonObject = JSONObject.parseObject(result);
		if (jsonObject.containsKey("errcode")) {
			String errcode = jsonObject.getString("errcode");
			if ("42002".equals(errcode)) {
				//重新获取token
				JSONObject jsonobj = wx_CommonServiceApi.code2Token(params.get("code"));
				logger.info("重新获取token成功"+jsonobj.toJSONString());
				return R.ok().put("data", jsonobj);
			}
		}
		return R.ok().put("data",jsonObject);
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
						String lxdh = "";
						for (int i = 0; i < array.size(); i++) {
							JSONObject j = array.getJSONObject(i);
							if (!"".equals(j.getString("lxdh"))) {
								lxdh = j.getString("lxdh");
							}
						}
						//直接绑定患者信息
						SecHos_Patient pa = patientService.getPatientByOpenid(params.get("openid"));
						if (pa == null) {
							return R.error("未找到相关用户");
						}
						pa.setPatientAddress(json.getString("lxdz"));
						pa.setPatientBirth(DateUtil.changeStrToDate3(json.getString("birth")));
						pa.setPatientIdcard(json.getString("zjh"));
						pa.setPatientMobile(lxdh);
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
						if (array.size() == 0 ||array.getJSONObject(0).isEmpty()) {
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
					String str = jsonObject.getString("message");
					String str2=str.substring(str.lastIndexOf("|")+1);
					return R.error(str2);
				}
			}else{
				return R.error("门诊建档接口异常");
			}
					
		}
		
		/**
		 * 更新患者信息
		 * <p>Title: bindingPatient</p>  	
		 * <p>Description: </p>
		 * @author hero  
		 * @return
		 */
		@ApiOperation(value="患者建档")
		@ResponseBody
		@RequestMapping(value="/updatePatient",produces="application/json;charset=utf-8",method=RequestMethod.POST)
		public R updatePatient(@RequestBody Map<String, String> params){
			checkParams(params, "patienGuid");
			checkParams(params, "hzxm");
			checkParams(params, "patid");
			checkParams(params, "lxdh");
			
			//门诊患者
			String result = wx_CommonServiceApi.updatePatient(params);
			//解析结果
			JSONObject jsonObject = JSONObject.parseObject(result);
			if (jsonObject.containsKey("success")) {
				boolean res = jsonObject.getBoolean("success");
				if (res) {
					//更新成功
					
					//直接绑定患者信息
					SecHos_Patient pa = new SecHos_Patient();
					pa.setRowGuid(params.get("patienGuid"));
					pa.setPatientMobile(params.get("lxdh"));
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
					return R.error("更新用户信息异常");
				}
			}else{
				return R.error("更新错误");
			}
					
		}
		
		/**
		 * 绑定用户身份证和姓名
		 * <p>Title: bindIdcard</p>  
		 * <p>Description: </p>
		 * @author hero  
		 * @return
		 */
		@RequestMapping(value="/bindInfo",produces="application/json;charset=utf-8",method=RequestMethod.POST)
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
					String str = obj.getString("message");
					String str2=str.substring(str.lastIndexOf("|")+1);
					return R.error(str2);
				}
			}else{
				String str = json.getString("message");
				String str2=str.substring(str.lastIndexOf("|")+1);
				return R.error(str2);
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
				String str = json.getString("message");
				String str2=str.substring(str.lastIndexOf("|")+1);
				return R.error(str2);
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
//			checkParams(params, "ksrq");
//			checkParams(params, "jsrq");
			params.put("ksrq", ksrq);
			params.put("jsrq", jsrq);
			String result = wx_CommonServiceApi.getReportListByPatid(params);
			JSONObject json = JSONObject.parseObject(result);
			if (json.getBoolean("success")) {
				return R.ok().put("data", json.getJSONArray("risReports"));
			}else{
				String str = json.getString("message");
				String str2=str.substring(str.lastIndexOf("|")+1);
				return R.error(str2);
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
				String str = json.getString("message");
				String str2=str.substring(str.lastIndexOf("|")+1);
				return R.error(str2);
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
//			checkParams(params, "ksrq");
//			checkParams(params, "jsrq");
			params.put("ksrq", ksrq);
			params.put("jsrq", jsrq);
			String result = wx_CommonServiceApi.getLabReportListByPatid(params);
			JSONObject json = JSONObject.parseObject(result);
			if (json.getBoolean("success")) {
				return R.ok().put("data", json.getJSONArray("lisReports"));
			}else{
				String str = json.getString("message");
				String str2=str.substring(str.lastIndexOf("|")+1);
				return R.error(str2);
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
				String str = json.getString("message");
				String str2=str.substring(str.lastIndexOf("|")+1);
				return R.error(str2);
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
				String str = json.getString("message");
				String str2=str.substring(str.lastIndexOf("|")+1);
				return R.error(str2);
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
				JSONArray arr = json.getJSONArray("zyyjjmxs");
				if (arr.size() == 0) {
					return R.error("未查到相关记录");
				}
				return R.ok().put("data", arr);
			}else{
				String str = json.getString("message");
				String str2=str.substring(str.lastIndexOf("|")+1);
				return R.error(str2);
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
				Map<String, String> m = new HashMap<>();
				for(int i=0;i<arr.size();i++){
					JSONObject arrItem = arr.getJSONObject(i);
					m.put(arrItem.getString("yjksdm"), arrItem.getString("yjksdm"));
				}
				//去重
				String[] starr = m.keySet().toArray(new String[0]);
				
				if (starr.length == 0) {
					return R.error("未查到相关记录");
				}
				JSONArray newArr = new JSONArray();
				
				for (int i = 0; i < starr.length; i++) {
					JSONObject da = new JSONObject();
					JSONArray children = new JSONArray();
					for (int j = 0; j < arr.size(); j++) {
						JSONObject arrItem = arr.getJSONObject(j);
						if ("儿科".equals(arrItem.getString("yjksmc"))) {
							continue;
						}
						if (starr[i].equals(arrItem.getString("yjksdm"))) {
							JSONObject ch = new JSONObject();
							da.put("ksdm", arrItem.getString("yjksdm"));
							da.put("ksmc", arrItem.getString("yjksmc"));
							ch.put("ksmc", arrItem.getString("ksmc"));
							ch.put("ksdm", arrItem.getString("ksdm"));
							ch.put("czlx", arrItem.getString("czlx"));
							children.add(ch);
						}
					}
					da.put("children", children);
					da.put("rowGuid", UUID.randomUUID().toString());
					newArr.add(da);
				}
				
				return R.ok().put("data", newArr);
			}else{
				String str = json.getString("message");
				String str2=str.substring(str.lastIndexOf("|")+1);
				return R.error(str2);
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
			return R.ok().put("data", arr);
		}else{
			String str = json.getString("message");
			String str2=str.substring(str.lastIndexOf("|")+1);
			return R.error(str2);
		}
	}

	/**
	 * 获取预约医生号源信息
	 * <p>Title: getAppointDoctorYNo</p>
	 * <p>Description: </p>
	 * @author wzl
	 * @return
	 */
	@ApiOperation(value="获取预约医生号源信息")
	@ResponseBody
	@RequestMapping(value="/getAppointDoctorYNo",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R getAppointDoctorYNo(@RequestBody Map<String, String> params){
		checkParams(params, "ksrq");
		checkParams(params, "jsrq");
		checkParams(params, "ysdm");
		String result =  wx_CommonServiceApi.getAppointDoctorYNo(params);
		JSONObject json = JSONObject.parseObject(result);
		if (json.getBoolean("success")) {
			JSONArray arr = json.getJSONArray("yyyshys");
			if (arr.size() == 0) {
				return R.error("未查到相关记录");
			}
			return R.ok().put("data", arr);
		}else{
			String str = json.getString("message");
			String str2=str.substring(str.lastIndexOf("|")+1);
			return R.error(str2);
		}
	}
	
	/**
	 * 取消预约登记接口
	 * <p>Title: getAppointDoctorXNo</p>
	 * <p>Description: </p>
	 * @author wzl
	 * @return
	 */
	@ApiOperation(value="取消预约登记接口")
	@ResponseBody
	@RequestMapping(value="/cancelSubmit",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R cancelSubmit(@RequestBody Map<String, String> params){
		checkParams(params, "yyxh");
		checkParams(params, "patid");
		String result =  wx_CommonServiceApi.cancelSubmit(params);
		JSONObject json = JSONObject.parseObject(result);
		if (json.getBoolean("success")) {
			return R.ok("取消成功");
		}else{
			String str = json.getString("message");
			String str2=str.substring(str.lastIndexOf("|")+1);
			return R.error(str2);
		}
	}
	
	
	/**
	 * 获取预约医生号序信息
	 * <p>Title: getAppointDoctorXNo</p>
	 * <p>Description: </p>
	 * @author wzl
	 * @return
	 */
	@ApiOperation(value="获取预约医生号序信息")
	@ResponseBody
	@RequestMapping(value="/getAppointDoctorXNo",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R getAppointDoctorXNo(@RequestBody Map<String, String> params){
		checkParams(params, "pbxh");
		String result =  wx_CommonServiceApi.getAppointDoctorXNo(params);
		JSONObject json = JSONObject.parseObject(result);
		if (json.getBoolean("success")) {
			JSONArray arr = json.getJSONArray("yyyshyhxs");
			if (arr.size() == 0) {
				return R.error("未查到相关记录");
			}
			return R.ok().put("data", arr.getJSONObject(0));
		}else{
			String str = json.getString("message");
			String str2=str.substring(str.lastIndexOf("|")+1);
			return R.error(str2);
		}
	}

	/**
	 * 获取预约科室号源信息
	 * <p>Title: getAppointDepartmentYNo</p>
	 * <p>Description: </p>
	 * @author wzl
	 * @return
	 */
	@ApiOperation(value="获取预约科室号源息")
	@ResponseBody
	@RequestMapping(value="/getAppointDepartmentYNo",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R getAppointDepartmentYNo(@RequestBody Map<String, String> params){
		checkParams(params, "ksrq");
		checkParams(params, "jsrq");
		checkParams(params, "ksdm");
		String result =  wx_CommonServiceApi.getAppointDepartmentYNo(params);
		JSONObject json = JSONObject.parseObject(result);
		if (json.getBoolean("success")) {
			JSONArray arr = json.getJSONArray("his_YYKSHYs");
			if (arr.size() == 0) {
				return R.error("未查到相关记录");
			}
			return R.ok().put("data", arr);
		}else{
			String str = json.getString("message");
			String str2=str.substring(str.lastIndexOf("|")+1);
			return R.error(str2);
		}
	}

	/**
	 * 获取预约科室号序信息
	 * <p>Title: getAppointDepartmentXNo</p>
	 * <p>Description: </p>
	 * @author wzl
	 * @return
	 */
	@ApiOperation(value="获取预约科室号序息")
	@ResponseBody
	@RequestMapping(value="/getAppointDepartmentXNo",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R getAppointDepartmentXNo(@RequestBody Map<String, String> params){
		checkParams(params, "pbxh");
		String result =  wx_CommonServiceApi.getAppointDepartmentXNo(params);
		JSONObject json = JSONObject.parseObject(result);
		if (json.getBoolean("success")) {
			JSONArray arr = json.getJSONArray("yykshyhxs");
			if (arr.size() == 0) {
				return R.error("未查到相关记录");
			}
			return R.ok().put("data", arr.getJSONObject(0));
		}else{
			String str = json.getString("message");
			String str2=str.substring(str.lastIndexOf("|")+1);
			return R.error(str2);
		}
	}

	/**
	 * 门诊预约登记
	 * <p>Title: getOutpatientAppointmentReg</p>
	 * <p>Description: </p>
	 * @author wzl
	 * @return
	 */
	@ApiOperation(value="门诊预约登记")
	@ResponseBody
	@RequestMapping(value="/getOutpatientAppointmentReg",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R getOutpatientAppointmentReg(@RequestBody Map<String, String> params){
		checkParams(params, "patid");
		checkParams(params, "pbxh");
		checkParams(params, "yyhx");
		String result =  wx_CommonServiceApi.getOutpatientAppointmentReg(params);
		JSONObject json = JSONObject.parseObject(result);
		if (json.getBoolean("success")) {
			return R.ok().put("data",json);
		}else{
			String str = json.getString("message");
			String str2=str.substring(str.lastIndexOf("|")+1);
			return R.error(str2);
		}
	}

	/**
	 * 当班出诊科室查询
	 * <p>Title: getDepartmentOnDuty</p>
	 * <p>Description: </p>
	 * @author wzl
	 * @return
	 */
	@ApiOperation(value="当班出诊科室查询")
	@ResponseBody
	@RequestMapping(value="/getDepartmentOnDuty",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R getDepartmentOnDuty(@RequestBody Map<String, String> params){
		String result =  wx_CommonServiceApi.getDepartmentOnDuty(params);
		JSONObject json = JSONObject.parseObject(result);
		if (json.getBoolean("success")) {
			JSONArray arr = json.getJSONArray("czdbksxxs");
			if (arr.size() == 0) {
				return R.error("未查到相关记录");
			}
			Map<String,String> m = new HashMap<>();
			for(int i=0;i<arr.size();i++){
				JSONObject arrItem = arr.getJSONObject(i);
				m.put(arrItem.getString("yjksdm"),arrItem.getString("yjksdm"));
			}
			String[] starr = m.keySet().toArray(new String[0]);
			if(starr.length == 0){
				return R.error("未查找到相关记录");
			}
			JSONArray newArr = new JSONArray();

			for(int i = 0;i<starr.length;i++){
				JSONObject da = new JSONObject();
				JSONArray children = new JSONArray();
				for(int j= 0;j<arr.size();j++){
					JSONObject arrItem = arr.getJSONObject(j);
					if ("儿科".equals(arrItem.getString("yjksmc"))) {
						continue;
					}
					if(starr[i].equals(arrItem.getString("yjksdm"))){
						JSONObject ch = new JSONObject();
						da.put("ksdm",arrItem.getString("yjksdm"));
						da.put("ksmc", arrItem.getString("yjksmc"));
						ch.put("ksmc", arrItem.getString("ksmc"));
						ch.put("ksdm", arrItem.getString("ksdm"));
						ch.put("czlx", arrItem.getString("czlx"));
						children.add(ch);
					}
				}
				da.put("children",children);
				newArr.add(da);
			}
			return R.ok().put("data", newArr);
		}else{
			String str = json.getString("message");
			String str2=str.substring(str.lastIndexOf("|")+1);
			return R.error(str2);
		}
	}

	/**
	 * 当班科室号源信息查询
	 * <p>Title: getDepartmentOnDutyYNo</p>
	 * <p>Description: </p>
	 * @author wzl
	 * @return
	 */
	@ApiOperation(value="当班科室号源信息查询")
	@ResponseBody
	@RequestMapping(value="/getDepartmentOnDutyYNo",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R getDepartmentOnDutyYNo(@RequestBody Map<String, String> params){
		checkParams(params, "ksdm");
		String result =  wx_CommonServiceApi.getDepartmentOnDutyYNo(params);
		JSONObject json = JSONObject.parseObject(result);
		if (json.getBoolean("success")) {
			JSONArray arr = json.getJSONArray("dbkshy");
			if (arr.size() == 0) {
				return R.error("未查到相关记录");
			}
			return R.ok().put("data", arr);
		}else{
			String str = json.getString("message");
			String str2=str.substring(str.lastIndexOf("|")+1);
			return R.error(str2);
		}
	}

	/**
	 * 当班医生信息查询
	 * <p>Title: getDoctorOnDuty</p>
	 * <p>Description: </p>
	 * @author wzl
	 * @return
	 */
	@ApiOperation(value="当班医生信息查询")
	@ResponseBody
	@RequestMapping(value="/getDoctorOnDuty",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R getDoctorOnDuty(@RequestBody Map<String, String> params){
		checkParams(params, "ksdm");
		String result =  wx_CommonServiceApi.getDoctorOnDuty(params);
		JSONObject json = JSONObject.parseObject(result);
		if (json.getBoolean("success")) {
			JSONArray arr = json.getJSONArray("dbyss");
			if (arr.size() == 0) {
				return R.error("未查到相关记录");
			}
			return R.ok().put("data", arr);
		}else{
			String str = json.getString("message");
			String str2=str.substring(str.lastIndexOf("|")+1);
			return R.error(str2);
		}
	}

	/**
	 * 当班医生号源信息查询
	 * <p>Title: getDoctorOnDutyYNo</p>
	 * <p>Description: </p>
	 * @author wzl
	 * @return
	 */
	@ApiOperation(value="当班医生号源信息查询")
	@ResponseBody
	@RequestMapping(value="/getDoctorOnDutyYNo",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R getDoctorOnDutyYNo(@RequestBody Map<String, String> params){
		checkParams(params, "ysdm");
		String result =  wx_CommonServiceApi.getDoctorOnDutyYNo(params);
		JSONObject json = JSONObject.parseObject(result);
		if (json.getBoolean("success")) {
			JSONArray arr = json.getJSONArray("dbyshys");
			if (arr.size() == 0) {
				return R.error("未查到相关记录");
			}
			return R.ok().put("data", arr);
		}else{
			String str = json.getString("message");
			String str2=str.substring(str.lastIndexOf("|")+1);
			return R.error(str2);
		}
	}




	/**
	 * 获取体检列表
	 * <p>Title: getMedicalReportList</p>
	 * <p>Description: </p>
	 * @author wzl
	 * @return
	 */
	@ApiOperation(value="获取体检列表")
	@ResponseBody
	@RequestMapping(value="/getMedicalReportList",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R getMedicalReportList(@RequestBody Map<String, String> params){
		checkParams(params, "idc");
		String result =  wx_CommonServiceApi.getMedicalReportList(params);
		JSONObject json = JSONObject.parseObject(result);
		if (json.getInteger("code") == 1) {
			JSONArray arr = json.getJSONArray("data");
			return R.ok().put("data", arr);
		}else{
			return R.error(json.getString("msg"));
		}
	}



	/**
	 * 获取体检详细信息
	 * <p>Title: getMedicalReportInfo</p>
	 * <p>Description: </p>
	 * @author wzl
	 * @return
	 */
	@ApiOperation(value="获取体检详细信息")
	@ResponseBody
	@RequestMapping(value="/getMedicalReportInfo",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R getMedicalReportInfo(@RequestBody Map<String, String> params){
		checkParams(params, "bhkcode");
		String result =  wx_CommonServiceApi.getMedicalReportInfo(params);
		JSONObject json = JSONObject.parseObject(result);
		
		if (json.getInteger("code") == 1) {
			JSONArray arr = json.getJSONArray("data");
			return R.ok().put("data", arr.getJSONObject(0));
		}else{
			return R.error(json.getString("msg"));
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
		 * 住院预交金接口
		 * <p>Title: beforePay</p>  
		 * <p>Description: </p>
		 * @author hero  
		 * @param params
		 * @return
		 */
		@ResponseBody
		@RequestMapping(value="/beforePay",produces="application/json;charset=utf-8",method=RequestMethod.POST)
		public R beforePay(@RequestBody Map<String, String> params){
			//调用充值预交金接口
			checkParams(params,"hzxm");
			checkParams(params, "patid");
			//预交金预充值
			String result = wx_CommonServiceApi.beforehandPay(params);
			JSONObject json = JSONObject.parseObject(result);
			if (json.getBoolean("success")) {
//				yjParams.put("hisddh", json.getString("hisddh"));
//				yjParams.put("yjlsh", json.getString("yjlsh"));
//				yjParams.put("zfje", rechargerecord.getPayMoney().toString());
//				yjParams.put("zflsh",hashmap.get("transaction_id"));
//				yjParams.put("zfsj", hashmap.get("time_end"));
//				
//				//预交金充值
//				String res = wx_CommonServiceApi.advancePay(yjParams);
//				JSONObject obj = JSONObject.parseObject(res);
//				if (obj.getBoolean("success")) {
//				}else{
//					logger.info(obj.getString("message"));
//					sm.clear();
//					sm.put("return_code", "FAIL");
//					sm.put("return_msg", "业务异常");
//					String xmlwx = XMLUtil.mapToXml(sm, true);
//					logger.info("返回给微信的xml为"+xmlwx);
//					return xmlwx;
//				}
				return R.ok().put("data",json);
			}else{
				String str = json.getString("message");
				String str2=str.substring(str.lastIndexOf("|")+1);
				return R.error(str2);
			}
		}
		
		/**
		 * 卫宁统一下单接口
		 * <p>Title: placeOrderByWN</p>  
		 * <p>Description: </p>
		 * @author hero  
		 * @param params
		 * @return
		 */
		@Transactional
		@PassToken
		@ResponseBody
		@RequestMapping(value="/placeOrderByWN",produces="application/json;charset=utf-8",method=RequestMethod.POST)
		public R placeOrderByWN(@RequestBody Map<String, String> params){
			//商户编码
			params.put("merchantId", mch_id);
			//操作员编号
			params.put("czyh", "weixin");
			//收费点编号
			params.put("storeId", "weixin");
			//请求唯一码
			params.put("requestId",UUID.randomUUID().toString());
			//请求时间戳
			SimpleDateFormat si = new SimpleDateFormat("yyyyMMddhhmmss");
			params.put("timestamp",si.format(new Date()));
			//医院代码
			params.put("yydm", "055159");
			
			
	        //支付渠道限制
	        params.put("limitPay", "0");
			params.put("hzxm", params.get("patientName"));
			params.put("paytype", "998");
			params.put("outerOrderNo", params.get("sjh"));
			params.put("subject", "pay");
			params.put("timeout_express", "3");
			params.put("callBackUrl", "https://p.zjgwsjk.com/2ysechos/pay.html#/noticeSuccess");
			params.put("notifyUrl", "https://p.zjgwsjk.com/2ysechos/wx/common/wn_callback");
			
			//预交金接口
			if ("yj".equals(params.get("action"))) {
				checkParams(params, "openid");
				checkParams(params, "yjMoney");
				checkParams(params, "patientGuid");
				checkParams(params, "patid");
				checkParams(params, "patientName");
				params.put("ysje", params.get("yjMoney"));
				 //订单类型
		        params.put("ddlx", "4");
		        //订单来源
		        params.put("ddly", "2");
		        
				//单位分
				int money = Integer.valueOf(AmountUtils.changeY2F(params.get("yjMoney"))); 

				//商户订单号
		        String out_trade_no = System.currentTimeMillis()+wx_CommonServiceApi.getRandomStringByLength(7);
				
				String result = "";
				try {
					result = wx_CommonServiceApi.placeOrderByWN(money,out_trade_no,params.get("openid"),params);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				if (result == "") {
					//如果返回结果为空，说明调用接口异常
					return R.error("调用下单接口失败");
				}
				
				JSONObject js = JSONObject.parseObject(result);
				//对返回结果进行解析
				if (js.getBoolean("success")) {
					
				}else{
					return R.error("下单失败");
				}
				
				
				//数据库创建订单
				Sechos_Rechargerecord order = new Sechos_Rechargerecord();
				String orderGuid = UUID.randomUUID().toString();
				order.setCreateTime(DateUtil.changeDate(new Date()));
				order.setRowGuid(orderGuid);
				order.setDelFlag(DelFlagEnum.NDELFLAG.getCode());
//				order.setMerchantNumber(out_trade_no);
				order.setMerchantNumber(params.get("outerOrderNo"));
				order.setRecordStatus(RecordStatusEnum.READYHANDLE.getCode());
				order.setPatientRowGuid(params.get("patientGuid"));
				order.setPatid(params.get("patid"));
				order.setPatientName(params.get("patientName"));
				order.setPayType(PayTypeEnum.ADVANCEPAY.getCode());
				order.setYjlsh(params.get("yjlsh"));
				order.setHisddh(params.get("sjh"));
				SimpleDateFormat sFormat = new SimpleDateFormat("yyyyMMddHHmmss");
				order.setZfsj(sFormat.format(new Date()));
				order.setBlh(params.get("blh"));
				order.setZfje(params.get("yjMoney"));
				//转decimal
				BigDecimal number = new BigDecimal(params.get("yjMoney"));
				order.setPayMoney(number);
				order.setJzlsh(params.get("jzlsh"));
				rechargerecordService.save(order);
				
//				//准备数据返回
//				SortedMap<Object, Object> obj =
//		                new TreeMap<Object, Object>();
//				obj.put("appId",appid);
//				obj.put("timeStamp",System.currentTimeMillis()/1000);
//				obj.put("nonceStr",wx_CommonServiceApi.getRandomStringByLength(32));
//				obj.put("package","prepay_id="+map.get("prepay_id"));
//				obj.put("signType", "MD5");
//				obj.put("paySign",wx_CommonServiceApi.createSign(obj));
				return R.ok("下单成功").put("data", js);
			}
			
			//门诊接口
			if ("mz".equals(params.get("action"))) {
				checkParams(params, "openid");
				checkParams(params, "mzMoney");
				checkParams(params, "patientGuid");
				checkParams(params, "patid");
				checkParams(params, "patientName");
//				checkParams(params, "yyxh");
				
				checkParams(params, "sjh");
				checkParams(params, "zje");
				checkParams(params, "yfje");
				checkParams(params, "zfje");
				
				params.put("ysje", params.get("yfje"));
				 //订单类型
		        params.put("ddlx", "2");
		        //订单来源
		        params.put("ddly", "1");
				//单位分
				int money = Integer.valueOf(AmountUtils.changeY2F(params.get("mzMoney"))); 

				//商户订单号
		        String out_trade_no = System.currentTimeMillis()+wx_CommonServiceApi.getRandomStringByLength(7);
				
		        String result = "";
				try {
					result = wx_CommonServiceApi.placeOrderByWN(money,out_trade_no,params.get("openid"),params);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				if (result == "") {
					//如果返回结果为空，说明调用接口异常
					return R.error("调用下单接口失败");
				}
				
				JSONObject js = JSONObject.parseObject(result);
				//对返回结果进行解析
				if (js.getBoolean("success")) {
				}else{
					return R.error("下单失败");
				}
				
				//数据库创建订单
				Sechos_Rechargerecord order = new Sechos_Rechargerecord();
				String orderGuid = UUID.randomUUID().toString();
				order.setCreateTime(DateUtil.changeDate(new Date()));
				order.setRowGuid(orderGuid);
				order.setDelFlag(DelFlagEnum.NDELFLAG.getCode());
//				order.setMerchantNumber(out_trade_no);
				order.setMerchantNumber(params.get("outerOrderNo"));
				order.setRecordStatus(RecordStatusEnum.READYHANDLE.getCode());
				order.setPatientRowGuid(params.get("patientGuid"));
				order.setPatid(params.get("patid"));
				order.setPatientName(params.get("patientName"));
				order.setPayType(PayTypeEnum.MZPAY.getCode());
				
				order.setGhxh(params.get("ghxh"));
				order.setSjh(params.get("sjh"));
				order.setZje(params.get("zje"));
				order.setYfje(params.get("yfje"));
				order.setZfje(params.get("zfje"));
				order.setZfsj(DateUtil.getYmdhmsFName());
				
				//转decimal
				BigDecimal number = new BigDecimal(params.get("mzMoney"));
				order.setPayMoney(number);
				rechargerecordService.save(order);
				
				return R.ok("下单成功").put("data", js);
			}
			
			//挂号接口
			if ("gh".equals(params.get("action"))) {
				checkParams(params, "openid");
				checkParams(params, "ghMoney");
				checkParams(params, "patientGuid");
				checkParams(params, "patid");
				checkParams(params, "patientName");
				
				checkParams(params, "ghxh");
				checkParams(params, "sjh");
				checkParams(params, "zje");
				checkParams(params, "yfje");
				checkParams(params, "zfje");
				
				params.put("ysje", params.get("yfje"));
				 //订单类型
		        params.put("ddlx", "1");
		        //订单来源
		        params.put("ddly", "1");
				//单位分
				int money = Integer.valueOf(AmountUtils.changeY2F(params.get("ghMoney"))); 

				//商户订单号
		        String out_trade_no = System.currentTimeMillis()+wx_CommonServiceApi.getRandomStringByLength(7);
				
		        String result = "";
				try {
					result = wx_CommonServiceApi.placeOrderByWN(money,out_trade_no,params.get("openid"),params);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				if (result == "") {
					//如果返回结果为空，说明调用接口异常
					return R.error("调用下单接口失败");
				}
				
				JSONObject js = JSONObject.parseObject(result);
				//对返回结果进行解析
				if (js.getBoolean("success")) {
				}else{
					return R.error("下单失败");
				}
				
				//数据库创建订单
				Sechos_Rechargerecord order = new Sechos_Rechargerecord();
				String orderGuid = UUID.randomUUID().toString();
				order.setCreateTime(DateUtil.changeDate(new Date()));
				order.setRowGuid(orderGuid);
				order.setDelFlag(DelFlagEnum.NDELFLAG.getCode());
//				order.setMerchantNumber(out_trade_no);
				order.setMerchantNumber(params.get("outerOrderNo"));
				order.setRecordStatus(RecordStatusEnum.READYHANDLE.getCode());
				order.setPatientRowGuid(params.get("patientGuid"));
				order.setPatid(params.get("patid"));
				order.setPatientName(params.get("patientName"));
				order.setPayType(PayTypeEnum.GHPAY.getCode());
				
				order.setGhxh(params.get("ghxh"));
				order.setSjh(params.get("sjh"));
				order.setZje(params.get("zje"));
				order.setYfje(params.get("yfje"));
				order.setZfje(params.get("zfje"));
				order.setYyxh(params.get("yyxh"));
				
				//转decimal
				BigDecimal number = new BigDecimal(params.get("ghMoney"));
				order.setPayMoney(number);
				rechargerecordService.save(order);
				
				//准备数据返回
				return R.ok("下单成功").put("data", js);
			}
			return R.error("action参数异常");
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
			//yj gh mz区分接口
			//预交金接口
			if ("yj".equals(params.get("action"))) {
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
				order.setPayType(PayTypeEnum.ADVANCEPAY.getCode());
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
			
			//门诊接口
			if ("mz".equals(params.get("action"))) {
				checkParams(params, "openid");
				checkParams(params, "mzMoney");
				checkParams(params, "patientGuid");
				checkParams(params, "patid");
				checkParams(params, "patientName");
//				checkParams(params, "yyxh");
				
				checkParams(params, "sjh");
				checkParams(params, "zje");
				checkParams(params, "yfje");
				checkParams(params, "zfje");
				
				//单位分
				int money = Integer.valueOf(AmountUtils.changeY2F(params.get("mzMoney"))); 

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
				order.setPayType(PayTypeEnum.MZPAY.getCode());
				
				order.setGhxh(params.get("ghxh"));
				order.setSjh(params.get("sjh"));
				order.setZje(params.get("zje"));
				order.setYfje(params.get("yfje"));
				order.setZfje(params.get("zfje"));
				order.setZfsj(DateUtil.getYmdhmsFName());
				
				//转decimal
				BigDecimal number = new BigDecimal(params.get("mzMoney"));
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
			
			//挂号接口
			if ("gh".equals(params.get("action"))) {
				checkParams(params, "openid");
				checkParams(params, "ghMoney");
				checkParams(params, "patientGuid");
				checkParams(params, "patid");
				checkParams(params, "patientName");
				
				checkParams(params, "ghxh");
				checkParams(params, "sjh");
				checkParams(params, "zje");
				checkParams(params, "yfje");
				checkParams(params, "zfje");
				//单位分
				int money = Integer.valueOf(AmountUtils.changeY2F(params.get("ghMoney"))); 

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
				order.setPayType(PayTypeEnum.GHPAY.getCode());
				
				order.setGhxh(params.get("ghxh"));
				order.setSjh(params.get("sjh"));
				order.setZje(params.get("zje"));
				order.setYfje(params.get("yfje"));
				order.setZfje(params.get("zfje"));
				order.setYyxh(params.get("yyxh"));
				
				//转decimal
				BigDecimal number = new BigDecimal(params.get("ghMoney"));
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
			
			return R.error("action参数异常");
		}
		
		/**
		 * 回调接口
		 * <p>Title: wx_callback</p>  
		 * <p>Description: </p>
		 * @author hero
		 */
		@ResponseBody
		@RequestMapping(value="/wn_callback",produces="application/json;charset=utf-8",method=RequestMethod.POST)
		public synchronized JSONObject wn_callback(HttpServletRequest request){
			
			//xml转map
			Map hashmap = new HashMap();
			hashmap = request.getParameterMap();
			logger.info("微信回调接口参数》》》"+JSONObject.toJSONString(hashmap));
			
			if ("false".equals(request.getParameter("success"))) {
				logger.info("错误代码"+request.getParameter("errcode"));
				throw new MyException("卫宁回调异常，异常信息》》"+request.getParameter("err"));
			}
//			//验签
//			String sign = wx_CommonServiceApi.createSign(sm);
//			if (!hashmap.get("sign").equals(sign)) {
//				throw new MyException("验签失败，sign比对不同");
//			}
//			//校验返回的订单金额是否与商户侧的订单金额一致
//			int orderMoney = Integer.valueOf(hashmap.get("total_fee"));//单位分
			//根据商户订单号查询该订单金额
			Sechos_Rechargerecord rechargerecord = rechargerecordService.queryByOrderNumber(request.getParameter("outerOrderNo"));
			if (rechargerecord == null) {
				throw new MyException("未查询到对应记录");
			}
			//根据患者guid查询患者基本信息
			SecHos_Patient patient = patientService.getPatientByGuid(rechargerecord.getPatientRowGuid());
			if (rechargerecord.getRecordStatus() != RecordStatusEnum.READYHANDLE.getCode()) {
				JSONObject resobj = new JSONObject();
				resobj.put("success",false);
				resobj.put("errcode", "500");
				resobj.put("err", "this deal is already pay");
				return resobj;
			}
			
			if (rechargerecord.getPayType() == PayTypeEnum.ADVANCEPAY.getCode()) {
				//调用充值预交金接口
				Map<String, String> yjParams = new HashMap<>();
				yjParams.put("hzxm", rechargerecord.getPatientName());
				yjParams.put("patid", rechargerecord.getPatid());
				yjParams.put("hisddh", rechargerecord.getHisddh());
				yjParams.put("yjlsh", rechargerecord.getYjlsh());
				yjParams.put("zfje", rechargerecord.getPayMoney().toString());
				yjParams.put("zflsh",request.getParameter("tradeNo"));
				yjParams.put("zfsj", rechargerecord.getZfsj());
				yjParams.put("jzlsh", rechargerecord.getJzlsh());
				
				//预交金充值
				String res = wx_CommonServiceApi.advancePay(yjParams);
				JSONObject obj = JSONObject.parseObject(res);
				if (obj.getBoolean("success")) {
					
				}else{
					logger.info(obj.getString("message"));
					//调用撤单接口
					Map<String, String> par = new HashMap<String, String>();
					par.put("outerOrderNo", rechargerecord.getMerchantNumber());
					par.put("ysje", rechargerecord.getZfje());
					par.put("hzxm", rechargerecord.getPatientName());
					par.put("patid", rechargerecord.getPatid());
					par.put("blh", rechargerecord.getBlh());
					par.put("cardno", patient.getPatientIdcard());
					par.put("sex",(patient.getPatientSex()==SexEnum.MALE.getCode())?SexEnum.MALE.getValue():SexEnum.FEMALE.getValue());
					par.put("lxdh", patient.getPatientMobile());
					par.put("zjhm", patient.getPatientIdcard());
					String reString = wx_CommonServiceApi.backPay(par);
					JSONObject jsonObject = JSONObject.parseObject(reString);
					if (jsonObject.getBoolean("success")) {
						
					}else{
						throw new MyException(jsonObject.toJSONString());
					}
					JSONObject resobj = new JSONObject();
					resobj.put("success",false);
					resobj.put("errcode", "500");
					resobj.put("err", "");
					return resobj;
				}
			}
			
			if (rechargerecord.getPayType() == PayTypeEnum.GHPAY.getCode()) {
				//挂号结算接口
				Map<String, String> par = new HashMap<>();
				par.put("patid",rechargerecord.getPatid());
				par.put("ghxh", rechargerecord.getGhxh());
				par.put("sjh", rechargerecord.getSjh());
				par.put("zje", rechargerecord.getZje());
				par.put("yfje", rechargerecord.getYfje());
				par.put("zffs", "2");
				par.put("zfje", rechargerecord.getZfje());
				par.put("zflsh", request.getParameter("tradeNo"));
				par.put("isynzh", "0");
				par.put("yyxh", rechargerecord.getYyxh());
				String result =  wx_CommonServiceApi.RegisteredSettlement(par);
				JSONObject json = JSONObject.parseObject(result);
				if (json.getBoolean("success")) {
					
				}else{
					logger.info(json.getString("message"));
					//调用撤单接口
					Map<String, String> par1 = new HashMap<String, String>();
					par1.put("outerOrderNo", rechargerecord.getSjh());
					par1.put("ysje", rechargerecord.getZfje());
					par1.put("hzxm", rechargerecord.getPatientName());
					par1.put("patid", rechargerecord.getPatid());
//					par1.put("blh", rechargerecord.getBlh());
					par1.put("cardno", patient.getPatientIdcard());
					par1.put("sex",(patient.getPatientSex()==SexEnum.MALE.getCode())?SexEnum.MALE.getValue():SexEnum.FEMALE.getValue());
					par1.put("lxdh", patient.getPatientMobile());
					par1.put("zjhm", patient.getPatientIdcard());
					String reString = wx_CommonServiceApi.backPay(par1);
					JSONObject jsonObject = JSONObject.parseObject(reString);
					if (jsonObject.getBoolean("success")) {
						
					}else{
						throw new MyException(jsonObject.toJSONString());
					}
					JSONObject resobj = new JSONObject();
					resobj.put("success",false);
					resobj.put("errcode", "500");
					resobj.put("err", "");
					return resobj;
				}
			}
			
			if (rechargerecord.getPayType() == PayTypeEnum.MZPAY.getCode()) {
				//门诊结算接口
				Map<String, String> par = new HashMap<>();
				par.put("patid",rechargerecord.getPatid());
				par.put("sjh", rechargerecord.getSjh());
				par.put("zje", rechargerecord.getZje());
				par.put("yfje", rechargerecord.getYfje());
				par.put("zffs", "2");
				par.put("zfje", rechargerecord.getZfje());
				par.put("zflsh", request.getParameter("tradeNo"));
				par.put("zfsj", rechargerecord.getZfsj());
				par.put("isynzh", "0");
				String result =  wx_CommonServiceApi.getOutpatientFeeSettlement(par);
				JSONObject json = JSONObject.parseObject(result);
				if (json.getBoolean("success")) {
					try {
						//发药机接口
						//查询处方信息
						JSONObject PrescriptionList = new JSONObject();
						JSONObject Prescriptions = new JSONObject();
						JSONArray Prescription = new JSONArray();
						//获取挂号预结算的ghxh
						
						List<SechosDrug> DrugsList = sechosDrugService.getByJzlsh(rechargerecord.getGhxh());
						if (DrugsList.size()!=0) {
							for (int i = 0; i < DrugsList.size(); i++) {
								SechosDrug dr = DrugsList.get(i);
								if ("1".equals(dr.getYpbz())) {
									//处方信息必须要药品时
									JSONObject PrescriptionItem = new JSONObject();
									PrescriptionItem.put("PrescriptionID", dr.getCfxh());
									SimpleDateFormat forma = new SimpleDateFormat("yyyyMMddhhmmss");
									Date date = null;
									try {
										date = forma.parse(dr.getKfsj());
									} catch (ParseException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									forma = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
									PrescriptionItem.put("DateTimeOfPrescription", forma.format(date));
									PrescriptionItem.put("Flag", "Outpatient");
									PrescriptionItem.put("Category", "常规");
									
									JSONArray jsonArray = new JSONArray();
									JSONObject jsonObject = new JSONObject();
									JSONObject jsobj = new JSONObject();
									jsonObject.put("MedCode", dr.getXmdm());
									jsonObject.put("MedName", dr.getXmmc());
									jsonObject.put("MedUnitDosage",dr.getYpgg());
									jsonObject.put("MedManufacturer", dr.getSccj());//药品厂商名称
									jsonObject.put("MedPackingUnits", dr.getYpdw());
									jsonObject.put("MedBasicUnits", dr.getJldw());
									jsonObject.put("MedOrdingUnits", dr.getJldw());
//									int ypjl = (int) Double.parseDouble(dr.getYpjl());
									int xmsl = Double.valueOf(dr.getXmsl()).intValue();
									jsonObject.put("MedPackingConverCoefficent", xmsl);//转换系数
									jsonObject.put("MedOrdingConverCoefficent", 1);
									int Xmsl = (int) Double.parseDouble(dr.getXmsl());
									jsonObject.put("Quantity", Xmsl);
									jsonArray.add(jsonObject);
									JSONObject object = new JSONObject();
									object.put("PrescriptionItem", jsonArray);
									PrescriptionItem.put("PrescriptionItems", object);
									Prescription.add(PrescriptionItem);
								}
							}
						}
						JSONObject PatientInfo = new JSONObject();
						//根据pation查询用户信息
						SecHos_Patient patient2 = patientService.getPatientByGuid(rechargerecord.getPatientRowGuid());
						PatientInfo.put("PatientID", rechargerecord.getPatid());
						PatientInfo.put("PatientName", rechargerecord.getPatientName());
						switch (patient2.getPatientSex()) {
						case 0:
							PatientInfo.put("PatientAdministrativeSex", "M");
							break;
							
						case 1:
							PatientInfo.put("PatientAdministrativeSex", "F");
							break;
						}
						SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
						PatientInfo.put("PatientDateofBirth", format1.format(patient2.getPatientBirth())); 
						SimpleDateFormat format = new SimpleDateFormat("yyyy");
						int age = Integer.parseInt(format.format(new Date())) - Integer.parseInt(format.format(patient.getPatientBirth()));
						PatientInfo.put("PatientAge", age);
						
						Prescriptions.put("Prescription", Prescription);
						
						PrescriptionList.put("PatientInfo", PatientInfo);
						PrescriptionList.put("Prescriptions", Prescriptions);
						
						
						
						JSONObject finjson = new JSONObject();
						finjson.put("PrescriptionList", PrescriptionList);
						Map<String, String> par1 = new HashMap<>();
						par1.put("PrescriptionList", finjson.toJSONString());
						String res =  wx_CommonServiceApi.task(par1);
						JSONObject js = JSONObject.parseObject(res);
						logger.info("发药机返回信息："+js.toJSONString());
						
						//发药机回调判断
						if ("True".equals(js.getJSONObject("Result").getJSONObject("Header").getString("ExecuteResult"))) {
							logger.info("发药成功");
						}else {
							logger.info("错误code为:"+js.getJSONObject("Result").getJSONObject("Header").getJSONObject("Error").getString("Code"));
							logger.info("错误描述为:"+js.getJSONObject("Result").getJSONObject("Header").getJSONObject("Error").getString("Message"));
						}
					} catch (Exception e) {
						logger.info("错误信息为:"+e.getMessage());
						e.printStackTrace();
					}
					
				}else{
					logger.info(json.getString("message"));
					if ("已经结算成功，无法重复结算".equals(json.getString("message"))) {
						
					}
					//调用撤单接口
					Map<String, String> par1 = new HashMap<String, String>();
					par1.put("outerOrderNo", rechargerecord.getSjh());
					par1.put("ysje", rechargerecord.getZfje());
					par1.put("hzxm", rechargerecord.getPatientName());
					par1.put("patid", rechargerecord.getPatid());
//					par1.put("blh", rechargerecord.getBlh());
					par1.put("cardno", patient.getPatientIdcard());
					par1.put("sex",(patient.getPatientSex()==SexEnum.MALE.getCode())?SexEnum.MALE.getValue():SexEnum.FEMALE.getValue());
					par1.put("lxdh", patient.getPatientMobile());
					par1.put("zjhm", patient.getPatientIdcard());
					String reString = wx_CommonServiceApi.backPay(par1);
					JSONObject jsonObject = JSONObject.parseObject(reString);
					if (jsonObject.getBoolean("success")) {
						
					}else{
						throw new MyException(jsonObject.toJSONString());
					}
					JSONObject resobj = new JSONObject();
					resobj.put("success",false);
					resobj.put("errcode", "500");
					resobj.put("err", "");
					return resobj;
				
				}
				
			}
			
			
			
			
			//若前面都通过，更新订单状态并返回正确信息给微信
			Sechos_Rechargerecord hosOrder = new Sechos_Rechargerecord();
			hosOrder.setRowGuid(rechargerecord.getRowGuid());
			hosOrder.setRecordStatus(RecordStatusEnum.ALREADYHANDLE.getCode());
			rechargerecordService.update(hosOrder);
			
			JSONObject resobj = new JSONObject();
			resobj.put("success",true);
			resobj.put("errcode", "200");
			return resobj;
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
				return xmlWx;
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
			
			if (rechargerecord.getPayType() == PayTypeEnum.ADVANCEPAY.getCode()) {
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
					yjParams.put("zfje", rechargerecord.getPayMoney().toString());
					yjParams.put("zflsh",hashmap.get("transaction_id"));
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
			}
			
			if (rechargerecord.getPayType() == PayTypeEnum.GHPAY.getCode()) {
				//挂号结算接口
				Map<String, String> par = new HashMap<>();
				par.put("patid",rechargerecord.getPatid());
				par.put("ghxh", rechargerecord.getGhxh());
				par.put("sjh", rechargerecord.getSjh());
				par.put("zje", rechargerecord.getZfje());
				par.put("yfje", rechargerecord.getYfje());
				par.put("zffs", "2");
				par.put("zfje", rechargerecord.getZfje());
				par.put("zflsh", hashmap.get("transaction_id"));
				par.put("isynzh", "0");
				par.put("yyxh", rechargerecord.getYyxh());
				String result =  wx_CommonServiceApi.RegisteredSettlement(par);
				JSONObject json = JSONObject.parseObject(result);
				if (json.getBoolean("success")) {
				
				}else{
					sm.clear();
					sm.put("return_code", "FAIL");
					sm.put("return_msg", "业务异常");
					String xmlwx = XMLUtil.mapToXml(sm, true);
					logger.info("返回给微信的xml为"+xmlwx);
					return xmlwx;
				}
				
				
			}
			
			if (rechargerecord.getPayType() == PayTypeEnum.MZPAY.getCode()) {
				//门诊结算接口
				Map<String, String> par = new HashMap<>();
				par.put("patid",rechargerecord.getPatid());
				par.put("sjh", rechargerecord.getSjh());
				par.put("zje", rechargerecord.getZfje());
				par.put("yfje", rechargerecord.getYfje());
				par.put("zffs", "2");
				par.put("zfje", rechargerecord.getZfje());
				par.put("zflsh", hashmap.get("transaction_id"));
				par.put("zfsj", rechargerecord.getZfsj());
				par.put("isynzh", "0");
				String result =  wx_CommonServiceApi.getOutpatientFeeSettlement(par);
				JSONObject json = JSONObject.parseObject(result);
				if (json.getBoolean("success")) {
					try {
						//发药机接口
						//查询处方信息
						JSONObject PrescriptionList = new JSONObject();
						JSONArray Prescriptions = new JSONArray();
						//获取挂号预结算的ghxh
						
						List<SechosDrug> DrugsList = sechosDrugService.getByJzlsh(rechargerecord.getGhxh());
						if (DrugsList.size()!=0) {
							for (int i = 0; i < DrugsList.size(); i++) {
								SechosDrug dr = DrugsList.get(i);
								if ("1".equals(dr.getYpbz())) {
									//处方信息必须要药品时
									JSONObject Prescription = new JSONObject();
									Prescription.put("PrescriptionID", dr.getCfxh());
									SimpleDateFormat forma = new SimpleDateFormat("yyyyMMddhhmmss");
									Date date = null;
									try {
										date = forma.parse(dr.getKfsj());
									} catch (ParseException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									forma = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
									Prescription.put("DateTimeOfPrescription", forma.format(date));
									Prescription.put("Flag", "Outpatient");
									Prescription.put("Category", "常规");
									
									JSONArray jsonArray = new JSONArray();
									JSONObject jsonObject = new JSONObject();
									jsonObject.put("MedCode", dr.getXmdm());
									jsonObject.put("MedName", dr.getXmmc());
									jsonObject.put("MedUnitDosage",dr.getYpgg());
									jsonObject.put("MedManufacturer", "厂商");//药品厂商名称
									jsonObject.put("MedPackingUnits", dr.getYpdw());
									jsonObject.put("MedBasicUnits", dr.getJldw());
									jsonObject.put("MedOrdingUnits", dr.getJldw());
									int ypjl = (int) Double.parseDouble(dr.getYpjl());
									jsonObject.put("MedPackingConverCoefficent", ypjl);//转换系数
									jsonObject.put("MedOrdingConverCoefficent", 1);
									int Xmsl = (int) Double.parseDouble(dr.getXmsl());
									jsonObject.put("Quantity", Xmsl);
									jsonArray.add(jsonObject);
									Prescription.put("PrescriptionItems",jsonArray);
									Prescriptions.add(Prescription);
								}
							}
							
							
						}
						JSONObject PatientInfo = new JSONObject();
						//根据pation查询用户信息
						SecHos_Patient patient = patientService.getPatientByGuid(rechargerecord.getPatientRowGuid());
						PatientInfo.put("PatientID", rechargerecord.getPatid());
						PatientInfo.put("PatientName", rechargerecord.getPatientName());
						switch (patient.getPatientSex()) {
						case 0:
							PatientInfo.put("PatientAdministrativeSex", "M");
							break;
							
						case 1:
							PatientInfo.put("PatientAdministrativeSex", "F");
							break;
						}
						SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
						PatientInfo.put("PatientDateofBirth", format1.format(patient.getPatientBirth())); 
						SimpleDateFormat format = new SimpleDateFormat("yyyy");
						int age = Integer.parseInt(format.format(new Date())) - Integer.parseInt(format.format(patient.getPatientBirth()));
						PatientInfo.put("PatientAge", age);
						
						PrescriptionList.put("PatientInfo", PatientInfo);
						PrescriptionList.put("Prescriptions", Prescriptions);
						
						Map<String, String> par1 = new HashMap<>();
						par1.put("PrescriptionList", PrescriptionList.toJSONString());
						String res =  wx_CommonServiceApi.task(par1);
						JSONObject js = JSONObject.parseObject(res);
						logger.info("发药机返回信息："+js.toJSONString());
						
						//发药机回调判断
						if ("True".equals(js.getJSONObject("Result").getJSONObject("Header").getString("ExecuteResult"))) {
							logger.info("发药成功");
						}else {
							logger.info("错误code为:"+js.getJSONObject("Result").getJSONObject("Header").getJSONObject("Error").getString("Code"));
							logger.info("错误描述为:"+js.getJSONObject("Result").getJSONObject("Header").getJSONObject("Error").getString("Message"));
						}
					} catch (Exception e) {
						logger.info("错误信息为:"+e.getMessage());
						throw new MyException("处理回调异常");
					}
					
					
					
				}else{
					logger.info(json.getString("message"));
					if ("已经结算成功，无法重复结算".equals(json.getString("message"))) {
						sm.clear();
						sm.put("return_code", "SUCCESS");
						sm.put("return_msg", "OK");
						String xmlWx = XMLUtil.mapToXml(sm, true);
						logger.info("返回给微信的xml为"+xmlWx);
						return xmlWx;
					}
					sm.clear();
					sm.put("return_code", "FAIL");
					sm.put("return_msg", "业务异常");
					String xmlwx = XMLUtil.mapToXml(sm, true);
					logger.info("返回给微信的xml为"+xmlwx);
					return xmlwx;
				}
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
	
	@PassToken
	@ApiOperation(value="发送发药机")
	@ResponseBody
	@RequestMapping(value="/sendFYJ",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R sendFYJ(@RequestBody JSONObject par){
//		Sechos_Rechargerecord rechargerecord = rechargerecordService.queryByOrderNumber(par.get("out_trade_no"));
//		//发药机接口
//		//查询处方信息
//		JSONObject PrescriptionList = new JSONObject();
//		JSONArray Prescriptions = new JSONArray();
//		List<SechosDrug> DrugsList = sechosDrugService.getByJzlsh(rechargerecord.getGhxh());
//		if (DrugsList.size()!=0) {
//			for (int i = 0; i < DrugsList.size(); i++) {
//				SechosDrug dr = DrugsList.get(i);
//				if ("1".equals(dr.getYpbz())) {
//					//处方信息必须要药品时
//					JSONObject Prescription = new JSONObject();
//					Prescription.put("PrescriptionID", dr.getCfxh());
//					SimpleDateFormat forma = new SimpleDateFormat("yyyyMMddhhmmss");
//					Date date = null;
//					try {
//						date = forma.parse(dr.getKfsj());
//					} catch (ParseException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					forma = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//					Prescription.put("DateTimeOfPrescription", forma.format(date));
//					Prescription.put("Flag", "Outpatient");
//					Prescription.put("Category", "常规");
//					
//					JSONArray jsonArray = new JSONArray();
//					JSONObject jsonObject = new JSONObject();
//					jsonObject.put("MedCode", dr.getXmdm());
//					jsonObject.put("MedName", dr.getXmmc());
//					jsonObject.put("MedUnitDosage",dr.getYpgg());
//					jsonObject.put("MedManufacturer", "厂商");//药品厂商名称
//					jsonObject.put("MedPackingUnits", dr.getYpdw());
//					jsonObject.put("MedBasicUnits", dr.getJldw());
//					jsonObject.put("MedOrdingUnits", dr.getJldw());
//					
//					int ypjl = (int) Double.parseDouble(dr.getYpjl());
//					jsonObject.put("MedPackingConverCoefficent", ypjl);//转换系数
//					jsonObject.put("MedOrdingConverCoefficent", 1);
//					int Xmsl = (int) Double.parseDouble(dr.getXmsl());
//					jsonObject.put("Quantity", Xmsl);
//					jsonArray.add(jsonObject);
//					Prescription.put("PrescriptionItems",jsonArray);
//					Prescriptions.add(Prescription);
//				}
//			}
//			
//		}
//		JSONObject PatientInfo = new JSONObject();
//		//根据pation查询用户信息
//		SecHos_Patient patient = patientService.getPatientByGuid(rechargerecord.getPatientRowGuid());
//		PatientInfo.put("PatientID", rechargerecord.getPatid());
//		PatientInfo.put("PatientName", rechargerecord.getPatientName());
//		switch (patient.getPatientSex()) {
//		case 0:
//			PatientInfo.put("PatientAdministrativeSex", "M");
//			break;
//			
//		case 1:
//			PatientInfo.put("PatientAdministrativeSex", "F");
//			break;
//		}
//		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
//		PatientInfo.put("PatientDateofBirth", format1.format(patient.getPatientBirth())); 
//		SimpleDateFormat format = new SimpleDateFormat("yyyy");
//		int age = Integer.parseInt(format.format(new Date())) - Integer.parseInt(format.format(patient.getPatientBirth()));
//		PatientInfo.put("PatientAge", age);
//		
//		PrescriptionList.put("PatientInfo", PatientInfo);
//		PrescriptionList.put("Prescriptions", Prescriptions);
//		
//		JSONObject finjson = new JSONObject();
//		finjson.put("PrescriptionList", PrescriptionList);
		Map<String, String> par2 = new HashMap<>();
		par2.put("PrescriptionList", par.toJSONString());
		String res =  wx_CommonServiceApi.task(par2);
		JSONObject js = JSONObject.parseObject(res);
		logger.info("发药机返回信息："+js.toJSONString());
		
		//发药机回调判断
		if ("True".equals(js.getJSONObject("Result").getJSONObject("Header").getString("ExecuteResult"))) {
			logger.info("发药成功");
		}else {
			logger.info("错误code为:"+js.getJSONObject("Result").getJSONObject("Header").getJSONObject("Error").getString("Code"));
			logger.info("错误描述为:"+js.getJSONObject("Result").getJSONObject("Header").getJSONObject("Error").getString("Message"));
		}
		
		
		
		return R.ok();
	}
		
		
	/**
	 * 挂号预算
	 * <p>Title: RegisteredBudget</p>
	 * <p>Description: </p>
	 * @author wzl
	 * @return
	 */
	@ApiOperation(value="挂号预算")
	@ResponseBody
	@RequestMapping(value="/RegisteredBudget",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R RegisteredBudget(@RequestBody Map<String, String> params){
		checkParams(params, "patid");
		checkParams(params, "bxh");
		checkParams(params, "hzxm");
		checkParams(params, "pbmxxh");
		checkParams(params, "isynzh");
		checkParams(params, "iszfjs");
		
		//获取yyxh
		Map<String, String> par = new HashMap<>();
		par.put("hzxm", params.get("hzxm"));
		par.put("patid", params.get("patid"));
		String res =  wx_CommonServiceApi.getPatientAppointInfo(par);
		JSONObject js = JSONObject.parseObject(res);
		
		String yyxh = "";
		if (js.getBoolean("success")) {
			JSONArray arr = js.getJSONArray("mzyyxxs");
			if (arr.size() == 0) {
				return R.error("未查到相关记录");
			}
			for (int i = 0; i < arr.size(); i++) {
				JSONObject j = arr.getJSONObject(i);
				if (params.get("pbmxxh").equals(j.getString("pbxh"))) {
					yyxh = j.getString("yyxh");
				}
			}
		}else{
			String str = js.getString("message");
			String str2=str.substring(str.lastIndexOf("|")+1);
			return R.error(str2);
		}
		params.put("yyxh", yyxh);
		String result =  wx_CommonServiceApi.RegisteredBudget(params);
		JSONObject json = JSONObject.parseObject(result);
		json.put("yyxh", yyxh);
		if (json.getBoolean("success")) {
			return R.ok().put("data",json);
		}else{
			return R.error();
		}
	}

	/**
	 * 挂号结算
	 * <p>Title: RegisteredSettlement</p>
	 * <p>Description: </p>
	 * @author wzl
	 * @return
	 */
	@ApiOperation(value="挂号结算")
	@ResponseBody
	@RequestMapping(value="/RegisteredSettlement",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R RegisteredSettlement(@RequestBody Map<String, String> params){
		checkParams(params, "patid");
		checkParams(params, "ghxh");
		checkParams(params, "sjh");
		checkParams(params, "zje");
		checkParams(params, "yfje");
		checkParams(params, "zffs");
		checkParams(params, "zfje");
		checkParams(params, "zflsh");
		checkParams(params, "isynzh");
		String result =  wx_CommonServiceApi.RegisteredBudget(params);
		JSONObject json = JSONObject.parseObject(result);
		if (json.getBoolean("success")) {
			return R.ok().put("data",json);
		}else{
			String str = json.getString("message");
			String str2=str.substring(str.lastIndexOf("|")+1);
			return R.error(str2);
		}
	}

	/**
	 * 查询门诊候诊信息(patid)
	 * <p>Title: getOutpatientWaitingInfo</p>
	 * <p>Description: </p>
	 * @author wzl
	 * @return
	 */
	@ApiOperation(value="查询门诊候诊信息(patid)")
	@ResponseBody
	@RequestMapping(value="/getOutpatientWaitingInfo",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R getOutpatientWaitingInfo(@RequestBody Map<String, String> params){
		checkParams(params, "hzxm");
		checkParams(params, "patid");
		String result =  wx_CommonServiceApi.getOutpatientWaitingInfo(params);
		JSONObject json = JSONObject.parseObject(result);
		if (json.getBoolean("success")) {
			JSONArray arr = json.getJSONArray("mzpds");
			if (arr.size() == 0) {
				return R.error("未查到相关记录");
			}
			return R.ok().put("data", arr.getJSONObject(0));
		}else{
			String str = json.getString("message");
			String str2=str.substring(str.lastIndexOf("|")+1);
			return R.error(str2);
		}
	}

	/**
	 * 查询门诊患者待缴费处方信息(PATID)
	 * <p>Title: getOutpatientToPayPrescription</p>
	 * <p>Description: </p>
	 * @author wzl
	 * @return
	 */
	@ApiOperation(value="查询门诊患者待缴费处方信息(PATID)")
	@ResponseBody
	@RequestMapping(value="/getOutpatientToPayPrescription",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R getOutpatientToPayPrescription(@RequestBody Map<String, String> params){
		checkParams(params, "hzxm");
		checkParams(params, "patid");
		checkParams(params, "ksrq");
		checkParams(params, "jsrq");
		String result =  wx_CommonServiceApi.getOutpatientToPayPrescription(params);
		JSONObject json = JSONObject.parseObject(result);
		if (json.getBoolean("success")) {
			JSONArray arr = json.getJSONArray("mzsfcfs");
			if (arr.size() == 0) {
				return R.error("未查到相关记录");
			}
			//根据XX存储药品信息
			for (int i = 0; i < arr.size(); i++) {
				JSONObject ob = arr.getJSONObject(i);
				//先删后增
				sechosDrugService.deleteByJzlsh(ob.getString("jzlsh"));
				SechosDrug sechosDrug = JSON.toJavaObject(ob,SechosDrug.class);
				//如果排序号为空，则自动转为0
		    	if (sechosDrug.getSortSq() == null) {
					sechosDrug.setSortSq(0);
				}
		    	//生成uuid作为rowguid
		        String uuid = java.util.UUID.randomUUID().toString();
				sechosDrug.setRowGuid(uuid);
				Date createTime = DateUtil.changeDate(new Date());
				sechosDrug.setCreateTime(createTime);
				sechosDrug.setPatientRowGuid(params.get("pationRowGuid"));
				//获取厂家信息
				params.clear();
				params.put("ypdm", sechosDrug.getXmdm());
				params.put("page", "1");
				params.put("jsfs", "3");
				String res = wx_CommonServiceApi.getMedInfo(params);
				JSONObject js = JSONObject.parseObject(res);
				if (js.getBoolean("success")) {
					JSONArray array =  js.getJSONArray("ypxxs");
					JSONObject j = array.getJSONObject(0);
					sechosDrug.setSccj(j.getString("cjmc"));
				}else{
					logger.info("未查询到药品详细信息");
				}
				sechosDrugService.save(sechosDrug);
			}
			
			return R.ok().put("data", arr);
		}else{
			String str = json.getString("message");
			String str2=str.substring(str.lastIndexOf("|")+1);
			return R.error(str2);
		}
	}

	/**
	 * 门诊收费预算
	 * <p>Title: getOutpatientFeeBudget</p>
	 * <p>Description: </p>
	 * @author wzl
	 * @return
	 */
	@ApiOperation(value="门诊收费预算")
	@ResponseBody
	@RequestMapping(value="/getOutpatientFeeBudget",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R getOutpatientFeeBudget(@RequestBody Map<String, String> params){
		checkParams(params, "patid");
		checkParams(params, "cfxhhj");
		checkParams(params, "isynzh");
		String result =  wx_CommonServiceApi.getOutpatientFeeBudget(params);
		JSONObject json = JSONObject.parseObject(result);
		if (json.getBoolean("success")) {
			return R.ok().put("data",json);
		}else{
			return R.error();
		}
	}

	/**
	 * 门诊收费结算
	 * <p>Title: getOutpatientFeeSettlement</p>
	 * <p>Description: </p>
	 * @author wzl
	 * @return
	 */
	@ApiOperation(value="门诊收费结算")
	@ResponseBody
	@RequestMapping(value="/getOutpatientFeeSettlement",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R getOutpatientFeeSettlement(@RequestBody Map<String, String> params){
		checkParams(params, "patid");
		checkParams(params, "sjh");
		checkParams(params, "zje");
		checkParams(params, "yfje");
		checkParams(params, "zffs");
		checkParams(params, "zfje");
		checkParams(params, "zflsh");
		checkParams(params, "zfsj");
		checkParams(params, "isynzh");
		String result =  wx_CommonServiceApi.getOutpatientFeeSettlement(params);
		JSONObject json = JSONObject.parseObject(result);
		if (json.getBoolean("success")) {
			return R.ok().put("data",json);
		}else{
			return R.error();
		}
	}

	/**
	 * 查询门诊患者收费结算信息(PATID)
	 * <p>Title: getOutpatientToPayPrescription</p>
	 * <p>Description: </p>
	 * @author wzl
	 * @return
	 */
	@ApiOperation(value="查询门诊患者收费结算信息(PATID)")
	@ResponseBody
	@RequestMapping(value="/getOutpatientFeeSettlementInfo",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R getOutpatientFeeSettlementInfo(@RequestBody Map<String, String> params){
		checkParams(params, "hzxm");
		checkParams(params, "patid");
		params.put("ksrq", ksrq);
		params.put("jsrq", jsrq);
		String result =  wx_CommonServiceApi.getOutpatientFeeSettlementInfo(params);
		JSONObject json = JSONObject.parseObject(result);
		if (json.getBoolean("success")) {
			JSONArray arr = json.getJSONArray("mzsfjsxxs");
			if (arr.size() == 0) {
				return R.error("未查到相关记录");
			}
			return R.ok().put("data", arr);
		}else{
			String str = json.getString("message");
			String str2=str.substring(str.lastIndexOf("|")+1);
			return R.error(str2);
		}
	}

	/**
	 * 查询门诊患者收费结算明细信息
	 * <p>Title: getOutpatientFeeSettlementDetail</p>
	 * <p>Description: </p>
	 * @author wzl
	 * @return
	 */
	@ApiOperation(value="查询门诊患者收费结算明细信息")
	@ResponseBody
	@RequestMapping(value="/getOutpatientFeeSettlementDetail",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R getOutpatientFeeSettlementDetail(@RequestBody Map<String, String> params){
		checkParams(params, "hzxm");
		checkParams(params, "jssjh");
		String result =  wx_CommonServiceApi.getOutpatientFeeSettlementDetail(params);
		JSONObject json = JSONObject.parseObject(result);
		if (json.getBoolean("success")) {
			JSONArray arr = json.getJSONArray("mzsfjsmxs");
			if (arr.size() == 0) {
				return R.error("未查到相关记录");
			}
			return R.ok().put("data", arr);
		}else{
			String str = json.getString("message");
			String str2=str.substring(str.lastIndexOf("|")+1);
			return R.error(str2);
		}
	}

	/**
	 * 查询患者预约信息(PATID)
	 * <p>Title: getPatientAppointInfo</p>
	 * <p>Description: </p>
	 * @author wzl
	 * @return
	 */
	@ApiOperation(value="查询患者预约信息(PATID)")
	@ResponseBody
	@RequestMapping(value="/getPatientAppointInfo",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R getPatientAppointInfo(@RequestBody Map<String, String> params){
		checkParams(params, "hzxm");
		checkParams(params, "patid");
		String result =  wx_CommonServiceApi.getPatientAppointInfo(params);
		JSONObject json = JSONObject.parseObject(result);
		if (json.getBoolean("success")) {
			JSONArray arr = json.getJSONArray("mzyyxxs");
			if (arr.size() == 0) {
				return R.error("未查到相关记录");
			}
			return R.ok().put("data", arr);
		}else{
			String str = json.getString("message");
			String str2=str.substring(str.lastIndexOf("|")+1);
			return R.error(str2);
		}
	}

	/**
	 * 查询患者预约信息(预约序号)
	 * <p>Title: getPatientAppointInfoByNum</p>
	 * <p>Description: </p>
	 * @author wzl
	 * @return
	 */
	@ApiOperation(value="查询患者预约信息(预约序号)")
	@ResponseBody
	@RequestMapping(value="/getPatientAppointInfoByNum",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R getPatientAppointInfoByNum(@RequestBody Map<String, String> params){
		checkParams(params, "hzxm");
		checkParams(params, "yyxh");
		String result =  wx_CommonServiceApi.getPatientAppointInfoByNum(params);
		JSONObject json = JSONObject.parseObject(result);
		if (json.getBoolean("success")) {
			JSONArray arr = json.getJSONArray("mzyyxxs");
			if (arr.size() == 0) {
				return R.error("未查到相关记录");
			}
			return R.ok().put("data", arr);
		}else{
			String str = json.getString("message");
			String str2=str.substring(str.lastIndexOf("|")+1);
			return R.error(str2);
		}
	}

	/**
	 * 查询门诊患者挂号记录(PATID)
	 * <p>Title: getPatientRegRecordByPatid</p>
	 * <p>Description: </p>
	 * @author wzl
	 * @return
	 */
	@ApiOperation(value="查询门诊患者挂号记录(PATID)")
	@ResponseBody
	@RequestMapping(value="/getPatientRegRecordByPatid",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R getPatientRegRecordByPatid(@RequestBody Map<String, String> params){
		checkParams(params, "hzxm");
		checkParams(params, "patid");
		checkParams(params, "ksrq");
		checkParams(params, "jsrq");
		checkParams(params, "czybz");
		String result =  wx_CommonServiceApi.getPatientRegRecordByPatid(params);
		JSONObject json = JSONObject.parseObject(result);
		if (json.getBoolean("success")) {
			JSONArray arr = json.getJSONArray("mzjzjls");
			if (arr.size() == 0) {
				return R.error("未查到相关记录");
			}
			return R.ok().put("data", arr);
		}else{
			String str = json.getString("message");
			String str2=str.substring(str.lastIndexOf("|")+1);
			return R.error(str2);
		}
	}

	/**
	 * 住院患者一日清查询
	 * <p>Title: getInpatientOneDayLiquidation</p>
	 * <p>Description: </p>
	 * @author wzl
	 * @return
	 */
	@ApiOperation(value="住院患者一日清查询")
	@ResponseBody
	@RequestMapping(value="/getInpatientOneDayLiquidation",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R getInpatientADayOf(@RequestBody Map<String, String> params){
		checkParams(params, "hzxm");
		checkParams(params, "jzlsh");
		if (params.get("cxrq") == null || "".equals(params.get("cxrq"))) {
			return R.error("请先选择您要查询的日期");
		}
		checkParams(params, "aslhz");
		String result =  wx_CommonServiceApi.getInpatientOneDayLiquidation(params);
		JSONObject json = JSONObject.parseObject(result);
		if (json.getBoolean("success")) {
			JSONArray arr = json.getJSONArray("zyyrqs");
			if (arr.size() == 0) {
				return R.error("未查到相关记录");
			}
			return R.ok().put("data", arr);
		}else{
			String str = json.getString("message");
			String str2=str.substring(str.lastIndexOf("|")+1);
			return R.error(str2);
		}
	}

	/**
	 * 查询住院患者基本信息(PATID)
	 * <p>Title: getInPatientInfoByPatid</p>
	 * <p>Description: </p>
	 * @author wzl
	 * @return
	 */
	@ApiOperation(value="查询住院患者基本信息(PATID)")
	@ResponseBody
	@RequestMapping(value="/getInPatientInfoByPatid",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R getInPatientInfoByPatid(@RequestBody Map<String, String> params){
		checkParams(params, "hzxm");
		checkParams(params, "patid");
		checkParams(params, "zyzt");
		String result =  wx_CommonServiceApi.getInPatientInfoByPatid(params);
		JSONObject json = JSONObject.parseObject(result);
		if (json.getBoolean("success")) {
			JSONArray arr = json.getJSONArray("zyjls");
			if (arr.size() == 0) {
				return R.error("未查到相关记录");
			}
			return R.ok().put("data", arr);
		}else{
			String str = json.getString("message");
			String str2=str.substring(str.lastIndexOf("|")+1);
			return R.error(str2);
		}
	}

	/**
	 * 查询住院患者基本信息(病历号)
	 * <p>Title: getInPatientInfoByBlh</p>
	 * <p>Description: </p>
	 * @author wzl
	 * @return
	 */
	@ApiOperation(value="查询住院患者基本信息(病历号)")
	@ResponseBody
	@RequestMapping(value="/getInPatientInfoByBlh",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R getInPatientInfoByBlh(@RequestBody Map<String, String> params){
		checkParams(params, "hzxm");
		checkParams(params, "blh");
		String result =  wx_CommonServiceApi.getInPatientInfoByBlh(params);
		JSONObject json = JSONObject.parseObject(result);
		if (json.getBoolean("success")) {
			JSONArray arr = json.getJSONArray("patinfos");
			if (arr.size() == 0) {
				return R.error("未查到相关记录");
			}
			return R.ok().put("data", arr);
		}else{
			String str = json.getString("message");
			String str2=str.substring(str.lastIndexOf("|")+1);
			return R.error(str2);
		}
	}
	
	@ApiOperation(value="获取药品详细信息")
	@ResponseBody
	@RequestMapping(value="/getMedInfo",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R getMedInfo(@RequestBody Map<String, String> params){
		checkParams(params, "ypdm");
		String result =  wx_CommonServiceApi.getMedInfo(params);
		JSONObject json = JSONObject.parseObject(result);
		if (json.getBoolean("success")) {
			JSONArray arr = json.getJSONArray("ypxxs");
			if (arr.size() == 0) {
				return R.error("未查到相关药品详细信息");
			}
			return R.ok().put("data", arr);
		}else{
			String str = json.getString("message");
			String str2=str.substring(str.lastIndexOf("|")+1);
			return R.error(str2);
		}
	}
	
	
	/**
	 * 
	 * <p>Title: updateOAUsers</p>  
	 * <p>Description: </p>
	 * @author hero  
	 * @return
	 */
	@ApiOperation(value="同步oa用户")
	@PassToken
	@ResponseBody
	@RequestMapping(value="/updateOAUsers")
	public R updateOAUsers(){
		
		String res = wx_CommonServiceApi.updateOA();
		JSONArray array = JSONArray.parseArray(res);
		if (array.size() == 0) {
			return R.error("更新失败");
		}
		System.out.println(array.toJSONString());
		
		List<Frame_User> userList = new ArrayList<>();
		Frame_User user;
		for (int i = 0; i < array.size(); i++) {
			JSONObject obj = array.getJSONObject(i);
			user = new Frame_User();
			user.setRowId(obj.getIntValue("userCode"));
			user.setRowGuid(obj.getString("rowGuid"));
			user.setDuty(obj.getString("duty"));
			user.setCreateTime(new Date());
			user.setDeptName(obj.getString("deptName"));
			if (obj.getString("mobile") != null) {
				user.setMobile(obj.getString("mobile"));
			}
			user.setUserName(obj.getString("displayName"));
			user.setLoginId(obj.getString("loginID"));
			user.setPassword(obj.getString("password"));
			userList.add(user);
		}
		
		//开设线程
		Thread thread = new InsertOaUsersThread(userList);
		thread.run();
		
		return R.ok("同步成功");
	}
}	
