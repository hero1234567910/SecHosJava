package com.basic.javaframe.dao;

import com.basic.javaframe.entity.Form_TableField;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>Title: TableFieldService</p>
 * <p>Description: 表单字段设置接口层</p>
 * @author my
 */
public interface Form_TableFieldDao {

    /**
     * 表单字段管理信息
     *
     * @param rowId 表单字段管理ID
     * @return 表单字段管理信息
     */
    Form_TableField selectFormTableFieldById(Integer rowId);

    /**
     * 查询表单字段设置信息
     *
     * @param name 字段名字
     * @return 表单字段管理信息
     */
    Form_TableField selectFormTableFieldByName(String name);

    /**
     * 查询表单字段管理列表
     *
     * @param formTableField 表单字段管理信息
     * @return 表单字段管理集合
     */
    List<Form_TableField> selectFormTableFieldList(Map<String, Object> params);

    /**
     * 新增表单字段管理
     *
     * @param formTableField 表单字段管理信息
     * @return 结果
     */
    int insertFormTableField(Form_TableField  formTableField);

    /**
     * 修改表单字段管理
     *
     * @param formTableField 表单字段信息
     * @return 结果
     */
    int updateFormTableField(Form_TableField formTableField);

    /**
     * 删除表单字段管理信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteFormTableFieldById(Integer[] ids);

    /**
     * 批量删除表单字段管理信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteFormTableFieldByIds(Integer[] ids);

    int getCount(Map<String, Object> params);

	List<Form_TableField> selectFieldByTableName(@Param("rowGuid")String rowGuid);


}
