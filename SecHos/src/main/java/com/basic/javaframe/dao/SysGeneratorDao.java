package com.basic.javaframe.dao;

import java.util.List;
import java.util.Map;

/**
 * SysGeneratorDao层
  * @ClassName: SysGeneratorDao 
  * @Description: SysGeneratorDao层
  * @author zwh
  * @date 2019年1月10日 下午2:22:43 
  *
 */
public interface SysGeneratorDao {
	
	/**
	 * 
	 * @Title: queryList 
	 * @param @param map
	 * @param @return    设定文件 
	 * @return List<Map<String,Object>>    返回类型 
	 * @throws
	 */
	List<Map<String, Object>> queryList(Map<String, Object> map);
	
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
	 * @Title: queryTable 
	 * @param @param tableName
	 * @param @return    设定文件 
	 * @return Map<String,String>    返回类型 
	 * @throws
	 */
	Map<String, String> queryTable(String tableName);
	
	/**
	 * 
	 * @Title: queryColumns 
	 * @param @param tableName
	 * @param @return    设定文件 
	 * @return List<Map<String,String>>    返回类型 
	 * @throws
	 */
	List<Map<String, String>> queryColumns(String tableName);
}
