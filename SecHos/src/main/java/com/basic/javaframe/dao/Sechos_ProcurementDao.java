package com.basic.javaframe.dao;

import com.basic.javaframe.entity.Sechos_Procurement;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author 
 * @date 2019-09-19 15:26:43
 */
public interface Sechos_ProcurementDao extends BaseDao<Sechos_Procurement> {

    int getCount2(Map<String, Object> map);

    List<Sechos_Procurement> getList2(Map<String, Object> map);

    Sechos_Procurement getDetailByGuid(String rowGuid);

}
