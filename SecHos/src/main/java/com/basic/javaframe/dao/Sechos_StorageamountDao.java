package com.basic.javaframe.dao;

import com.basic.javaframe.entity.Sechos_Storageamount;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * 
 * @author 
 * @date 2019-09-24 16:23:08
 */
public interface Sechos_StorageamountDao extends BaseDao<Sechos_Storageamount> {

    /**
     * 更新数量
     * @param Num
     * @param drugCode
     * @return
     */
    int updateNum(@Param("Num")Integer Num,@Param("drugCode")String drugCode);

    /**
     * 获取总数
     * @param drugGuid
     * @return
     */
    int getSumByGuid(@Param("drugGuid")String drugGuid);

    int subtractNum(@Param("Num")Integer Num,@Param("drugCode")String drugCode);
	
}
