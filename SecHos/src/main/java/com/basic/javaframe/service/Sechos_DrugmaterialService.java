package com.basic.javaframe.service;

import com.basic.javaframe.entity.Sechos_Drugmaterial;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author 
 * @date 2019-09-19 10:22:39
 */
public interface Sechos_DrugmaterialService {
	
	List<Sechos_Drugmaterial> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(Sechos_Drugmaterial sechosDrugmaterial);
	
	void update(Sechos_Drugmaterial sechosDrugmaterial);
	
	void deleteBatch(String[] rowGuids);

	Sechos_Drugmaterial getDetailByGuid(String rowGuid);

	List<String> getDrugCodes();

	String getDrugName(String drugName);
}
