package com.basic.javaframe.service.impl;

import com.basic.javaframe.entity.Sechos_Storageamount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import com.basic.javaframe.dao.Sechos_StorageamountDao;
import com.basic.javaframe.service.Sechos_StorageamountService;




@Service("sechosStorageamountService")
@Transactional
public class Sechos_StorageamountServiceImpl implements Sechos_StorageamountService {
	@Autowired
	private Sechos_StorageamountDao sechosStorageamountDao;

	@Override
	public List<Sechos_Storageamount> getList(Map<String, Object> map){
		return sechosStorageamountDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return sechosStorageamountDao.getCount(map);
	}

	@Override
	public void save(Sechos_Storageamount sechosStorageamount){
		sechosStorageamountDao.save(sechosStorageamount);
	}

	@Override
	public void update(Sechos_Storageamount sechosStorageamount){
		sechosStorageamountDao.update(sechosStorageamount);
	}

	@Override
	public void deleteBatch(String[] rowGuids){
		sechosStorageamountDao.deleteBatch(rowGuids);
	}

	@Override
	public void updateNum(Integer Num, String drugCode) {
		sechosStorageamountDao.updateNum(Num,drugCode);
	}

}
