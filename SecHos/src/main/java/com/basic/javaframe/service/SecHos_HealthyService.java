package com.basic.javaframe.service;

import com.basic.javaframe.entity.SecHos_Healthy;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface SecHos_HealthyService {

    List<SecHos_Healthy> getList(Map<String, Object> map);

    int getCount(Map<String, Object> map);

    void save(SecHos_Healthy secHos_Healthy);

    void update(SecHos_Healthy secHos_Healthy);

    void deleteBatch(String[] rowGuids);

    SecHos_Healthy getByDetail(String openId, Date date);

    SecHos_Healthy getByOpenId(String openId);

    SecHos_Healthy getByRowGuid(String rowGuid);

    List<SecHos_Healthy> getListByOpenId(String openId);

    List<SecHos_Healthy> getListByName(String name);

    List<SecHos_Healthy> getListByCurrentDay(Date date);
}
