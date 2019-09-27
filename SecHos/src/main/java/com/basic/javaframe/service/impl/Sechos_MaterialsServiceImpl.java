package com.basic.javaframe.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import com.basic.javaframe.dao.Sechos_MaterialsDao;
import com.basic.javaframe.entity.Sechos_Materials;
import com.basic.javaframe.service.Sechos_MaterialsService;




@Service("sechosMaterialsService")
@Transactional
public class Sechos_MaterialsServiceImpl implements Sechos_MaterialsService {
	@Autowired
	private Sechos_MaterialsDao sechosMaterialsDao;

	@Override
	public List<Sechos_Materials> getList(Map<String, Object> map){
		return sechosMaterialsDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return sechosMaterialsDao.getCount(map);
	}

	@Override
	public void save(Sechos_Materials sechosMaterials){
		sechosMaterialsDao.save(sechosMaterials);
	}

	@Override
	public void update(Sechos_Materials sechosMaterials){
		sechosMaterialsDao.update(sechosMaterials);
	}

	@Override
	public void deleteBatch(String[] rowGuids){
		sechosMaterialsDao.deleteBatch(rowGuids);
	}

	@Override
	public Sechos_Materials getDetailByGuid(String rowGuid) {
		return sechosMaterialsDao.getDetailByGuid(rowGuid);
	}

	@Override
	public List<String> getMaterialCodes() {
		return sechosMaterialsDao.getMaterialCodes();
	}

	@Override
	public Sechos_Materials getMaterialName(String drugCode) {
		return sechosMaterialsDao.getMaterialName(drugCode);
	}

	@Override
	public <T> int checkMaterialCode(T t) {
		return sechosMaterialsDao.checkMaterialCode(t);
	}

}
