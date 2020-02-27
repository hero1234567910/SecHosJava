package com.basic.javaframe.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.basic.javaframe.dao.Sechos_ConsultationDao;
import com.basic.javaframe.dao.Sechos_PopuPersonDao;
import com.basic.javaframe.entity.Sechos_Consultation;
import com.basic.javaframe.entity.Sechos_PopuPerson;
import com.basic.javaframe.service.Sechos_ConsultationService;
import com.basic.javaframe.service.Sechos_PopuPersonService;




@Service("sechos_PopuPersonService")
@Transactional
public class Sechos_PopuPersonServiceImpl implements Sechos_PopuPersonService {
	
	@Autowired
	Sechos_PopuPersonDao sechos_PopuPersonDao;
	
	@Override
	public void save(Sechos_PopuPerson person) {
		sechos_PopuPersonDao.save(person);
	}

	@Override
	public Sechos_PopuPerson getByDetail(String rowGuid) {
		return sechos_PopuPersonDao.getByDetail(rowGuid);
	}

	@Override
	public Sechos_PopuPerson getByPopuPersonGuid(String popuPersonGuid) {
		// TODO Auto-generated method stub
		return sechos_PopuPersonDao.getByPopuPersonGuid(popuPersonGuid);
	}
	

}
