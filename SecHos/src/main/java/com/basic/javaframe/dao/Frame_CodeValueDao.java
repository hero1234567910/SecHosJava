package com.basic.javaframe.dao;

import com.basic.javaframe.entity.Frame_CodeValue;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * <p>Title: CodeValueDao</p>
 * <p>Description: 代码值接口层</p>
 * @author wzl
 */
public interface Frame_CodeValueDao {
    /**
     * 查询代码值参数列表
     *
     * @param params 系统参数类别信息
     * @return 集合
     */
    List<Frame_CodeValue> getCodeValue(Map<String,Object> params);

    /**
     * 查询代码关联值参数列表
     *
     * @param params 系统参数类别信息
     * @return 集合
     */
    List<Frame_CodeValue> getCodesToValue(Map<String,Object> params);

    //获取数量
    int getCount(Map<String,Object> params);

    /**
     * 新增代码值参数
     *
     * @param  codeValue 代码值信息
     * @return 结果
     */
    int insertCodeValue(Frame_CodeValue codeValue);

    /**
     * 批量更新代码项参数
     *
     * @param frameCodeValue 代码值信息
     * @return 结果
     */
    void updateCodeValue(Frame_CodeValue frameCodeValue);

    /**
     * 批量删除代码值参数
     *
     * @param ids 代码值信息
     * @return 结果
     */
    int deleteCodeValue(Integer[] ids);

	List<Frame_CodeValue> getCodeByValue(@Param("codeGuid")String code);

	List<Frame_CodeValue> getCodeValueByName(@Param("ruid")String ruid);
}
