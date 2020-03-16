package com.basic.javaframe.dao;

import com.basic.javaframe.entity.Frame_User;
import com.basic.javaframe.entity.Sechos_PopuPerson;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * 
 * @author 
 * @date 2019-06-26 14:30:13
 */
public interface Sechos_PopuPersonDao extends BaseDao<Sechos_PopuPersonDao> {

	void save(Sechos_PopuPerson person);

	Sechos_PopuPerson getByDetail(String rowGuid);

	Sechos_PopuPerson getByPopuPersonGuid(String popuPersonGuid);

	Sechos_PopuPerson getByPopuPersonOpenId(String openId);

	/**
	 * 查询推广次数
	 * @param promotersGuid
	 * @return
	 */
	int getMyPopuCount(String promotersGuid);

	/**
	 * 查询推广次数大于0的
	 * @param offset
	 * @param limit
	 * @return
	 */
	List<Frame_User> getPopuListByAdmin(@Param("offset") Integer offset, @Param("limit")Integer limit);

	/**
	 * 直接读表次数
	 * @param rowGuid
	 * @return
	 */
	Frame_User getCountByGuid(String rowGuid);
    
}
