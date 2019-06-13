package com.basic.javaframe.common.utils;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.transaction.annotation.Transactional;

import com.basic.javaframe.common.exception.MyException;
import com.basic.javaframe.entity.Column;
import com.basic.javaframe.entity.Form_TableField;
import com.basic.javaframe.entity.Frame_CodeValue;
import com.basic.javaframe.entity.Table;
import com.basic.javaframe.service.Form_TableFieldService;
import com.basic.javaframe.service.Frame_CodeValueService;

/**
 * 代码生成器   工具类
 *
 * @author jd
 * @email jd@qq.com
 * @date 2016年12月19日 下午11:40:24
 */
public class GenUtils {
	static Form_TableFieldService tableService = (Form_TableFieldService) SpringContextUtils.getBean("tableFieldService");
	static Frame_CodeValueService codeValueService = (Frame_CodeValueService) SpringContextUtils.getBean("codeValueService");
	
	public static List<String> getTemplates(){
		List<String> templates = new ArrayList<String>();
		templates.add("generator/template/Entity.java.vm");
		templates.add("generator/template/Dao.java.vm");
		templates.add("generator/template/Mapper.xml.vm");
		templates.add("generator/template/Service.java.vm");
		templates.add("generator/template/ServiceImpl.java.vm");
		templates.add("generator/template/Controller.java.vm");
		templates.add("generator/template/list.js.vm");
		templates.add("generator/template/list.ftl.vm");
//		templates.add("generator/template/add.ftl.vm");
		templates.add("generator/template/edit.ftl.vm");
//		templates.add("generator/template/info.ftl.vm");
//		templates.add("generator/template/common.ftl.vm");
//		templates.add("generator/template/menu.sql.vm");
		return templates;
	}

	/**
	 * 生成代码
	 * @param rowGuid 
	 */
	public static void generatorCode(Map<String, String> table,
			List<Map<String, String>> columns, String rowGuid){
		//配置信息
		Configuration config = getConfig();

		//表信息
		Table tableEntity = new Table();
		tableEntity.setTableName(table.get("tableName"));
		tableEntity.setComments(table.get("tableComment"));
		//表名转换成Java类名
		String className = tableToJava(tableEntity.getTableName(), config.getString("tablePrefix"));
		tableEntity.setClassName(className);
		tableEntity.setClassname(StringUtils.uncapitalize(className));
		//获取长度
		//列信息
		List<Column> columsList = new ArrayList<>();
		
		//获取对应的属性
		List<Form_TableField> fields = tableService.selectFieldByTableName(rowGuid);
		if (fields == null || fields.size() == 0) {
			throw new MyException("不存在对应表，或者该表未添加任何字段");
		}
		
		for(Map<String, String> column : columns){
			Column columnEntity = new Column();
			columnEntity.setColumnName(column.get("columnName"));
			columnEntity.setDataType(column.get("dataType"));
			//设置字段长度
			int start=column.get("maxLength").indexOf("(");
			int end =column.get("maxLength").indexOf(")");
			if(start!=-1){
				columnEntity.setMaxLength(column.get("maxLength").substring(start+1,end));
			}else{
				columnEntity.setMaxLength("20");
			}
			columnEntity.setComments(column.get("columnComment"));
			columnEntity.setExtra(column.get("extra"));

			//列名转换成Java属性名
			String attrName = columnToJava(columnEntity.getColumnName());
			columnEntity.setAttrName(attrName);
			columnEntity.setAttrname(StringUtils.uncapitalize(attrName));

			//列的数据类型，转换成Java类型
			String attrType = config.getString(columnEntity.getDataType(), "unknowType");
			columnEntity.setAttrType(attrType);

			//是否主键
			if("PRI".equalsIgnoreCase(column.get("columnKey")) && tableEntity.getPk() == null){
				tableEntity.setPk(columnEntity);
			}
			for (Form_TableField field : fields) {
				if (column.get("columnName").equals(field.getFieldName())) {
					//获取对应代码项
					List<Frame_CodeValue> codeValues = new ArrayList<>();
					if (field.getCodesGuid()!= null && field.getCodesGuid() != "") {
						codeValues = codeValueService.getCodeValueByName(field.getCodesGuid());
					}
					columnEntity.setCodeValues(codeValues);
					columnEntity.setIsRequired(String.valueOf(field.getMustFill()));
					columnEntity.setIsShow(String.valueOf(field.getShowInadd()));
					columnEntity.setShowType(String.valueOf(field.getFieldDisplayType()));
					columnEntity.setIsSelected(String.valueOf(field.getIsQueryCondition()));
				}
			}
			columsList.add(columnEntity);
		}
		tableEntity.setColumns(columsList);

		//没主键，则第一个字段为主键
		if(tableEntity.getPk() == null){
			tableEntity.setPk(tableEntity.getColumns().get(0));
		}
		
		//设置velocity资源加载器
		Properties prop = new Properties();
		prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		Velocity.init(prop);

		//封装模板数据
		Map<String, Object> map = new HashMap<>();
		map.put("tableName", tableEntity.getTableName());
		map.put("comments", tableEntity.getComments());
		map.put("pk", tableEntity.getPk());
		map.put("className", tableEntity.getClassName());
		map.put("classname", tableEntity.getClassname());
		map.put("pathName", tableEntity.getClassname().toLowerCase());
		map.put("columns", tableEntity.getColumns());
		map.put("package", config.getString("package"));
		map.put("author", config.getString("author"));
		map.put("email", config.getString("email"));
		map.put("datetime", DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));
        VelocityContext context = new VelocityContext(map);
        
