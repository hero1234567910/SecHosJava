package com.basic.javaframe.service.impl;

import com.basic.javaframe.dao.Sechos_RechargerecordDao;
import com.basic.javaframe.entity.Sechos_Rechargerecord;
import com.basic.javaframe.service.Sechos_RechargerecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service("sechosRechargerecordService")
@Transactional
public class Sechos_RechargerecordServiceImpl implements Sechos_RechargerecordService {
	@Autowired
	private Sechos_RechargerecordDao sechosRechargerecordDao;

	@Override
	public List<Sechos_Rechargerecord> getList(Map<String, Object> map){
		return sechosRechargerecordDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return sechosRechargerecordDao.getCount(map);
	}

	@Override
	public void save(Sechos_Rechargerecord sechosRechargerecord){
		sechosRechargerecordDao.save(sechosRechargerecord);
	}

	@Override
	public void update(Sechos_Rechargerecord sechosRechargerecord){
		sechosRechargerecordDao.update(sechosRechargerecord);
	}

	@Override
	public void deleteBatch(String[] rowGuids){
		sechosRechargerecordDao.deleteBatch(rowGuids);
	}
	
}
