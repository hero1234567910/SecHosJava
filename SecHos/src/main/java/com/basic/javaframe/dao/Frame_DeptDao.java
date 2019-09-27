package com.basic.javaframe.dao;

import com.basic.javaframe.entity.Frame_Dept;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * 部门管理 Dao层
 *
 * @author wzl
 * @date 2019-03-06
 */

public interface Frame_DeptDao {

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
    int insertFrameDept(Frame_Dept frameDept);

    /**
     * 修改部门管理
     *
     * @param frameDept 部门管理信息
     * @return 结果
     */
    int updateFrameDept(Frame_Dept frameDept);

    /**
     * 删除部门管理信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteFrameDeptById(Integer[] ids);

    /**
     * 批量删除部门管理信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteFrameDeptByIds(Integer[] ids);

	int getCount(Map<String, Object> params);
	
	/**
	 * 查询所有顶级部门
	 * <p>Title: findTopDepts</p>  
	 * <p>Description: 查询所有顶级部门</p>
	 * @author hero  
	 * @return
	 */
	List<Frame_Dept> findTopDepts();

	List<Frame_Dept> getByPdeptCode(String deptCode);

    /**
     * 通过部门编号查询上级部门
     * @param deptCode
     */
    String getByDeptCode(String deptCode);

	<T> int checkDept(T t);

    /**
     * 通过guid 获取部门名
     */
    String getDeptNameByGuid(String rowGuid);

    /**
     * 通过名称获取GUID
     * @param deptName
     * @return
     */
    String getDeptGuidByName(String deptName);
}
