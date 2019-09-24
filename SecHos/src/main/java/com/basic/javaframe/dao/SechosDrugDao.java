package com.basic.javaframe.dao;

import java.util.List;

import com.basic.javaframe.entity.SechosDrug;

/**
 * 
 * 
 * @author 
 * @date 2019-09-18 14:20:57
 */
public interface SechosDrugDao extends BaseDao<SechosDrug> {

	List<SechosDrug> getByPationGuid(String patientRowGuid);

	void deleteByJzlsh(String string);

	List<SechosDrug> getByJzlsh(String ghxh);
	
}
