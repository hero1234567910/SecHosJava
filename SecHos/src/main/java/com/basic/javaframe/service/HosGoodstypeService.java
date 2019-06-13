package com.basic.javaframe.service;

import com.alibaba.fastjson.JSONArray;
import com.basic.javaframe.entity.HosGoodstype;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author 
 * @date 2019-04-26 13:44:20
 */
public interface HosGoodstypeService {
	
	List<HosGoodstype> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(HosGoodstype hosGoodstype);
	
	void update(HosGoodstype hosGoodstype);
	
	void deleteBatch(String[] rowGuids);

	JSONArray findTopGoodsTypes();

	String getByGoodsTypeCode(String goodsTypeCode);

	<T> int checkGoodsType(T t);

	String getTypeNameByGuid(String rowGuid);

	JSONArray getHosGoodsByPtypeCoed(String code);

	List<HosGoodstype> getTopGoodsTypes();

}
