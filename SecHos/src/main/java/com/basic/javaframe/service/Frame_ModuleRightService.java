package com.basic.javaframe.service;

import com.alibaba.fastjson.JSONArray;
import com.basic.javaframe.entity.Frame_ModuleRight;

import java.util.List;

/**
 * 模块权限
* <p>Title: Frame_ModuleRightService</p>
* <p>Description: 模块权限</p>
* @author hero
 */
public interface Frame_ModuleRightService {

	 /**
     * 新增模块权限
     *
     * @param
     * @return 结果
     */
    void insertFrameModuleRight(Frame_ModuleRight moduleRight);

    /**
     * 批量新增模块权限
     *
     * @param
     * @return 结果
     */
    void insertModuleRightBatch(List<Frame_ModuleRight> frameModuleRights);

    /**
     * 批量新增之前的删除
     *
     * @param
     * @return 结果
     */
    void deleteModuleRightBatch(String roleGuid);

    /**
     * 删除模块包含内容
     *
     * @param
     * @return 结果
     */
    void deleteModuleRightByGuid(String[] rowGuids);


    /**
     * 修改模块权限
     *
     * @param
     * @return 结果
     */
    void updateFrameModuleRight(Frame_ModuleRight moduleRight);

    /**
     * 删除模块权限
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    void deleteFrameModuleRightById(Integer[] ids);

	JSONArray selectModuleByRoleGuid(String roleGuid);

	JSONArray selectModuleByRole(String roleGuid);

}
