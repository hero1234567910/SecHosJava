package com.basic.javaframe.service.impl;

import com.basic.javaframe.dao.Form_TableFieldDao;
import com.basic.javaframe.dao.Form_TableInfoDao;
import com.basic.javaframe.entity.Form_TableField;
import com.basic.javaframe.service.Form_TableFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
/**
 *表单字段 IMPL服务层
 *
 * @author my
 * @date 2019-03-18
 */
@Service("tableFieldService")
public class Form_TableFieldServiceImpl implements Form_TableFieldService {
    @Autowired
    private Form_TableFieldDao form_tableFieldDao;
    @Override
    public Form_TableField selectFormTableFieldById(Integer rowId) {
        return form_tableFieldDao.selectFormTableFieldById(rowId);
    }

    @Override
    public Form_TableField selectFormTableFieldByName(String name) {
        return form_tableFieldDao.selectFormTableFieldByName(name);
    }

    @Override
    public List<Form_TableField> selectFormTableFieldList(Map<String, Object> params) {
        return form_tableFieldDao.selectFormTableFieldList(params);
    }

    @Override
    public void insertFormTableField(Form_TableField formTableField) {
         form_tableFieldDao.insertFormTableField(formTableField);
    }

    @Override
    public void updateFormTableField(Form_TableField formTableField) {
        form_tableFieldDao.updateFormTableField(formTableField);
    }

    @Override
    public void deleteFormTableFieldById(Integer[] ids) {
       form_tableFieldDao.deleteFormTableFieldById(ids);
    }

    @Override
    public void deleteFormTableFieldByIds(Integer[] ids) {
       form_tableFieldDao.deleteFormTableFieldByIds(ids);
    }

    @Override
    public int getCount(Map<String, Object> params) {
        return form_tableFieldDao.getCount(params);
    }

	@Override
	public List<Form_TableField> selectFieldByTableName(String rowGuid) {
		// TODO Auto-generated method stub
		return form_tableFieldDao.selectFieldByTableName(rowGuid);
	}

}
