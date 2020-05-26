package com.basic.javaframe.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.basic.javaframe.dao.Sechos_DepartmentDao;
import com.basic.javaframe.entity.Sechos_Department;
import com.basic.javaframe.service.Sechos_DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service("sechosDepartmentService")
@Transactional
public class Sechos_DepartmentServiceImpl implements Sechos_DepartmentService {
    @Autowired
    private Sechos_DepartmentDao sechosDepartmentDao;

    @Override
    public List<Sechos_Department> getList(Map<String, Object> map) {
        return sechosDepartmentDao.getList(map);
    }

    @Override
    public int getCount(Map<String, Object> map) {
        return sechosDepartmentDao.getCount(map);
    }

    @Override
    public void save(Sechos_Department sechosDepartment) {
        sechosDepartmentDao.save(sechosDepartment);
    }

    @Override
    public void update(Sechos_Department sechosDepartment) {
        sechosDepartmentDao.update(sechosDepartment);
    }

    @Override
    public void deleteBatch(String[] rowGuids) {
        sechosDepartmentDao.deleteBatch(rowGuids);
    }

    @Override
    public Sechos_Department getDetailByGuid(String rowGuid) {
        return sechosDepartmentDao.getDetailByGuid(rowGuid);
    }

    @Override
    public JSONArray getDepartmentList() {
        return getDepartmentTrees(sechosDepartmentDao.getDepartmentList());
    }

    @Override
    public List<Sechos_Department> getDepartmentListToWx() {
        return sechosDepartmentDao.getDepartmentList();
    }

    @Override
    public Sechos_Department checkDepartmentName(String departmentName, String rowGuid) {
        return sechosDepartmentDao.checkDepartmentName(departmentName, rowGuid);
    }

    private JSONArray getDepartmentTrees(List<Sechos_Department> deptTopTrees) {
        JSONArray array = new JSONArray();
        for (Sechos_Department sechosDepartment : deptTopTrees) {
            JSONObject json = new JSONObject();
            json.put("name", sechosDepartment.getDepartmentName());
            json.put("rowGuid", sechosDepartment.getRowGuid());
            array.add(json);
        }
        return array;
    }


}
