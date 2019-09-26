package com.basic.javaframe.dao;

import com.basic.javaframe.entity.Sechos_Outboundm2m;
import com.basic.javaframe.entity.Sechos_Purchasingm2m;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * 
 * @author 
 * @date 2019-09-25 10:43:02
 */
public interface Sechos_Outboundm2mDao extends BaseDao<Sechos_Outboundm2m> {

    /**
     * 根据订单Guid获取采购详细数据
     * @param outboundGuid
     * @return
     */
    List<Sechos_Outboundm2m> getListByPGuid(@Param("outboundGuid") String outboundGuid);

    /**
     * 重复出库检测
     * @param t
     * @param <T>
     * @return
     */
    <T> int getCountByGuid(T t);
}