        //获取模板列表
		List<String> templates = getTemplates();
		for(String template : templates){
			//渲染模板
            OutputStream output = null;
			try {
				File file = new File("src/"+getFileName(template, tableEntity.getClassName(), config.getString("package")));//文件路径（路径+文件名）
	             if (!file.exists()) {   //文件不存在则创建文件，先创建目录
	                 File dir = new File(file.getParent());
	                 dir.mkdirs();
	                 file.createNewFile();
	             }else{
	            	 throw new MyException("已经存在该模板文件");
	             }
				output = new FileOutputStream(file);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			StringWriter sw = new StringWriter();
			Template tpl = Velocity.getTemplate(template, "UTF-8");
			tpl.merge(context, sw);
			try {
				byte[] sourceBytes = sw.toString().getBytes("UTF-8");
		        if(null!=sourceBytes){
		            IOUtils.write(sw.toString(), output, "UTF-8");
					IOUtils.closeQuietly(sw);
					IOUtils.closeQuietly(output);
		        }
			} catch (IOException e) {
				throw new MyException("渲染模板失败，表名：" + tableEntity.getTableName(), e);
			}
		}
	}


	/**
	 * 列名转换成Java属性名
	 */
	public static String columnToJava(String columnName) {
//		return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "");
		return capitalizeFullySelf(columnName, new char[]{'_'}).replace("_", "");
	}

	
	public static String capitalizeFullySelf(String str, char[] delimiters) {
        int delimLen = (delimiters == null ? -1 : delimiters.length);
        if (str == null || str.length() == 0 || delimLen == 0) {
            return str;
        }
//        str = str.toLowerCase();
        return capitalize(str, delimiters);
    }
	
