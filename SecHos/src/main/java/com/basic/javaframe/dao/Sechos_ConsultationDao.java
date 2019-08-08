package com.basic.javaframe.dao;

import org.apache.ibatis.annotations.Param;

import com.basic.javaframe.entity.Sechos_Consultation;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author 
 * @date 2019-06-26 14:30:13
 */
public interface Sechos_ConsultationDao extends BaseDao<Sechos_Consultation> {
    /**
     * 回复咨询
     * @param sechosConsultation
     * @return
     */
    int reply(Sechos_Consultation sechosConsultation);

	Sechos_Consultation queryByGuid(@Param("rowGuid")String rowGuid);
	

    /**
     * 获取未回复信息数量
     * @return
     */
    int getReplyCount();

    /**
     * 数据概览
     * @return
     */
    List<Map<String, Object>> selectQusStatisical();
}
