package com.basic.javaframe.dao;

import com.basic.javaframe.entity.HosUser;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * 
 * @author 
 * @date 2019-04-29 13:49:42
 */
public interface HosUserDao extends BaseDao<HosUser> {
    /**
     * 通过openid获取用户信息
     * @param openid
     * @return
     */
    HosUser getUserByOpenid(String openid);

	HosUser getUserByGuid(@Param("userGuid")String userGuid);
	
}
