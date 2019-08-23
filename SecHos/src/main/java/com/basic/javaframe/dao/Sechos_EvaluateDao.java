package com.basic.javaframe.dao;

import com.basic.javaframe.entity.Sechos_Evaluate;

/**
 * 
 * 
 * @author 
 * @date 2019-07-30 14:29:09
 */
public interface Sechos_EvaluateDao extends BaseDao<Sechos_Evaluate> {

	Sechos_Evaluate selectByGuid(String patientRowGuid);

	Sechos_Evaluate selectByPatid(String patid);
	
}
