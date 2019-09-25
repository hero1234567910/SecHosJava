package com.basic.javaframe.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import com.basic.javaframe.dao.Sechos_StockremovalDao;
import com.basic.javaframe.entity.Sechos_Stockremoval;
import com.basic.javaframe.service.Sechos_StockremovalService;




@Service("sechosStockremovalService")
@Transactional
public class Sechos_StockremovalServiceImpl implements Sechos_StockremovalService {
	@Autowired
	private Sechos_StockremovalDao sechosStockremovalDao;

	@Override
	public List<Sechos_Stockremoval> getList(Map<String, Object> map){
		return sechosStockremovalDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return sechosStockremovalDao.getCount(map);
	}

	@Override
	public void save(Sechos_Stockremoval sechosStockremoval){
		sechosStockremovalDao.save(sechosStockremoval);
	}

	@Override
	public void update(Sechos_Stockremoval sechosStockremoval){
		sechosStockremovalDao.update(sechosStockremoval);
	}

	@Override
	public void deleteBatch(String[] rowGuids){
		sechosStockremovalDao.deleteBatch(rowGuids);
	}

	@Override
	public int insertRemove(String outboundGuid) {
		return sechosStockremovalDao.insertRemove(outboundGuid);
	}

	@Override
	public int insertOutDate(Sechos_Stockremoval sechosStockremoval) {
		return sechosStockremovalDao.insertOutDate(sechosStockremoval);
	}

	@Override
	public List<Sechos_Stockremoval> getListByOutboundGuid(String outboundGuid) {
		return sechosStockremovalDao.getListByOutboundGuid(outboundGuid);
	}

	@Override
	public void deleteInsert(String outboundGuid) {
		sechosStockremovalDao.deleteInsert(outboundGuid);
	}

}
