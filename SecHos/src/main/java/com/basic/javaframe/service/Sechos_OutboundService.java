package com.basic.javaframe.service;

import com.basic.javaframe.entity.Sechos_Outbound;
import com.basic.javaframe.entity.Sechos_Procurement;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author 
 * @date 2019-09-25 09:45:11
 */
public interface Sechos_OutboundService {
	
	List<Sechos_Outbound> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(Sechos_Outbound sechosOutbound);
	
	void update(Sechos_Outbound sechosOutbound);
	
	void deleteBatch(String[] rowGuids);

	List<Sechos_Outbound> getList2(Map<String, Object> map);

	int getCount2(Map<String, Object> map);

	Sechos_Outbound getDetailByGuid(String rowGuid);
}
