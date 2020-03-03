package com.basic.javaframe.service.api;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

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
	
	public void ceshi(){
		System.out.println("ceshi");
	}
	
	/**
	 * 通过网页授权code获取token
	 * <p>Title: code2Token</p>  
	 * <p>Description: </p>
	 * @author hero  
	 * @param code
	 * @return
	 */
	public JSONObject code2Token(String code) {
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
		String refreshToken = json.getString("refresh_token");
		
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
	    
	    JSONObject obj = new JSONObject();
	    obj.put("resultUser", resultUser);
	    obj.put("access_token", access_token);
	    obj.put("refresh_token", refreshToken);
	    return obj;
	}
	
	/**
	 * 获取微信token
	 * <p>Title: getToken</p>  
	 * <p>Description: </p>
	 * @author hero  
	 * @return
	 */
	public String getToken(){
		Map<String, String> params = new HashMap<>();
		params.put("appid", appid);
		params.put("secret", appsecret);
		params.put("grant_type", "client_credential");
		
		logger.info("获取网页授权token接口参数》》》"+JSONObject.toJSONString(params));
	    String result = HttpUtil.sendGet("https://api.weixin.qq.com/cgi-bin/token",params);
	    logger.info("获取网页授权token接口返回成功》》》"+JSONObject.toJSONString(result));
		
		return result;
	}
	
	
	/**
	 * 微信获取用户信息
	 * <p>Title: getWxUserByToken</p>  
	 * <p>Description: </p>
	 * @author hero  
	 * @return
	 */
	public String getWxUserByToken(Map<String, String> params){
		
		params.put("lang", "zh_CN");
		logger.info("获取网页授权用户信息接口参数》》》"+JSONObject.toJSONString(params));
	    String resultUser = HttpUtil.sendGet("https://api.weixin.qq.com/sns/userinfo", params);
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
		params.put("yydm",yydm);
		params.put("accesskey",accesskey);
		params.put("action","PUTHZJD");
		logger.info("绑定门诊患者(患者建档)接口参数》》》"+JSONObject.toJSONString(params));
	    String result = HttpUtil.sendPost(wnUrl,params);
	    logger.info("绑定门诊患者(患者建档)接口返回成功》》》"+JSONObject.toJSONString(result));
		return result;
	}
	
	/**
	 * 更新患者信息
	 * <p>Title: bingdingOutPatient</p>  
	 * <p>Description: </p>
	 * @author hero  
	 * @return
	 */
	public String updatePatient(Map<String, String> params) {
		// 准备参数
		params.put("yydm",yydm);
		params.put("accesskey",accesskey);
		params.put("action","PUTHZXXGX");
		logger.info("更新患者接口参数》》》"+JSONObject.toJSONString(params));
	    String result = HttpUtil.sendPost(wnUrl,params);
	    logger.info("更新患者接口返回成功》》》"+JSONObject.toJSONString(result));
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
		params.put("zffs", "2");
		params.put("port","2");
		
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
		params.put("username",username);
		params.put("userpwd",userpwd);
		logger.info("获取体检报告列表服务参数》》》"+JSONObject.toJSONString(params));
		String res = HttpUtil.sendPost(wsUrl+"/getMedicalReportListService", params);
		logger.info("获取体检报告列表服务返回成功》》》"+JSONObject.toJSONString(res));
		return res;
	}
	
	/**
	 * 发药机接口
	 * <p>Title: getMedicalReportList</p>
	 * <p>Description: </p>
	 * @author wzl
	 * @param params
	 * @return
	 */
	public String task(Map<String,String> params){
		logger.info("发药机接口服务参数》》》"+JSONObject.toJSONString(params));
		String res = HttpUtil.sendPost(wsUrl+"/TaskAdmit", params);
		logger.info("发药机接口服务返回成功》》》"+JSONObject.toJSONString(res));
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
		params.put("username",username);
		params.put("userpwd",userpwd);
		logger.info("获取体检报告信息服务参数》》》"+JSONObject.toJSONString(params));
		String res = HttpUtil.sendPost(wsUrl+"/getMedicalReportInfoService", params);
		logger.info("获取体检报告信息服务返回成功》》》"+JSONObject.toJSONString(res));
		return res;
	}

	/**
	 * 准备参数调用卫宁支付接口
	 * @throws UnsupportedEncodingException 
	 */
	public String placeOrderByWN(int money,String out_trade_no,String openid,Map<String, String> params) throws UnsupportedEncodingException {
		
		//随机数nonce_str
		String nonce_str = getRandomStringByLength(32);
		//标价金额（单位分）
		int total_fee = money;
		//交易类型
		String trade_type = "JSAPI";
		
		SortedMap<Object, Object> sm =
                new TreeMap<Object, Object>();
		
		sm.putAll(params);
		String sign = createSign(sm);
		
		params.put("sign", sign);
		
		logger.info("卫宁下单接口参数》》》》"+JSONObject.toJSONString(params));
		String result = "";
		try {
			System.out.println(wnzfUrl);
			result = HttpUtil.sendPost(wnzfUrl+"/winningpay!execWinningPayTradePayPrecreate.do",params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("返回成功》》》"+JSONObject.toJSONString(result));
		
		return result;
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
		sm.put("sign_type", "MD5");
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
	 * 撤单
	 * <p>Title: backPay</p>  
	 * <p>Description: </p>
	 * @author hero  
	 * @return
	 */
	public String backPay(Map<String, String> params){
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
		
		params.put("cdly", "1");
		params.put("paytype", "7");
		SortedMap<Object, Object> sm =
                new TreeMap<Object, Object>();
		sm.putAll(params);
		String sign = createSign(sm);
		params.put("sign",sign);
		
		logger.info("撤单接口参数》》》"+JSONObject.toJSONString(params));
		String res = HttpUtil.sendPost(wnzfUrl+"/winningpay!execWinningPayCancel.do", params);
		logger.info("撤单接口返回成功》》》"+JSONObject.toJSONString(res));
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
		params.put("port", "1");

		logger.info("获取挂号结算接口参数》》》"+JSONObject.toJSONString(params));
		String res = HttpUtil.sendPost(wnUrl, params);
		logger.info("获取挂号结算接口返回成功》》》"+JSONObject.toJSONString(res));
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
		params.put("port", "1");

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
	
	/**
	 * 刷新token
	 * <p>Title: refreshToken</p>  
	 * <p>Description: </p>
	 * @author hero  
	 * @param params
	 * @return
	 */
	public String refreshToken(Map<String, String> params) {
		// TODO Auto-generated method stub
		params.put("appid", appid);
		params.put("grant_type","refresh_token");
		
		logger.info("刷新token接口参数》》》"+JSONObject.toJSONString(params));
		String res = HttpUtil.sendGet("https://api.weixin.qq.com/sns/oauth2/refresh_token", params);
		logger.info("刷新token接口返回成功》》》"+JSONObject.toJSONString(res));
		return res;
	}

	/**
	 * 住院患者一日清查询
	 * <p>Title: getInpatientOneDayLiquidation</p>
	 * <p>Description: </p>
	 * @author wzl
	 * @param params
	 * @return
	 */
	public String getInpatientOneDayLiquidation(Map<String, String> params) {
		// TODO Auto-generated method stub
		params.put("yydm", yydm);
		params.put("accesskey", accesskey);
		params.put("action","GETZYYRQ");

		logger.info("住院患者一日清查询接口参数》》》"+JSONObject.toJSONString(params));
		String res = HttpUtil.sendPost(wnUrl, params);
		logger.info("住院患者一日清查询接口返回成功》》》"+JSONObject.toJSONString(res));
		return res;
	}

    /**
     * 查询住院患者基本信息(PATID)
     * <p>Title: getInPatientInfoByPatid</p>
     * <p>Description: </p>
     * @author hero
     * @return
     */
    public String getInPatientInfoByPatid(Map<String, String> params) {
        // 准备参数
        params.put("yydm", yydm);
        params.put("accesskey", accesskey);
        params.put("action","GETZYJLPAT");
        logger.info("查询住院患者接口参数》》》"+JSONObject.toJSONString(params));
        String res = HttpUtil.sendPost(wnUrl,params);
        logger.info("查询住院患者接口返回成功》》》"+JSONObject.toJSONString(res));
        return res;
    }

	/**
	 * 查询住院患者基本信息(病历号)
	 * <p>Title: getInPatientInfoByBlh</p>
	 * <p>Description: </p>
	 * @author wzl
	 * @return
	 */
	public String getInPatientInfoByBlh(Map<String, String> params) {
		// 准备参数
		params.put("yydm", yydm);
		params.put("accesskey", accesskey);
		params.put("action","GETZYHZBLH");
		logger.info("查询住院患者接口参数》》》"+JSONObject.toJSONString(params));
		String res = HttpUtil.sendPost(wnUrl,params);
		logger.info("查询住院患者接口返回成功》》》"+JSONObject.toJSONString(res));
		return res;
	}
	
	/**
	 * 查询药品详细信息
	 * <p>Title: getMedInfo</p>  
	 * <p>Description: </p>
	 * @author hero  
	 * @param params
	 * @return
	 */
	public String getMedInfo(Map<String, String> params) {
		params.put("yydm", yydm);
		params.put("accesskey", accesskey);
		params.put("action","GETYPXX");
		logger.info("查询药品详细信息接口参数》》》"+JSONObject.toJSONString(params));
		String res = HttpUtil.sendPost(wnUrl,params);
		logger.info("查询药品详细信息接口返回成功》》》"+JSONObject.toJSONString(res));
		return res;
	}
	
	/**
	 * 查询oa用户数据
	 * <p>Title: updateOA</p>  
	 * <p>Description: </p>
	 * @author hero  
	 * @return
	 */
	public String updateOA() {
		Map<String, String> params = new HashMap<>();
		logger.info("查询oa用户数据接口》》》");
		String res = HttpUtil.sendPost(wsUrl+"/getUsers", params);
		logger.info("查询oa用户数据接口返回成功》》》");
		return res;
	}
	
	/**
	 * 获取二维码关注公众号
	 * <p>Title: getDoctorPic</p>  
	 * <p>Description: </p>
	 * @author hero  
	 * @param accessToken
	 * @return
	 */
	public String getDoctorPic(String accessToken,String popuPersonGuid) {
		Map<String, String> params = new HashMap<>();
		JSONObject js = new JSONObject();
		js.put("scene_str", popuPersonGuid);
		
		JSONObject o = new JSONObject();
		o.put("scene", js);
		
		JSONObject parJson = new JSONObject();
		parJson.put("expire_seconds", 2592000);
		parJson.put("action_name", "QR_STR_SCENE");
		parJson.put("action_info", o);
		
		logger.info("获取二维码关注公众号接口》》》"+parJson);
		String res = HttpUtil.doPost("https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+accessToken,parJson);
		logger.info("获取二维码关注公众号接口返回成功》》》"+res);
		return res;
	}
	
	/**
	 * 根据ticket换取图片
	 * <p>Title: getByTicket</p>  
	 * <p>Description: </p>
	 * @author hero  
	 * @return
	 */
	public String getByTicket(String ticket) {
		Map<String, String> params = new HashMap<>();
		params.put("ticket", ticket);
		
		logger.info("获取二维码接口》》》"+ticket);
		String res = HttpUtil.sendGetInto("https://mp.weixin.qq.com/cgi-bin/showqrcode",params);
		logger.info("获取二维码接口返回成功》》》"+res);
		return res;
	}

}
