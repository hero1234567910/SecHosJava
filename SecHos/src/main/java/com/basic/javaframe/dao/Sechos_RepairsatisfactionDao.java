package com.basic.javaframe.dao;

import com.basic.javaframe.entity.Sechos_Repairsatisfaction;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * 
 * @author 
 * @date 2019-09-05 16:14:01
 */
public interface Sechos_RepairsatisfactionDao extends BaseDao<Sechos_Repairsatisfaction> {

    /**
     * 根据Guid查询信息
     * @param rowGuid
     * @return
     */
    Sechos_Repairsatisfaction getDetailByGuid(@Param("rowGuid") String rowGuid);
}
