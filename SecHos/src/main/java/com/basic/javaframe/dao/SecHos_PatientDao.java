package com.basic.javaframe.dao;

import com.basic.javaframe.entity.SecHos_Patient;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * 
 * @author 
 * @date 2019-06-14 13:57:13
 */
public interface SecHos_PatientDao extends BaseDao<SecHos_Patient> {
    /**
     * 根据openid查询就诊人
     * @param openid
     * @return
     */
    SecHos_Patient getPatientByOpenid(@Param("openid") String openid);

    /**
     * 根据rowGuid查询就诊人
     * @param rowGuid
     * @return
     */
    SecHos_Patient getPatientByGuid(@Param("rowGuid") String rowGuid);

    /**
     * 查询被推广人列表
     * @param promotersGuid
     * @param offset
     * @param limit
     * @return
     */
    List<SecHos_Patient> getPatientListByOpenId(@Param("promotersGuid") String promotersGuid , @Param("offset") Integer offset, @Param("limit")Integer limit);

	void updatePj(SecHos_Patient pa);
}
