package com.basic.javaframe.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import com.basic.javaframe.dao.PatientDao;
import com.basic.javaframe.entity.Patient;
import com.basic.javaframe.service.PatientService;




@Service("patientService")
@Transactional
public class PatientServiceImpl implements PatientService {
	@Autowired
	private PatientDao patientDao;

	@Override
	public List<Patient> getList(Map<String, Object> map){
		return patientDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return patientDao.getCount(map);
	}

	@Override
	public void save(Patient patient){
		patientDao.save(patient);
	}

	@Override
	public void update(Patient patient){
		patientDao.update(patient);
	}

	@Override
	public void deleteBatch(String[] rowGuids){
		patientDao.deleteBatch(rowGuids);
	}

	@Override
	public Patient getPatientByOpenid(String openid) {
		return patientDao.getPatientByOpenid(openid);
	}

}
