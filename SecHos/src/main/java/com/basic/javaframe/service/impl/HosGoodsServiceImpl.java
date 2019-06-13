package com.basic.javaframe.service.impl;

import com.basic.javaframe.dao.HosGoodsDao;
import com.basic.javaframe.dao.HosGoodstypeDao;
import com.basic.javaframe.entity.HosGoods;
import com.basic.javaframe.service.HosGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wzl
 */
@Service("wxHosGoodsService")
@Transactional
public class HosGoodsServiceImpl implements HosGoodsService {
    @Autowired
    private HosGoodsDao hosGoodsDao;

    @Autowired
    private HosGoodstypeDao hosGoodstypeDao;

    @Override
    public List<HosGoods> getList(Map<String, Object> map) {
        return hosGoodsDao.getList(map);
    }

    @Override
    public int getCount(Map<String, Object> map) {
        return hosGoodsDao.getCount(map);
    }

    @Override
    public void save(HosGoods hosGoods) {
        hosGoodsDao.save(hosGoods);
    }

    @Override
    public void update(HosGoods hosGoods) {
        hosGoodsDao.update(hosGoods);
    }

    @Override
    public void deleteBatch(String[] rowGuids) {
        hosGoodsDao.deleteBatch(rowGuids);
    }

    @Override
    public Map<String, Object> getGoodsByGuid(String rowGuid) {
        String goodsTypeGuid = hosGoodsDao.getGoodsByGuid(rowGuid);
        String goodsTypeName = hosGoodstypeDao.getTypeNameByGuid(goodsTypeGuid);
        Map<String,Object> map = new HashMap<>();
        map.put("goodsTypeGuid",goodsTypeGuid);
        map.put("goodsTypeName",goodsTypeName);
        return map;
    }

    @Override
    public <T> int checkGoods(T t) {
        return hosGoodsDao.checkGoods(t);
    }

    @Override
    public void goodsUpShelf(String[] rowGuids) {
        hosGoodsDao.goodsUpShelf(rowGuids);
    }

    @Override
    public void goodsDownShelf(String[] rowGuids){
        hosGoodsDao.goodsDownShelf(rowGuids);
    }

    @Override
    public void deleteGoods(String[] goodsTypeGuid) {
        hosGoodsDao.deleteGoods(goodsTypeGuid);
    }

    @Override
    public String getGoodsNameByGuid(String rowGuid) {
        return hosGoodsDao.getGoodsNameByGuid(rowGuid);
    }

    @Override
    public BigDecimal getGoodsPriceByGuid(String rowGuid) {
        return hosGoodsDao.getGoodsPriceByGuid(rowGuid);
    }

}
