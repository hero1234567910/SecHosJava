package com.basic.javaframe.service;

import com.basic.javaframe.entity.SecHos_hospitalized;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author 
 * @date 2019-06-17 10:59:07
 */
public interface SecHos_hospitalizedService {
	
	List<SecHos_hospitalized> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(SecHos_hospitalized sechosHospitalized);
	
	void update(SecHos_hospitalized sechosHospitalized);
	
	void deleteBatch(String[] rowGuids);
}
