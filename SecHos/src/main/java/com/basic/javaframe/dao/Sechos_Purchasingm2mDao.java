package com.basic.javaframe.dao;

import com.basic.javaframe.entity.Sechos_Purchasingm2m;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * 
 * @author 
 * @date 2019-09-20 10:55:46
 */
public interface Sechos_Purchasingm2mDao extends BaseDao<Sechos_Purchasingm2m> {

    /**
     * 根据订单Guid获取采购详细数据
     * @param purchaseGuid
     * @return
     */
    List<Sechos_Purchasingm2m> getListByPGuid(@Param("purchaseGuid") String purchaseGuid);
	
}
