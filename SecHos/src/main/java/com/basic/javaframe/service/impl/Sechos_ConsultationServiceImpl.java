package com.basic.javaframe.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import com.basic.javaframe.dao.Sechos_ConsultationDao;
import com.basic.javaframe.entity.Sechos_Consultation;
import com.basic.javaframe.service.Sechos_ConsultationService;




@Service("sechosConsultationService")
@Transactional
public class Sechos_ConsultationServiceImpl implements Sechos_ConsultationService {
	@Autowired
	private Sechos_ConsultationDao sechosConsultationDao;

	@Override
	public List<Sechos_Consultation> getList(Map<String, Object> map){
		return sechosConsultationDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return sechosConsultationDao.getCount(map);
	}

	@Override
	public void save(Sechos_Consultation sechosConsultation){
		sechosConsultationDao.save(sechosConsultation);
	}

	@Override
	public void update(Sechos_Consultation sechosConsultation){
		sechosConsultationDao.update(sechosConsultation);
	}

	@Override
	public void deleteBatch(String[] rowGuids){
		sechosConsultationDao.deleteBatch(rowGuids);
	}

	@Override
	public void reply(Sechos_Consultation sechosConsultation) {
		sechosConsultationDao.reply(sechosConsultation);
	}

}
