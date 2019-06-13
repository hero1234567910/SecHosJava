package com.basic.javaframe.service;

import com.basic.javaframe.entity.Frame_User_Extra;

import java.util.List;
import java.util.Map;

/**
 * <p>Title: UserExtraService</p>
 * <p>Description: 用户额外服务层</p>
 * @author wzl
 */
public interface Frame_User_ExtraService {

    /**
     * 插入用户额外信息
     *
     * @param userExtra 用户信息
     * @return 用户额外信息
     */
    void insertExtra(Frame_User_Extra userExtra);

    /**
     * 更新用户额外信息
     *
     * @param userExtra 用户id
     * @return 用户额外信息
     */
    void updateExtra(Frame_User_Extra userExtra);

    /**
     * 删除用户额外信息
     *
     * @param id 用户id
     * @return 用户额外信息
     */
    void deleteExtra(int id);

    /**
     * 批量删除用户额外信息
     *
     * @param id 用户id
     * @return 用户额外信息
     */
    void deleteExtraBatch(Integer[] id);

    /**
     * 通过id查询用户额外信息
     *
     * @param id 用户id
     * @return 用户额外信息
     */
    Frame_User_Extra getUserExtraById(Integer id);

    /**
     * 通过身份证查询用户额外信息
     *
     * @param idCard 用户名字
     * @return 用户额外信息
     */
    Frame_User_Extra getUserExtraByIdCard(String idCard);

    /**
     * 获取所有用户额外信息
     *
     * @param params 用户信息表
     * @return 用户额外信息
     */
    List<Frame_User_Extra> getUserExtra(Map<String,Object> params);

    int getCount(Map<String,Object> params);

}
