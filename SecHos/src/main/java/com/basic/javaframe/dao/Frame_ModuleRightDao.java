package com.basic.javaframe.dao;

import java.util.List;

import com.basic.javaframe.entity.Frame_User;
import org.apache.ibatis.annotations.Param;

import com.basic.javaframe.entity.Frame_ModuleRight;

public interface Frame_ModuleRightDao {

	//批量新增
	void insertModuleRightBatch(List<Frame_ModuleRight> frameModuleRights);

	//批量删除
	void deleteModuleRightBatch(@Param("roleGuid")String roleGuid);

	void insertFrameModuleRight(Frame_ModuleRight moduleRight);

	void updateFrameModuleRight(Frame_ModuleRight moduleRight);

	void deleteFrameModuleRightById(Integer[] ids);

	void deleteModuleRoleByGuid(@Param("rowGuid")String[] rowGuid);

	List<Frame_ModuleRight> selectModuleByRoleGuid(@Param("roleGuid")String roleGuid);

}
