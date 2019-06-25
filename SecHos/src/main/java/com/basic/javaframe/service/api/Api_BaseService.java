package com.basic.javaframe.service.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.basic.javaframe.controller.Form_TableInfoController;
import com.basic.javaframe.service.RedisService;

public class Api_BaseService {
	
	//定义一个全局的记录器，通过LoggerFactory获取
	protected Logger logger = LoggerFactory.getLogger(Api_BaseService.class);
	
	@Autowired
	RedisService redisService;
	
	@Value(value = "${wx.api.appid}")
	public String appid;
	
	@Value(value = "${wx.api.appsecret}")
	public String appsecret;
	
	@Value(value="${wx.api.mch_id}")
	public String mch_id;
	
	@Value(value="${wx.api.body}")
	public String body;
	
	@Value(value="${wx.api.ip}")
	public String spbill_create_ip;
	
	@Value(value="${wx.api.notify_url}")
	public String notify_url;
	
	@Value(value="${wx.api.key}")
	public String key;
	
	@Value(value="${wx.api.url}")
	public String url;
	
	@Value(value="${wn.yydm}")
	public String yydm;
	
	@Value(value="${wn.accesskey}")
	public String accesskey;
	
	@Value(value="${wn.url}")
	public String wnUrl;
}
