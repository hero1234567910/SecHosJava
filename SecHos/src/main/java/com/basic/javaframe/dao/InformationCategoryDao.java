package com.basic.javaframe.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.basic.javaframe.entity.InformationCategory;

/**
 * 
 * 
 * @author 
 * @date 2019-04-09 11:30:38
 */
public interface InformationCategoryDao extends BaseDao<InformationCategory> {

	List<InformationCategory> findTopTrees();

	List<InformationCategory> getByPCategoryCode(String categoryCode);

	List<InformationCategory> getAllList();

	List<InformationCategory> getCategoryByGuid(@Param("categoryGuid")String categoryGuid);
	
}
