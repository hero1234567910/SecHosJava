package com.basic.javaframe.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import io.swagger.annotations.Api;

@CrossOrigin
@Api(value = "基础控制器")
@RestController
@RequestMapping("sys/base")
public class BaseController {
	
	//定义一个全局的记录器，通过LoggerFactory获取
    public Logger logger = LoggerFactory.getLogger(BaseController.class);
    
    @Value(value = "${upload.file.path}")
    public String filePath;
    
    @Value(value = "${spring.datasource.driver-class-name}")
   	public String driver;   
   	
   	@Value(value = "${spring.datasource.url}")
   	public String url;   
   	
   	@Value(value = "${spring.datasource.username}")
   	public String userName;
   	
   	@Value(value = "${spring.datasource.password}")
   	public String password;
   	
   	@Value(value = "${upload.file.url}")
   	public String fileUrl;
   	
   	@Value(value="${wx.api.mch_id}")
	public String mch_id;
   	
}
