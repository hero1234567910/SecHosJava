package com.basic.javaframe.controller;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.basic.javaframe.common.WebSocket.WebSocketServer;
import com.basic.javaframe.common.customclass.PassToken;
import com.basic.javaframe.common.print.MyPrint;
import com.basic.javaframe.common.utils.DateUtil;
import com.basic.javaframe.common.utils.R;
import com.basic.javaframe.entity.HosOrder;
import com.basic.javaframe.service.HosOrderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin
@RequestMapping("/ceshi")
@Api(value="测试")
public class ceshiController {
	
	@Autowired
	HosOrderService hosOrderService;
	
	@PassToken
	@ApiOperation(value="测试")
	@RequestMapping(value="/test")
	public void test(){
		 
//		PrintUtil.print();
		try {
			WebSocketServer.sendInfo("测试发送消息","20");
		} catch (IOException e) {
			e.printStackTrace();
		}  
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
	
	@PassToken
	@ApiOperation(value="ceshi")
	@RequestMapping(value="/ceshimiao",method=RequestMethod.POST)
	@ResponseBody
	public HosOrder ceshimiao(){
		HosOrder order = hosOrderService.queryByOrderNumber("1558315748576x7egkrr");
		return order;
	}
	
	@PassToken
	@RequestMapping("/print")
	@ResponseBody
	public String print() {
	    MyPrint myPrint = new MyPrint();
	    //设置打印页面数量
	    hosOrderService.printOrder("5c052be8-5df0-4898-ac82-ffcb7f3da485");
	    return "打印...";
	}
	
}
