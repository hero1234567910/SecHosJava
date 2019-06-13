package com.basic.javaframe.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;
import org.aspectj.apache.bcel.classfile.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.basic.javaframe.common.exception.MyException;
import com.basic.javaframe.common.utils.GenUtils;
import com.basic.javaframe.dao.SysGeneratorDao;
import com.basic.javaframe.service.SysGeneratorService;

/**
 * 代码生成实现类
  * @ClassName: SysGeneratorServiceImpl 
  * @Description: 代码生成实现类
  * @author zwh
  * @date 2019年1月10日 下午3:32:04 
  *
 */
@Service("sysGeneratorService")
public class SysGeneratorServiceImpl implements SysGeneratorService {
	@Autowired
	private SysGeneratorDao sysGeneratorDao;

	@Override
	public List<Map<String, Object>> queryList(Map<String, Object> map) {
		return sysGeneratorDao.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return sysGeneratorDao.queryTotal(map);
	}

	@Override
	public Map<String, String> queryTable(String tableName) {
		System.out.println(tableName);
		return sysGeneratorDao.queryTable(tableName);
	}

	@Override
	public List<Map<String, String>> queryColumns(String tableName) {
		return sysGeneratorDao.queryColumns(tableName);
	}

	@Override
	public byte[] generatorCode(String tableName,String rowGuid) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//		ZipOutputStream zip = new ZipOutputStream(outputStream);
		//查询表信息
		Map<String, String> table = queryTable(tableName);
		if (table == null) {
			throw new MyException("该表不存在");
		}
		//查询列信息
		List<Map<String, String>> columns = queryColumns(tableName);
		//生成代码
		GenUtils.generatorCode(table, columns,rowGuid);
//		IOUtils.closeQuietly(zip);
		
		return outputStream.toByteArray();
	}

}
