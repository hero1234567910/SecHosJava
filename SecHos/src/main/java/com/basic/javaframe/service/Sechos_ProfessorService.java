package com.basic.javaframe.service;

import com.basic.javaframe.entity.Sechos_Professor;

import java.util.List;
import java.util.Map;

/**
 * @author wzl
 * @date 2019-08-28 10:11:37
 */
public interface Sechos_ProfessorService {

    List<Sechos_Professor> getList(Map<String, Object> map);

    int getCount(Map<String, Object> map);

    void save(Sechos_Professor sechosProfessor);

    void update(Sechos_Professor sechosProfessor);

    void deleteBatch(String[] rowGuids);

    Sechos_Professor getDetailByGuid(String rowGuid);

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
    Sechos_Professor checkProfessorName(String professorName, String rowGuid);

}
