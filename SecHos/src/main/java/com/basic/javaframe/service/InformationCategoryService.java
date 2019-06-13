package com.basic.javaframe.service;

import com.alibaba.fastjson.JSONArray;
import com.basic.javaframe.entity.InformationCategory;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author 
 * @date 2019-04-09 11:30:38
 */
public interface InformationCategoryService {
	
	List<InformationCategory> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(InformationCategory informationCategory);
	
	void update(InformationCategory informationCategory);
	
	void deleteBatch(String[] rowGuids);

	JSONArray findTrees();

	List<InformationCategory> getAllList();

	List<InformationCategory> getCategoryByGuid(String categoryGuid);
}
