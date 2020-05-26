package com.basic.javaframe.controller;

import com.basic.javaframe.common.customclass.PassToken;
import com.basic.javaframe.common.utils.*;
import com.basic.javaframe.entity.Sechos_Professor;
import com.basic.javaframe.service.Frame_AttachService;
import com.basic.javaframe.service.Sechos_DepartmentService;
import com.basic.javaframe.service.Sechos_ProfessorService;
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
@Api(value = "专家管理")
@RestController
@CrossOrigin
@RequestMapping("sys/sechosprofessor")
public class Sechos_ProfessorController {

    private final static Logger logger = LoggerFactory.getLogger(Sechos_ProfessorController.class);

    @Autowired
    private Sechos_ProfessorService sechosProfessorService;

    @Autowired
    private Sechos_DepartmentService sechosDepartmentService;

    @Autowired
    private Frame_AttachService attachService;

    /**
     * 列表数据
     */
    @PassToken
    @ApiOperation(value = "获取专家表")
    @ResponseBody
    @RequestMapping(value = "/listData", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    public LayuiUtil listData(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<Sechos_Professor> sechosProfessorList = sechosProfessorService.getList(query);
        int total = sechosProfessorService.getCount(query);
        PageUtils pageUtil = new PageUtils(sechosProfessorList, total, query.getLimit(), query.getPage());
        return LayuiUtil.data(pageUtil.getTotalCount(), pageUtil.getList());
    }

    /**
     * 新增
     **/
    @ApiOperation(value = "新增专家信息")
    @ResponseBody
    @RequestMapping(value = "/add", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public R add(@RequestBody Sechos_Professor sechosProfessor) {
        //如果排序号为空，则自动转为0
        if (sechosProfessor.getSortSq() == null) {
            sechosProfessor.setSortSq(0);
        }
        //生成uuid作为rowguid
        String uuid = java.util.UUID.randomUUID().toString();
        sechosProfessor.setRowGuid(uuid);
        sechosProfessor.setDelFlag(0);
        Date createTime = DateUtil.changeDate(new Date());
        sechosProfessor.setCreateTime(createTime);
        String imgGuid = java.util.UUID.randomUUID().toString();
        sechosProfessor.setPicGuid(imgGuid);
        sechosProfessorService.save(sechosProfessor);
        String[] guids = {sechosProfessor.getUploadImgGuid()};
        attachService.updateAttach(imgGuid, guids);
        return R.ok();
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改专家信息")
    @ResponseBody
    @RequestMapping(value = "/update", produces = "application/json; charset=utf-8", method = RequestMethod.POST)
    public R update(@RequestBody Sechos_Professor sechosProfessor) {
        if (sechosProfessor.getUploadImgGuid() != null) {
            String imgGuid = java.util.UUID.randomUUID().toString();
            sechosProfessor.setPicGuid(imgGuid);
            String[] guids = {sechosProfessor.getUploadImgGuid()};
            attachService.updateAttach(imgGuid, guids);
        }
        sechosProfessorService.update(sechosProfessor);
        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除专家信息")
    @ResponseBody
    @RequestMapping(value = "/delete", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public R delete(@RequestBody String[] rowGuids) {
        sechosProfessorService.deleteBatch(rowGuids);
        return R.ok();
    }

    @ApiOperation(value = "通过rowGuid获取一条记录")
    @ResponseBody
    @RequestMapping(value = "/getDetailByGuid", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public R getDetailByGuid(@RequestBody String rowGuid) {
        Sechos_Professor sechosProfessor = sechosProfessorService.getDetailByGuid(rowGuid);
        return R.ok().put("data", sechosProfessor);
    }

    @ApiOperation(value = "名称重复验证")
    @ResponseBody
    @RequestMapping(value = "/checkProfessorName", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public R checkProfessorName(@RequestParam("professorName") String professorName, @RequestParam("rowGuid") String rowGuid) {
        Sechos_Professor sechosProfessor = sechosProfessorService.checkProfessorName(professorName, rowGuid);
        if (sechosProfessor != null) {
            return R.error("专家名称重复！");
        } else {
            return R.ok();
        }
    }
}
