package com.basic.javaframe.service;

import com.basic.javaframe.entity.Sechos_Rechargerecord;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author 
 * @date 2019-07-10 16:02:23
 */
public interface Sechos_RechargerecordService {
	
	List<Sechos_Rechargerecord> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(Sechos_Rechargerecord sechosRechargerecord);
	
	void update(Sechos_Rechargerecord sechosRechargerecord);
	
	void deleteBatch(String[] rowGuids);

	Sechos_Rechargerecord queryByOrderNumber(String num);

	List<Map<String, Object>> selectRechargeStatisical();

	List<Sechos_Rechargerecord> selectAllRecharge();

	Sechos_Rechargerecord getDetailByGuid(String rowGuid);
}
