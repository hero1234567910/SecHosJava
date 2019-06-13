package com.basic.javaframe.dao;

import com.basic.javaframe.entity.InformationInfo;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author 
 * @date 2019-04-09 14:18:17
 */
public interface InformationInfoDao extends BaseDao<InformationInfo> {


    /**
     * 获取所有审核通过的信息
     * @param params
     * @return
     */
    List<InformationInfo> getList2(Map<String,Object> params);
    /**
     * 获取审核通过信息的数量
     * @param params
     * @return
     */
    int getCount2(Map<String,Object> params);
    /**
     * 审核通知通过
     * @param rowGuid
     * @return
     */
    int auditPassInfo(String rowGuid);

    /**
     * 审核不通过
     * @param rowGuid
     * @return
     */
    int auditFailInfo(String rowGuid);

    /**
     * 发布信息
     *
     * @param ids 需要启用的数据ID
     * @return 结果
     */
    int deliverInfoById(Integer[] ids);

    /**
     *停止发布
     *
     * @param ids 需要禁用的数据ID
     * @return 结果
     */
    int stopDeliverById(Integer[] ids);

    int getTypeName(InformationInfo informationInfo);

    //信息状态On
    int infoOn(Integer id);
    //信息状态Off
    int infoOff(Integer id);
    /**
     * 获取发布的通告
     * <p>Title: getMInfoMation</p>  
     * <p>Description: </p>
     * @author hero  
     * @return
     */
	String getMInfoMation();
}
