package com.basic.javaframe.service.api;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.basic.javaframe.common.exception.MyException;
import com.basic.javaframe.common.utils.HttpUtil;
import com.basic.javaframe.common.utils.MD5Util;
import com.basic.javaframe.common.utils.R;
import com.basic.javaframe.common.utils.XMLUtil;
import com.basic.javaframe.entity.HosOrder;
import com.basic.javaframe.service.RedisService;
import com.mysql.fabric.xmlrpc.base.Params;

@Service("wx_CommonServiceApi")
public class Wx_CommonServiceIApi extends Api_BaseService{
	
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
		System.out.println(createSign(sm));
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
	 * 通过网页授权code获取token
	 * <p>Title: code2Token</p>  
	 * <p>Description: </p>
	 * @author hero  
	 * @param code
	 * @return
	 */
	public String code2Token(String code) {
//		if (redisService.exists("access_token") && redisService.get("access_token")!= null) {
//			
//		}
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
	 * 退款
	 * <p>Title: refund</p>  
	 * <p>Description: </p>
	 * @author hero  
	 * @param out_trade_no
	 * @param order 
	 * @return
	 */
	public String refund(String out_trade_no, HosOrder order) {
		//准备参数
		
		//随机数nonce_str
		String nonce_str = getRandomStringByLength(32);
		//退款单号
		String out_refund_no = UUID.randomUUID().toString();
		
		SortedMap<Object, Object> sm =
                new TreeMap<Object, Object>();
		sm.put("appid", appid);
		sm.put("mch_id", mch_id);
		sm.put("nonce_str", nonce_str);
		sm.put("out_trade_no", out_trade_no);
		sm.put("out_refund_no", out_refund_no);
		sm.put("notify_url", notify_url);
		//退款金额
		sm.put("refund_fee", order.getOrderMoney().intValue());
		//订单金额
		sm.put("total_fee", order.getOrderMoney().intValue());
		sm.put("sign", createSign(sm));
		
		String xml = XMLUtil.mapToXml(sm, false);
		logger.info("退款接口参数》》》"+JSONObject.toJSONString(xml));
		String result = "";
		try {
			result = HttpUtil.sendXmlMsg("https://api.mch.weixin.qq.com/secapi/pay/refund", xml);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("返回成功》》》"+JSONObject.toJSONString(result));
		
		return result;
	}
	
}
