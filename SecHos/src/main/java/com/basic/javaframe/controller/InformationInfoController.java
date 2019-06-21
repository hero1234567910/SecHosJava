package com.basic.javaframe.controller;

import java.util.*;

import com.basic.javaframe.common.utils.DateUtil;
import com.basic.javaframe.common.utils.LayuiUtil;
import com.basic.javaframe.entity.Frame_User;
import com.basic.javaframe.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.basic.javaframe.common.customclass.PassToken;
import com.basic.javaframe.entity.Frame_Attach;
import com.basic.javaframe.entity.InformationInfo;
import com.basic.javaframe.entity.Information_Info_Category;
import com.basic.javaframe.common.utils.PageUtils;
import com.basic.javaframe.common.utils.Query;
import com.basic.javaframe.common.utils.R;


/**
 * <p>InformationInfoController</p>
 * <p>信息查询控制层</p>
 *
 * @author wzl
 * @date 2019-04-09 14:18:17
 */
@Api(value = "信息查询")
@RestController
@CrossOrigin
@RequestMapping("sys/informationinfo")
public class InformationInfoController {

    private final static Logger logger = LoggerFactory.getLogger(InformationInfoController.class);
    @Autowired
    private InformationInfoService informationInfoService;

    @Autowired
    private Frame_AttachService frameAttachService;

    @Autowired
    private Information_Info_CategoryService infoCategoryService;
    @Autowired
    private Frame_UserService userService;

