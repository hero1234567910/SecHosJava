package com.basic.javaframe.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import com.basic.javaframe.dao.Sechos_EvaluateDao;
import com.basic.javaframe.entity.Sechos_Evaluate;
import com.basic.javaframe.service.Sechos_EvaluateService;




@Service("sechosEvaluateService")
@Transactional
public class Sechos_EvaluateServiceImpl implements Sechos_EvaluateService {
	@Autowired
	private Sechos_EvaluateDao sechosEvaluateDao;

	@Override
	public List<Sechos_Evaluate> getList(Map<String, Object> map){
		return sechosEvaluateDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return sechosEvaluateDao.getCount(map);
	}

	@Override
	public void save(Sechos_Evaluate sechosEvaluate){
		sechosEvaluateDao.save(sechosEvaluate);
	}

	@Override
	public void update(Sechos_Evaluate sechosEvaluate){
		sechosEvaluateDao.update(sechosEvaluate);
	}

	@Override
	public void deleteBatch(String[] rowGuids){
		sechosEvaluateDao.deleteBatch(rowGuids);
	}
	
}
