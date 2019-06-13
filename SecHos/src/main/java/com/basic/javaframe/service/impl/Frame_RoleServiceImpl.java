package com.basic.javaframe.service.impl;

import com.basic.javaframe.dao.Frame_RoleDao;
import com.basic.javaframe.entity.Frame_Role;
import com.basic.javaframe.service.Frame_RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 角色管理 IMPL服务层
 *
 * @author my
 * @date 2019-03-11
 */
@Service("roleService")
public class Frame_RoleServiceImpl implements Frame_RoleService {

    @Autowired
    private Frame_RoleDao frame_roleDao;

    /**
     * 查询角色管理信息
     *
     * @param rowId 角色管理ID
     * @return 角色管理信息
     */
    @Override
    public Frame_Role selectFrameRoleById(Integer rowId) {
        return frame_roleDao.selectFrame_RoleById(rowId);
    }

    /**
     * 查询角色名
     *
     * @param name 角色管理信息
     * @return 角色管理集合
     */
    @Override
    public Frame_Role selectFrameRoleByName(String name) {
        return frame_roleDao.selectFrameRoleByName(name);
    }

    /**
     * 查询角色管理列表
     *
     * @param frame_role 角色管理信息
     * @return 角色管理集合
     */
    @Override
    public List<Frame_Role> selectFrameRoleList(Map<String, Object> params) {
        return frame_roleDao.selectFrameRoleList(params);
    }

    /**
     * 增加角色
     *
     * @param frameRole 角色管理信息
     * @return 角色管理集合
     */
    @Override
    public void insertFrameRole(Frame_Role frameRole) {
       frame_roleDao.insertFrameRole(frameRole);
    }

    /**
     * 更新角色
     *
     * @param frameRole 角色管理信息
     * @return 角色管理集合
     */
    @Override
    public void updateFrameRole(Frame_Role frameRole) {
        frame_roleDao.updateFrameRole(frameRole);
    }

    /**
     * 删除角色
     *
     * @param ids 角色id
     * @return 角色管理集合
     */
    @Override
    public void deleteFrameRoleByRowGuid(String[] ids) {
       frame_roleDao.deleteFrameRoleByRowGuid(ids);
    }

    /**
     * 批量删除角色
     *
     * @param ids 角色id
     * @return 角色管理集合
     */
    @Override
    public void deleteFrameRoleByIds(Integer[] ids) {
       frame_roleDao.deleteFrameRoleByIds(ids);
    }

    @Override
    public int getCount(Map<String, Object> params) {
        return frame_roleDao.getCount(params);
    }

	@Override
	public List<Frame_Role> getAllRole() {
		// TODO Auto-generated method stub
		return frame_roleDao.getAllRole();
	}

    @Override
    public Frame_Role checkUserByRoleName(String roleName) {
        return frame_roleDao.checkUserByRoleName(roleName);
    }
}
