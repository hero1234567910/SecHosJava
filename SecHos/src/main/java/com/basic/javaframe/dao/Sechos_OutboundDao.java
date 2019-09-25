package com.basic.javaframe.dao;

import com.basic.javaframe.entity.Sechos_Outbound;
import com.basic.javaframe.entity.Sechos_Procurement;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author 
 * @date 2019-09-25 09:45:11
 */
public interface Sechos_OutboundDao extends BaseDao<Sechos_Outbound> {

    int getCount2(Map<String, Object> map);

    List<Sechos_Outbound> getList2(Map<String, Object> map);

    Sechos_Outbound getDetailByGuid(String rowGuid);

}