	 public static String capitalize(String str, char[] delimiters) {
	        int delimLen = (delimiters == null ? -1 : delimiters.length);
	        if (str == null || str.length() == 0 || delimLen == 0) {
	            return str;
	        }
	        int strLen = str.length();
	        StringBuffer buffer = new StringBuffer(strLen);
	        boolean capitalizeNext = true;
	        for (int i = 0; i < strLen; i++) {
	            char ch = str.charAt(i);

	            if (isDelimiter(ch, delimiters)) {
	                buffer.append(ch);
	                capitalizeNext = true;
	            } else if (capitalizeNext) {
	                buffer.append(Character.toTitleCase(ch));
	                capitalizeNext = false;
	            } else {
	                buffer.append(ch);
	            }
	        }
	        return buffer.toString();
	    }
	 private static boolean isDelimiter(char ch, char[] delimiters) {
	        if (delimiters == null) {
	            return Character.isWhitespace(ch);
	        }
	        for (int i = 0, isize = delimiters.length; i < isize; i++) {
	            if (ch == delimiters[i]) {
	                return true;
	            }
	        }
	        return false;
	    }
	
	/**
	 * 表名转换成Java类名
	 */
	public static String tableToJava(String tableName, String tablePrefix) {
		if(StringUtils.isNotBlank(tablePrefix)){
			tableName = tableName.replace(tablePrefix, "");
		}
		return columnToJava(tableName);
	}

	/**
	 * 获取配置信息
	 */
	public static Configuration getConfig(){
		try {
			return new PropertiesConfiguration("generator/generator.properties");
		} catch (ConfigurationException e) {
			throw new MyException("获取配置文件失败，", e);
		}
	}

	/**
	 * 获取文件名
	 */
	public static String getFileName(String template, String className, String packageName){
		String packagePath = "main" + File.separator + "java" + File.separator;
		if(StringUtils.isNotBlank(packageName)){
			packagePath += packageName.replace(".", File.separator) + File.separator;
		}

		if(template.contains("Entity.java.vm")){
			return packagePath + "entity" + File.separator + className + ".java";
		}

		if(template.contains("Dao.java.vm")){
			return packagePath + "dao" + File.separator + className + "Dao.java";
		}

		if(template.contains("Mapper.xml.vm")){
			return "main" + File.separator + "resources" +  File.separator + "mapper"
					+ File.separator + className + "Mapper.xml";
		}

		if(template.contains("Service.java.vm")){
			return packagePath + "service" + File.separator + className + "Service.java";
		}

		if(template.contains("ServiceImpl.java.vm")){
			return packagePath + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
		}

		if(template.contains("Controller.java.vm")){
			return packagePath + "controller" + File.separator + className + "Controller.java";
		}

		if(template.contains("list.ftl.vm")){
			return "main" + File.separator + "resources" +  File.separator + "templates"
					+ File.separator + className.toLowerCase() + File.separator + "list.html";
		}
		if(template.contains("list.js.vm")){
			return "main" + File.separator + "resources" +  File.separator + "templates"
					+ File.separator + className.toLowerCase() +File.separator+"js"+ File.separator + "list.js";
		}
		if(template.contains("add.ftl.vm")){
			return "main" + File.separator + "resources" +  File.separator + "templates"
					+ File.separator + className.toLowerCase() + File.separator + "add.ftl";
		}
		if(template.contains("edit.ftl.vm")){
			return "main" + File.separator + "resources" +  File.separator + "templates"
					+ File.separator + className.toLowerCase() + File.separator + "edit.html";
		}
		if(template.contains("info.ftl.vm")){
			return "main" + File.separator + "resources" +  File.separator + "templates"
					+ File.separator + className.toLowerCase() + File.separator + "info.ftl";
		}
		if(template.contains("infoSearch.js.vm")){
			return "main" + File.separator + "resources" +  File.separator + "templates"
					+ File.separator + className.toLowerCase()  +File.separator + "js"+ File.separator + "infoSearch.js";
		}
		if(template.contains("common.ftl.vm")){
			return "main" + File.separator + "resources" +  File.separator + "templates"
					+ File.separator + className.toLowerCase() + File.separator + "common.ftl";
		}
		if(template.contains("menu.sql.vm")){
			return className.toLowerCase() + "_menu.sql";
		}

		return null;
	}
}
