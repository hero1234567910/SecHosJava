package com.basic.javaframe.dao;

import org.apache.ibatis.annotations.Param;

import com.alibaba.fastjson.JSONArray;
import com.basic.javaframe.entity.HosOrder;

import javax.validation.constraints.Size;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author 
 * @date 2019-04-29 08:39:45
 */
public interface HosOrderDao extends BaseDao<HosOrder> {

	HosOrder queryByOrderNumber(@Param("orderNumber")String orderNumber);

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
	List<HosOrder> getOrderListByUserGuid(@Param("orderUserGuid") String orderUserGuid);

	/**
	 * 根据用户Guid查询商品标识
	 * @param orderUserGuid
	 * @return
	 */
	String getOrderItemByUserGuid(String orderUserGuid);


	/**
	 * 根据订单Guid查询订单详细信息
	 * @param rowGuid
	 * @return
	 */
	HosOrder getOrderDetailByGuid(@Param("rowGuid") String rowGuid);

	
	HosOrder getByRowGuid(@Param("rowGuid")String rowGuid);

	List<HosOrder> selectAllOrder();

	List<Map<String, Object>> selectOrderStatisical();

	void updateOrderStatus(@Param("rowGuids")String[] rowGuids);
	
//	/**
//	 * 根据guid查询订单详细信息
//	 * <p>Title: getOrderInfoByRowGuid</p>  
//	 * <p>Description: </p>
//	 * @author hero  
//	 * @param rowGuid
//	 * @return
//	 */
//	HosOrder getOrderInfoByRowGuid(@Param("rowGuid")String rowGuid);

}
