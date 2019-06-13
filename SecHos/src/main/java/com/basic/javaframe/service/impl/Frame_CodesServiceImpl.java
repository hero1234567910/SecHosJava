package com.basic.javaframe.service.impl;

import com.basic.javaframe.dao.Frame_CodesDao;
import com.basic.javaframe.entity.Frame_Codes;
import com.basic.javaframe.service.Frame_CodesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>Title: FrameCodesService</p>
 * <p>Description: 代码项IMPL服务层</p>
 *
 * @author wzl
 */
@Service("codesService")
public class Frame_CodesServiceImpl implements Frame_CodesService {

    @Autowired
    private Frame_CodesDao codesDao;

    /**
     * 获取代码项类别信息
     * @param params 代码项信息
     * @return
     */
    @Override
    public List<Frame_Codes> getCodes(Map<String, Object> params) {
        return codesDao.getCodes(params);
    }

    @Override
    public int getCount(Map<String, Object> params) {
        return codesDao.getCount(params);
    }

    /**
     * 新增代码项参数
     * @param codes 代码项参数信息
     */
    @Override
    public void insertCodes(Frame_Codes codes) {
        codesDao.insertCodes(codes);
    }

    /**
     * 更新代码项参数
     * @param codeName
     * 代码项参数信息
     */
    @Override
    public void updateCodesBatch(Integer[] rowId,String[] codeName,Integer[] sortSq) {
        codesDao.updateCodesBatch(rowId,codeName,sortSq);
    }

    /**
     * 批量删除代码项参数
     * @param ids 代码项ID信息
     */
    @Override
    public void deleteCodes(Integer[] ids) {
        codesDao.deleteCodes(ids);
    }

    /**
     * 获取所有代码项参数
     * @return
     */
    @Override
    public List<Frame_Codes> getAllCodes() {
        return codesDao.getAllCodes();
    }

    @Override
    public void updateCodes(Frame_Codes frameCodes) {
        codesDao.updateCodes(frameCodes);
    }

    @Override
    public <T> int checkCodeName(T t) {
        return codesDao.checkCodeName(t);
    }

	@Override
	public List<Frame_Codes> getCodesByName(String codeName) {
		// TODO Auto-generated method stub
		return codesDao.getCodesByName(codeName);
	}
}
