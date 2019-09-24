package com.basic.javaframe.service;

import com.basic.javaframe.entity.Sechos_Procurement;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author 
 * @date 2019-09-19 15:26:43
 */
public interface Sechos_ProcurementService {
	
	List<Sechos_Procurement> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(Sechos_Procurement sechosProcurement);
	
	void update(Sechos_Procurement sechosProcurement);
	
	void deleteBatch(String[] rowGuids);

	List<Sechos_Procurement> getList2(Map<String, Object> map);

	int getCount2(Map<String, Object> map);

	Sechos_Procurement getDetailByGuid(String rowGuid);

	int changeOverDueMark(String[] strings);
}
