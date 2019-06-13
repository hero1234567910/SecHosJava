package com.basic.javaframe.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import com.basic.javaframe.dao.HosUserDao;
import com.basic.javaframe.entity.HosUser;
import com.basic.javaframe.service.HosUserService;




@Service("hosUserService")
@Transactional
public class HosUserServiceImpl implements HosUserService {
	@Autowired
	private HosUserDao hosUserDao;

	@Override
	public List<HosUser> getList(Map<String, Object> map){
		return hosUserDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return hosUserDao.getCount(map);
	}

	@Override
	public void save(HosUser hosUser){
		hosUserDao.save(hosUser);
	}

	@Override
	public void update(HosUser hosUser){
		hosUserDao.update(hosUser);
	}

	@Override
	public void deleteBatch(String[] rowGuids){
		hosUserDao.deleteBatch(rowGuids);
	}

	@Override
	public HosUser getUserByOpenid(String openid) {
		return hosUserDao.getUserByOpenid(openid);
	}

	@Override
	public HosUser getUserByGuid(String userGuid) {
		// TODO Auto-generated method stub
		return hosUserDao.getUserByGuid(userGuid);
	}

}
