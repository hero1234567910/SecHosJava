package com.basic.javaframe.dao;

import com.basic.javaframe.entity.Sechos_Putinstorage;
import com.basic.javaframe.entity.Sechos_Stockremoval;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * 
 * @author 
 * @date 2019-09-25 14:43:58
 */
public interface Sechos_StockremovalDao extends BaseDao<Sechos_Stockremoval> {

    /**
     * 入库表插入
     * @param outboundGuid
     * @return
     */
    int insertRemove(@Param("outboundGuid") String outboundGuid);

    /**
     * 入库表更新时间与入库人
     * @param sechosStockremoval
     * @return
     */
    int insertOutDate(Sechos_Stockremoval sechosStockremoval);

    /**
     * 根据单号获取入库材料列表
     * @param outboundGuid
     * @return
     */
    List<Sechos_Stockremoval> getListByOutboundGuid(@Param("outboundGuid") String outboundGuid);

    /**删除冗余**
     *
     */
    int deleteInsert(String outboundGuid);
}
