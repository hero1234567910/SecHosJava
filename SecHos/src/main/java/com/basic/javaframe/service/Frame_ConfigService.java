package com.basic.javaframe.service;

import com.basic.javaframe.entity.Frame_Config;

import java.util.List;
import java.util.Map;

/**
 * <p>Title: ConfigService</p>
 * <p>Description: 系统参数类务层</p>
 * @author wzl
 */
public interface Frame_ConfigService {

    /**
     * 查询系统参数列表
     *
     * @param params 系统参数信息
     * @return 集合
     */
    List<Frame_Config> getConfig(Map<String,Object> params);

    //获取数量
    int getCount(Map<String, Object> params);

    /**
     * 新增系统参数
     *
     * @param config 系统参数信息
     *
     */
    void insertConfig(Frame_Config config);

    /**
     * 更新系统参数
     *
     * @param config 系统参数信息
     *
     */
    void updateConfig(Frame_Config config);

    /**
     * 批量删除系统参数信息
     *
     * @param ids 需要删除的数据ID
     *
     */
    void deleteConfig(Integer[] ids);

    /**
     * 获取默认系统密码参数
     *
     * @param
     *
     * *系统默认密码信息
     */
    String getDefaultPassWord();

    /**
     * 参数名重复性检测
     * @param t
     * @param <T>
     * @return
     */
    <T> int checkConfigName(T t);

    /**
     * 删除参数信息
     *
     * @param categoryGuid 需要删除的数据ID
     * @return 结果
     */
    void deleteConfigByCategoryGuid(String categoryGuid);

	Frame_Config getConfigByName(String string);
}

