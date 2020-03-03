package com.basic.javaframe.service;


import java.util.List;
import java.util.Map;

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
}
