package com.basic.javaframe.service;

import com.basic.javaframe.entity.Information_Info_Category;

import java.util.List;
import java.util.Map;

/**
 *  服务层
 *
 * @author
 *
 */
public interface Information_Info_CategoryService {
    /**
     * 查询信息
     *
     * @param rowId ID
     * @return 管理信息
     */
    Information_Info_Category selectInfoCategoryById(Integer rowId);
    /**
     * 查询信息
     *
     * @param name 名字
     * @return 管理信息
     */
    Information_Info_Category selectInfoCategoryByName(String name);
    /**
     * 查询管理列表
     *
     * @param InfoCategory 信息
     * @return 表单管理集合
     */
    List<Information_Info_Category> selectInfoCategoryList(Map<String, Object> params);
    /**
     * 新增
     *
     * @param InfoCategory 信息
     * @return 结果
     */
    void insert(Information_Info_Category InfoCategory);

    /**
     * 修改
     *
     * @param InfoCategory 信息
     * @return 结果
     */
    void update(Information_Info_Category InfoCategory);

    /**
     * 批量删除信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    void deleteByIds(Integer[] ids);

    int getCount(Map<String, Object> params);

    /**
     * 通过栏目GUID获取信息
     * @param categoryGuid
     */
    List<String> getInfoByCateGuid(String categoryGuid);
}
