package com.basic.javaframe.dao;

import com.basic.javaframe.entity.Frame_Role;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * 角色管理 Dao层
 *
 * @author my
 * @date 2019-03-11
 */
public interface Frame_RoleDao {
    /**
     * 查询角色信息
     *
     * @param rowId 角色ID
     * @return 角色信息
     */
    Frame_Role selectFrame_RoleById(Integer rowId);

    /**
     * 查询角色管理信息
     *
     * @param name 角色名
     * @return 角色信息
     */
    Frame_Role selectFrameRoleByName(String name);

    /**
     * 查询角色管理表
     *
     * @param frame_role 角色管理信息
     * @return 角色管理集合
     */
    List<Frame_Role> selectFrameRoleList(Map<String, Object> params);

    /**
     * 新增角色管理
     *
     * @param frameRole 角色管理信息
     * @return 结果
     */
    int insertFrameRole(Frame_Role frameRole);

    /**
     * 修改角色管理
     *
     * @param frameRole 角色管理信息
     * @return 结果
     */
    int updateFrameRole(Frame_Role frameRole);

    /**
     * 删除角色管理信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    void deleteFrameRoleByRowGuid(@Param("rowGuid")String[] ids);

    /**
     * 批量删除角色管理信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteFrameRoleByIds(Integer[] ids);

    int getCount(Map<String, Object> params);

	List<Frame_Role> getAllRole();

    /**
     * 角色名重复检测
     *
     * @param roleName
     * @return
     */
    Frame_Role checkUserByRoleName(@Param("roleName") String roleName);
}
