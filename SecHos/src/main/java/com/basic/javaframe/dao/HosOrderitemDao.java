package com.basic.javaframe.dao;

import com.basic.javaframe.entity.HosOrderitem;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * 
 * @author 
 * @date 2019-04-29 13:49:14
 */
public interface HosOrderitemDao extends BaseDao<HosOrderitem> {

    /**
     * 通过orderGuid查询goodsGuid
     * @param orderGuid
     * @return
     */
    String getGoodsByItemGuid(@Param("orderGuid")String orderGuid);
	
}
