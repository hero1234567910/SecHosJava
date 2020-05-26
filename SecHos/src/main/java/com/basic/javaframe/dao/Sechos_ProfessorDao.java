package com.basic.javaframe.dao;

import com.basic.javaframe.entity.Sechos_Professor;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wzl
 * @date 2019-08-28 10:11:37
 */
public interface Sechos_ProfessorDao extends BaseDao<Sechos_Professor> {

    /**
     * 根据Guid查询信息
     *
     * @param rowGuid
     * @return
     */
    Sechos_Professor getDetailByGuid(@Param("rowGuid") String rowGuid);

    /**
     * 根据科室获取专家列表
     *
     * @param departmentGuid
     * @return list
     */
    List<Sechos_Professor> getListByGuid(String departmentGuid);

    /**
     * 科室名称重复检测
     *
     * @param professorName
     * @param rowGuid
     * @return
     */
    Sechos_Professor checkProfessorName(@Param("professorName") String professorName, @Param("rowGuid") String rowGuid);

}
