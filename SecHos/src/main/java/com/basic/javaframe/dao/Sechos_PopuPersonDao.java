package com.basic.javaframe.dao;

import org.apache.ibatis.annotations.Param;

import com.basic.javaframe.entity.Sechos_Consultation;
import com.basic.javaframe.entity.Sechos_PopuPerson;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author 
 * @date 2019-06-26 14:30:13
 */
public interface Sechos_PopuPersonDao extends BaseDao<Sechos_PopuPersonDao> {

	void save(Sechos_PopuPerson person);

	Sechos_PopuPerson getByDetail(String rowGuid);

	Sechos_PopuPerson getByPopuPersonGuid(String popuPersonGuid);

	Sechos_PopuPerson getByPopuPersonOpenId(String openId);
    
}
