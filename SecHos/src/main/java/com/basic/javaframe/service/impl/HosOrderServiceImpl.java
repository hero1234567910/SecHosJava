package com.basic.javaframe.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.basic.javaframe.common.print.MyPrint;
import com.basic.javaframe.dao.HosOrderDao;
import com.basic.javaframe.entity.HosOrder;
import com.basic.javaframe.service.HosOrderService;




@Service("hosOrderService")
@Transactional
public class HosOrderServiceImpl implements HosOrderService {
	@Autowired
	private HosOrderDao hosOrderDao;

	@Override
	public List<HosOrder> getList(Map<String, Object> map){
		return hosOrderDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return hosOrderDao.getCount(map);
	}

	@Override
	public void save(HosOrder hosOrder){
		hosOrderDao.save(hosOrder);
	}

	@Override
	public void update(HosOrder hosOrder){
		hosOrderDao.update(hosOrder);
	}

	@Override
	public void deleteBatch(String[] rowGuids){
		hosOrderDao.deleteBatch(rowGuids);
	}

	@Override
	public HosOrder queryByOrderNumber(String orderNumber) {
		// TODO Auto-generated method stub
		return hosOrderDao.queryByOrderNumber(orderNumber);
	}

	@Override
	public HosOrder getOrderByMerchantNumber(String merchantNumber) {
		return hosOrderDao.getOrderByMerchantNumber(merchantNumber);
	}

	@Override
	public HosOrder getOrderByStatus1(int orderStatus) {
		return hosOrderDao.getOrderByStatus1(orderStatus);
	}

	@Override
	public List<HosOrder> getOrderListByUserGuid(String orderUserGuid) {
		return hosOrderDao.getOrderListByUserGuid(orderUserGuid);
	}

	@Override
	public void getOrderItemByUserGuid(String orderUserGuid) {
		hosOrderDao.getOrderItemByUserGuid(orderUserGuid);
	}

	@Override
	public HosOrder getOrderDetailByGuid(String rowGuid) {
		return hosOrderDao.getOrderDetailByGuid(rowGuid);
	}

	public HosOrder getByRowGuid(String rowGuid) {
		// TODO Auto-generated method stub
		return hosOrderDao.getByRowGuid(rowGuid);
	}

	@Override
	public void printOrder(String rowGuid) {
		//获取打印数据
		HosOrder hosOrder = getByRowGuid(rowGuid);
		 MyPrint myPrint = new MyPrint();
	    //设置打印页面数量
		 myPrint.setHosOrder(hosOrder);
	    myPrint.setTotalPageCount(1);
	    myPrint.doPrint(myPrint);
	    System.out.println("打印成功。。。");

	}

	@Override
	public List<HosOrder> selectAllOrder() {
		// TODO Auto-generated method stub
		return hosOrderDao.selectAllOrder();
	}

	@Override
	public List<Map<String, Object>> selectOrderStatisical() {
		String[] timeArray = {"6:00","6:30","7:00","7:30","8:00","8:30","9:00",
				"9:30","10:00","10:30","11:00","11:30","12:00","12:30","13:00",
				"13:30","14:00","14:30","15:00","16:30","17:00","17:30","18:00",
				"18:30","19:00","19:30","20:00","20:30","21:00","21:30","22:00"};
		// TODO Auto-generated method stub
		List<Map<String, Object>> maps = new ArrayList<>();
		maps = hosOrderDao.selectOrderStatisical();
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
	public void updateOrderStatus(String[] rowGuids) {
		// TODO Auto-generated method stub
		hosOrderDao.updateOrderStatus(rowGuids);
	}

//	@Override
//	public HosOrder getOrderInfoByRowGuid(String rowGuid) {
//		// TODO Auto-generated method stub
//		return hosOrderDao.getOrderInfoByRowGuid(rowGuid);
//	}


}
