package com.basic.javaframe.service;


import com.basic.javaframe.entity.Frame_User;
import com.basic.javaframe.entity.Sechos_PopuPerson;

import java.util.List;

/**
 * @author
 * @date 2019-08-28 10:11:37
 */
public interface Sechos_PopuPersonService {

    void save(Sechos_PopuPerson person);

    Sechos_PopuPerson getByDetail(String rowGuid);

    Sechos_PopuPerson getByPopuPersonGuid(String popuPersonGuid);

    Sechos_PopuPerson getByPopuPersonOpenId(String OpenId);

    /**
     * 获取推广次数
     *
     * @param promotersGuid
     * @return
     */
    int getMyPopuCount(String promotersGuid);

    /**
     * 查询推广次数大于0的
     *
     * @return
     */
    List<Frame_User> getPopuListByAdmin(Integer offset, Integer limit);

    /**
     * 直接读表次数
     * @param rowGuid
     * @return
     */
    Frame_User getCountByGuid(String rowGuid);
}
