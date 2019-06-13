package com.basic.javaframe.service;

import com.basic.javaframe.entity.Frame_ConfigCateGory;

import java.util.List;
import java.util.Map;

/**
 * <p>Title: ConfigCategoryService</p>
 * <p>Description: 系统参数类别服务层</p>
 * @author wzl
 */
public interface Frame_ConfigCategoryService {

    /**
     * 查询系统参数类别列表
     *
     * @param params 系统参数类别信息
     * @return 集合
     */
    List<Frame_ConfigCateGory> getConfigCategory(Map<String,Object> params);

    //获取数量
    int getCount(Map<String, Object> params);

    /**
     * 新增系统参数类别
     *
     * @param configCateGory 系统参数类别信息
     *
     */
    void insertConfigCategory(Frame_ConfigCateGory configCateGory);

    /**
     * 更新系统参数类别
     *
     * @param configCateGory 系统参数类别信息
     *
     */
    void updateConfigCateory(Frame_ConfigCateGory configCateGory);

    /**
     * 批量删除系统参数类别信息
     *
     * @param rowGuid 需要删除的数据ID
     *
     */
    void deleteConfigCategory(String rowGuid);

    /**
     * 获取所有参数类别
     * @return
     */
    List<Frame_ConfigCateGory> getAllCategory();

    /**
     * 查询类别名重复接口
     * @param t
     * @param <T>
     * @return
     */
    <T> int checkCategoryName(T t);
}
