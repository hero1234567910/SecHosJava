package com.basic.javaframe.service.impl;

import com.basic.javaframe.entity.Sechos_Outboundm2m;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import com.basic.javaframe.dao.Sechos_Outboundm2mDao;
import com.basic.javaframe.service.Sechos_Outboundm2mService;




@Service("sechosOutboundm2mService")
@Transactional
public class Sechos_Outboundm2mServiceImpl implements Sechos_Outboundm2mService {
	@Autowired
	private Sechos_Outboundm2mDao sechosOutboundm2mDao;

	@Override
	public List<Sechos_Outboundm2m> getList(Map<String, Object> map){
		return sechosOutboundm2mDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return sechosOutboundm2mDao.getCount(map);
	}

	@Override
	public void save(Sechos_Outboundm2m sechosOutboundm2m){
		sechosOutboundm2mDao.save(sechosOutboundm2m);
	}

	@Override
	public void update(Sechos_Outboundm2m sechosOutboundm2m){
		sechosOutboundm2mDao.update(sechosOutboundm2m);
	}

	@Override
	public void deleteBatch(String[] rowGuids){
		sechosOutboundm2mDao.deleteBatch(rowGuids);
	}

	@Override
	public List<Sechos_Outboundm2m> getListByPGuid(String outboundGuid) {
		return sechosOutboundm2mDao.getListByPGuid(outboundGuid);
	}

	@Override
	public <T> int getCountByGuid(T t) {
		return sechosOutboundm2mDao.getCountByGuid(t);
	}

}
