package com.basic.javaframe.service.impl;

import com.basic.javaframe.dao.Frame_Role_UserDao;
import com.basic.javaframe.entity.Frame_Role_User;
import com.basic.javaframe.service.Frame_Role_UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
/**
 * 用户与角色关系管理 IMPL服务层
 *
 * @author my
 * @date 2019-03-12
 */
@Service("frame_role_user Service")
public class Frame_Role_UserServiceImpl implements Frame_Role_UserService {

    @Autowired
    private Frame_Role_UserDao frame_role_userDao;

    /**
     * 查询用户与角色关系管理信息
     *
     * @param rowId 用户管理ID
     * @return 用户与角色关系管理信息
     */
    @Override
    public Frame_Role_User selectFrameRoleUserById(Integer rowId) {
        return frame_role_userDao.selectFrameRoleUserById(rowId);
    }

    /**
     * 查询用户名字
     *
     * @param name 用户与角色关系管理信息
     * @return 用户与角色关系管理集合
     */
    @Override
    public Frame_Role_User selectFrameRoleUserByName(String name) {
        return frame_role_userDao.selectFrameRoleUserByName(name);
    }

    /**
     * 查询用户与角色关系管理列表
     *
     * @param frame_Role_User 用户与角色信息管理信息
     * @return 用户与角色信息管理集合
     */
    @Override
    public List<Frame_Role_User> selectFrameRoleUserList(Map<String, Object> params) {
        return frame_role_userDao.selectFrameRoleUserList(params);
    }

    /**
     * 增加用户与角色关系
     *
     * @param frame_Role_User 用户与角色信息管理信息
     * @return 用户与角色关系管理集合
     */
    @Override
    public void insertFrameRoleUser(Frame_Role_User frame_Role_User) {
        frame_role_userDao.insertFrameRoleUser(frame_Role_User);
    }

    /**
     * 更新用户与角色关系
     *
     * @param frame_Role_User 用户与角色关系管理信息
     * @return 用户与角色关系管理集合
     */
    @Override
    public void updateFrameRoleUser(Frame_Role_User frame_Role_User) {
        frame_role_userDao.updateFrameRoleUser(frame_Role_User);
    }

    /**
     * 删除关系
     *
     * @param ids 用户id
     * @return 用户与角色关系管理集合
     */
    @Override
    public void deleteFrameRoleUserById(Integer[] ids) {
       frame_role_userDao.deleteFrameRoleUserById(ids);
    }

    /**
     * 批量删除关系
     *
     * @param ids 用户id
     * @return 用户与角色关系管理集合
     */
    @Override
    public void deleteFrameRoleUserByIds(Integer[] ids) {
        frame_role_userDao.deleteFrameRoleUserByIds(ids);
    }

    @Override
    public int getCount(Map<String, Object> params) {
        return frame_role_userDao.getCount(params);
    }

	@Override
	public void insertBatch(List<Frame_Role_User> roUserList) {
		// TODO Auto-generated method stub
		frame_role_userDao.insertBatch(roUserList);
	}

	@Override
	public List<String> getCheckedRole() {
		// TODO Auto-generated method stub
		return frame_role_userDao.getCheckedRole();
	}

	@Override
	public List<String> getCheckedRole(String userGuid) {
		// TODO Auto-generated method stub
		return frame_role_userDao.getCheckedRole(userGuid);
	}

	@Override
	public void deleteByUserId(String rowId) {
		// TODO Auto-generated method stub
		frame_role_userDao.deleteByUserId(rowId);
	}

    @Override
    public void deleteRoleUserByGuid(String[] userGuids) {
        frame_role_userDao.deleteRoleUserByGuid(userGuids);
    }
}
