package com.basic.javaframe.service;

import com.basic.javaframe.entity.HosOrderitem;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author 
 * @date 2019-04-29 13:49:14
 */
public interface HosOrderitemService {
	
	List<HosOrderitem> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(HosOrderitem hosOrderitem);
	
	void update(HosOrderitem hosOrderitem);
	
	void deleteBatch(String[] rowGuids);

	String getGoodsByItemGuid(String orderGuid);
}
