package com.basic.javaframe.controller;

import com.basic.javaframe.common.customclass.PassToken;
import com.basic.javaframe.common.utils.*;
import com.basic.javaframe.entity.Form_TableInfo;
import com.basic.javaframe.service.Form_TableInfoService;
import com.basic.javaframe.service.SysGeneratorService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
/**
 * 表单管理 信息操作处理
 * 表单控制层
 * @author my
 * @date 2019-03-18
 */
@CrossOrigin
@Api(value = "表单")
@RestController
@RequestMapping("sys/form")
public class Form_TableInfoController extends BaseController{

    @Autowired
    private Form_TableInfoService tableInfoService;
    
    @Autowired
    private SysGeneratorService sysGeneratorService;
    
    /**
     * 查询表单列表
     * <p>Title: getTableInfo</p>
     * <p>Description: 表单</p>
     * @author my
     * @param form_TableInfo
     * @return
     */
    @PassToken
    @ApiOperation(value = "查询表单列表")
    @ResponseBody
    @RequestMapping(value = "/getTableInfo",produces="application/json;charset=utf-8",method= RequestMethod.GET)
    public LayuiUtil getTableInfo(@RequestParam Map<String, Object> params){
        Query query = new Query(params);
        List<Form_TableInfo> list = tableInfoService.selectFormTableInfoList(query);
        int total = tableInfoService.getCount(query);
        PageUtils pageUtil = new PageUtils(list, total, query.getLimit(), query.getPage());
        return LayuiUtil.data(pageUtil.getTotalCount(), pageUtil.getList());
    }

    /**
     * 新增表单
     * <p>Title: addForm</p>
     * <p>Description: 新增表单</p>
     * @author my
     * @param form_tableInfo
     * @return
     * @throws SQLException 
     * @throws ClassNotFoundException 
     */
    @ApiOperation(value="新增表单")
    @ResponseBody
    @RequestMapping(value="/add",produces="application/json;charset=utf-8",method=RequestMethod.POST)
    public R addTableInfo(@RequestBody Form_TableInfo form_tableInfo) throws Exception{
    	//连接数据库
		Class.forName(driver);
		//测试url中是否包含useSSL字段，没有则添加设该字段且禁用
		if( url.indexOf("?") == -1 ){
			url = url + "?useSSL=false" ;
		}
		else if( url.indexOf("useSSL=false") == -1 || url.indexOf("useSSL=true") == -1 )
		{
			url = url + "&useSSL=false";
		}
		Connection conn = DriverManager.getConnection(url, userName, password);
		Statement stat = conn.createStatement();
		//获取数据库表名
		ResultSet rs = conn.getMetaData().getTables(null, null, form_tableInfo.getPhysicalName(), null);
		
		// 判断表是否存在，如果存在则什么都不做，否则创建表
		if( rs.next() ){
			return R.error("表已存在");
		}
		else{
			//创建行政区划表
			stat.executeUpdate("CREATE TABLE "+form_tableInfo.getPhysicalName()+"("
					+ "row_id bigint(20) NOT NULL AUTO_INCREMENT,"
					+ "row_guid varchar(50) NOT NULL,"
					+ "create_time datetime NOT NULL,"
					+ "del_flag int(1) default '0',"
					+ "sort_sq int(20) default '0',"
					+ "PRIMARY KEY (row_id)"
					+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8;"
					);
		}
		// 释放资源
		stat.close();
        conn.close();
    	
        //生成uuid作为rowguid
        String uuid = UUID.randomUUID().toString();
        form_tableInfo.setRowGuid(uuid);
        tableInfoService.insertFormTableInfo(form_tableInfo);
        return R.ok();
    }

