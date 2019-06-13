package com.basic.javaframe.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import com.basic.javaframe.dao.HosOrderitemDao;
import com.basic.javaframe.entity.HosOrderitem;
import com.basic.javaframe.service.HosOrderitemService;




@Service("hosOrderitemService")
@Transactional
public class HosOrderitemServiceImpl implements HosOrderitemService {
	@Autowired
	private HosOrderitemDao hosOrderitemDao;

	@Override
	public List<HosOrderitem> getList(Map<String, Object> map){
		return hosOrderitemDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return hosOrderitemDao.getCount(map);
	}

	@Override
	public void save(HosOrderitem hosOrderitem){
		hosOrderitemDao.save(hosOrderitem);
	}

	@Override
	public void update(HosOrderitem hosOrderitem){
		hosOrderitemDao.update(hosOrderitem);
	}

	@Override
	public void deleteBatch(String[] rowGuids){
		hosOrderitemDao.deleteBatch(rowGuids);
	}

	@Override
	public String getGoodsByItemGuid(String orderGuid) {
		return hosOrderitemDao.getGoodsByItemGuid(orderGuid);
	}

}
