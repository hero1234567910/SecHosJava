package com.basic.javaframe.service.impl;

import com.basic.javaframe.dao.Frame_User_ExtraDao;
import com.basic.javaframe.entity.Frame_User_Extra;
import com.basic.javaframe.service.Frame_User_ExtraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>Title: UserExtraServiceImpl</p>
 * <p>Description: 用户额外服务层</p>
 * @author wzl
 */
@Service("userExtraService")
public class Frame_User_ExtraServiceImpl implements Frame_User_ExtraService {

    @Autowired
    private Frame_User_ExtraDao userExtraDao;

    /**
     * 插入用户额外信息
     *
     * @param userExtra 用户额外信息
     * @return 用户额外信息
     */
    @Override
    public void insertExtra(Frame_User_Extra userExtra) {
        userExtraDao.insertExtra(userExtra);
    }

    /**
     * 更新用户额外信息
     *
     * @param userExtra 用户额外信息
     * @return 用户额外信息
     */
    @Override
    public void updateExtra(Frame_User_Extra userExtra) {
        userExtraDao.updateExtra(userExtra);
    }

    /**
     * 删除用户额外信息
     *
     * @param id 用户额外信息id
     * @return 用户额外信息id
     */
    @Override
    public void deleteExtra(int id) {
        userExtraDao.deleteExtra(id);
    }

    /**
     * 批量删除用户额外信息
     *
     * @param id 用户额外信息ids
     * @return 用户额外信息ids
     */
    @Override
    public void deleteExtraBatch(Integer[] id) {
        userExtraDao.deleteExtraBatch(id);
    }

    /**
     * 通过id查询用户额外信息
     *
     * @param id 用户额外信息id
     * @return 用户额外信息
     */
    @Override
    public Frame_User_Extra getUserExtraById(Integer id) {
        return userExtraDao.getUserExtraById(id);
    }

    /**
     * 通过身份证查询用户额外信息
     *
     * @param idCard 用户身份证
     * @return 用户额外信息
     */
    @Override
    public Frame_User_Extra getUserExtraByIdCard(String idCard) {
        return userExtraDao.getUserExtraByIdCard(idCard);
    }

    /**
     * 获取所有用户额外信息
     *
     * @param params 用户额外信息
     * @return 用户额外信息
     */
    @Override
    public List<Frame_User_Extra> getUserExtra(Map<String, Object> params) {
        return userExtraDao.getUserExtra(params);
    }

    /**
     * 分页用户额外信息
     *
     * @param params 用户额外信息
     * @return 用户额外信息
     */
    @Override
    public int getCount(Map<String, Object> params) {
        return userExtraDao.getCount(params);
    }
}
