package com.basic.javaframe.service.impl;

import com.basic.javaframe.dao.SecHos_HealthyDao;
import com.basic.javaframe.entity.SecHos_Healthy;
import com.basic.javaframe.service.SecHos_HealthyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("SecHos_HealthyService")
@Transactional
public class SecHos_HealthyServiceImpl implements SecHos_HealthyService {

    @Autowired
    private SecHos_HealthyDao secHos_HealthyDao;

    @Override
    public List<SecHos_Healthy> getList(Map<String, Object> map) {
        return secHos_HealthyDao.getList(map);
    }

    @Override
    public int getCount(Map<String, Object> map) {
        return secHos_HealthyDao.getCount(map);
    }

    @Override
    public void save(SecHos_Healthy secHos_Healthy) {
        secHos_HealthyDao.save(secHos_Healthy);
    }

    @Override
    public void update(SecHos_Healthy secHos_Healthy) {
        secHos_HealthyDao.update(secHos_Healthy);
    }

    @Override
    public void deleteBatch(String[] rowGuids) {
        secHos_HealthyDao.deleteBatch(rowGuids);
    }

    @Override
    public SecHos_Healthy getByDetail(String openId, Date date) {
        return secHos_HealthyDao.getByDetail(openId,date);
    }

    @Override
    public SecHos_Healthy getByOpenId(String openId) {
        return secHos_HealthyDao.getByOpenId(openId);
    }

    @Override
    public SecHos_Healthy getByRowGuid(String rowGuid) {
        return secHos_HealthyDao.getByRowGuid(rowGuid);
    }

    @Override
    public List<SecHos_Healthy> getListByOpenId(String openId) {
        return secHos_HealthyDao.getListByOpenId(openId);
    }

    @Override
    public List<SecHos_Healthy> getListByName(String name) {
        return secHos_HealthyDao.getListByName(name);
    }
}
