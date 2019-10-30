package com.basic.javaframe.dao;

import com.basic.javaframe.entity.Sechos_Rechargerecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author 
 * @date 2019-07-10 16:02:23
 */
public interface Sechos_RechargerecordDao extends BaseDao<Sechos_Rechargerecord> {

	Sechos_Rechargerecord queryByOrderNumber(String num);

	/**
	 * 获取昨日门诊挂号记录
	 * @return
	 */
	List<Map<String, Object>> selectRechargeStatisical();

	/**
	 * 获取所有缴费记录
	 */
	List<Sechos_Rechargerecord> selectAllRecharge();

	/**
	 * 根据rowGuid获取一条记录
	 * @param rowGuid
	 * @return
	 */
	Sechos_Rechargerecord getDetailByGuid(@Param("rowGuid") String rowGuid);
}
