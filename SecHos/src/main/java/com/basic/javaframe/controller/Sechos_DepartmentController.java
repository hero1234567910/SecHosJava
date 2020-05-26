package com.basic.javaframe.controller;

import com.alibaba.fastjson.JSONArray;
import com.basic.javaframe.common.customclass.PassToken;
import com.basic.javaframe.common.utils.*;
import com.basic.javaframe.entity.Sechos_Department;
import com.basic.javaframe.service.Frame_AttachService;
import com.basic.javaframe.service.Sechos_DepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @author wzl
 * @date 2019-08-28 10:11:37
 */
@Api(value = "科室管理")
@RestController
@CrossOrigin
@RequestMapping("sys/sechosdepartment")
public class Sechos_DepartmentController {

    private final static Logger logger = LoggerFactory.getLogger(Sechos_DepartmentController.class);
    @Autowired
    private Sechos_DepartmentService sechosDepartmentService;

    @Autowired
    private Frame_AttachService attachService;

    /**
     * 列表数据
     */
    @PassToken
    @ApiOperation(value = "获取科室表")
    @ResponseBody
    @RequestMapping(value = "/listData", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    public LayuiUtil listData(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<Sechos_Department> sechosDepartmentList = sechosDepartmentService.getList(query);
        int total = sechosDepartmentService.getCount(query);
        PageUtils pageUtil = new PageUtils(sechosDepartmentList, total, query.getLimit(), query.getPage());
        return LayuiUtil.data(pageUtil.getTotalCount(), pageUtil.getList());
    }

    /**
     * 新增
     **/
    @ApiOperation(value = "新增科室信息")
    @ResponseBody
    @RequestMapping(value = "/add", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public R add(@RequestBody Sechos_Department sechosDepartment) {
        //如果排序号为空，则自动转为0
        if (sechosDepartment.getSortSq() == null) {
            sechosDepartment.setSortSq(0);
        }
        //生成uuid作为rowguid
        String uuid = java.util.UUID.randomUUID().toString();
        sechosDepartment.setRowGuid(uuid);
        sechosDepartment.setDelFlag(0);
        Date createTime = DateUtil.changeDate(new Date());
        sechosDepartment.setCreateTime(createTime);
        String imgGuid = java.util.UUID.randomUUID().toString();
        sechosDepartment.setPicGuid(imgGuid);
        sechosDepartmentService.save(sechosDepartment);
        String[] guids = {sechosDepartment.getUploadImgGuid()};
        attachService.updateAttach(imgGuid, guids);
        return R.ok();
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改科室信息")
    @ResponseBody
    @RequestMapping(value = "/update", produces = "application/json; charset=utf-8", method = RequestMethod.POST)
    public R update(@RequestBody Sechos_Department sechosDepartment) {
        if (sechosDepartment.getUploadImgGuid() != null) {
            String imgGuid = java.util.UUID.randomUUID().toString();
            sechosDepartment.setPicGuid(imgGuid);
            String[] guids = {sechosDepartment.getUploadImgGuid()};
            attachService.updateAttach(imgGuid, guids);
        }
        sechosDepartmentService.update(sechosDepartment);
        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除科室信息")
    @ResponseBody
    @RequestMapping(value = "/delete", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public R delete(@RequestBody String[] rowGuids) {
        sechosDepartmentService.deleteBatch(rowGuids);
        return R.ok();
    }

    @ApiOperation(value = "通过rowGuid获取一条记录")
    @ResponseBody
    @RequestMapping(value = "/getDetailByGuid", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public R getDetailByGuid(@RequestBody String rowGuid) {
        Sechos_Department sechosDepartment = sechosDepartmentService.getDetailByGuid(rowGuid);
        return R.ok().put("data", sechosDepartment);
    }

    @ApiOperation(value = "获取tree")
    @ResponseBody
    @RequestMapping(value = "/getDepartmentList", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    public R getDepartmentList() {
        JSONArray trees = sechosDepartmentService.getDepartmentList();
        return R.ok().put("data", trees);
    }

    @ApiOperation(value = "获取tree")
    @ResponseBody
    @RequestMapping(value = "/getDepartmentList", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public R getDepartmentListToWx() {
        List<Sechos_Department> sechosDepartmentList = sechosDepartmentService.getDepartmentListToWx();
        return R.ok().put("data", sechosDepartmentList);
    }

    @ApiOperation(value = "名称重复验证")
    @ResponseBody
    @RequestMapping(value = "/checkDepartmentName", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public R checkDepartmentName(@RequestParam("departmentName") String departmentName, @RequestParam("rowGuid") String rowGuid) {
        Sechos_Department sechosDepartment = sechosDepartmentService.checkDepartmentName(departmentName, rowGuid);
        if (sechosDepartment != null) {
            return R.error("科室名称重复！");
        } else {
            return R.ok();
        }
    }
}
