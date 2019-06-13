package com.basic.javaframe.dao;

import java.util.List;
import java.util.Map;

import com.basic.javaframe.entity.Frame_Module;
import org.apache.ibatis.annotations.Param;

public interface Frame_ModuleDao {

	List<Frame_Module> selectFrameModuleList(Map<String, Object> params);

	void insertFrameModule(Frame_Module module);

	void updateFrameModule(Frame_Module module);

	void deleteFrameModuleById(Integer[] ids);

	int getCount(Map<String, Object> params);

	List<Frame_Module> findModules();

	List<Frame_Module> getByPmoduleCode(String moduleCode);

	/**
	 * 通过模块编号查询上级模块
	 * @param moduleCode
	 * @return
	 */
	String getByModuleCode(String moduleCode);

	/**
	 * 模块名重复检测
	 *
	 * @param moduleName
	 */
	Frame_Module checkModuleName(@Param("moduleName")String moduleName);

	/**
	 * 模块地址重复检测
	 *
	 * @param moduleAddr
	 * @return moduleAddr
	 */
	Frame_Module checkModuleAddr(@Param("moduleAddr")String moduleAddr);

}
