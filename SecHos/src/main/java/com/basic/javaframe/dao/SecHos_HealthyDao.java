package com.basic.javaframe.dao;

import com.basic.javaframe.entity.SecHos_Healthy;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface SecHos_HealthyDao extends BaseDao<SecHos_Healthy>{

    SecHos_Healthy getByDetail(@Param("openId") String openId,@Param("date") Date date);

    SecHos_Healthy getByOpenId(String openId);

    SecHos_Healthy getByRowGuid(String rowGuid);

    List<SecHos_Healthy> getListByOpenId(String openId);

    List<SecHos_Healthy> getListByName(String name);

    List<SecHos_Healthy> getListByCurrentDay();
}
