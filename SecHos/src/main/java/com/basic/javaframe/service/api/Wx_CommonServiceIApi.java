package com.basic.javaframe.service.api;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.basic.javaframe.common.exception.MyException;
import com.basic.javaframe.common.utils.HttpUtil;
import com.basic.javaframe.common.utils.MD5Util;
import com.basic.javaframe.common.utils.R;
import com.basic.javaframe.common.utils.XMLUtil;

import io.swagger.annotations.Info;

@Service("wx_CommonServiceApi")
public class Wx_CommonServiceIApi extends Api_BaseService{

	/**
	 * 通过网页授权code获取token
	 * <p>Title: code2Token</p>  
	 * <p>Description: </p>
	 * @author hero  
	 * @param code
	 * @return
	 */
	public String code2Token(String code) {
		//准备参数
		String grant_type = "authorization_code";
	    Map<String, String> parsms = new HashMap<String, String>();
	    parsms.put("appid", appid);
	    parsms.put("secret", appsecret);
	    parsms.put("grant_type", grant_type);
	    parsms.put("code", code);
	    
	    logger.info("获取网页授权token接口参数》》》"+JSONObject.toJSONString(parsms));
	    String result = HttpUtil.sendGet("https://api.weixin.qq.com/sns/oauth2/access_token",parsms);
	    logger.info("获取网页授权token接口返回成功》》》"+JSONObject.toJSONString(result));
	    JSONObject json = JSONObject.parseObject(result);
		if (json.containsKey("errcode")) {
			String errcode = json.getString("errcode");
			throw new MyException("获取accsee_token异常,errcode为"+errcode);
		}
		
		String openid = json.getString("openid");
		String access_token = json.getString("access_token");
		String expireTime = json.getString("expires_in");
		
//		//服务端存储code
//		redisService.set("access_token", access_token,Long.valueOf(expireTime));
		
		//准备参数
	    parsms.clear();
	    parsms.put("access_token",access_token);
	    parsms.put("openid", openid);
	    parsms.put("lang", "zh_CN");
	    
	    logger.info("获取网页授权用户信息接口参数》》》"+JSONObject.toJSONString(parsms));
	    String resultUser = HttpUtil.sendGet("https://api.weixin.qq.com/sns/userinfo", parsms);
	    logger.info("获取网页授权用户信息接口返回成功》》》"+JSONObject.toJSONString(resultUser));
	    return resultUser;
	}
	
	/**
	 * 绑定门诊患者(患者建档)
	 * <p>Title: bingdingOutPatient</p>  
	 * <p>Description: </p>
	 * @author hero  
	 * @return
	 */
	public String bingdingOutPatient(Map<String, String> params) {
		// 准备参数
		params.put("action", "PUTHZJD");
		params.put("accesskey", accesskey);
		params.put("yydm", yydm);
		logger.info("绑定门诊患者(患者建档)接口参数》》》"+JSONObject.toJSONString(params));
	    String result = HttpUtil.sendPost(wnUrl,params);
	    logger.info("绑定门诊患者(患者建档)接口返回成功》》》"+JSONObject.toJSONString(result));
		return result;
	}
	
