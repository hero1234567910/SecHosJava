package com.basic.javaframe.service;

import com.basic.javaframe.entity.Patient;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author 
 * @date 2019-06-14 13:57:13
 */
public interface PatientService {
	
	List<Patient> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(Patient patient);
	
	void update(Patient patient);
	
	void deleteBatch(String[] rowGuids);
}
