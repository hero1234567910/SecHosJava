package com.basic.javaframe.dao;

import com.basic.javaframe.entity.HosGoodstype;

import java.util.List;

/**
 * 
 * 
 * @author wzl
 * @date 2019-04-26 13:44:20
 */
public interface HosGoodstypeDao extends BaseDao<HosGoodstype> {

    /**
     * 查询所有顶级食品类别
     * <p>Title: findTopGoodsType</p>
     * <p>Description: 查询所有顶级食品类别</p>
     * @author wzl
     * @return
     */
    List<HosGoodstype> findTopGoodsTypes();

    List<HosGoodstype> getByPgoodsCode(String PgoodsCode);

    /**
     * 通过食品编号查询上级食品
     * @param goodsTypeCode*/
    String getByGoodsTypeCode(String goodsTypeCode);

    <T> int checkGoodsType(T t);

    /**
     * 通过guid 获取食品
     */
    String getTypeNameByGuid(String rowGuid);
    
    HosGoodstype getByRowGuid(String rowGuid);


}
