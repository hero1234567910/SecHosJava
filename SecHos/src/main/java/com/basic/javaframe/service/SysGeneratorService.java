package com.basic.javaframe.service;

import java.util.List;
import java.util.Map;

/**
 * 代码生成service
  * @ClassName: SysGeneratorService 
  * @Description: 代码生成service
  * @author zwh
  * @date 2019年1月10日 下午3:08:27 
  *
 */
public interface SysGeneratorService {
	
	/**
	 * 
	 * @Title: queryList 
	 * @Description: 获取列表
	 * @param @param map
	 * @param @return    设定文件 
	 * @return List<Map<String,Object>>    返回类型 
	 * @throws
	 */
	List<Map<String, Object>> queryList(Map<String, Object> map);
	
	/**
	 * 
	 * @Title: queryTotal 
	 * @Description: 获取条数
	 * @param @param map
	 * @param @return    设定文件 
	 * @return int    返回类型 
	 * @throws
	 */
	int queryTotal(Map<String, Object> map);
	
	/**
	 * 
	 * @Title: queryTable 
	 * @Description: 
	 * @param @param tableName
	 * @param @return    设定文件 
	 * @return Map<String,String>    返回类型 
	 * @throws
	 */
	Map<String, String> queryTable(String tableName);
	
	/**
	 * 
	 * @Title: queryColumns 
	 * @Description: queryColumns
	 * @param @param tableName
	 * @param @return    设定文件 
	 * @return List<Map<String,String>>    返回类型 
	 * @throws
	 */
	List<Map<String, String>> queryColumns(String tableName);
	
	/**
	 * 
	 * @Title: generatorCode 
	 * @Description: 生成代码
	 * @param @param tableNames
	 * @param @return    设定文件 
	 * @return byte[]    返回类型 
	 * @throws
	 */
	byte[] generatorCode(String table, String rowGuid);
}
