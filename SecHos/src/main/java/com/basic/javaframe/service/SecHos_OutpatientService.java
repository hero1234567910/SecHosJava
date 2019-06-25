package com.basic.javaframe.service;

import com.basic.javaframe.entity.SecHos_Outpatient;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author 
 * @date 2019-06-18 08:46:50
 */
public interface SecHos_OutpatientService {
	
	List<SecHos_Outpatient> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(SecHos_Outpatient sechosOutpatient);
	
	void update(SecHos_Outpatient sechosOutpatient);
	
	void deleteBatch(String[] rowGuids);
}
