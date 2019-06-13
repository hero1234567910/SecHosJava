package com.basic.javaframe.dao;

import com.basic.javaframe.entity.HosGoods;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 
 * 
 * @author wzl
 * @date 2019-04-28 10:46:49
 */
public interface HosGoodsDao extends BaseDao<HosGoods> {

    /**
     * 菜品名重复检测
     *
     * @param t
     */
    <T> int checkGoods(T t);

    String getGoodsByGuid(@Param("rowGuid")String rowGuid);

    /**
     * 批量上架
     * @param rowGuids
     * @return
     */
    int goodsUpShelf(String[] rowGuids);

    /**
     * 批量下架
     * @param rowGuids
     * @return
     */
    int goodsDownShelf(String[] rowGuids);

    /**
     * 删除类别时删除所有内容
     * @param goodsTypeGuid
     * @return
     */
    int deleteGoods(String[] goodsTypeGuid);
    
    List<HosGoods> getGoodsByTypeGuid(@Param("guid") String guid);

    /**
     * 通过行标获取名称
     * @param goodsName
     * @return
     */
    String getGoodsNameByGuid(String goodsName);

    /**
     * 通过行标获取价格
     * @param rowGuid
     * @return
     */
    BigDecimal getGoodsPriceByGuid(String rowGuid);
	
}
