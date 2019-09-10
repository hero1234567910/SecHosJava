package com.basic.javaframe.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import com.basic.javaframe.dao.Sechos_RepairsatisfactionDao;
import com.basic.javaframe.entity.Sechos_Repairsatisfaction;
import com.basic.javaframe.service.Sechos_RepairsatisfactionService;




@Service("sechosRepairsatisfactionService")
@Transactional
public class Sechos_RepairsatisfactionServiceImpl implements Sechos_RepairsatisfactionService {
	@Autowired
	private Sechos_RepairsatisfactionDao sechosRepairsatisfactionDao;

	@Override
	public List<Sechos_Repairsatisfaction> getList(Map<String, Object> map){
		return sechosRepairsatisfactionDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return sechosRepairsatisfactionDao.getCount(map);
	}

	@Override
	public void save(Sechos_Repairsatisfaction sechosRepairsatisfaction){
		sechosRepairsatisfactionDao.save(sechosRepairsatisfaction);
	}

	@Override
	public void update(Sechos_Repairsatisfaction sechosRepairsatisfaction){
		sechosRepairsatisfactionDao.update(sechosRepairsatisfaction);
	}

	@Override
	public void deleteBatch(String[] rowGuids){
		sechosRepairsatisfactionDao.deleteBatch(rowGuids);
	}

	@Override
	public Sechos_Repairsatisfaction getDetailByGuid(String rowGuid) {
		return sechosRepairsatisfactionDao.getDetailByGuid(rowGuid);
	}

	@Override
	public List<Sechos_Repairsatisfaction> getMySatList(Map<String, Object> map) {
		return sechosRepairsatisfactionDao.getMySatList(map);
	}

	@Override
	public int getMySatCount(Map<String, Object> map) {
		return sechosRepairsatisfactionDao.getMySatCount(map);
	}

	@Override
	public int countSats(String repairGuid) {
		return sechosRepairsatisfactionDao.countSats(repairGuid);
	}

}
