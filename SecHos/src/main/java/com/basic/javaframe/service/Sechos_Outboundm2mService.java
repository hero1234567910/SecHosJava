package com.basic.javaframe.service;

import com.basic.javaframe.entity.Sechos_Outboundm2m;
import com.basic.javaframe.entity.Sechos_Purchasingm2m;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author 
 * @date 2019-09-25 10:43:02
 */
public interface Sechos_Outboundm2mService {
	
	List<Sechos_Outboundm2m> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(Sechos_Outboundm2m sechosOutboundm2m);
	
	void update(Sechos_Outboundm2m sechosOutboundm2m);
	
	void deleteBatch(String[] rowGuids);

	List<Sechos_Outboundm2m> getListByPGuid(String outboundGuid);

	<T> int getCountByGuid(T t);
}
