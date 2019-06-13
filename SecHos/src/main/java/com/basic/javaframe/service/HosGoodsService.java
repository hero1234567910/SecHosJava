package com.basic.javaframe.service;

import com.basic.javaframe.entity.HosGoods;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author wzl
 * @date 2019-04-28 10:46:49
 */
public interface HosGoodsService {

    List<HosGoods> getList(Map<String, Object> map);

    int getCount(Map<String, Object> map);

    void save(HosGoods hosGoods);

    void update(HosGoods hosGoods);

    void deleteBatch(String[] rowGuids);

    /**
     * @param rowGuid
     * @return
     */
    Map<String, Object> getGoodsByGuid(String rowGuid);

    /**
     * 菜品名重复性检测
     *
     * @param t
     * @param <T>
     * @return
     */
    <T> int checkGoods(T t);

    /**
     * 批量上架菜品
     *
     * @param rowGuids
     */
    void goodsUpShelf(String[] rowGuids);

    /**
     * 批量下架菜品
     *
     * @param rowGuids
     */
    void goodsDownShelf(String[] rowGuids);

    /**
     * 删除类别后删除内容
     * @param goodsTypeGuid
     */
    void deleteGoods(String[] goodsTypeGuid);

    /**
     * 通过行号获取名称
     * @param rowGuid
     * @return
     */
    String getGoodsNameByGuid(String rowGuid);

    /**
     * 通过行号获取价格
     * @param rowGuid
     * @return
     */
    BigDecimal getGoodsPriceByGuid(String rowGuid);
}
