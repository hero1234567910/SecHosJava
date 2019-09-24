package com.basic.javaframe.service.impl;

import com.basic.javaframe.entity.Sechos_Putinstorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.basic.javaframe.dao.Sechos_PutinstorageDao;
import com.basic.javaframe.service.Sechos_PutinstorageService;




@Service("sechosPutinstorageService")
@Transactional
public class Sechos_PutinstorageServiceImpl implements Sechos_PutinstorageService {
	@Autowired
	private Sechos_PutinstorageDao sechosPutinstorageDao;

	@Override
	public List<Sechos_Putinstorage> getList(Map<String, Object> map){
		return sechosPutinstorageDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return sechosPutinstorageDao.getCount(map);
	}

	@Override
	public void save(Sechos_Putinstorage sechosPutinstorage){
		sechosPutinstorageDao.save(sechosPutinstorage);
	}

	@Override
	public void update(Sechos_Putinstorage sechosPutinstorage){
		sechosPutinstorageDao.update(sechosPutinstorage);
	}

	@Override
	public void deleteBatch(String[] rowGuids){
		sechosPutinstorageDao.deleteBatch(rowGuids);
	}

	@Override
	public void insertStorage(String purchaseGuid) {
		sechosPutinstorageDao.insertStorage(purchaseGuid);
	}

	@Override
	public void insertInDate(Sechos_Putinstorage sechosPutinstorage) {
	sechosPutinstorageDao.insertInDate(sechosPutinstorage);
	}

	@Override
	public List<String> getStorageOverdue() {
		return sechosPutinstorageDao.getStorageOverdue();
	}

	@Override
	public List<Sechos_Putinstorage> getListByPurchaseGuid(String purchaseGuid) {
		return sechosPutinstorageDao.getListByPurchaseGuid(purchaseGuid);
	}

}
