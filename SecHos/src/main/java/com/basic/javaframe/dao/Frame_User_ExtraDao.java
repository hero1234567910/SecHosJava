package com.basic.javaframe.dao;

import com.basic.javaframe.entity.Frame_User_Extra;

import java.util.List;
import java.util.Map;

/**
 * <p>Title: User_Extra_Dao</p>
 * <p>Description: 用户额外信息接口层</p>
 * @author wzl
 */
public interface Frame_User_ExtraDao {
    //删除
    int deleteExtra(Integer id);

    //批量删除
    int deleteExtraBatch(Integer[] id);

    //插入
    int insertExtra(Frame_User_Extra userExtra);

    //修改
    int updateExtra(Frame_User_Extra userExtra);

    //通过id查询
    Frame_User_Extra getUserExtraById(Integer id);

    //通过身份证查询
    Frame_User_Extra getUserExtraByIdCard(String idCard);

    //查询所有
    List<Frame_User_Extra> getUserExtra(Map<String,Object> params);

    int getCount(Map<String,Object> params);

}
