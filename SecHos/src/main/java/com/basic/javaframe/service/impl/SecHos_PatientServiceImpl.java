package com.basic.javaframe.service.impl;

import com.basic.javaframe.entity.SecHos_Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import com.basic.javaframe.dao.SecHos_PatientDao;
import com.basic.javaframe.service.SecHos_PatientService;




@Service("patientService")
@Transactional
public class SecHos_PatientServiceImpl implements SecHos_PatientService {
	@Autowired
	private SecHos_PatientDao secHosPatientDao;

	@Override
	public List<SecHos_Patient> getList(Map<String, Object> map){
		return secHosPatientDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return secHosPatientDao.getCount(map);
	}

	@Override
	public void save(SecHos_Patient secHosPatient){
		secHosPatientDao.save(secHosPatient);
	}

	@Override
	public void update(SecHos_Patient secHosPatient){
		secHosPatientDao.update(secHosPatient);
	}

	@Override
	public void deleteBatch(String[] rowGuids){
		secHosPatientDao.deleteBatch(rowGuids);
	}

	@Override
	public SecHos_Patient getPatientByOpenid(String openid) {
		return secHosPatientDao.getPatientByOpenid(openid);
	}

}
