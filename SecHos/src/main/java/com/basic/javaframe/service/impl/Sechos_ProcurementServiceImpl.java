package com.basic.javaframe.service.impl;

import com.basic.javaframe.entity.Sechos_Procurement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import com.basic.javaframe.dao.Sechos_ProcurementDao;
import com.basic.javaframe.service.Sechos_ProcurementService;




@Service("sechosProcurementService")
@Transactional
public class Sechos_ProcurementServiceImpl implements Sechos_ProcurementService {
	@Autowired
	private Sechos_ProcurementDao sechosProcurementDao;

	@Override
	public List<Sechos_Procurement> getList(Map<String, Object> map){
		return sechosProcurementDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return sechosProcurementDao.getCount(map);
	}

	@Override
	public void save(Sechos_Procurement sechosProcurement){
		sechosProcurementDao.save(sechosProcurement);
	}

	@Override
	public void update(Sechos_Procurement sechosProcurement){
		sechosProcurementDao.update(sechosProcurement);
	}

	@Override
	public void deleteBatch(String[] rowGuids){
		sechosProcurementDao.deleteBatch(rowGuids);
	}

	@Override
	public List<Sechos_Procurement> getList2(Map<String, Object> map) {
		return sechosProcurementDao.getList2(map);
	}

	@Override
	public int getCount2(Map<String, Object> map) {
		return sechosProcurementDao.getCount2(map);
	}

	@Override
	public Sechos_Procurement getDetailByGuid(String rowGuid) {
		return sechosProcurementDao.getDetailByGuid(rowGuid);
	}

}
