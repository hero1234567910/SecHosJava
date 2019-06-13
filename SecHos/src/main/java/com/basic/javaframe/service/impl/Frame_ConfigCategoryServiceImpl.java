package com.basic.javaframe.service.impl;

import com.basic.javaframe.dao.Frame_ConfigCategoryDao;
import com.basic.javaframe.entity.Frame_ConfigCateGory;
import com.basic.javaframe.service.Frame_ConfigCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>Title: FrameConfigCategoryServiceImpl</p>
 * <p>Description: 系统参数类别IMPL服务层</p>
 *
 * @author wzl
 */
@Service("configCategoryService")
public class Frame_ConfigCategoryServiceImpl implements Frame_ConfigCategoryService {

    @Autowired
    private Frame_ConfigCategoryDao configCategoryDao;

    /**
     * 获取系统参数类别信息
     * @param params 系统参数类别信息
     * @return
     */
    @Override
    public List<Frame_ConfigCateGory> getConfigCategory(Map<String, Object> params) {
        return configCategoryDao.getConfigCategory(params);
    }

    /**
     * 获取数量
     * @param params
     * @return
     */
    @Override
    public int getCount(Map<String, Object> params) {
        return configCategoryDao.getCount(params);
    }

    /**
     * 添加系统参数类别信息
     * @param configCateGory 系统参数类别信息
     */
    @Override
    public void insertConfigCategory(Frame_ConfigCateGory configCateGory) {
        configCategoryDao.insertConfigCategory(configCateGory);
    }

    /**
     * 更新系统参数类别信息
     * @param configCateGory 系统参数类别信息
     */
    @Override
    public void updateConfigCateory(Frame_ConfigCateGory configCateGory) {
        configCategoryDao.updateConfigCategory(configCateGory);
    }

    /**
     * 删除系统参数类别信息
     * @param rowGuid 需要删除的数据ID
     */
    @Override
    public void deleteConfigCategory(String rowGuid) {
        configCategoryDao.deleteConfigCategory(rowGuid);
    }

    /**
     * 获取所有系统参数类别
     * @return
     */
    @Override
    public List<Frame_ConfigCateGory> getAllCategory() {
        return configCategoryDao.getAllCategory();
    }

    @Override
    public <T> int checkCategoryName(T t) {
        return configCategoryDao.checkCategoryName(t);
    }
}
