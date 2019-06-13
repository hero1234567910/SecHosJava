package com.basic.javaframe.service;

import com.basic.javaframe.entity.Form_TableInfo;

import java.util.List;
import java.util.Map;
/**
 * 表单管理 服务层
 *
 * @author my
 * @date 2019-03-18
 */
public interface Form_TableInfoService {


        /**
         * 查询表单管理信息
         *
         * @param rowId 表单管理ID
         * @return 表单管理信息
         */
        Form_TableInfo selectFormTableInfoById(Integer rowId);

        /**
         * 查询表单管理信息
         *
         * @param name 表单名字
         * @return 表单管理信息
         */
        Form_TableInfo selectFormTableInfoByName(String name);

        /**
         * 查询表单管理列表
         *
         * @param form_tableInfo 表单管理信息
         * @return 表单管理集合
         */
        List<Form_TableInfo> selectFormTableInfoList(Map<String, Object> params);

        /**
         * 新增表单管理
         *
         * @param formTableInfo 表单管理信息
         * @return 结果
         */
        void insertFormTableInfo(Form_TableInfo formTableInfo);

        /**
         * 修改表单管理
         *
         * @param formTableInfo 表单管理信息
         * @return 结果
         */
        void updateFormTableInfo(Form_TableInfo formTableInfo);

        /**
         * 删除表单管理信息
         *
         * @param ids 需要删除的数据ID
         * @return 结果
         */
        void deleteFormTableInfoById(Integer[] ids);

        /**
         * 批量删除表单管理信息
         *
         * @param ids 需要删除的数据ID
         * @return 结果
         */
        void deleteFormTableInfoByIds(Integer[] ids);

        int getCount(Map<String, Object> params);

        /**
         * 新增表单关联
         *
         * @param formTableInfo 表单关联信息
         * @return 结果
         */
        void insertGuid(Form_TableInfo formTableInfo);
    }


