package com.basic.javaframe.dao;

import com.basic.javaframe.entity.Frame_Role_User;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * 用户与角色关系管理 Dao层
 *
 * @author my
 * @date 2019-03-12
 */
public interface Frame_Role_UserDao {
    /**
     * 查询用户与角色关系信息
     *
     * @param rowId 用户ID
     * @return 用户与角色关系管理信息
     */
    Frame_Role_User selectFrameRoleUserById(Integer rowId);

    /**
     * 查询用户角色关系管理信息
     *
     * @param name 用户名字
     * @return 用户与角色关系管理信息
     */
    Frame_Role_User selectFrameRoleUserByName(String name);

    /**
     * 查询用户与角色关系管理列表
     *
     * @param frame_Role_User 用户与角色关系管理信息
     * @return 用户与角色关系集合
     */
    List<Frame_Role_User> selectFrameRoleUserList(Map<String, Object> params);

    /**
     * 新增用户与角色关系管理
     *
     * @param frame_Role_User 用户与角色关系管理信息
     * @return 结果
     */
    int insertFrameRoleUser(Frame_Role_User frame_Role_User);

    /**
     * 修改用户与角色关系管理
     *
     * @param frame_Role_User 用户与角色关系管理信息
     * @return 结果
     */
    int updateFrameRoleUser(Frame_Role_User frame_Role_User);

    /**
     * 删除用户与角色关系管理信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteFrameRoleUserById(Integer[] ids);

    /**
     * 批量删除用户与角色关系管理信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteFrameRoleUserByIds(Integer[] ids);

    int getCount(Map<String, Object> params);

	void insertBatch(List<Frame_Role_User> roUserList);

	List<String> getCheckedRole();

	List<String> getCheckedRole(@Param("userGuid")String userGuid);

	void deleteByUserId(@Param("userGuid")String userGuid);

    void deleteRoleUserByGuid(@Param("userGuid")String[] userGuids);

}
