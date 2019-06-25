package com.basic.javaframe.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import com.basic.javaframe.dao.SecHos_OutpatientDao;
import com.basic.javaframe.entity.SecHos_Outpatient;
import com.basic.javaframe.service.SecHos_OutpatientService;




@Service("sechosOutpatientService")
@Transactional
public class SecHos_OutpatientServiceImpl implements SecHos_OutpatientService {
	@Autowired
	private SecHos_OutpatientDao sechosOutpatientDao;

	@Override
	public List<SecHos_Outpatient> getList(Map<String, Object> map){
		return sechosOutpatientDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return sechosOutpatientDao.getCount(map);
	}

	@Override
	public void save(SecHos_Outpatient sechosOutpatient){
		sechosOutpatientDao.save(sechosOutpatient);
	}

	@Override
	public void update(SecHos_Outpatient sechosOutpatient){
		sechosOutpatientDao.update(sechosOutpatient);
	}

	@Override
	public void deleteBatch(String[] rowGuids){
		sechosOutpatientDao.deleteBatch(rowGuids);
	}
	
}
