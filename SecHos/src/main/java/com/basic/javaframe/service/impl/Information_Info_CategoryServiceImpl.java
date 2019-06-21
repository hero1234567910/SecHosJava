package com.basic.javaframe.service.impl;

import com.basic.javaframe.dao.Information_Info_CategoryDao;
import com.basic.javaframe.entity.Information_Info_Category;
import com.basic.javaframe.service.Information_Info_CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
/**
 *IMPL服务层
 *
 * @author my
 *
 */
@Service("InfoCategoryService")
public class Information_Info_CategoryServiceImpl implements Information_Info_CategoryService {
    @Autowired
    private Information_Info_CategoryDao InfoCategoryDao;

    @Override
    public Information_Info_Category selectInfoCategoryById(Integer rowId) {
        return InfoCategoryDao.selectInfoCategoryById(rowId);
    }

    @Override
    public Information_Info_Category selectInfoCategoryByName(String name) {
        return InfoCategoryDao.selectInfoCategoryByName(name);
    }

    @Override
    public List<Information_Info_Category> selectInfoCategoryList(Map<String, Object> params) {
        return InfoCategoryDao.selectInfoCategoryList(params);
    }

    @Override
    public void insert(Information_Info_Category InfoCategory) {
        InfoCategoryDao.insert(InfoCategory);
    }

    @Override
    public void update(Information_Info_Category InfoCategory) {
        InfoCategoryDao.update(InfoCategory);
    }

    @Override
    public void deleteByCateGuids(String[] categoryGuids) {
        InfoCategoryDao.deleteByCateGuids(categoryGuids);
    }

    @Override
    public int getCount(Map<String, Object> params) {
        return 0;
    }

    @Override
    public List<String> getInfoByCateGuid(String categoryGuid) {
        return InfoCategoryDao.getInfoByCateGuid(categoryGuid);
    }

    @Override
    public String getSingleInfoByCateGuid(String categoryGuid) {
        return InfoCategoryDao.getSingleInfoByCateGuid(categoryGuid);
    }
}
