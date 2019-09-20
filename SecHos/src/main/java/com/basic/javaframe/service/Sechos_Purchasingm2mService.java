package com.basic.javaframe.service;

import com.basic.javaframe.entity.Sechos_Purchasingm2m;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author 
 * @date 2019-09-20 10:55:46
 */
public interface Sechos_Purchasingm2mService {
	
	List<Sechos_Purchasingm2m> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(Sechos_Purchasingm2m sechosPurchasingm2m);
	
	void update(Sechos_Purchasingm2m sechosPurchasingm2m);
	
	void deleteBatch(String[] rowGuids);
}
