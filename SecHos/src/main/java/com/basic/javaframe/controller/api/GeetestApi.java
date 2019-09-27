package com.basic.javaframe.controller.api;

import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.basic.javaframe.common.customclass.PassToken;
import com.basic.javaframe.common.utils.R;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value="滑动验证")
@RestController
@CrossOrigin
@RequestMapping(value="sys/getGeetest")
public class GeetestApi {
	
	@PassToken
	@ApiOperation(value="使用Get的方式返回challenge和capthca_id")
	@RequestMapping(value="/getId",produces="application/json;charset=utf-8",method=RequestMethod.GET)
	public String getGeetest(HttpServletRequest request){
		
		
		System.out.println(GeetestConfig.getGeetest_id());
		GeetestLib gtSdk = new GeetestLib(GeetestConfig.getGeetest_id(), GeetestConfig.getGeetest_key(), 
				GeetestConfig.isnewfailback());

		String resStr = "{}";
		
		String userid = "test";
		
		//自定义参数,可选择添加
		HashMap<String, String> param = new HashMap<String, String>();
		param.put("user_id", userid); //网站用户id
		param.put("client_type", "h5"); //web:电脑上的浏览器；h5:手机上的浏览器，包括移动应用内完全内置的web_view；native：通过原生SDK植入APP应用的方式
		param.put("ip_address", "127.0.0.1"); //传输用户请求验证时所携带的IP

		//进行验证预处理
		int gtServerStatus = gtSdk.preProcess(param);
		
		//将服务器状态设置到session中
		request.getSession().setAttribute(gtSdk.gtServerStatusSessionKey, gtServerStatus);
		//将userid设置到session中
		request.getSession().setAttribute("userid", userid);
		
		resStr = gtSdk.getResponseStr();

		
		return resStr;
	}
}
