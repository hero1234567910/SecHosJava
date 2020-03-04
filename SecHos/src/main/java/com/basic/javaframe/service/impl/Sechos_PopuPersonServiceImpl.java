package com.basic.javaframe.service.impl;

import com.basic.javaframe.dao.Sechos_PopuPersonDao;
import com.basic.javaframe.entity.Frame_User;
import com.basic.javaframe.entity.Sechos_PopuPerson;
import com.basic.javaframe.service.Sechos_PopuPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service("sechos_PopuPersonService")
@Transactional
public class Sechos_PopuPersonServiceImpl implements Sechos_PopuPersonService {

    @Autowired
    Sechos_PopuPersonDao sechos_PopuPersonDao;

    @Override
    public void save(Sechos_PopuPerson person) {
        sechos_PopuPersonDao.save(person);
    }

    @Override
    public Sechos_PopuPerson getByDetail(String rowGuid) {
        return sechos_PopuPersonDao.getByDetail(rowGuid);
    }

    @Override
    public Sechos_PopuPerson getByPopuPersonGuid(String popuPersonGuid) {
        // TODO Auto-generated method stub
        return sechos_PopuPersonDao.getByPopuPersonGuid(popuPersonGuid);
    }

    @Override
    public Sechos_PopuPerson getByPopuPersonOpenId(String OpenId) {
        return sechos_PopuPersonDao.getByPopuPersonOpenId(OpenId);
    }

    @Override
    public int getMyPopuCount(String promotersGuid) {
        return sechos_PopuPersonDao.getMyPopuCount(promotersGuid);
    }

    @Override
    public List<Frame_User> getPopuListByAdmin(Integer offset, Integer limit) {
        return sechos_PopuPersonDao.getPopuListByAdmin(offset, limit);
    }


}
