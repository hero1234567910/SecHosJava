package com.basic.javaframe.service;

import com.basic.javaframe.entity.Sechos_Drugmaterial;
import com.basic.javaframe.entity.Sechos_Materials;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author 
 * @date 2019-09-26 10:51:41
 */
public interface Sechos_MaterialsService {
	
	List<Sechos_Materials> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(Sechos_Materials sechosMaterials);
	
	void update(Sechos_Materials sechosMaterials);
	
	void deleteBatch(String[] rowGuids);

	Sechos_Materials getDetailByGuid(String rowGuid);

	List<String> getMaterialCodes();

	Sechos_Materials getMaterialName(String drugCode);

	<T> int checkMaterialCode(T t);
}
