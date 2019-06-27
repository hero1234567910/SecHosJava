package com.basic.javaframe.dao;

import com.basic.javaframe.entity.Sechos_Consultation;

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

    /**
     * 获取未回复信息数量
     * @return
     */
    int getReplyCount();
}
