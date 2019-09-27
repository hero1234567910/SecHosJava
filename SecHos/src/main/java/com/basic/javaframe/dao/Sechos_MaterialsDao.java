package com.basic.javaframe.dao;

import com.basic.javaframe.entity.Sechos_Drugmaterial;
import com.basic.javaframe.entity.Sechos_Materials;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * 
 * @author 
 * @date 2019-09-26 10:51:41
 */
public interface Sechos_MaterialsDao extends BaseDao<Sechos_Materials> {

    Sechos_Materials getDetailByGuid(@Param("rowGuid") String rowGuid);

    /**
     * 获取所有药品代码
     * @return
     */
    List<String> getMaterialCodes();

    /**
     * 根据药品代码获取药品名称
     * @param materialCode
     * @return
     */
    Sechos_Materials getMaterialName(@Param("materialCode")String materialCode);


    <T> int checkMaterialCode(T t);
}
