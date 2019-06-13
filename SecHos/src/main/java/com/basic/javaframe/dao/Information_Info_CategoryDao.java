package com.basic.javaframe.dao;

import com.basic.javaframe.entity.Information_Info_Category;
import org.apache.ibatis.annotations.Param;

import javax.sound.sampled.Line;
import java.util.List;
import java.util.Map;

/**
 * <p>Title: Information_Info_CategoryService</p>
 * <p>Description: 接口层</p>
 * @author
 */
public interface Information_Info_CategoryDao {
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
     * @param InfoCategory 表单管理信息
     * @return 表单管理集合
     */
    List<Information_Info_Category> selectInfoCategoryList(Map<String, Object> params);
    /**
     * 新增
     *
     * @param InfoCategory 管理信息
     * @return 结果
     */
    int insert(Information_Info_Category InfoCategory);

    /**
     * 修改
     *
     * @param InfoCategory 信息
     * @return 结果
     */
    int update(Information_Info_Category InfoCategory);

    /**
     * 批量删除信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteByIds(Integer[] ids);

    int getCount(Map<String, Object> params);

    /**
     * 通过栏目guid获取信息
     * @param categoryGuid
     * @return
     */
    List<String> getInfoByCateGuid(@Param("categoryGuid") String categoryGuid);
}
