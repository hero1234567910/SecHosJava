package com.basic.javaframe.service;

import com.basic.javaframe.entity.HosAddress;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author 
 * @date 2019-05-10 09:20:48
 */
public interface HosAddressService {
	
	List<HosAddress> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(HosAddress hosAddress);
	
	void update(HosAddress hosAddress);
	
	void deleteBatch(String[] rowGuids);

	/**
	 * 根据用户Guid查询地址
	 * @param hosUserGuid
	 * @return
	 */
	List<HosAddress> getAddressListByUserGuid(String hosUserGuid);

}
