package com.basic.javaframe.service;

import com.basic.javaframe.entity.InformationInfo;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author 
 * @date 2019-04-09 14:18:17
 */
public interface InformationInfoService {
	
	List<InformationInfo> getList(Map<String, Object> map);

	/**
	 * 获取审核通过信息表
	 * @param params
	 * @return
	 */
	List<InformationInfo> getList2(Map<String, Object> params);

	int getCount2(Map<String,Object> params);

	int getCount(Map<String, Object> map);
	
	void save(InformationInfo informationInfo);
	
	void update(InformationInfo informationInfo);
	
	void deleteBatch(String[] categoryGuids);

	/**
	 * 审核通过
	 * @param rowGuid
	 */
	void auditPassInfo(String rowGuid);

	/**
	 * 审核不通过
	 * @param rowGuid
	 */
	void auditFailInfo(String rowGuid);

	//发布信息
	void deliverInfoById(Integer[] ids);

	//停止发布
	void stopDeliverById(Integer[] ids);

	void getTypeName(InformationInfo informationInfo);

	void infoOn(Integer id);

	void infoOff(Integer id);

	String getMInfoMation();

	/**
	 * 获取最新的单个医院简介信息
	 * @param categoryGuid
	 * @return
	 */
	InformationInfo getHosIntroduction(String categoryGuid);

	InformationInfo getWxInformation();
}
