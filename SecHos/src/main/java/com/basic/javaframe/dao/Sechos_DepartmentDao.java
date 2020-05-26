package com.basic.javaframe.dao;

import com.basic.javaframe.entity.Sechos_Department;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wzl
 * @date 2019-08-28 10:11:37
 */
public interface Sechos_DepartmentDao extends BaseDao<Sechos_Department> {

    /**
     * 根据Guid查询信息
     *
     * @param rowGuid
     * @return
     */
    Sechos_Department getDetailByGuid(@Param("rowGuid") String rowGuid);

    /**
     * 获取所有
     *
     * @return list
     */
    List<Sechos_Department> getDepartmentList();

    /**
     * 科室名称重复检测
     *
     * @param departmentName
     * @param rowGuid
     * @return
     */
    Sechos_Department checkDepartmentName(@Param("departmentName") String departmentName, @Param("rowGuid") String rowGuid);

}
