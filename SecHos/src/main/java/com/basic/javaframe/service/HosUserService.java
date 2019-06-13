package com.basic.javaframe.service;

import com.basic.javaframe.entity.HosUser;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author 
 * @date 2019-04-29 13:49:42
 */
public interface HosUserService {
	
	List<HosUser> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(HosUser hosUser);
	
	void update(HosUser hosUser);
	
	void deleteBatch(String[] rowGuids);

	HosUser getUserByOpenid(String openid);

	HosUser getUserByGuid(String userGuid);
}
