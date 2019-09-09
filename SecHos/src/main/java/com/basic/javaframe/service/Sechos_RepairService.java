package com.basic.javaframe.service;

import com.basic.javaframe.entity.Sechos_Repair;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author 
 * @date 2019-08-28 10:11:37
 */
public interface Sechos_RepairService {
	
	List<Sechos_Repair> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(Sechos_Repair sechosRepair);
	
	void update(Sechos_Repair sechosRepair);
	
	void deleteBatch(String[] rowGuids);

	Sechos_Repair getDetailByGuid(String rowGuid);

	List<Sechos_Repair> getListByGuid(String rowGuid);

	void cancelRepair(String rowGuid);

	List<Sechos_Repair> getMaintainList(Map<String, Object> map);

	int getMaintainCount(Map<String, Object> map);

	void successRepair(Map<String, Object> params);

	List<Sechos_Repair> getMyList(Map<String, Object> map);

	int getMyCount(Map<String, Object> map);
}
