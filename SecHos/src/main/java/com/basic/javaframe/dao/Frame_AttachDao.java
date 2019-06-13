package com.basic.javaframe.dao;

import com.basic.javaframe.entity.Frame_Attach;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface Frame_AttachDao {
    /**
     * 附件表信息
     *
     * @param rowId 附件表ID
     * @return 附件表信息
     */
    Frame_Attach selectFrameAttachById(Integer rowId);

    /**
     * 查询表单管理信息
     *
     * @param name 表单名字
     * @return 表单管理信息
     */
    Frame_Attach selectFrameAttachByName(String name);

    /**
     * 查询表单管理列表
     *
     * @param Frame_Attach 表单管理信息
     * @return 表单管理集合
     */
    List<Frame_Attach> selectFrameAttachList(Map<String, Object> params);

    /**
     * 新增表单管理
     *
     * @param frameAttach 表单管理信息
     * @return 结果
     */
    int insertFrameAttach(Frame_Attach frameAttach);

    /**
     * 修改表单管理
     *
     * @param frameAttach 部门表单信息
     * @return 结果
     */
    int updateFrameAttach(Frame_Attach frameAttach);

    /**
     * 删除表单管理信息
     *
     * @param guids 需要删除的数据ID
     * @return 结果
     */
    int deleteFrameAttachById(@Param("guids")String[] guids);

    int getCount(Map<String, Object> params);

	void updateAttach(@Param("attachGuid")String attachGuid, @Param("rowGuid")String[] rowGuid);

	Frame_Attach getByFormGuid(@Param("guid")String guid);

	List<Frame_Attach> getAttachList(@Param("guid")String guid);
}
