package com.basic.javaframe.service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.basic.javaframe.common.utils.Query;
import com.basic.javaframe.entity.Frame_User;

/**
 * <p>Title: UserService</p>
 * <p>Description: 用户服务层</p>
 * @author my
 */
public interface Frame_UserService {
    //新增
    void insert(Frame_User user);

    //编辑
    void update(Frame_User user);
    void updateAdmin(Frame_User user);

    //删除
    void delete(Integer[] ids);

    //批量删除
    void deleteUserById(String[] rowGuid);

    //启用用户
    void enableUserById(Integer[] ids);

    //禁用用户
    void forbidUserById(Integer[] ids);

    //重置密码
    void resetPasswordById(String password,Integer[] rowIds);

    //修改排序号
    void saveSortSq(Integer[] rowId,Integer[] sortSq);

//    //获取id
//    User getById(int id);

    //通过姓名查询
    Frame_User getFrameUserByLoginId(String loginId);

    //获取所有启用用户
    List<Frame_User> getUser(Map<String, Object> params);

    //点击角色获取用户
    List<Frame_User> getUserFromRole(String roleGuid);

	int getCount(Map<String, Object> params);

	Frame_User findUserByGuid(String guid);

	//用户名重复检测
    <T> int checkUser(T t);

    //通过用户行号获取部门guid和名称
    Map<String, Object> getDeptByGuid(String rowGuid);

    //验证旧密码
    String checkOldPassword(String rowGuid);

    //更新新密码
    void updateNewPassword(String rowGuid,String password);

    //更新登录时间
    void updateLoginTime(Frame_User frameUser);

    Frame_User getUserByOpenid(String openid);

    List<Frame_User> getUserByDeptGuid(String deptGuid);

	void updateOaUsers(List<Frame_User> users);

	Frame_User getOAUserByLoginId(String loginId);

	List<Frame_User> getOAUsersList(Map<String, Object> params);
	
	


}
