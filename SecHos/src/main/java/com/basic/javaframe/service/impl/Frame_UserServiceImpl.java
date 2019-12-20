package com.basic.javaframe.service.impl;

import com.basic.javaframe.dao.Frame_DeptDao;
import com.basic.javaframe.dao.Frame_UserDao;
import com.basic.javaframe.entity.Frame_Dept;
import com.basic.javaframe.entity.Frame_User;
import com.basic.javaframe.service.Frame_UserService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Title: UserService</p>
 * <p>Description: 用户服务层</p>
 *
 * @author my
 */
@Service("userService")
public class Frame_UserServiceImpl implements Frame_UserService {


    @Autowired
    private Frame_UserDao frame_userDao;

    @Autowired
    private Frame_DeptDao frame_deptDao;

    //添加
    @Override
    public void insert(Frame_User user) {
        frame_userDao.insert(user);
    }

    //修改
    @Override
    public void update(Frame_User user) {
        frame_userDao.update(user);
    }

    @Override
    public void updateAdmin(Frame_User user) {
        frame_userDao.updateAdmin(user);
    }

    @Override
    public void delete(Integer[] ids) {

    }

    //删除
//    @Override
//    public void delete(Integer[] ids) {
//        frame_userDao.deleteUserById(ids);
//    }

    //批量删除
    @Override
    public void deleteUserById(String[] rowGuid) {
        frame_userDao.deleteUserById(rowGuid);
    }

    //启用用户
    @Override
    public void enableUserById(Integer[] ids) {
        frame_userDao.enableUserById(ids);
    }

    //禁用用户
    @Override
    public void forbidUserById(Integer[] ids) {
        frame_userDao.forbidUserById(ids);
    }

    //重置密码
    @Override
    public void resetPasswordById(String password, Integer[] rowIds) {
         frame_userDao.resetPasswordById(password,rowIds);
    }

    //保存排序号
    @Override
    public void saveSortSq(Integer[] rowId,Integer[] sortSq) {
        frame_userDao.saveSortSq(rowId,sortSq);
    }

//    //通过id查询
//    @Override
//    public User getById(int id) {
//        return frame_userDao.getById(id);
//    }

    //通过姓名查询
    @Override
    public Frame_User getFrameUserByLoginId(String loginId) {
        return frame_userDao.getFrameUserByLoginId(loginId);
    }

    //获取所有列表信息
    @Override
    public List<Frame_User> getUser(Map<String, Object> params) {
        // TODO Auto-generated method stub
        return frame_userDao.getUser(params);
    }

    @Override
    public List<Frame_User> getUserFromRole(String roleGuid) {
        return frame_userDao.getUserFromRole(roleGuid);
    }


    @Override
    public int getCount(Map<String, Object> params) {
        // TODO Auto-generated method stub
        return frame_userDao.getCount(params);
    }

	@Override
	public Frame_User findUserByGuid(String guid) {
		// TODO Auto-generated method stub
		return frame_userDao.findUserByGuid(guid);
	}

    @Override
    public <T> int checkUser(T t) {
        return frame_userDao.checkUser(t);
    }

    @Override
    public Map<String, Object> getDeptByGuid(String rowGuid) {
        String deptGuid= frame_userDao.getDeptByGuid(rowGuid);
        String deptName = frame_deptDao.getDeptNameByGuid(deptGuid);
        Map<String, Object> map = new HashMap<>();
        map.put("deptGuid", deptGuid);
        map.put("deptName", deptName);
        return map;
    }

    @Override
    public String checkOldPassword(String rowGuid) {
        return frame_userDao.checkOldPassword(rowGuid);
    }

    @Override
    public void updateNewPassword(String rowGuid,String password) {
         frame_userDao.updateNewPassword(rowGuid,password);
    }

    @Override
    public void updateLoginTime(Frame_User frameUser) {
        frame_userDao.updateLoginTime(frameUser);
    }

    @Override
    public Frame_User getUserByOpenid(String openid) {
        return frame_userDao.getUserByOpenid(openid);
    }

    @Override
    public List<Frame_User> getUserByDeptGuid(String deptGuid) {
        return frame_userDao.getUserByDeptGuid(deptGuid);
    }

	@Override
	public void updateOaUsers(List<Frame_User> users) {
		frame_userDao.deletOaUsers();
		frame_userDao.insertOaUsers(users);
	}

	@Override
	public Frame_User getOAUserByLoginId(String loginId) {
		// TODO Auto-generated method stub
		return frame_userDao.getOAUserByLoginId(loginId);
	}

	@Override
	public List<Frame_User> getOAUsersList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return frame_userDao.getOAUsersList(params);
	}
}


