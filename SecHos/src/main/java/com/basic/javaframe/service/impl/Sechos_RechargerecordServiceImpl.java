package com.basic.javaframe.service.impl;

import com.basic.javaframe.dao.Sechos_RechargerecordDao;
import com.basic.javaframe.entity.Sechos_Rechargerecord;
import com.basic.javaframe.service.Sechos_RechargerecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("sechosRechargerecordService")
@Transactional
public class Sechos_RechargerecordServiceImpl implements Sechos_RechargerecordService {
	@Autowired
	private Sechos_RechargerecordDao sechosRechargerecordDao;

	@Override
	public List<Sechos_Rechargerecord> getList(Map<String, Object> map){
		return sechosRechargerecordDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return sechosRechargerecordDao.getCount(map);
	}

	@Override
	public void save(Sechos_Rechargerecord sechosRechargerecord){
		sechosRechargerecordDao.save(sechosRechargerecord);
	}

	@Override
	public void update(Sechos_Rechargerecord sechosRechargerecord){
		sechosRechargerecordDao.update(sechosRechargerecord);
	}

	@Override
	public void deleteBatch(String[] rowGuids){
		sechosRechargerecordDao.deleteBatch(rowGuids);
	}

	@Override
	public Sechos_Rechargerecord queryByOrderNumber(String num) {
		// TODO Auto-generated method stub
		return sechosRechargerecordDao.queryByOrderNumber(num);
	}

	@Override
	public List<Map<String, Object>> selectRechargeStatisical() {
		String[] timeArray = {"6:00","6:30","7:00","7:30","8:00","8:30","9:00",
				"9:30","10:00","10:30","11:00","11:30","12:00","12:30","13:00",
				"13:30","14:00","14:30","15:00","16:30","17:00","17:30","18:00",
				"18:30","19:00","19:30","20:00","20:30","21:00","21:30","22:00"};
		// TODO Auto-generated method stub
		List<Map<String, Object>> maps = new ArrayList<>();
		maps = sechosRechargerecordDao.selectRechargeStatisical();
		//数据操作
		if (maps != null) {
			for (int i = 0; i < maps.size(); i++) {
				if ("0".equals(maps.get(i).get("M").toString())) {
					String time = maps.get(i).get("Hour")+":00";
					maps.get(i).put("time", time);
				}
				if ("1".equals(maps.get(i).get("M").toString())) {
					String time = maps.get(i).get("Hour")+":30";
					maps.get(i).put("time", time);
				}
			}

			for (int j = 0; j < timeArray.length; j++) {
				boolean flag = true;
				for(int i = 0; i < maps.size(); i++){
					if (timeArray[j].equals(maps.get(i).get("time"))) {
						flag = false;
					}
				}
				if (flag) {
					Map<String, Object> par = new HashMap<>();
					par.put("time", timeArray[j]);
					par.put("Count", 0);
					maps.add(par);
				}
			}
		}
		return maps;
	}

	@Override
	public List<Sechos_Rechargerecord> selectAllRecharge() {
		return sechosRechargerecordDao.selectAllRecharge();
	}

	@Override
	public Sechos_Rechargerecord getDetailByGuid(String rowGuid) {
		return sechosRechargerecordDao.getDetailByGuid(rowGuid);
	}

	@Override
	public int countYestdayMz() {
		return sechosRechargerecordDao.countYestdayMz();
	}

}
