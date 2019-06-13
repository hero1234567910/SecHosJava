package com.basic.javaframe.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import com.basic.javaframe.dao.HosAddressDao;
import com.basic.javaframe.entity.HosAddress;
import com.basic.javaframe.service.HosAddressService;




@Service("hosAddressService")
@Transactional
public class HosAddressServiceImpl implements HosAddressService {
	@Autowired
	private HosAddressDao hosAddressDao;

	@Override
	public List<HosAddress> getList(Map<String, Object> map){
		return hosAddressDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return hosAddressDao.getCount(map);
	}

	@Override
	public void save(HosAddress hosAddress){
		hosAddressDao.save(hosAddress);
	}

	@Override
	public void update(HosAddress hosAddress){
		hosAddressDao.update(hosAddress);
	}

	@Override
	public void deleteBatch(String[] rowGuids){
		hosAddressDao.deleteBatch(rowGuids);
	}

	@Override
	public List<HosAddress> getAddressListByUserGuid(String hosUserGuid) {
		return hosAddressDao.getAddressListByUserGuid(hosUserGuid);
	}

}
