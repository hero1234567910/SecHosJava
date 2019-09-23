package com.basic.javaframe.service.impl;

import com.basic.javaframe.entity.Sechos_Purchasingm2m;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import com.basic.javaframe.dao.Sechos_Purchasingm2mDao;
import com.basic.javaframe.service.Sechos_Purchasingm2mService;




@Service("sechosPurchasingm2mService")
@Transactional
public class Sechos_Purchasingm2mServiceImpl implements Sechos_Purchasingm2mService {
	@Autowired
	private Sechos_Purchasingm2mDao sechosPurchasingm2mDao;

	@Override
	public List<Sechos_Purchasingm2m> getList(Map<String, Object> map){
		return sechosPurchasingm2mDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return sechosPurchasingm2mDao.getCount(map);
	}

	@Override
	public void save(Sechos_Purchasingm2m sechosPurchasingm2m){
		sechosPurchasingm2mDao.save(sechosPurchasingm2m);
	}

	@Override
	public void update(Sechos_Purchasingm2m sechosPurchasingm2m){
		sechosPurchasingm2mDao.update(sechosPurchasingm2m);
	}

	@Override
	public void deleteBatch(String[] rowGuids){
		sechosPurchasingm2mDao.deleteBatch(rowGuids);
	}

	@Override
	public List<Sechos_Purchasingm2m> getListByPGuid(String purchaseGuid) {
		return sechosPurchasingm2mDao.getListByPGuid(purchaseGuid);
	}

}
