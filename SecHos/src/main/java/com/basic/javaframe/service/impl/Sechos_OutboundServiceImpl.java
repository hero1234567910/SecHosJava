package com.basic.javaframe.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import com.basic.javaframe.dao.Sechos_OutboundDao;
import com.basic.javaframe.entity.Sechos_Outbound;
import com.basic.javaframe.service.Sechos_OutboundService;




@Service("sechosOutboundService")
@Transactional
public class Sechos_OutboundServiceImpl implements Sechos_OutboundService {
	@Autowired
	private Sechos_OutboundDao sechosOutboundDao;

	@Override
	public List<Sechos_Outbound> getList(Map<String, Object> map){
		return sechosOutboundDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return sechosOutboundDao.getCount(map);
	}

	@Override
	public void save(Sechos_Outbound sechosOutbound){
		sechosOutboundDao.save(sechosOutbound);
	}

	@Override
	public void update(Sechos_Outbound sechosOutbound){
		sechosOutboundDao.update(sechosOutbound);
	}

	@Override
	public void deleteBatch(String[] rowGuids){
		sechosOutboundDao.deleteBatch(rowGuids);
	}

	@Override
	public List<Sechos_Outbound> getList2(Map<String, Object> map) {
		return sechosOutboundDao.getList2(map);
	}

	@Override
	public int getCount2(Map<String, Object> map) {
		return sechosOutboundDao.getCount2(map);
	}

	@Override
	public Sechos_Outbound getDetailByGuid(String rowGuid) {
		return sechosOutboundDao.getDetailByGuid(rowGuid);
	}

}
