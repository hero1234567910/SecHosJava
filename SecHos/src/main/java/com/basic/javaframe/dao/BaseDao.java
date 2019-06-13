package com.basic.javaframe.dao;

import java.util.List;
import java.util.Map;

/**
 * BaseDao层
  * @ClassName: BaseDao 
  * @Description: BaseDao层
  * @author zwh
  * @date 2019年1月10日 下午2:13:29 
  * 
  * @param <T>
 */
public interface BaseDao<T> {
	
	/**
	 * 
	 * @Title: save 
	 * @param @param t    设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	int save(T t);
	
	/**
	 * 
	 * @Title: save 
	 * @param @param map    设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	void save(Map<String, Object> map);
	
	/**
	 * 
	 * @Title: saveBatch 
	 * @param @param list    设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	void saveBatch(List<T> list);
	
	/**
	 * 
	 * @Title: update 
	 * @param @param t
	 * @param @return    设定文件 
	 * @return int    返回类型 
	 * @throws
	 */
	int update(T t);
	
	/**
	 * 
	 * @Title: update 
	 * @param @param map
	 * @param @return    设定文件 
	 * @return int    返回类型 
	 * @throws
	 */
	int update(Map<String, Object> map);
	
	/**
	 * 
	 * @Title: delete 
	 * @param @param id
	 * @param @return    设定文件 
	 * @return int    返回类型 
	 * @throws
	 */
	int delete(Object id);
	
	/**
	 * 
	 * @Title: delete 
	 * @param @param map
	 * @param @return    设定文件 
	 * @return int    返回类型 
	 * @throws
	 */
	int delete(Map<String, Object> map);
	
	/**
	 * 
	 * @Title: deleteBatch 
	 * @param @param id
	 * @param @return    设定文件 
	 * @return int    返回类型 
	 * @throws
	 */
	int deleteBatch(Object[] id);
	
	/**
	 * 
	 * @Title: queryObject 
	 * @param @param id
	 * @param @return    设定文件 
	 * @return T    返回类型 
	 * @throws
	 */
	T queryObject(Object id);
	
	/**
	 * 
	 * @Title: get 
	 * @param @param id
	 * @param @return    设定文件 
	 * @return T    返回类型 
	 * @throws
	 */
	T get(Object id);
	
	/**
	 * 
	 * @Title: queryList 
	 * @param @param map
	 * @param @return    设定文件 
	 * @return List<T>    返回类型 
	 * @throws
	 */
	List<T> queryList(Map<String, Object> map);
	
	/**
	 * 
	 * @Title: getList 
	 * @param @param map
	 * @param @return    设定文件 
	 * @return List<T>    返回类型 
	 * @throws
	 */
	List<T> getList(Map<String, Object> map);
	
	/**
	 * 
	 * @Title: queryList 
	 * @param @param id
	 * @param @return    设定文件 
	 * @return List<T>    返回类型 
	 * @throws
	 */
	List<T> queryList(Object id);
	
	/**
	 * 
	 * @Title: queryTotal 
	 * @param @param map
	 * @param @return    设定文件 
	 * @return int    返回类型 
	 * @throws
	 */
	int queryTotal(Map<String, Object> map);
	
	/**
	 * 
	 * @Title: getCount 
	 * @param @param map
	 * @param @return    设定文件 
	 * @return int    返回类型 
	 * @throws
	 */
	int getCount(Map<String, Object> map);
	
	/**
	 * 
	 * @Title: queryTotal 
	 * @param @return    设定文件 
	 * @return int    返回类型 
	 * @throws
	 */
	int queryTotal();
}
