package com.basic.javaframe.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import com.basic.javaframe.dao.SecHos_hospitalizedDao;
import com.basic.javaframe.entity.SecHos_hospitalized;
import com.basic.javaframe.service.SecHos_hospitalizedService;




@Service("sechosHospitalizedService")
@Transactional
public class SecHos_hospitalizedServiceImpl implements SecHos_hospitalizedService {
	@Autowired
	private SecHos_hospitalizedDao sechosHospitalizedDao;

	@Override
	public List<SecHos_hospitalized> getList(Map<String, Object> map){
		return sechosHospitalizedDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return sechosHospitalizedDao.getCount(map);
	}

	@Override
	public void save(SecHos_hospitalized sechosHospitalized){
		sechosHospitalizedDao.save(sechosHospitalized);
	}

	@Override
	public void update(SecHos_hospitalized sechosHospitalized){
		sechosHospitalizedDao.update(sechosHospitalized);
	}

	@Override
	public void deleteBatch(String[] rowGuids){
		sechosHospitalizedDao.deleteBatch(rowGuids);
	}
	
}
