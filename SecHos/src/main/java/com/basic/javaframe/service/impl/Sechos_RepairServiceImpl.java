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

	@Override
	public List<Sechos_Repair> getListByGuid(String rowGuid) {
		return sechosRepairDao.getListByGuid(rowGuid);
	}

	@Override
	public void cancelRepair(String rowGuid) {
		sechosRepairDao.cancelRepair(rowGuid);
	}

	@Override
	public List<Sechos_Repair> getMaintainList(Map<String, Object> map) {
		return sechosRepairDao.getMaintainList(map);
	}

	@Override
	public int getMaintainCount(Map<String, Object> map) {
		return sechosRepairDao.getMaintainCount(map);
	}

	@Override
	public void successRepair(Map<String, Object> params) {
		sechosRepairDao.successRepair(params);
	}

	@Override
	public List<Sechos_Repair> getMyList(Map<String, Object> map) {
		return sechosRepairDao.getMyList(map);
	}

	@Override
	public int getMyCount(Map<String, Object> map) {
		return sechosRepairDao.getMyCount(map);
	}

	@Override
	public int countRepairs(String maintainGuid) {
		return sechosRepairDao.countRepairs(maintainGuid);
	}

	@Override
	public void assignBatch(String maintainGuid, String maintainName,String[] rowGuids) {
		sechosRepairDao.assignBatch(maintainGuid,maintainName,rowGuids);
	}

}
