package com.basic.javaframe.dao;

import com.basic.javaframe.entity.Frame_Codes;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>Title: CodesDao</p>
 * <p>Description: 代码项接口层</p>
 * @author wzl
 */
public interface Frame_CodesDao {
    /**
     * 查询代码参数列表
     *
     * @param params 代码项信息集合
     * @return 集合
     */
    List<Frame_Codes> getCodes(Map<String,Object> params);

    //获取数量
    int getCount(Map<String,Object> params);

    /**
     * 新增代码项参数
     *
     * @param  codes 代码项信息
     * @return 结果
     */
    int insertCodes(Frame_Codes codes);

    /**
     * 批量更新代码项参数
     *
     * @param sortSq 代码项信息
     * @return 结果
     */
    void updateCodesBatch(@Param("rowId")Integer[] rowId,@Param("codeName")String[] codeName,@Param("sortSq")Integer[] sortSq);

    /**
     * 单个更新代码项参数
     *
     * @param frameCodes 代码项信息
     * @return 结果
     */
    void updateCodes(Frame_Codes frameCodes);

    /**
     * 批量删除代码项参数
     *
     * @param ids 代码项信息
     * @return 结果
     */
    int deleteCodes(Integer[] ids);

    /**
     * 获取所有代码项
     * @return
     */
    List<Frame_Codes> getAllCodes();

    /**
     * 代码名称重复检测
     * @param t
     * @param <T>
     * @return
     */
    <T> int checkCodeName(T t);

	List<Frame_Codes> getCodesByName(@Param("codeName")String codeName);
}
