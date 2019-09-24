package com.basic.javaframe.service;

import com.basic.javaframe.entity.Sechos_Storageamount;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author 
 * @date 2019-09-24 16:23:08
 */
public interface Sechos_StorageamountService {
	
	List<Sechos_Storageamount> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(Sechos_Storageamount sechosStorageamount);
	
	void update(Sechos_Storageamount sechosStorageamount);
	
	void deleteBatch(String[] rowGuids);

	void updateNum(Integer Num,String drugCode);
}
