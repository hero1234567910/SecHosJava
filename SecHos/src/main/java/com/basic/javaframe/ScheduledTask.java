package com.basic.javaframe;

import java.awt.Frame;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.basic.javaframe.Thread.InsertOaUsersThread;
import com.basic.javaframe.common.utils.R;
import com.basic.javaframe.common.utils.SpringContextUtils;
import com.basic.javaframe.controller.ceshiController;
import com.basic.javaframe.entity.Frame_User;
import com.basic.javaframe.service.Frame_ModuleRightService;
import com.basic.javaframe.service.Frame_ModuleService;
import com.basic.javaframe.service.Frame_UserService;
import com.basic.javaframe.service.api.Wx_CommonServiceIApi;
import com.basic.javaframe.service.impl.Frame_ModuleServiceImpl;


/**
  * 定时任务
  * @ClassName: ScheduledTask 
  * @Description: 定时任务
  * @author keeny
  * @date 2018年12月17日 下午1:30:40 
  *
 */
@Component
public class ScheduledTask {

	private static final Logger logger = LoggerFactory.getLogger(ScheduledTask.class);
	
//	Wx_CommonServiceIApi wx_CommonServiceApi = (Wx_CommonServiceIApi) SpringContextUtils.getBean("wx_CommonServiceApi");
	
	@Autowired
	Wx_CommonServiceIApi wx_CommonServiceApi;
	
	@Autowired
	Frame_UserService userService;
	/**
	 *    定时更新oa用户数据
	 * @Title: financeStatic 
	 * @Description: 定时更新oa用户数据
	 * @param     设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	@Scheduled(cron = "0 0 0,12 * * ?")
	public void financeStatic() {
		String res = wx_CommonServiceApi.updateOA();
		JSONArray array = JSONArray.parseArray(res);
		if (array.size() == 0) {
			logger.info("更新失败");
		}
		
		List<Frame_User> userList = new ArrayList<>();
		Frame_User user;
		for (int i = 0; i < array.size(); i++) {
			JSONObject obj = array.getJSONObject(i);
			user = new Frame_User();
			user.setRowId(obj.getIntValue("userCode"));
			user.setDelFlag(obj.getIntValue("delFlag"));
			user.setRowGuid(obj.getString("rowGuid"));
			user.setDuty(obj.getString("duty"));
			user.setCreateTime(new Date());
			user.setDeptName(obj.getString("deptName"));
			if (obj.getString("mobile") != null) {
				user.setMobile(obj.getString("mobile"));
			}
			user.setUserName(obj.getString("displayName"));
			user.setLoginId(obj.getString("loginID"));
			user.setPassword(obj.getString("password"));
			
			//查询每一个推广次数
			Frame_User u = userService.getOAUserByLoginId(obj.getString("loginID"));
			if(u != null){
				user.setExtensionCount(u.getExtensionCount());
			}else
				user.setExtensionCount(0);
			
			userList.add(user);
		}
		
		//开设线程
		Thread thread = new InsertOaUsersThread(userList);
		thread.run();
		
		logger.info("同步成功");
		
	}
}
