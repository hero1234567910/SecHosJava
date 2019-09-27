package com.basic.javaframe.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.basic.javaframe.entity.Frame_Dept;
import com.basic.javaframe.entity.Frame_Module;

public interface Frame_ModuleService {
	/**
     * 查询部门管理列表
     *
     * @param params 部门管理信息
     * @return 部门管理集合
     */
     List<Frame_Module> selectFrameModuleList(Map<String, Object> params);

    /**
     * 新增部门管理
     *
     * @param module 部门管理信息
     * @return 结果
     */
    void insertFrameModule(Frame_Module module);

    /**
     * 修改部门管理
     *
     * @param module 部门管理信息
     * @return 结果
     */
    void updateFrameModule(Frame_Module module);
    
    /**
     * 删除部门管理信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    void deleteFrameModuleById(Integer[] ids);
   

	int getCount(Map<String, Object> params);

	JSONArray findModules();

	JSONArray getTrees();

	String getByModuleCode(String moduleCode);

    /**
     * 模块名重复检测接口
     * @param moduleName
     *
     */
    Frame_Module checkModuleName(String moduleName);

    /**
     * 模块地址重复检测接口
     * @param moduleAddr
     *
     */
    Frame_Module checkModuleAddr(String moduleAddr);
}
