package com.basic.javaframe.service;


import com.basic.javaframe.entity.Frame_Role_User;

import java.util.List;
import java.util.Map;

/**
 * 用户与角色关系管理 服务层
 *
 * @author my
 * @date 2019-03-12
 */
public interface Frame_Role_UserService {

    /**
     * 查询用户角色关系管理信息
     *
     * @param rowId 用户与角色关系管理ID
     * @return 用户与角色关系管理信息
     */
    Frame_Role_User selectFrameRoleUserById(Integer rowId);

    /**
     * 查询用户与角色关系管理信息
     *
     * @param name 用户名字
     * @return 用户与角色关系管理信息
     */
    Frame_Role_User selectFrameRoleUserByName(String name);

    /**
     * 查询用户与角色关系管理列表
     *
     * @param frame_Role_User 用户与角色关系管理信息
     * @return 用户与角色关系管理集合
     */
    List<Frame_Role_User> selectFrameRoleUserList(Map<String, Object> params);

    /**
     * 新增用户与角色关系管理
     *
     * @param frame_Role_User 用户与角色关系管理信息
     * @return 结果
     */
    void insertFrameRoleUser(Frame_Role_User frame_Role_User);

    /**
     * 修改用户与角色关系管理
     *
     * @param frame_Role_User 用户与角色关系管理信息
     * @return 结果
     */
    void updateFrameRoleUser(Frame_Role_User frame_Role_User);

    /**
     * 删除用户与角色关系管理信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    void deleteFrameRoleUserById(Integer[] ids);

    /**
     * 批量删除用户与角色关系管理信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    void deleteFrameRoleUserByIds(Integer[] ids);

    int getCount(Map<String, Object> params);

	void insertBatch(List<Frame_Role_User> roUserList);

	List<String> getCheckedRole();

	List<String> getCheckedRole(String userGuid);

	void deleteByUserId(String userGuid);

    void deleteRoleUserByGuid(String[] userGuids);
}