	/**
	 * 查询住院患者
	 * <p>Title: bingdingHospitalized</p>  
	 * <p>Description: </p>
	 * @author hero  
	 * @return
	 */
	public String selectHospitalized(Map<String, String> params) {
		// 准备参数
		params.put("yydm", yydm);
		params.put("accesskey", accesskey);
		params.put("action","GETZYHZZJH");
		logger.info("查询住院患者接口参数》》》"+JSONObject.toJSONString(params));
	    String res = HttpUtil.sendPost(wnUrl,params);
	    logger.info("查询住院患者接口返回成功》》》"+JSONObject.toJSONString(res));
		return res;
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
	 * 查询门诊患者
	 * <p>Title: selectOutPatient</p>  
	 * <p>Description: </p>
	 * @author hero  
	 * @param params
	 * @return
	 */
	public String selectOutPatient(Map<String, String> params) {
		//查询是否有门诊患者
		params.put("yydm", yydm);
		params.put("accesskey", accesskey);
		params.put("action","GETMZHZZJH");
		logger.info("查询门诊患者接口参数》》》"+JSONObject.toJSONString(params));
	    String res = HttpUtil.sendPost(wnUrl,params);
	    logger.info("查询门诊患者接口返回成功》》》"+JSONObject.toJSONString(res));
	    
		return res;
	}
	
	/**
	 * 预交金预充值接口
	 * <p>Title: beforehandPay</p>  
	 * <p>Description: </p>
	 * @author hero  
	 * @param params
	 * @return
	 */
	public String beforehandPay(Map<String, String> params) {
		//首先调取查询住院患者就诊记录获取住院就诊流水号
		params.put("yydm", yydm);
		params.put("accesskey", accesskey);
		params.put("action", "GETZYJLPAT");
		params.put("zyzt","0");
		params.put("hzxm",params.get("hzxm"));
		params.put("patid",params.get("patid"));
		logger.info("查询住院患者就诊记录接口参数》》》"+JSONObject.toJSONString(params));
		String result = HttpUtil.sendPost(wnUrl, params);
		logger.info("查询住院患者就诊记录接口返回成功》》》"+JSONObject.toJSONString(result));
		
		//解析结果
		JSONObject jsonObject = JSONObject.parseObject(result);
		JSONArray array = new JSONArray();
		if (jsonObject.containsKey("success")) {
			boolean resu = jsonObject.getBoolean("success");
			if (resu) {
				array = jsonObject.getJSONArray("zyjls");
			}else {
				throw new MyException("查询失败");
			}
		}else {
			throw new MyException("查询住院患者就诊记录接口异常");
		}
		
		params.put("action","PUTZYYJJYS");
		params.put("jzlsh", array.getJSONObject(0).getString("jzlsh"));
		logger.info("预交金预充值接口参数》》》"+JSONObject.toJSONString(params));
		String res = HttpUtil.sendPost(wnUrl, params);
		logger.info("预交金预充值接口返回成功》》》"+JSONObject.toJSONString(res));
		return res;
	}
	
	/**
	 * 预交金充值
	 * <p>Title: advancePay</p>  
	 * <p>Description: </p>
	 * @author hero  
	 * @param params
	 * @return
	 */
	public String advancePay(Map<String, String> params) {
		params.put("yydm", yydm);
		params.put("accesskey", accesskey);
		params.put("action","PUTZYYJJ");
		params.put("zffs", "1");
		
		logger.info("预交金充值接口参数》》》"+JSONObject.toJSONString(params));
		String res = HttpUtil.sendPost(wnUrl, params);
		logger.info("预交金充值接口返回成功》》》"+JSONObject.toJSONString(res));
		return res;
	}
	
	/**
	 * 通过patid查询检查报告列表
	 * <p>Title: getReportListByPatid</p>  
	 * <p>Description: </p>
	 * @author hero  
	 * @param params
	 * @return
	 */
	public String getReportListByPatid(Map<String, String> params) {
		params.put("yydm", yydm);
		params.put("accesskey", accesskey);
		params.put("action","GETRISLISTPAT");
		
		logger.info("查询检查报告列表接口参数》》》"+JSONObject.toJSONString(params));
		String res = HttpUtil.sendPost(wnUrl, params);
		logger.info("查询检查报告列表接口返回成功》》》"+JSONObject.toJSONString(res));
		
		return res;
	}
	
	/**
	 * 获取实验检查报告列表
	 * <p>Title: getLabReportListByPatid</p>  
	 * <p>Description: </p>
	 * @author hero  
	 * @param params
	 * @return
	 */
	public String getLabReportListByPatid(Map<String, String> params) {
		params.put("yydm", yydm);
		params.put("accesskey", accesskey);
		params.put("action","GETLISLISTPAT");
		
		logger.info("查询实验检查报告列表接口参数》》》"+JSONObject.toJSONString(params));
		String res = HttpUtil.sendPost(wnUrl, params);
		logger.info("查询实验检查报告列表接口返回成功》》》"+JSONObject.toJSONString(res));
		return res;
	}
	
	/**
	 * 获取报告结果
	 * <p>Title: getReportDetail</p>  
	 * <p>Description: </p>
	 * @author hero  
	 * @param params
	 * @return
	 */
	public String getReportDetail(Map<String, String> params) {
		params.put("yydm", yydm);
		params.put("accesskey", accesskey);
		params.put("action","GETRISRESULT");
		
		logger.info("查询检查报告结果接口参数》》》"+JSONObject.toJSONString(params));
		String res = HttpUtil.sendPost(wnUrl, params);
		logger.info("查询检查报告结果接口返回成功》》》"+JSONObject.toJSONString(res));
		return res;
	}
	
	/**
	 * 获取实验报告结果
	 * <p>Title: getLabReportDetail</p>  
	 * <p>Description: </p>
	 * @author hero  
	 * @param params
	 * @return
	 */
	public String getLabReportDetail(Map<String, String> params) {
		params.put("yydm", yydm);
		params.put("accesskey", accesskey);
		params.put("action","GETLISRESULT");
		
		logger.info("查询实验报告结果接口参数》》》"+JSONObject.toJSONString(params));
		String res = HttpUtil.sendPost(wnUrl, params);
		logger.info("查询实验报告结果接口返回成功》》》"+JSONObject.toJSONString(res));
		return res;
	}
	
	/**
	 * 获取就诊流水号
	 * <p>Title: getJzlsh</p>  
	 * <p>Description: </p>
	 * @author hero  
	 * @param params
	 * @return
	 */
	public String getJzlsh(Map<String, String> params) {
		// TODO Auto-generated method stub
		params.put("yydm", yydm);
		params.put("accesskey", accesskey);
		params.put("action","GETZYJLPAT");
		params.put("zyzt","0");
		
		logger.info("获取就诊流水号接口参数》》》"+JSONObject.toJSONString(params));
		String res = HttpUtil.sendPost(wnUrl, params);
		logger.info("获取就诊流水号接口返回成功》》》"+JSONObject.toJSONString(res));
		return res;
	}
	
	/**
	 * 获取汇总信息
	 * <p>Title: getSummary</p>  
	 * <p>Description: </p>
	 * @author hero  
	 * @param params
	 * @return
	 */
	public String getSummary(Map<String, String> params) {
		// TODO Auto-generated method stub
		params.put("yydm", yydm);
		params.put("accesskey", accesskey);
		params.put("action","GETZYYJJHZ");

		logger.info("获取汇总信息接口参数》》》"+JSONObject.toJSONString(params));
		String res = HttpUtil.sendPost(wnUrl, params);
		logger.info("获取汇总信息接口返回成功》》》"+JSONObject.toJSONString(res));
		return res;
	}
	
	/**
	 * 获取详细信息
	 * <p>Title: getAdvanceDetail</p>  
	 * <p>Description: </p>
	 * @author hero  
	 * @param params
	 * @return
	 */
	public String getAdvanceDetail(Map<String, String> params) {
		// TODO Auto-generated method stub
		params.put("yydm", yydm);
		params.put("accesskey", accesskey);
		params.put("action","GETZYYJJMX");

		logger.info("获取明细信息接口参数》》》"+JSONObject.toJSONString(params));
		String res = HttpUtil.sendPost(wnUrl, params);
		logger.info("获取明细信息接口返回成功》》》"+JSONObject.toJSONString(res));
		return res;
	}

	/**
	 * 获取预约出诊科室信息
	 * <p>Title: getAppointRoomInfo</p>
	 * <p>Description: </p>
	 * @author wzl
	 * @param params
	 * @return
	 */
	public String getAppointRoomInfo(Map<String,String> params){
		params.put("yydm",yydm);
		params.put("accesskey",accesskey);
		params.put("action","GETCZKSXX");
		logger.info("获取预约出诊科室信息接口参数》》》"+JSONObject.toJSONString(params));
		String res = HttpUtil.sendPost(wnUrl, params);
		logger.info("获取预约出诊科室信息接口返回成功》》》"+JSONObject.toJSONString(res));
		return res;
	}

	/**
	 * 获取预约出诊医生信息
	 * <p>Title: getAppointDoctorInfo</p>
	 * <p>Description: </p>
	 * @author wzl
	 * @param params
	 * @return
	 */
	public String getAppointDoctorInfo(Map<String,String> params){
		params.put("yydm",yydm);
		params.put("accesskey",accesskey);
		params.put("action","GETCZYSXX");
		logger.info("获取预约出诊医生信息接口参数》》》"+JSONObject.toJSONString(params));
		String res = HttpUtil.sendPost(wnUrl, params);
		logger.info("获取预约出诊医生信息接口返回成功》》》"+JSONObject.toJSONString(res));
		return res;
	}

	/**
	 * 获取体检报告列表
	 * <p>Title: getMedicalReportList</p>
	 * <p>Description: </p>
	 * @author wzl
	 * @param params
	 * @return
	 */
	public String getMedicalReportList(Map<String,String> params){
		logger.info("获取体检报告列表接口参数》》》"+JSONObject.toJSONString(params));
		String res = HttpUtil.sendPost(wnUrl+"/getBhkList", params);
		logger.info("获取体检报告列表接口返回成功》》》"+JSONObject.toJSONString(res));
		return res;
	}

	/**
	 * 获取体检报告信息
	 * <p>Title: getMedicalReportInfo</p>
	 * <p>Description: </p>
	 * @author wzl
	 * @param params
	 * @return
	 */
	public String getMedicalReportInfo(Map<String,String> params){
		logger.info("获取体检报告信息接口参数》》》"+JSONObject.toJSONString(params));
		String res = HttpUtil.sendPost(wnUrl+"/getBhkInfo", params);
		logger.info("获取体检报告信息接口返回成功》》》"+JSONObject.toJSONString(res));
		return res;
	}



	/**
	 * 准备参数调用微信接口
	 * @throws UnsupportedEncodingException 
	 */
	public String placeOrder(int money,String out_trade_no,String openid) throws UnsupportedEncodingException {
		
		//随机数nonce_str
		String nonce_str = getRandomStringByLength(32);
		//标价金额（单位分）
		int total_fee = money;
		//交易类型
		String trade_type = "JSAPI";
		
		SortedMap<Object, Object> sm =
                new TreeMap<Object, Object>();
		
		sm.put("appid", appid);
		sm.put("mch_id", mch_id);
		sm.put("nonce_str", nonce_str);
		sm.put("body",body);
		sm.put("out_trade_no", out_trade_no);
		sm.put("spbill_create_ip", spbill_create_ip);
		sm.put("notify_url", notify_url);
		sm.put("trade_type", trade_type);
		sm.put("total_fee", total_fee);
		sm.put("openid", openid);
		sm.put("sign", createSign(sm));
		
		
		
		String xml = XMLUtil.mapToXml(sm, false);
		logger.info("统一下单接口参数》》》》"+(xml));
		String result = "";
		try {
			result = HttpUtil.sendXmlMsg(url, xml);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("返回成功》》》"+JSONObject.toJSONString(result));
		
		return result;
	}
	
	
	/**
	* 微信支付签名算法sign
	* @param characterEncoding
	* @param parameters
	* @return
	*/
	public String createSign(SortedMap<Object,Object> parameters){
		StringBuffer sb = new StringBuffer();
		Set es = parameters.entrySet();//所有参与传参的参数按照accsii排序（升序）
		Iterator it = es.iterator();
		while(it.hasNext()) {
			Map.Entry entry = (Map.Entry)it.next();
			String k = (String)entry.getKey();
			Object v = entry.getValue();
			if(null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
			 sb.append(k + "=" + v + "&");
			}
		}
		 
		sb.append("key=" + key);
		System.out.println(sb);
		String sign = MD5Util.md5Password(sb.toString()).toUpperCase();
		return sign;
	}
	
	public static String getRandomStringByLength(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }


	/**
	 * 获取预约医生号源
	 * <p>Title: getAppointDoctorYNo</p>
	 * <p>Description: </p>
	 * @author wzl
	 * @param params
	 * @return
	 */
	public String getAppointDoctorYNo(Map<String, String> params) {
		// TODO Auto-generated method stub
		params.put("yydm", yydm);
		params.put("accesskey", accesskey);
		params.put("action","GETYYYSHY");

		logger.info("获取预约医生号源接口参数》》》"+JSONObject.toJSONString(params));
		String res = HttpUtil.sendPost(wnUrl, params);
		logger.info("获取预约医生号源接口返回成功》》》"+JSONObject.toJSONString(res));
		return res;
	}

	/**
	 * 获取预约医生号序
	 * <p>Title: getAppointDoctorXNo</p>
	 * <p>Description: </p>
	 * @author wzl
	 * @param params
	 * @return
	 */
	public String getAppointDoctorXNo(Map<String, String> params) {
		// TODO Auto-generated method stub
		params.put("yydm", yydm);
		params.put("accesskey", accesskey);
		params.put("action","GETYYYSHX");

		logger.info("获取预约医生号源接口参数》》》"+JSONObject.toJSONString(params));
		String res = HttpUtil.sendPost(wnUrl, params);
		logger.info("获取预约医生号源接口返回成功》》》"+JSONObject.toJSONString(res));
		return res;
	}

	/**
	 * 获取预约科室号源
	 * <p>Title: getAppointDepartmentYNo</p>
	 * <p>Description: </p>
	 * @author wzl
	 * @param params
	 * @return
	 */
	public String getAppointDepartmentYNo(Map<String, String> params) {
		// TODO Auto-generated method stub
		params.put("yydm", yydm);
		params.put("accesskey", accesskey);
		params.put("action","GETYYKSHY");

		logger.info("获取预约科室号源接口参数》》》"+JSONObject.toJSONString(params));
		String res = HttpUtil.sendPost(wnUrl, params);
		logger.info("获取预约科室号源接口返回成功》》》"+JSONObject.toJSONString(res));
		return res;
	}

	/**
	 * 获取预约科室号序
	 * <p>Title: getAppointDepartmentXNo</p>
	 * <p>Description: </p>
	 * @author wzl
	 * @param params
	 * @return
	 */
	public String getAppointDepartmentXNo(Map<String, String> params) {
		// TODO Auto-generated method stub
		params.put("yydm", yydm);
		params.put("accesskey", accesskey);
		params.put("action","GETYYKSHX");

		logger.info("获取预约科室号序接口参数》》》"+JSONObject.toJSONString(params));
		String res = HttpUtil.sendPost(wnUrl, params);
		logger.info("获取预约科室号序接口返回成功》》》"+JSONObject.toJSONString(res));
		return res;
	}

	/**
	 * 门诊预约登记
	 * <p>Title: getOutpatientAppointmentReg</p>
	 * <p>Description: </p>
	 * @author wzl
	 * @param params
	 * @return
	 */
	public String getOutpatientAppointmentReg(Map<String, String> params) {
		// TODO Auto-generated method stub
		params.put("yydm", yydm);
		params.put("accesskey", accesskey);
		params.put("action","PUTMZYY");

		logger.info("获取门诊预约登记接口参数》》》"+JSONObject.toJSONString(params));
		String res = HttpUtil.sendPost(wnUrl, params);
		logger.info("获取门诊预约登记接口返回成功》》》"+JSONObject.toJSONString(res));
		return res;
	}

	/**
	 * 当班出诊科室查询
	 * <p>Title: getDepartmentOnDuty</p>
	 * <p>Description: </p>
	 * @author wzl
	 * @param params
	 * @return
	 */
	public String getDepartmentOnDuty(Map<String, String> params) {
		// TODO Auto-generated method stub
		params.put("yydm", yydm);
		params.put("accesskey", accesskey);
		params.put("action","GETCZDBKSXX");

		logger.info("获取当班出诊科室查询接口参数》》》"+JSONObject.toJSONString(params));
		String res = HttpUtil.sendPost(wnUrl, params);
		logger.info("获取当班出诊科室查询接口返回成功》》》"+JSONObject.toJSONString(res));
		return res;
	}

	/**
	 * 当班出诊科室号源查询
	 * <p>Title: getDepartmentOnDutyYNo</p>
	 * <p>Description: </p>
	 * @author wzl
	 * @param params
	 * @return
	 */
	public String getDepartmentOnDutyYNo(Map<String, String> params) {
		// TODO Auto-generated method stub
		params.put("yydm", yydm);
		params.put("accesskey", accesskey);
		params.put("action","GETDBKSHY");

		logger.info("获取当班出诊科室号源查询接口参数》》》"+JSONObject.toJSONString(params));
		String res = HttpUtil.sendPost(wnUrl, params);
		logger.info("获取当班出诊科室号源查询接口返回成功》》》"+JSONObject.toJSONString(res));
		return res;
	}

	/**
	 * 当班医生信息查询
	 * <p>Title: getDoctorOnDuty</p>
	 * <p>Description: </p>
	 * @author wzl
	 * @param params
	 * @return
	 */
	public String getDoctorOnDuty(Map<String, String> params) {
		// TODO Auto-generated method stub
		params.put("yydm", yydm);
		params.put("accesskey", accesskey);
		params.put("action","GETDBYS");

		logger.info("获取当班医生信息查询接口参数》》》"+JSONObject.toJSONString(params));
		String res = HttpUtil.sendPost(wnUrl, params);
		logger.info("获取当班医生信息查询接口返回成功》》》"+JSONObject.toJSONString(res));
		return res;
	}

	/**
	 * 当班医生号源信息查询
	 * <p>Title: getDoctorOnDutyYNo</p>
	 * <p>Description: </p>
	 * @author wzl
	 * @param params
	 * @return
	 */
	public String getDoctorOnDutyYNo(Map<String, String> params) {
		// TODO Auto-generated method stub
		params.put("yydm", yydm);
		params.put("accesskey", accesskey);
		params.put("action","GETDBYSHY");

		logger.info("获取当班医生号源信息查询接口参数》》》"+JSONObject.toJSONString(params));
		String res = HttpUtil.sendPost(wnUrl, params);
		logger.info("获取当班医生号源信息查询接口返回成功》》》"+JSONObject.toJSONString(res));
		return res;
	}

	/**
	 * 挂号预算
	 * <p>Title: RegisteredBudget</p>
	 * <p>Description: </p>
	 * @author wzl
	 * @param params
	 * @return
	 */
	public String RegisteredBudget(Map<String, String> params) {
		// TODO Auto-generated method stub
		params.put("yydm", yydm);
		params.put("accesskey", accesskey);
		params.put("action","PUTGHYS");

		logger.info("获取挂号预算信息查询接口参数》》》"+JSONObject.toJSONString(params));
		String res = HttpUtil.sendPost(wnUrl, params);
		logger.info("获取挂号预算信息查询接口返回成功》》》"+JSONObject.toJSONString(res));
		return res;
	}

	/**
	 * 挂号结算
	 * <p>Title: RegisteredSettlement</p>
	 * <p>Description: </p>
	 * @author wzl
	 * @param params
	 * @return
	 */
	public String RegisteredSettlement(Map<String, String> params) {
		// TODO Auto-generated method stub
		params.put("yydm", yydm);
		params.put("accesskey", accesskey);
		params.put("action","PUTGHJS");

		logger.info("获取挂号结算信息查询接口参数》》》"+JSONObject.toJSONString(params));
		String res = HttpUtil.sendPost(wnUrl, params);
		logger.info("获取挂号结算信息查询接口返回成功》》》"+JSONObject.toJSONString(res));
		return res;
	}

	/**
	 * 查询门诊候诊信息(patid)
	 * <p>Title: getOutpatientWaitingInfo</p>
	 * <p>Description: </p>
	 * @author wzl
	 * @param params
	 * @return
	 */
	public String getOutpatientWaitingInfo(Map<String, String> params) {
		// TODO Auto-generated method stub
		params.put("yydm", yydm);
		params.put("accesskey", accesskey);
		params.put("action","GETMZPDPAT");

		logger.info("查询门诊候诊信息(patid)接口参数》》》"+JSONObject.toJSONString(params));
		String res = HttpUtil.sendPost(wnUrl, params);
		logger.info("查询门诊候诊信息(patid)接口返回成功》》》"+JSONObject.toJSONString(res));
		return res;
	}

	/**
	 * 查询门诊患者待缴费处方信息(PATID)
	 * <p>Title: getOutpatientToPayPrescription</p>
	 * <p>Description: </p>
	 * @author wzl
	 * @param params
	 * @return
	 */
	public String getOutpatientToPayPrescription(Map<String, String> params) {
		// TODO Auto-generated method stub
		params.put("yydm", yydm);
		params.put("accesskey", accesskey);
		params.put("action","GETMZSFCF");

		logger.info("查询门诊患者待缴费处方信息(PATID)接口参数》》》"+JSONObject.toJSONString(params));
		String res = HttpUtil.sendPost(wnUrl, params);
		logger.info("查询门诊患者待缴费处方信息(PATID)接口返回成功》》》"+JSONObject.toJSONString(res));
		return res;
	}

	/**
	 * 门诊收费预算
	 * <p>Title: getOutpatientFeeBudget</p>
	 * <p>Description: </p>
	 * @author wzl
	 * @param params
	 * @return
	 */
	public String getOutpatientFeeBudget(Map<String, String> params) {
		// TODO Auto-generated method stub
		params.put("yydm", yydm);
		params.put("accesskey", accesskey);
		params.put("action","PUTMZYS");

		logger.info("查询门诊收费预算接口参数》》》"+JSONObject.toJSONString(params));
		String res = HttpUtil.sendPost(wnUrl, params);
		logger.info("查询门诊收费预算接口返回成功》》》"+JSONObject.toJSONString(res));
		return res;
	}

	/**
	 * 门诊收费结算
	 * <p>Title: getOutpatientFeeSettlement</p>
	 * <p>Description: </p>
	 * @author wzl
	 * @param params
	 * @return
	 */
	public String getOutpatientFeeSettlement(Map<String, String> params) {
		// TODO Auto-generated method stub
		params.put("yydm", yydm);
		params.put("accesskey", accesskey);
		params.put("action","PUTMZJS");

		logger.info("查询门诊收费结算接口参数》》》"+JSONObject.toJSONString(params));
		String res = HttpUtil.sendPost(wnUrl, params);
		logger.info("查询门诊收费结算接口返回成功》》》"+JSONObject.toJSONString(res));
		return res;
	}

	/**
	 * 查询门诊患者收费结算信息(PATID)
	 * <p>Title: getOutpatientFeeSettlementInfo</p>
	 * <p>Description: </p>
	 * @author wzl
	 * @param params
	 * @return
	 */
	public String getOutpatientFeeSettlementInfo(Map<String, String> params) {
		// TODO Auto-generated method stub
		params.put("yydm", yydm);
		params.put("accesskey", accesskey);
		params.put("action","GETMZSFJSPAT");

		logger.info("查询门诊患者收费结算信息(PATID)接口参数》》》"+JSONObject.toJSONString(params));
		String res = HttpUtil.sendPost(wnUrl, params);
		logger.info("查询门诊患者收费结算信息(PATID)接口返回成功》》》"+JSONObject.toJSONString(res));
		return res;
	}

	/**
	 * 查询门诊患者收费结算明细信息
	 * <p>Title: getOutpatientFeeSettlementDetail</p>
	 * <p>Description: </p>
	 * @author wzl
	 * @param params
	 * @return
	 */
	public String getOutpatientFeeSettlementDetail(Map<String, String> params) {
		// TODO Auto-generated method stub
		params.put("yydm", yydm);
		params.put("accesskey", accesskey);
		params.put("action","GETMZSFJSMX");

		logger.info("查询门诊患者收费结算明细信息接口参数》》》"+JSONObject.toJSONString(params));
		String res = HttpUtil.sendPost(wnUrl, params);
		logger.info("查询门诊患者收费结算明细信息接口返回成功》》》"+JSONObject.toJSONString(res));
		return res;
	}

	/**
	 * 查询患者预约信息(PATID)
	 * <p>Title: getPatientAppointInfo</p>
	 * <p>Description: </p>
	 * @author wzl
	 * @param params
	 * @return
	 */
	public String getPatientAppointInfo(Map<String, String> params) {
		// TODO Auto-generated method stub
		params.put("ksrq", "20190101");
		params.put("jsrq","20950101");
		params.put("yydm", yydm);
		params.put("accesskey", accesskey);
		params.put("action","GETHZYYXXPAT");

		logger.info("查询患者预约信息(PATID)接口参数》》》"+JSONObject.toJSONString(params));
		String res = HttpUtil.sendPost(wnUrl, params);
		logger.info("查询患者预约信息(PATID)接口返回成功》》》"+JSONObject.toJSONString(res));
		return res;
	}

    /**
     * 查询患者预约信息(预约序号)
     * <p>Title: getPatientAppointInfoByNum</p>
     * <p>Description: </p>
     * @author wzl
     * @param params
     * @return
     */
    public String getPatientAppointInfoByNum(Map<String, String> params) {
        // TODO Auto-generated method stub
        params.put("yydm", yydm);
        params.put("accesskey", accesskey);
        params.put("action","GETHZYYXXYYXH");

        logger.info("查询患者预约信息(预约序号)接口参数》》》"+JSONObject.toJSONString(params));
        String res = HttpUtil.sendPost(wnUrl, params);
        logger.info("查询患者预约信息(预约序号)接口返回成功》》》"+JSONObject.toJSONString(res));
        return res;
    }

	/**
	 * 查询门诊患者挂号记录(PATID)
	 * <p>Title: getPatientRegRecordByPatid</p>
	 * <p>Description: </p>
	 * @author wzl
	 * @param params
	 * @return
	 */
	public String getPatientRegRecordByPatid(Map<String, String> params) {
		// TODO Auto-generated method stub
		params.put("yydm", yydm);
		params.put("accesskey", accesskey);
		params.put("action","GETMZJLPAT");

		logger.info("查询门诊患者挂号记录(PATID)接口参数》》》"+JSONObject.toJSONString(params));
		String res = HttpUtil.sendPost(wnUrl, params);
		logger.info("查询门诊患者挂号记录(PATID)接口返回成功》》》"+JSONObject.toJSONString(res));
		return res;
	}
	
	/**
	 * 预约取消登记
	 * <p>Title: cancelSubmit</p>  
	 * <p>Description: </p>
	 * @author hero  
	 * @param params
	 * @return
	 */
	public String cancelSubmit(Map<String, String> params) {
		// TODO Auto-generated method stub
		params.put("yydm", yydm);
		params.put("accesskey", accesskey);
		params.put("action","PUTMZYYQX");

		logger.info("预约取消登记接口参数》》》"+JSONObject.toJSONString(params));
		String res = HttpUtil.sendPost(wnUrl, params);
		logger.info("预约取消登记接口返回成功》》》"+JSONObject.toJSONString(res));
		return res;
	}


}
