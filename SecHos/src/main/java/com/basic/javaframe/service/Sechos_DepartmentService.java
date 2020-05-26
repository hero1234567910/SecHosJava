package com.basic.javaframe.service;

import com.alibaba.fastjson.JSONArray;
import com.basic.javaframe.entity.Sechos_Department;

import java.util.List;
import java.util.Map;

/**
 * @author wzl
 * @date 2019-08-28 10:11:37
 */
public interface Sechos_DepartmentService {

    List<Sechos_Department> getList(Map<String, Object> map);

    int getCount(Map<String, Object> map);

    void save(Sechos_Department sechosDepartment);

    void update(Sechos_Department sechosDepartment);

    void deleteBatch(String[] rowGuids);

    Sechos_Department getDetailByGuid(String rowGuid);

    /**
     * 获取所有tree
     *
     * @return list
     */
    JSONArray getDepartmentList();

    /**
     * 获取所有
     *
     * @return list
     */
    List<Sechos_Department> getDepartmentListToWx();

    /**
     * 科室名称重复检测
     *
     * @param departmentName
     * @param rowGuid
     * @return
     */
    Sechos_Department checkDepartmentName(String departmentName, String rowGuid);

}
