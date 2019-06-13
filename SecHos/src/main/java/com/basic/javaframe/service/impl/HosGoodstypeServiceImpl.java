package com.basic.javaframe.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.basic.javaframe.common.exception.MyException;
import com.basic.javaframe.dao.HosGoodsDao;
import com.basic.javaframe.dao.HosGoodstypeDao;
import com.basic.javaframe.entity.Frame_Dept;
import com.basic.javaframe.entity.HosGoods;
import com.basic.javaframe.entity.HosGoodstype;
import com.basic.javaframe.service.HosGoodstypeService;


@Service("hosGoodstypeService")
@Transactional
public class HosGoodstypeServiceImpl implements HosGoodstypeService {
    @Autowired
    private HosGoodstypeDao hosGoodstypeDao;
    
    @Autowired
    private HosGoodsDao hosgoodsDao;

    @Override
    public List<HosGoodstype> getList(Map<String, Object> map) {
        return hosGoodstypeDao.getList(map);
    }

    @Override
    public int getCount(Map<String, Object> map) {
        return hosGoodstypeDao.getCount(map);
    }

    @Override
    public void save(HosGoodstype hosGoodstype) {
        hosGoodstypeDao.save(hosGoodstype);
    }

    @Override
    public void update(HosGoodstype hosGoodstype) {
        hosGoodstypeDao.update(hosGoodstype);
    }

    @Override
    public void deleteBatch(String[] rowGuids) {
    	//根据ids获取是否有子集
    	List<String> list = new ArrayList<>(Arrays.asList(rowGuids));
    	for (String it : rowGuids) {
    		rowGuids = deleteFrameDeptById2(it, rowGuids);
		}
        hosGoodstypeDao.deleteBatch(rowGuids);
    }
    
    //递归获取
    public String[] deleteFrameDeptById2(String rowGuid,String[] rowGuids){
    	List<String> list = new ArrayList<>(Arrays.asList(rowGuids));
    	HosGoodstype goodstype =  hosGoodstypeDao.getByRowGuid(rowGuid);
    	if (goodstype != null) {
    		List<HosGoodstype> goodstypes = hosGoodstypeDao.getByPgoodsCode(goodstype.getGoodsTypeCode());
        	if (goodstypes != null &&goodstypes.size() != 0) {
    			for (int i = 0; i < goodstypes.size(); i++) {
    				list.add(goodstypes.get(i).getRowGuid());
    				rowGuids = new String[list.size()];
    		    	list.toArray(rowGuids);//3,5   //3,5,1   
    		    	rowGuids = deleteFrameDeptById2(goodstypes.get(i).getRowGuid(),rowGuids);
    			}
    		}else{
    			//最底层
    			//根据是否有子去判断是否删除
    			List<HosGoods> hosGoods = hosgoodsDao.getGoodsByTypeGuid(rowGuid);
    			if (hosGoods != null && hosGoods.size() != 0) {
					throw new MyException("该分类下还有菜品存在，请先删除对应的菜品");
				}
    		}
		}
    	return rowGuids;
    }

    @Override
    public JSONArray findTopGoodsTypes() {
        List<HosGoodstype> goodsTypeTrees = hosGoodstypeDao.findTopGoodsTypes();
        return getChildGoodsTypes(goodsTypeTrees);
    }

    public JSONArray getChildGoodsTypes(List<HosGoodstype> goodsTypeTrees) {
        JSONArray array = new JSONArray();
        for (HosGoodstype hosGoodstype : goodsTypeTrees){
            JSONObject json = new JSONObject();
            json.put("typeName",hosGoodstype.getTypeName());
            json.put("name",hosGoodstype.getTypeName());
            json.put("goodsTypeCode",hosGoodstype.getGoodsTypeCode());
            json.put("pgoodsTypeCode",hosGoodstype.getPgoodsTypeCode());
            json.put("rowGuid",hosGoodstype.getRowGuid());
            List<HosGoodstype> childGoodsType = hosGoodstypeDao.getByPgoodsCode(hosGoodstype.getGoodsTypeCode());
            json.put("children",getChildGoodsTypes(childGoodsType));
            array.add(json);
        }
        return array;
    }

    @Override
    public String getByGoodsTypeCode(String goodsTypeCode) {
        return hosGoodstypeDao.getByGoodsTypeCode(goodsTypeCode);
    }

    @Override
    public <T> int checkGoodsType(T t) {
        return hosGoodstypeDao.checkGoodsType(t);
    }

    @Override
    public String getTypeNameByGuid(String rowGuid) {
        return hosGoodstypeDao.getTypeNameByGuid(rowGuid);
    }

	@Override
	public JSONArray getHosGoodsByPtypeCoed(String code) {
		// TODO Auto-generated method stub
		List<HosGoodstype> childGoodsType = hosGoodstypeDao.getByPgoodsCode(code);
		List<HosGoods> goodsList = new ArrayList<>();
		JSONArray array = new JSONArray();
		if (childGoodsType == null || childGoodsType.size() == 0) {
			return array;
		}
		//根据所有子类去查询所有菜品
		for (int i = 0; i < childGoodsType.size(); i++) {
			JSONObject obj = new JSONObject();
			goodsList = hosgoodsDao.getGoodsByTypeGuid(childGoodsType.get(i).getRowGuid());
			obj.put("goodsList", goodsList);
			obj.put("goodsType", childGoodsType.get(i));
			array.add(obj);
		}
		return array;
	}
	
	public JSONArray getChildGoodsTypesBy(List<HosGoodstype> goodsTypeTrees) {
        JSONArray array = new JSONArray();
        for (HosGoodstype hosGoodstype : goodsTypeTrees){
            JSONObject json = new JSONObject();
            json.put("typeName",hosGoodstype.getTypeName());
            json.put("name",hosGoodstype.getTypeName());
            json.put("goodsTypeCode",hosGoodstype.getGoodsTypeCode());
            json.put("rowGuid",hosGoodstype.getRowGuid());
            List<HosGoodstype> childGoodsType = hosGoodstypeDao.getByPgoodsCode(hosGoodstype.getGoodsTypeCode());
            json.put("children",getChildGoodsTypes(childGoodsType));
            array.add(json);
        }
        return array;
    }

	@Override
	public List<HosGoodstype> getTopGoodsTypes() {
		 List<HosGoodstype> goodsTypeTrees = hosGoodstypeDao.findTopGoodsTypes();
		return goodsTypeTrees;
	}

}
