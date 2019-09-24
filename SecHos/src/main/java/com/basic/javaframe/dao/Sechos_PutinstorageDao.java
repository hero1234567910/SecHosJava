package com.basic.javaframe.dao;

import com.basic.javaframe.entity.Sechos_Putinstorage;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author
 * @date 2019-09-24 09:43:42
 */
public interface Sechos_PutinstorageDao extends BaseDao<Sechos_Putinstorage> {

    /**
     * 入库表插入
     * @param purchaseGuid
     * @return
     */
    int insertStorage(@Param("purchaseGuid") String purchaseGuid);

    /**
     * 入库表更新时间与入库人
     * @param sechosPutinstorage
     * @return
     */
    int insertInDate(Sechos_Putinstorage sechosPutinstorage);

    /**
     * 获取有将近过期材料的单号
     * @return
     */
    List<String> getStorageOverdue();

    /**
     * 根据单号获取入库材料列表
     * @param purchaseGuid
     * @return
     */
    List<Sechos_Putinstorage> getListByPurchaseGuid(@Param("purchaseGuid") String purchaseGuid);
}
