package com.basic.javaframe.service;

import com.alibaba.fastjson.JSONArray;
import com.basic.javaframe.common.utils.Query;
import com.basic.javaframe.entity.Frame_Dept;
import java.util.List;
import java.util.Map;

/**
 * 部门管理 服务层
 *
 * @author wzl
 * @date 2019-03-06
 */
public interface Frame_DeptService {

    /**
     * 查询部门管理信息
     *
     * @param rowId 部门管理ID
     * @return 部门管理信息
     */
    Frame_Dept selectFrameDeptById(Integer rowId);

    /**
     * 查询部门管理信息
     *
     * @param name 部门名字
     * @return 部门管理信息
     */
    Frame_Dept selectFrameDeptByName(String name);

    /**
     * 查询部门管理列表
     *
     * @param frame_dept 部门管理信息
     * @return 部门管理集合
     */
     List<Frame_Dept> selectFrameDeptList(Map<String, Object> params);

    /**
     * 新增部门管理
     *
     * @param frameDept 部门管理信息
     * @return 结果
     */
    void insertFrameDept(Frame_Dept frameDept);

    /**
     * 修改部门管理
     *
     * @param frameDept 部门管理信息
     * @return 结果
     */
    void updateFrameDept(Frame_Dept frameDept);

    /**
     * 删除部门管理信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    void deleteFrameDeptById(Integer[] ids);

    /**
     * 批量删除部门管理信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    void deleteFrameDeptByIds(Integer[] ids);

	int getCount(Map<String, Object> params);

	JSONArray findTopDepts();

    String getByDeptCode(String deptCode);

	<T> int checkDept(T t);

	//通过部门行号获取名称
	void getDeptNameByGuid(String rowGuid);
}
