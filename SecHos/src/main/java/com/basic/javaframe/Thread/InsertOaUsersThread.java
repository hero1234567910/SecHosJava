package com.basic.javaframe.Thread;

import java.util.List;

import com.basic.javaframe.common.utils.SpringContextUtils;
import com.basic.javaframe.entity.Frame_User;
import com.basic.javaframe.service.Frame_UserService;

/**
 * 开启线程
* <p>Title: InsertOaUsersThread</p>  
* <p>Description: </p>  
* @author hero
 */
public class InsertOaUsersThread extends Thread{
	List<Frame_User> userlist;
	
	Frame_UserService userService = (Frame_UserService) SpringContextUtils.getBean("userService");
	
	public InsertOaUsersThread(List<Frame_User> userlist) {
		this.userlist = userlist;
	}

	public void run(){
		System.out.println("task start ------");
		//更新oa用户
		userService.updateOaUsers(userlist);
		System.out.println("task end ------");
	}
}
