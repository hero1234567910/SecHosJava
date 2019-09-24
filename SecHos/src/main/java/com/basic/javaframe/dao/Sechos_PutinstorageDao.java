package com.basic.javaframe.dao;

import com.basic.javaframe.entity.Sechos_Putinstorage;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

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


}
