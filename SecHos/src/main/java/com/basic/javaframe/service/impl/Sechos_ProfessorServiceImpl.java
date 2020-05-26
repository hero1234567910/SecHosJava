package com.basic.javaframe.service.impl;

import com.basic.javaframe.dao.Sechos_ProfessorDao;
import com.basic.javaframe.entity.Sechos_Professor;
import com.basic.javaframe.service.Sechos_ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


/**
 * @author wzl
 */
@Service("sechosProfessorService")
@Transactional
public class Sechos_ProfessorServiceImpl implements Sechos_ProfessorService {
    @Autowired
    private Sechos_ProfessorDao sechosProfessorDao;

    @Override
    public List<Sechos_Professor> getList(Map<String, Object> map) {
        return sechosProfessorDao.getList(map);
    }

    @Override
    public int getCount(Map<String, Object> map) {
        return sechosProfessorDao.getCount(map);
    }

    @Override
    public void save(Sechos_Professor sechosProfessor) {
        sechosProfessorDao.save(sechosProfessor);
    }

    @Override
    public void update(Sechos_Professor sechosProfessor) {
        sechosProfessorDao.update(sechosProfessor);
    }

    @Override
    public void deleteBatch(String[] rowGuids) {
        sechosProfessorDao.deleteBatch(rowGuids);
    }

    @Override
    public Sechos_Professor getDetailByGuid(String rowGuid) {
        return sechosProfessorDao.getDetailByGuid(rowGuid);
    }

    @Override
    public List<Sechos_Professor> getListByGuid(String departmentGuid) {
        return sechosProfessorDao.getListByGuid(departmentGuid);
    }

    @Override
    public Sechos_Professor checkProfessorName(String professorName, String rowGuid) {
        return sechosProfessorDao.checkProfessorName(professorName, rowGuid);
    }


}
