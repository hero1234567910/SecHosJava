package com.basic.javaframe.service;

import com.alibaba.fastjson.JSONArray;
import com.basic.javaframe.entity.HosOrder;

import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;

/**
 * 
 * 
 * @author my
 * @date 2019-04-29 08:39:45
 */
public interface HosOrderService {
	
	List<HosOrder> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(HosOrder hosOrder);
	
	void update(HosOrder hosOrder);
	
	void deleteBatch(String[] rowGuids);

	HosOrder queryByOrderNumber(String orderNumber);
	
	/**
	 * 根据rowGuid查询
	 * <p>Title: getByRowGuid</p>  
	 * <p>Description: </p>
	 * @author hero  
	 * @param rowGuid
	 * @return
	 */
	HosOrder getByRowGuid(String rowGuid);

	/**
	 * 根据商户订单号查询订单
	 * @param merchantNumber
	 * @return
	 */
	HosOrder getOrderByMerchantNumber(String merchantNumber);

	/**
	 * 查询所有为已支付的订单信息
	 * @param orderStatus
	 * @return
	 */
	HosOrder getOrderByStatus1(int orderStatus);

	/**
	 * 根据用户Guid查询订单
	 * @param orderUserGuid
	 * @return
	 */
	List<HosOrder> getOrderListByUserGuid(String orderUserGuid);

	/**
	 * 根据用户Guid查询商品标识
	 * @param orderUserGuid
	 * @return
	 */
	void getOrderItemByUserGuid(String orderUserGuid);


	/**
	 * 根据订单Guid查询订单详细信息
	 * @param rowGuid
	 * @return
	 */
	HosOrder getOrderDetailByGuid(String rowGuid);

	void printOrder(String rowGuid);

	List<HosOrder> selectAllOrder();
	
	/**
	 * 每隔半小时统计
	 * <p>Title: selectOrderStatisical</p>  
	 * <p>Description: </p>
	 * @author hero  
	 * @return
	 */
	List<Map<String, Object>> selectOrderStatisical();

	void updateOrderStatus(String[] rowGuids);

//	HosOrder getOrderInfoByRowGuid(String rowGuid);

}
