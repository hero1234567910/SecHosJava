package com.basic.javaframe.dao;

import com.basic.javaframe.entity.Sechos_Repair;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
}
