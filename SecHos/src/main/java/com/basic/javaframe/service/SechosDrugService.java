package com.basic.javaframe.service;

import com.basic.javaframe.entity.SechosDrug;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author 
 * @date 2019-09-18 14:20:57
 */
public interface SechosDrugService {
	
	List<SechosDrug> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(SechosDrug sechosDrug);
	
	void update(SechosDrug sechosDrug);
	
	void deleteBatch(String[] rowGuids);

	List<SechosDrug> getByPationGuid(String patientRowGuid);

	void deleteByJzlsh(String string);

	List<SechosDrug> getByJzlsh(String ghxh);
}
