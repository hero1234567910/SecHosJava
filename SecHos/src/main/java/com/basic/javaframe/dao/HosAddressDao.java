package com.basic.javaframe.dao;

import com.basic.javaframe.entity.HosAddress;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 
 * 
 * @author 
 * @date 2019-05-10 09:20:48
 */
public interface HosAddressDao extends BaseDao<HosAddress> {

    /**
     * 根据用户Guid查询地址
     * @param hosUserGuid
     * @return
     */
    List<HosAddress> getAddressListByUserGuid(@Param("hosUserGuid") String hosUserGuid);
	
}
