package com.basic.javaframe.service;

import com.basic.javaframe.entity.Form_TableField;

import java.util.List;
import java.util.Map;

/**
 * 表单字段管理 服务层
 *
 * @author my
 * @date 2019-03-18
 */
public interface Form_TableFieldService {

    /**
     * 查询表单管理信息
     *
     * @param rowId 表单管理ID
     * @return 表单管理信息
     */
    Form_TableField selectFormTableFieldById(Integer rowId);

    /**
     * 查询表单管理信息
     *
     * @param name 表单名字
     * @return 表单管理信息
     */
    Form_TableField selectFormTableFieldByName(String name);
    
    /**
     * 查询表单管理列表
     *
     * @param form_tableField 表单管理信息
     * @return 表单管理集合
     */
    List<Form_TableField> selectFormTableFieldList(Map<String, Object> params);

    /**
     * 新增表单管理
     *
     * @param formTableField 表单管理信息
     * @return 结果
     */
    void insertFormTableField(Form_TableField formTableField);

    /**
     * 修改表单管理
     *
     * @param formTableField 表单管理信息
     * @return 结果
     */
    void updateFormTableField(Form_TableField formTableField);

    /**
     * 删除表单管理信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    void deleteFormTableFieldById(Integer[] ids);

    /**
     * 批量删除表单管理信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    void deleteFormTableFieldByIds(Integer[] ids);

    int getCount(Map<String, Object> params);
    
    List<Form_TableField> selectFieldByTableName(String rowGuid);

}
