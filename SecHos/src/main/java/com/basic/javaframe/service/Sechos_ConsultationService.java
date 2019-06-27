package com.basic.javaframe.service;

import com.basic.javaframe.entity.Sechos_Consultation;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author 
 * @date 2019-06-26 14:30:13
 */
public interface Sechos_ConsultationService {
	
	List<Sechos_Consultation> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(Sechos_Consultation sechosConsultation);
	
	void update(Sechos_Consultation sechosConsultation);
	
	void deleteBatch(String[] rowGuids);

	/**
	 * 回复咨询
	 * @param sechosConsultation
	 */
	void reply(Sechos_Consultation sechosConsultation);
	
	Sechos_Consultation queryByGuid(String rowGuid);
}
