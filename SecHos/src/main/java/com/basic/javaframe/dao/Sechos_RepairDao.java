package com.basic.javaframe.dao;

import com.basic.javaframe.entity.Sechos_Repair;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * 
 * @author 
 * @date 2019-08-28 10:11:37
 */
public interface Sechos_RepairDao extends BaseDao<Sechos_Repair> {

    /**
     * 根据Guid查询信息
     * @param rowGuid
     * @return
     */
    Sechos_Repair getDetailByGuid(@Param("rowGuid") String rowGuid);
	
}
