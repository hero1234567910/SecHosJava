package com.basic.javaframe.service;

import com.basic.javaframe.entity.Sechos_Putinstorage;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author 
 * @date 2019-09-24 09:43:42
 */
public interface Sechos_PutinstorageService {
	
	List<Sechos_Putinstorage> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(Sechos_Putinstorage sechosPutinstorage);
	
	void update(Sechos_Putinstorage sechosPutinstorage);
	
	void deleteBatch(String[] rowGuids);

	void insertStorage(String purchaseGuid);

	void insertInDate(Sechos_Putinstorage sechosPutinstorage);
}
