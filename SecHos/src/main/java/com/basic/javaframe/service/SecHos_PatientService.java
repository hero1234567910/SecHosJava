package com.basic.javaframe.service;

import com.basic.javaframe.entity.SecHos_Patient;

import java.util.List;
import java.util.Map;

/**
 * @author
 * @date 2019-06-14 13:57:13
 */
public interface SecHos_PatientService {

    List<SecHos_Patient> getList(Map<String, Object> map);

    int getCount(Map<String, Object> map);

    void save(SecHos_Patient secHosPatient);

    void update(SecHos_Patient secHosPatient);

    void deleteBatch(String[] rowGuids);

    SecHos_Patient getPatientByOpenid(String openid);

    SecHos_Patient getPatientByGuid(String rowGuid);

    /**
     * 查询被推广人列表
     *
     * @param promotersGuid
     * @param offset
     * @param limit
     * @return
     */
    List<SecHos_Patient> getPatientListByOpenId(String promotersGuid, Integer offset, Integer limit);

	void updatePj(SecHos_Patient pa);
}
