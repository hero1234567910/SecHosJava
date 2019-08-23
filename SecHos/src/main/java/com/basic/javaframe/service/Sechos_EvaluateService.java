package com.basic.javaframe.service;

import com.basic.javaframe.entity.Sechos_Evaluate;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author 
 * @date 2019-07-30 14:29:09
 */
public interface Sechos_EvaluateService {
	
	List<Sechos_Evaluate> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(Sechos_Evaluate sechosEvaluate);
	
	void update(Sechos_Evaluate sechosEvaluate);
	
	void deleteBatch(String[] rowGuids);

	Sechos_Evaluate selectByGuid(String patientRowGuid);

	Sechos_Evaluate selectByPatid(String patid);
}
