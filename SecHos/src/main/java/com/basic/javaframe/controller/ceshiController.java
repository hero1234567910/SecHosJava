package com.basic.javaframe.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.basic.javaframe.Thread.CeshiThread;
import com.basic.javaframe.common.WebSocket.WebSocketServer;
import com.basic.javaframe.common.customclass.PassToken;
import com.basic.javaframe.common.utils.DateUtil;
import com.basic.javaframe.common.utils.HttpUtil;
import com.basic.javaframe.common.utils.R;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin
@RequestMapping("/ceshi")
@Api(value="测试")
public class ceshiController {
	
	@Value(value="${wservice.url}")
	public String wsUrl;
	
	/**
	 * 获取项目根路径
	 *
	 * @return
	 */
	private  String getResourceBasePath() {
	    // 获取跟目录
	    File path = null;
	    try {
	        path = new File(ResourceUtils.getURL("classpath:").getPath());
	    } catch (FileNotFoundException e) {
	        // nothing to do
	    }
	    if (path == null || !path.exists()) {
	        path = new File("");
	    }

	    String pathStr = path.getAbsolutePath();
	    // 如果是在eclipse中运行，则和target同级目录,如果是jar部署到服务器，则默认和jar包同级
	    pathStr = pathStr.replace("\\target\\classes", "");

	    return pathStr;
	}
	
	
	@PassToken
	@ApiOperation(value="测试")
	@RequestMapping(value="/test")
	public String test(Map<String, String> parmas){
		String res = HttpUtil.sendPost("http://10.20.200.62:8081/iis/DecryptApi/Encryp/getEncrypPassword",parmas);
		System.out.println(res);
		return res;
	}
	
	@PassToken
	@ApiOperation(value="测试")
	@RequestMapping(value="/test2")
	public void test2(){
		Thread thread = new CeshiThread();
		thread.run();
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
