package com.basic.javaframe.dao;

import com.basic.javaframe.entity.Sechos_Drugmaterial;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * 
 * @author 
 * @date 2019-09-19 10:22:39
 */
public interface Sechos_DrugmaterialDao extends BaseDao<Sechos_Drugmaterial> {

    Sechos_Drugmaterial getDetailByGuid(@Param("rowGuid") String rowGuid);
	
}
