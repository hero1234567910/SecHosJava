package com.basic.javaframe.service;

import com.basic.javaframe.entity.Sechos_Stockremoval;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author 
 * @date 2019-09-25 14:43:58
 */
public interface Sechos_StockremovalService {
	
	List<Sechos_Stockremoval> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(Sechos_Stockremoval sechosStockremoval);
	
	void update(Sechos_Stockremoval sechosStockremoval);
	
	void deleteBatch(String[] rowGuids);

	int insertRemove(String outboundGuid);

	/**
	 * 入库表更新时间与入库人
	 * @param sechosStockremoval
	 * @return
	 */
	int insertOutDate(Sechos_Stockremoval sechosStockremoval);

	/**
	 * 根据单号获取入库材料列表
	 * @param outboundGuid
	 * @return
	 */
	List<Sechos_Stockremoval> getListByOutboundGuid(String outboundGuid);

	void deleteInsert(String outboundGuid);
}
