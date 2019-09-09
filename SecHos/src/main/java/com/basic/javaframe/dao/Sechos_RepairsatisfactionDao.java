package com.basic.javaframe.dao;

import com.basic.javaframe.entity.Sechos_Repairsatisfaction;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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

    /**
     * 获取我的评价列表
     * @param map
     * @return
     */
    List<Sechos_Repairsatisfaction> getMySatList(Map<String, Object> map);

    int getMySatCount(Map<String, Object> params);
}
