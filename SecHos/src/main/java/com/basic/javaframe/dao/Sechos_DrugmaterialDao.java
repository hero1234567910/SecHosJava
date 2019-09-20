package com.basic.javaframe.dao;

import com.basic.javaframe.entity.Sechos_Drugmaterial;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * 
 * @author 
 * @date 2019-09-19 10:22:39
 */
public interface Sechos_DrugmaterialDao extends BaseDao<Sechos_Drugmaterial> {

    Sechos_Drugmaterial getDetailByGuid(@Param("rowGuid") String rowGuid);

    /**
     * 获取所有药品代码
     * @return
     */
    List<String> getDrugCodes();

    /**
     * 根据药品代码获取药品名称
     * @param drugCode
     * @return
     */
    String getDrugName(@Param("drugCode")String drugCode);


    <T> int checkDrugCode(T t);
}
