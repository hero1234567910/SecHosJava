package com.basic.javaframe.service;

import com.basic.javaframe.entity.Frame_Attach;

import java.util.List;
import java.util.Map;

public interface Frame_AttachService {
    /**
     * 查询附件表管理信息
     *
     * @param rowId 表单管理ID
     * @return 表单管理信息
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
     * @param frameAttach 表单管理信息
     * @return 表单管理集合
     */
    List<Frame_Attach> selectFrameAttachList(Map<String, Object> params);

    /**
     * 新增表单管理
     *
     * @param frameAttach 表单管理信息
     * @return 结果
     */
    void insertFrameAttach(Frame_Attach frameAttach);

    /**
     * 修改表单管理
     *
     * @param frameAttach 表单管理信息
     * @return 结果
     */
    void updateFrameAttach(Frame_Attach frameAttach);

    /**
     * 批量删除表单管理信息
     *
     * @param guids 需要删除的数据ID
     * @return 结果
     */
    void deleteFrameAttachByIds(String[] guids);

    int getCount(Map<String, Object> params);
    
    void updateAttach(String attachGuid,String[] rowGuid);

	Frame_Attach getByFormGuid(String guid);

	List<Frame_Attach> getAttachList(String guid);

}
