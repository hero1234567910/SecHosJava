package com.basic.javaframe.service;

import com.basic.javaframe.entity.Sechos_Repairsatisfaction;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author 
 * @date 2019-09-05 16:14:01
 */
public interface Sechos_RepairsatisfactionService {
	
	List<Sechos_Repairsatisfaction> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(Sechos_Repairsatisfaction sechosRepairsatisfaction);
	
	void update(Sechos_Repairsatisfaction sechosRepairsatisfaction);
	
	void deleteBatch(String[] rowGuids);

	Sechos_Repairsatisfaction getDetailByGuid(String rowGuid);

	List<Sechos_Repairsatisfaction> getMySatList(Map<String, Object> map);

	int getMySatCount(Map<String, Object> map);
}
