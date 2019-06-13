package com.basic.javaframe.controller;

import com.alibaba.fastjson.JSON;
import com.basic.javaframe.common.customclass.PassToken;
import com.basic.javaframe.common.utils.*;
import com.basic.javaframe.entity.Form_TableField;
import com.basic.javaframe.entity.Form_TableInfo;
import com.basic.javaframe.service.Form_TableFieldService;
import com.basic.javaframe.service.Form_TableInfoService;
import com.basic.javaframe.service.SysGeneratorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.*;
import java.util.UUID;

/**
 * 字段管理 信息操作处理
 * 字段控制层
 * @author my
 * @date 2019-03-18
 */
@CrossOrigin
@Api(value = "字段")
@RestController
@RequestMapping("sys/form/parameter")
public class Form_TableFieldController extends BaseController{

    //定义一个全局的记录器，通过LoggerFactory获取
    private final static Logger logger = LoggerFactory.getLogger(Form_TableFieldController.class);

    @Autowired
    private Form_TableFieldService tableFieldService;
    @Autowired
    private SysGeneratorService sysGeneratorService;


    /**
     * 查询表单列表
     * <p>Title: getTableField</p>
     * <p>Description: 表单</p>
     * @author my
     * @param params
     * @return
     */
    @PassToken
    @ApiOperation(value = "查询字段列表")
    @ResponseBody
    @RequestMapping(value = "/getTableField",produces="application/json;charset=utf-8",method= RequestMethod.GET)
    public LayuiUtil getTableField(@RequestParam Map<String, Object> params){
        Query query = new Query(params);
        List<Form_TableField> list = tableFieldService.selectFormTableFieldList(query);
        int total = tableFieldService.getCount(query);
        PageUtils pageUtil = new PageUtils(list, total, query.getLimit(), query.getPage());
        return LayuiUtil.data(pageUtil.getTotalCount(), pageUtil.getList());
    }

    /**
     * 新增字段
     * <p>Title: addField/p>
     * <p>Description: 新增字段</p>
     * @author my
     * @param form_tableField
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @ApiOperation(value="新增字段")
    @ResponseBody
    @RequestMapping(value="/add",produces="application/json;charset=utf-8",method=RequestMethod.POST)
    public R addTableField(@RequestBody Map<String,Object> params) throws Exception{
        Form_TableField tablefield = JSON.parseObject(JSON.toJSONString(params.get("field")), Form_TableField.class);
        Form_TableInfo tableInfo = new Form_TableInfo();
        String mustfill;
        if(tablefield.getMustFill()=='0')
            mustfill="null";
        else
            mustfill = "not null";
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
        ResultSet rs = conn.getMetaData().getTables(null, null,tableInfo.getPhysicalName(), null);
        // 判断表是否存在，如果存在则什么都不做，否则创建表
        if( rs.next() ){
            //创建行政区划表
       stat.executeUpdate("alter  table "+params.get("tableInfo").toString()+" add  "+tablefield.getFieldName()+" "+tablefield.getFieldType()+"("+tablefield.getFieldLength()+")   "+mustfill+" comment '"+tablefield.getFieldDisplayName()+"';");
        }
        else{
            return R.error("表不存在");
        }
        // 释放资源
       stat.close();
       conn.close();
        //生成uuid作为rowguid
        String uuid = UUID.randomUUID().toString();
        tablefield.setRowGuid(uuid);
        tablefield.setAllowTo(params.get("rowGuid").toString());
        tableFieldService.insertFormTableField(tablefield);
        return R.ok();
    }

    /**
     * 修改表单
     * <p>Title: updateTableField</p>
     * <p>Description: 字段</p>
     * @author my
     * @param formTableField
     * @return
     */
    @ApiOperation(value = "修改字段")
    @ResponseBody
    @RequestMapping(value = "/updateTableField/{id}",produces="application/json;charset=utf-8",method= RequestMethod.PUT)
    public R updateTableField(@PathVariable("id") Integer id,@RequestBody Map<String,Object> params) throws Exception{
        Form_TableField formTableField = JSON.parseObject(JSON.toJSONString(params.get("field")), Form_TableField.class);
        String tableName = params.get("tableName").toString();
        String originName = params.get("originalName").toString();
        formTableField.setRowId(id);
        tableFieldService.updateFormTableField(formTableField);
        Form_TableInfo tableInfo = new Form_TableInfo();
        String mustfill ;
        if(formTableField.getMustFill()=='0')
            mustfill="null";
        else
            mustfill = "not null";
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
        ResultSet rs = conn.getMetaData().getTables(null, null,tableInfo.getPhysicalName(), null);
        System.out.println(tableInfo.getPhysicalName());
        // 判断表是否存在，如果存在则什么都不做，否则创建表
        if( rs.next() ){
            //创建行政区划表
            stat.executeUpdate("alter table "+tableName+"  change  "+originName+"  "+formTableField.getFieldName()+"  "+formTableField.getFieldType()+ " ("+formTableField.getFieldLength()+")   "+mustfill+" comment  '"+formTableField.getFieldDisplayName()+"';");
        }
        else{
            return R.error("表不存在");
        }
        // 释放资源
        stat.close();
        conn.close();
        return R.ok();
    }

    /**
     * 删除多个表单
     * <p>Title: deleteTableField</p>
     * <p>Description: 表单</p>
     * @author my
     * @param ids
     * @return
     * @throws Exception
     */
    @ApiOperation(value="删除多个字段")
    @ResponseBody
    @RequestMapping(value="/deleteTableField/{ids}",produces="application/json;charset=utf-8",method=RequestMethod.POST)
    public R deleteTableField(@PathVariable("ids")Integer[] ids,@RequestBody Map<String,Object> params) throws Exception{
        String tableName = params.get("tableName").toString();
        List<String> fieldNames = (List<String>) params.get("fieldNames");
        
        for (int i = 0; i < ids.length; i++) {
        	System.out.println(ids[i]);
		}
        
        
        tableFieldService.deleteFormTableFieldById(ids);
        Form_TableInfo tableInfo = new Form_TableInfo();
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
        ResultSet rs = conn.getMetaData().getTables(null, null,tableInfo.getPhysicalName(), null);
        System.out.println(tableInfo.getPhysicalName());
        // 判断表是否存在，如果存在则什么都不做，否则创建表
        if( rs.next() ){
            //遍历字段名
        	for (int i = 0; i < fieldNames.size(); i++) {
        		stat.executeUpdate("alter table "+tableName+" drop "+fieldNames.get(i)+";");
			}
        }
        else{
            return R.error("表不存在");
        }
        // 释放资源
        stat.close();
        conn.close();
        return R.ok();
    }

    /**
     * 通过表单名查询
     * <p>Title: findFormTableFieldByName</p>
     * <p>Description: 表单名</p>
     * @author my
     * @param
     * @return
     */
    @ApiOperation(value="通过字段名查询")
    @ResponseBody
    @RequestMapping(value="/findFormTableFieldByName/{name}",produces="application/json;charset=utf-8",method=RequestMethod.POST)
    public R selectFormTableFieldByName(@PathVariable("name")String tableFieldName ){
        tableFieldService.selectFormTableFieldByName(tableFieldName);
        return R.ok();
    }

}
