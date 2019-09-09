package com.basic.javaframe.dao;

import com.basic.javaframe.entity.Sechos_Repair;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;
import java.util.Map;

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

    /**
     * 根据Guid查询个人报修信息
     * @param rowGuid
     * @return
     */
    List<Sechos_Repair> getListByGuid(@Param("repairGuid") String rowGuid);

    /**
     * 报修状态改变
     * @param rowGuid
     * @return
     */
    int cancelRepair(@Param("rowGuid") String rowGuid);

    /**
     * 获取维修列表
     * @param map
     * @return
     */
    List<Sechos_Repair> getMaintainList(Map<String, Object> map);

    int getMaintainCount(Map<String, Object> params);

    /**
     * 报修状态改变
     * @param params
     * @return
     */
    int successRepair(Map<String, Object> params);

    /**
     * 获取维修记录列表
     * @param map
     * @return
     */
    List<Sechos_Repair> getMyList(Map<String, Object> map);

    int getMyCount(Map<String, Object> params);
}
