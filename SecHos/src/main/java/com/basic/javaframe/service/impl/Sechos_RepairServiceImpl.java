package com.basic.javaframe.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import com.basic.javaframe.dao.Sechos_RepairDao;
import com.basic.javaframe.entity.Sechos_Repair;
import com.basic.javaframe.service.Sechos_RepairService;




@Service("sechosRepairService")
@Transactional
public class Sechos_RepairServiceImpl implements Sechos_RepairService {
	@Autowired
	private Sechos_RepairDao sechosRepairDao;

	@Override
	public List<Sechos_Repair> getList(Map<String, Object> map){
		return sechosRepairDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return sechosRepairDao.getCount(map);
	}

	@Override
	public void save(Sechos_Repair sechosRepair){
		sechosRepairDao.save(sechosRepair);
	}

	@Override
	public void update(Sechos_Repair sechosRepair){
		sechosRepairDao.update(sechosRepair);
	}

	@Override
	public void deleteBatch(String[] rowGuids){
		sechosRepairDao.deleteBatch(rowGuids);
	}

	@Override
	public Sechos_Repair getDetailByGuid(String rowGuid) {
		return sechosRepairDao.getDetailByGuid(rowGuid);
	}

}