    /**
     * 列表数据
     */
    @PassToken
    @ApiOperation(value = "获取所有发布信息")
    @ResponseBody
    @RequestMapping(value = "/listData", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    public LayuiUtil listData(@RequestParam Map<String, Object> params) {
        InformationInfo informationInfo = new InformationInfo();
        informationInfoService.getTypeName(informationInfo);
        if (params.get("categorys") != null && params.get("categorys") != "") {
            String[] arr = params.get("categorys").toString().split(",");
            params.put("categorys", arr);
        }
        //查询列表数据
        Query query = new Query(params);
        List<InformationInfo> informationInfoList = informationInfoService.getList(query);
        int total = informationInfoService.getCount(query);
        PageUtils pageUtil = new PageUtils(informationInfoList, total, query.getLimit(), query.getPage());
        return LayuiUtil.data(pageUtil.getTotalCount(), pageUtil.getList());
    }

    /**
     * 审核通过列表数据
     */
    @PassToken
    @ApiOperation(value = "获取审核通过的发布信息")
    @ResponseBody
    @RequestMapping(value = "/listData2", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    public LayuiUtil listData2(@RequestParam Map<String, Object> params) {
        InformationInfo informationInfo = new InformationInfo();
        informationInfoService.getTypeName(informationInfo);
        if (params.get("categorys") != null && params.get("categorys") != "") {
            String[] arr = params.get("categorys").toString().split(",");
            params.put("categorys", arr);
        }
        Query query = new Query(params);
        List<InformationInfo> informationInfoList = informationInfoService.getList2(query);
        int total = informationInfoService.getCount2(query);
        PageUtils pageUtil = new PageUtils(informationInfoList, total, query.getLimit(), query.getPage());
        return LayuiUtil.data(pageUtil.getTotalCount(), pageUtil.getList());

    }

    /**
     * 新增
     **/
    @ApiOperation(value = "新增")
    @ResponseBody
    @RequestMapping(value = "/add", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public R add(@RequestBody InformationInfo informationInfo) {
        //生成uuid作为rowguid
        String uuid = java.util.UUID.randomUUID().toString();
        informationInfo.setRowGuid(uuid);
        Date createTime = DateUtil.changeDate(new Date());
        informationInfo.setCreateTime(createTime);
        informationInfo.setSortSq(0);
        String attachGuid = java.util.UUID.randomUUID().toString();
        informationInfo.setAttachGuid(attachGuid);
        String categoryGuid = java.util.UUID.randomUUID().toString();
        informationInfo.setCategoryGuid(categoryGuid);

        informationInfoService.save(informationInfo);

        //新增完以后再去更新附件表
        if (informationInfo.getAttachRowGuids() != null && informationInfo.getAttachRowGuids().length != 0) {
            frameAttachService.updateAttach(attachGuid, informationInfo.getAttachRowGuids());
        }
        //插入信息栏目多对多表
        Information_Info_Category inforCategory = new Information_Info_Category();
        inforCategory.setRowGuid(UUID.randomUUID().toString());
        inforCategory.setCreateTime(createTime);
        inforCategory.setInfoGuid(informationInfo.getCategoryGuid());

        String[] cateGorys = informationInfo.getCategoryGuids();
        if (cateGorys == null || cateGorys.length == 0) {
            return R.error("发布栏目为空");
        }
        for (int i = 0; i < cateGorys.length; i++) {
            inforCategory.setCategoryGuid(cateGorys[i]);
            infoCategoryService.insert(inforCategory);
        }

        return R.ok().put("msg", "提交成功");
    }


    /**
     * 修改
     */
    @ApiOperation(value = "修改发布信息")
    @ResponseBody
    @RequestMapping(value = "/update", produces = "application/json; charset=utf-8", method = RequestMethod.PUT)
    public R update(@RequestBody InformationInfo informationInfo) {
        informationInfoService.update(informationInfo);
        //更新完以后再去更新附件表
        if (informationInfo.getAttachRowGuids() != null && informationInfo.getAttachRowGuids().length != 0) {
            frameAttachService.updateAttach(informationInfo.getAttachGuid(), informationInfo.getAttachRowGuids());
        }
        return R.ok().put("msg", "更新成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除发布信息")
    @ResponseBody
    @RequestMapping(value = "/deleteInfo/{categoryGuids}", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public R delete(@PathVariable("categoryGuids") String[] categoryGuids) {
        informationInfoService.deleteBatch(categoryGuids);
        infoCategoryService.deleteByCateGuids(categoryGuids);
        return R.ok();
    }

    /**
     * 审核通过
     */
    @ApiOperation(value = "审核信息通过")
    @ResponseBody
    @RequestMapping(value = "/auditPassInfo/{rowGuid}", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public R auditPassInfo(@PathVariable("rowGuid") String rowGuid) {
        informationInfoService.auditPassInfo(rowGuid);
        return R.ok();
    }

    /**
     * 审核不通过
     */
    @ApiOperation(value = "审核信息不通过")
    @ResponseBody
    @RequestMapping(value = "/auditFailInfo/{rowGuid}", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public R auditFailInfo(@PathVariable("rowGuid") String rowGuid) {
        informationInfoService.auditFailInfo(rowGuid);
        return R.ok();
    }

    /**
     * 发布多条信息
     * <p>Title: deliverInfo</p>
     * <p>Description: 信息</p>
     *
     * @param ids
     * @return
     * @author
     */
    @ApiOperation(value = "发布多条信息")
    @ResponseBody
    @RequestMapping(value = "/deliverInfo/{id}", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public R deliverInfo(@PathVariable("id") Integer[] ids) {
        informationInfoService.deliverInfoById(ids);
        return R.ok();
    }

    /**
     * 停止发布信息
     * <p>Title: stopDeliver</p>
     * <p>Description: 信息</p>
     *
     * @param ids
     * @return
     * @author
     */
    @ApiOperation(value = "停止发布信息")
    @ResponseBody
    @RequestMapping(value = "/stopDeliver/{id}", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public R stopDeliver(@PathVariable("id") Integer[] ids) {
        informationInfoService.stopDeliverById(ids);
        return R.ok();
    }

    /**
     * 根据栏目查询信息
     *
     * @return
     */
    @ApiOperation(value = "根据栏目查询信息")
    @ResponseBody
    @RequestMapping(value = "/getInfoByCateGuid/{guid}", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    public R getInfoByGuid(@PathVariable("guid") String guid) {
        List<String> list = infoCategoryService.getInfoByCateGuid(guid);
        return R.ok().put("data", list);
    }

    /**
     * 信息发布到微信端
     */
    @ApiOperation(value = "信息发布到微信端")
    @ResponseBody
    @RequestMapping(value = "/infoDelivery/{rowId}", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public R auditFailInfo(@PathVariable("rowId") Integer rowId) {
        informationInfoService.infoOff(rowId);
        informationInfoService.infoOn(rowId);
        return R.ok();
    }
}
