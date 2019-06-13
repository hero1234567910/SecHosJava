package com.basic.javaframe.service;

import com.basic.javaframe.entity.Frame_CodeValue;

import java.util.List;
import java.util.Map;

/**
 * <p>Title: CodeValueService</p>
 * <p>Description: 代码值类务层</p>
 * @author wzl
 */
public interface Frame_CodeValueService {
	
    /**
     * 查询代码值参数列表
     *
     * @param params 代码值参数信息
     * @return 集合
     */
    List<Frame_CodeValue> getCodeValue(Map<String,Object> params);

    /**
     * 查询代码关联值参数列表
     *
     * @param params 代码值参数信息
     * @return 集合
     */
    List<Frame_CodeValue> getCodesToValue(Map<String,Object> params);

    //获取数量
    int getCount(Map<String,Object> params);

    /**
     * 新增代码值参数列表
     *
     * @param codeValue 系统参数信息
     * @return 集合
     */
    void insertCodeValue(Frame_CodeValue codeValue);

    /**
     * 更新代码值参数列表
     *
     * @param frameCodeValue 代码值参数信息
     * @return 集合
     *
     */
    void updateCodeValue(Frame_CodeValue frameCodeValue);

    /**
     * 批量删除代码项参数列表
     *
     * @param ids 代码值参数信息
     * @return 集合
     */
    void deleteCodeValue(Integer[] ids);

	List<Frame_CodeValue> getCodeByValue(String code);

	List<Frame_CodeValue> getCodeValueByName(String name);
	
	String getCodeByNameAndValue(String name,String code);

	Map<String, String> getCodeToMap(String name);
}

