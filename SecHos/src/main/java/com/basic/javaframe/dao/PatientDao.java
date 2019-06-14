package com.basic.javaframe.dao;

import com.basic.javaframe.entity.Patient;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * 
 * @author 
 * @date 2019-06-14 13:57:13
 */
public interface PatientDao extends BaseDao<Patient> {
    /**
     * 根据openid查询就诊人
     * @param openid
     * @return
     */
    Patient getPatientByOpenid(@Param("openid") String openid);
	
}