    /**
     * 修改表单
     * <p>Title: updateTableInfo</p>
     * <p>Description: 表单</p>
     * @author my
     * @param formTableInfo
     * @return
     */
    @ApiOperation(value = "修改表单")
    @ResponseBody
    @RequestMapping(value = "/updateTableInfo/{id}",produces="application/json;charset=utf-8",method= RequestMethod.POST)
    public R updateTableInfo(@PathVariable("id") Integer id,@RequestBody Form_TableInfo formTableInfo)throws Exception{
    	System.out.println(formTableInfo.getOriginName());
    	//连接数据库
		Class.forName(driver);
		//测试url中是否包含useSSL字段，没有则添加设该字段且禁用
		if( url.indexOf("?") == -1 ){
			url = url + "?useSSL=false" ;
		}
		else if( url.indexOf("useSSL=false") == -1 || url.indexOf("useSSL=true") == -1 )
		{
			url = url + "&useSSL=false";
		}
		Connection conn = DriverManager.getConnection(url, userName, password);
		Statement stat = conn.createStatement();
		//获取数据库表名
		ResultSet rs = conn.getMetaData().getTables(null, null, formTableInfo.getOriginName(), null);
		
		// 判断表是否存在，如果存在则什么都不做，否则创建表
		if( !rs.next() ){
			return R.error("表不存在");
		}
		else{
			//创建行政区划表
			stat.executeUpdate("alter table "+formTableInfo.getOriginName()+" rename "+formTableInfo.getPhysicalName());
		}
		// 释放资源
		stat.close();
        conn.close();
    	
    	formTableInfo.setRowId(id);
        tableInfoService.updateFormTableInfo(formTableInfo);
        return R.ok();
    }

    /**
     * 删除多个表单
     * <p>Title: deleteTableInfo</p>
     * <p>Description: 表单</p>
     * @author my
     * @param ids
     * @return
     * @throws Exception 
     */
    @ApiOperation(value="删除多个表单")
    @ResponseBody
    @RequestMapping(value="/deleteTableInfo/{id}",produces="application/json;charset=utf-8",method=RequestMethod.POST)
    public R deleteTableInfo(@PathVariable("id")Integer[] ids) throws Exception{
    	//加上事务
    	
    	//连接数据库
		Class.forName(driver);
		//测试url中是否包含useSSL字段，没有则添加设该字段且禁用
		if( url.indexOf("?") == -1 ){
			url = url + "?useSSL=false" ;
		}
		else if( url.indexOf("useSSL=false") == -1 || url.indexOf("useSSL=true") == -1 )
		{
			url = url + "&useSSL=false";
		}
		Connection conn = DriverManager.getConnection(url, userName, password);
		Statement stat = conn.createStatement();
		
		//根据id查询对应表名
		for(int i=0;i<ids.length;i++){
			Form_TableInfo info = tableInfoService.selectFormTableInfoById(ids[i]);
			//获取数据库表名
			ResultSet rs = conn.getMetaData().getTables(null, null, info.getPhysicalName(), null);
			// 判断表是否存在，如果存在则什么都不做，否则创建表
			if( !rs.next() ){
				return R.error("表不存在");
			}
			//做删除操作
			stat.executeUpdate("DROP TABLE "+info.getPhysicalName());
		}
		
        tableInfoService.deleteFormTableInfoById(ids);
        return R.ok();
    }

    /**
     * 通过表单名查询
     * <p>Title: findFormTableInfoByName</p>
     * <p>Description: 表单名</p>
     * @author my
     * @param
     * @return
     */
    @ApiOperation(value="通过表单名查询")
    @ResponseBody
    @RequestMapping(value="/findFormTableInfoByName/{name}",produces="application/json;charset=utf-8",method=RequestMethod.POST)
    public R selectFormTableInfoByName(@PathVariable("name")String tableInfoName ){
        tableInfoService.selectFormTableInfoByName(tableInfoName);
        return R.ok();
    }
    
    /**
     * 生成代码
     * <p>Title: code</p>  
     * <p>Description: </p>
     * @author hero  
     * @param request
     * @param response
     * @throws IOException
     */
    @PassToken
    @ApiOperation(value="生成代码")
	@RequestMapping(value="/code")
	public R code(@RequestBody Map<String, Object> params, HttpServletResponse response) throws IOException{
		String table =  (String) params.get("table");
		String rowGuid = (String) params.get("rowGuid");
		sysGeneratorService.generatorCode(table,rowGuid);
		return R.ok();
//		response.reset();  
//        response.addHeader("Content-Length",data.length+"");  
//        response.setContentType("application/octet-stream; charset=UTF-8");  
//        String filename = "code.zip";
//        response.addHeader("Content-Disposition", "attachment; filename="+filename);
//        IOUtils.write(data, response.getOutputStream());  
	}
}




