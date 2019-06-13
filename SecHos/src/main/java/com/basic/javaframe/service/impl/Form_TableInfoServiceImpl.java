package com.basic.javaframe.service.impl;

import com.basic.javaframe.dao.Form_TableInfoDao;
import com.basic.javaframe.entity.Form_TableInfo;
import com.basic.javaframe.service.Form_TableInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
/**
 *表单 IMPL服务层
 *
 * @author my
 * @date 2019-03-18
 */
@Service("tableInfoService")
public class Form_TableInfoServiceImpl implements Form_TableInfoService {


        @Autowired
        private Form_TableInfoDao form_tableInfoDao;

    @Override
    public Form_TableInfo selectFormTableInfoById(Integer rowId) {
        return form_tableInfoDao.selectFormTableInfoById(rowId);
    }

    @Override
    public Form_TableInfo selectFormTableInfoByName(String name) {
        return form_tableInfoDao.selectFormTableInfoByName(name);
    }

    @Override
    public List<Form_TableInfo> selectFormTableInfoList(Map<String, Object> params) {
        return form_tableInfoDao.selectFormTableInfoList(params);
    }

    @Override
    public void insertFormTableInfo(Form_TableInfo frameTableInfo) {
        form_tableInfoDao.insertFormTableInfo(frameTableInfo);
    }

    @Override
    public void updateFormTableInfo(Form_TableInfo frameTableInfo) {
        form_tableInfoDao.updateFormTableInfo(frameTableInfo);
    }

    @Override
    public void deleteFormTableInfoById(Integer[] ids) {
        form_tableInfoDao.deleteFormTableInfoById(ids);
    }

    @Override
    public void deleteFormTableInfoByIds(Integer[] ids) {
        form_tableInfoDao.deleteFormTableInfoByIds(ids);
    }

    @Override
    public int getCount(Map<String, Object> params) {
        return form_tableInfoDao.getCount(params);
    }

    @Override
    public void insertGuid(Form_TableInfo formTableInfo) {
        form_tableInfoDao.insertGuid(formTableInfo);
    }
}
