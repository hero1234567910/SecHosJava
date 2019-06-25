package com.basic.javaframe.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.basic.javaframe.common.WebSocket.WebSocketServer;
import com.basic.javaframe.common.customclass.PassToken;
import com.basic.javaframe.common.utils.DateUtil;
import com.basic.javaframe.common.utils.R;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin
@RequestMapping("/ceshi")
@Api(value="测试")
public class ceshiController {
	
	@PassToken
	@ApiOperation(value="测试")
	@RequestMapping(value="/test")
	public void test(){
		System.out.println(DateUtil.changeStrToDate3("19901210"));
	}
	
	//页面请求
	@PassToken
	@RequestMapping("/socket/{cid}")
	public ModelAndView socket(@PathVariable String cid) {
		ModelAndView mav=new ModelAndView("/socket");
		mav.addObject("cid", cid);
		return mav;
	}
	
	//推送数据接口
	@PassToken
	@ResponseBody
	@RequestMapping("/socket/push/{cid}")
	public R pushToWeb(@PathVariable String cid,String message) {  
		try {
			WebSocketServer.sendInfo(message,cid);
		} catch (IOException e) {
			e.printStackTrace();
			return R.error(cid+"#"+e.getMessage());
		}  
		return R.ok(cid);
	} 
	
}
