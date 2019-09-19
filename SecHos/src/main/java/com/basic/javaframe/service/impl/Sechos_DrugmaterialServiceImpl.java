package com.basic.javaframe.service.impl;

import com.basic.javaframe.entity.Sechos_Drugmaterial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import com.basic.javaframe.dao.Sechos_DrugmaterialDao;
import com.basic.javaframe.service.Sechos_DrugmaterialService;




@Service("sechosDrugmaterialService")
@Transactional
public class Sechos_DrugmaterialServiceImpl implements Sechos_DrugmaterialService {
	@Autowired
	private Sechos_DrugmaterialDao sechosDrugmaterialDao;

	@Override
	public List<Sechos_Drugmaterial> getList(Map<String, Object> map){
		return sechosDrugmaterialDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return sechosDrugmaterialDao.getCount(map);
	}

	@Override
	public void save(Sechos_Drugmaterial sechosDrugmaterial){
		sechosDrugmaterialDao.save(sechosDrugmaterial);
	}

	@Override
	public void update(Sechos_Drugmaterial sechosDrugmaterial){
		sechosDrugmaterialDao.update(sechosDrugmaterial);
	}

	@Override
	public void deleteBatch(String[] rowGuids){
		sechosDrugmaterialDao.deleteBatch(rowGuids);
	}

	@Override
	public Sechos_Drugmaterial getDetailByGuid(String rowGuid) {
		return sechosDrugmaterialDao.getDetailByGuid(rowGuid);
	}

}
