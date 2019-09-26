package com.basic.javaframe.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import com.basic.javaframe.dao.SechosDrugDao;
import com.basic.javaframe.entity.SechosDrug;
import com.basic.javaframe.service.SechosDrugService;




@Service("sechosDrugService")
@Transactional
public class SechosDrugServiceImpl implements SechosDrugService {
	@Autowired
	private SechosDrugDao sechosDrugDao;

	@Override
	public List<SechosDrug> getList(Map<String, Object> map){
		return sechosDrugDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return sechosDrugDao.getCount(map);
	}

	@Override
	public void save(SechosDrug sechosDrug){
		sechosDrugDao.save(sechosDrug);
	}

	@Override
	public void update(SechosDrug sechosDrug){
		sechosDrugDao.update(sechosDrug);
	}

	@Override
	public void deleteBatch(String[] rowGuids){
		sechosDrugDao.deleteBatch(rowGuids);
	}

	@Override
	public List<SechosDrug> getByPationGuid(String patientRowGuid) {
		// TODO Auto-generated method stub
		return sechosDrugDao.getByPationGuid(patientRowGuid);
	}

	@Override
	public void deleteByJzlsh(String string) {
		// TODO Auto-generated method stub
		sechosDrugDao.deleteByJzlsh(string);
	}

	@Override
	public List<SechosDrug> getByJzlsh(String ghxh) {
		// TODO Auto-generated method stub
		return sechosDrugDao.getByJzlsh(ghxh);
	}
	
}
