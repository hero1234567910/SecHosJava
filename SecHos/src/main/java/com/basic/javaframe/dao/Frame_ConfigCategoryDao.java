package com.basic.javaframe.dao;

import com.basic.javaframe.entity.Frame_ConfigCateGory;
import java.util.List;
import java.util.Map;

/**
 * <p>Title: ConfigCategoryDao</p>
 * <p>Description: 系统参数类别接口层</p>
 * @author wzl
 */
public interface Frame_ConfigCategoryDao {
    /**
     * 查询系统参数类别列表
     *
     * @param params 系统参数类别信息
     * @return 集合
     */
    List<Frame_ConfigCateGory> getConfigCategory(Map<String, Object> params);

    //获取数量
    int getCount(Map<String, Object> params);

    /**
     * 新增系统参数类别
     *
     * @param configCateGory 系统参数类别信息
     * @return 结果
     */
    int insertConfigCategory(Frame_ConfigCateGory configCateGory);

    /**
     * 更新系统参数类别
     *
     * @param configCateGory 系统参数类别信息
     * @return 结果
     */
    int updateConfigCategory(Frame_ConfigCateGory configCateGory);

    /**
     * 删除系统参数类别信息
     *
     * @param rowGuid 需要删除的数据ID
     * @return 结果
     */
    void deleteConfigCategory(String rowGuid);

    /**
     *
     * 获取所有参数类别
     */
    List<Frame_ConfigCateGory> getAllCategory();

    /**
     * 验证参数类别名重复性
     * @param t
     * @param <T>
     * @return
     */
    <T> int checkCategoryName(T t);
}
