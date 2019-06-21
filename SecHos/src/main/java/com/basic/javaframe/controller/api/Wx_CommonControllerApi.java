package com.basic.javaframe.controller.api;

import java.io.InputStream;
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

import com.alibaba.fastjson.JSONObject;
import com.basic.javaframe.common.WebSocket.WebSocketServer;
import com.basic.javaframe.common.customclass.PassToken;
import com.basic.javaframe.common.enumresource.PatientEnum;
import com.basic.javaframe.common.utils.R;
import com.basic.javaframe.controller.BaseController;
import com.basic.javaframe.service.Frame_ConfigService;
import com.basic.javaframe.service.RedisService;
import com.basic.javaframe.service.api.Wx_CommonServiceIApi;


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
		
		System.out.println(openid+"   "+openid+"   "+headimgurl);
		
//		//根据openid查询是否有该用户,没有则生成一条新用户，有则返回该用户信息
//		HosUser user = hosUserService.getUserByOpenid(openid);
//		if (user == null) {
//			HosUser hosUser = new HosUser();
//			hosUser.setDelFlag(DelFlagEnum.NDELFLAG.getCode());
//			hosUser.setCreateTime(DateUtil.changeDate(new Date()));
//			String uuid = java.util.UUID.randomUUID().toString();
//			hosUser.setRowGuid(uuid);
//			hosUser.setOpenid(openid);
//			hosUser.setHosUserName(nickname);
//			//设定微信头像
//			hosUser.setHosHeadImgUrl(headimgurl);
//			hosUserService.save(hosUser);
//			return R.ok().put("data", hosUser);
//		}
		return R.ok().put("data", "openid");	
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
		 * 绑定患者
		 * <p>Title: bindingPatient</p>  
		 * <p>Description: </p>
		 * @author hero  
		 * @return
		 */
		@RequestMapping(value="/bingdingPatient",produces="application/json;charset=utf-8",method=RequestMethod.POST)
		public R bindingPatient(@RequestBody Map<String, String> params){
			if (params.get("action") != null) {
				if (PatientEnum.OUTPATIENT.getCode().equals(params.get("action"))) {
					//门诊患者
					//wx_CommonServiceApi.bingdingOutPatient();
				}
				if (PatientEnum.HOSPITALIZED.getCode().equals(params.get("action"))) {
					//住院患者
					
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
		public R advancePay(){
			
			return R.ok();
		}
	
}
