package com.basic.javaframe.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.basic.javaframe.dao.Sechos_ConsultationDao;
import com.basic.javaframe.entity.Sechos_Consultation;
import com.basic.javaframe.service.Sechos_ConsultationService;




@Service("sechosConsultationService")
@Transactional
public class Sechos_ConsultationServiceImpl implements Sechos_ConsultationService {
	@Autowired
	private Sechos_ConsultationDao sechosConsultationDao;

	@Override
	public List<Sechos_Consultation> getList(Map<String, Object> map){
		return sechosConsultationDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return sechosConsultationDao.getCount(map);
	}

	@Override
	public void save(Sechos_Consultation sechosConsultation){
		sechosConsultationDao.save(sechosConsultation);
	}

	@Override
	public void update(Sechos_Consultation sechosConsultation){
		sechosConsultationDao.update(sechosConsultation);
	}

	@Override
	public void deleteBatch(String[] rowGuids){
		sechosConsultationDao.deleteBatch(rowGuids);
	}

	@Override
	public void reply(Sechos_Consultation sechosConsultation) {
		sechosConsultationDao.reply(sechosConsultation);
	}

	@Override
	public Sechos_Consultation queryByGuid(String rowGuid) {
		// TODO Auto-generated method stub
		return sechosConsultationDao.queryByGuid(rowGuid);
	}

	@Override
	public int getReplyCount() {
		return sechosConsultationDao.getReplyCount();
	}

	@Override
	public List<Map<String, Object>> selectQusStatisical() {
        String[] timeArray = {"6:00","6:30","7:00","7:30","8:00","8:30","9:00",
                "9:30","10:00","10:30","11:00","11:30","12:00","12:30","13:00",
                "13:30","14:00","14:30","15:00","16:30","17:00","17:30","18:00",
                "18:30","19:00","19:30","20:00","20:30","21:00","21:30","22:00"};
        // TODO Auto-generated method stub
        List<Map<String, Object>> maps = new ArrayList<>();
        maps = sechosConsultationDao.selectQusStatisical();
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

}
