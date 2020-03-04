package com.basic.javaframe.service;


import com.basic.javaframe.entity.Sechos_PopuPerson;

/**
 * 
 * 
 * @author 
 * @date 2019-08-28 10:11:37
 */
public interface Sechos_PopuPersonService {
	
	void save(Sechos_PopuPerson person);
	
	Sechos_PopuPerson getByDetail(String rowGuid);
	
	Sechos_PopuPerson getByPopuPersonGuid(String popuPersonGuid);
	
	Sechos_PopuPerson getByPopuPersonOpenId(String OpenId);

	/**
	 * 获取推广次数
	 * @param promotersGuid
	 * @return
	 */
	int getMyPopuCount(String promotersGuid);
}
