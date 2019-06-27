package com.basic.javaframe.service.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.basic.javaframe.common.exception.MyException;
import com.basic.javaframe.common.utils.HttpUtil;
import com.basic.javaframe.common.utils.R;

import io.swagger.annotations.Info;

@Service("wx_CommonServiceApi")
public class Wx_CommonServiceIApi extends Api_BaseService{
	
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
		if (jsonObject.containsKey("success")) {
			boolean resu = jsonObject.getBoolean("success");
			if (resu) {
				
			}else {
				throw new MyException("查询失败");
			}
		}else {
			throw new MyException("查询住院患者就诊记录接口异常");
		}
		
		params.put("action","PUTZYYJJYS");
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
//		params.put("ptlsh", value);
//		params.put("zffs", value);
//		params.put("zfje", value);
//		params.put("zflsh", value);
//		params.put("zfsj", value);
		
		logger.info("预交金充值接口参数》》》"+JSONObject.toJSONString(params));
		String res = HttpUtil.sendPost(wnUrl, params);
		logger.info("预交金充值接口返回成功》》》"+JSONObject.toJSONString(res));
		return res;
	}
	
}
