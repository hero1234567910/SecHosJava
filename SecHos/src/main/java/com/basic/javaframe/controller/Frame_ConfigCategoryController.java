package com.basic.javaframe.controller;

import com.basic.javaframe.common.customclass.PassToken;
import com.basic.javaframe.common.utils.*;
import com.basic.javaframe.entity.Frame_ConfigCateGory;
import com.basic.javaframe.service.Frame_ConfigCategoryService;
import com.basic.javaframe.service.Frame_ConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p>Title: Frame_ConfigCategoryController</p>
 * <p>Description: 系统参数类别控制层</p>
 *
 * @author wzl
 */
@CrossOrigin
@Api(value = "系统参数类别")
@RestController
@RequestMapping("sys/configCategory")
public class Frame_ConfigCategoryController {
    //定义一个全局的记录器，通过LoggerFactory获取
    private final static Logger logger = LoggerFactory.getLogger(Frame_ConfigCategoryController.class);

    @Autowired
    private Frame_ConfigCategoryService configCategoryService;

    @Autowired
    private Frame_ConfigService configService;

    /**
     * 获取所有系统参数类别
     * <p>Title: getConfigCategory</p>
     * <p>Description: 系统参数类别</p>
     *
     * @param params
     * @return
     * @author wzl
     */
    @PassToken
    @ApiOperation(value = "获取所有系统参数类别")
    @ResponseBody
    @RequestMapping(value = "/getConfigCategory", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    public LayuiUtil getConfigCategory(@RequestParam Map<String, Object> params) {
        Query query = new Query(params);
        List<Frame_ConfigCateGory> cateGoryList = configCategoryService.getConfigCategory(query);
        int total = configCategoryService.getCount(query);
        PageUtils pageUtil = new PageUtils(cateGoryList, total, query.getLimit(), query.getPage());
        return LayuiUtil.data(pageUtil.getTotalCount(), pageUtil.getList());
    }

    /**
     * 新增用户
     * <p>Title: insert</p>
     * <p>Description: 新增系统参数类别</p>
     *
     * @param configCateGory
     * @return
     * @author wzl
     */
    @ApiOperation(value = "新增系统参数类别")
    @ResponseBody
    @RequestMapping(value = "/insertConfigCategory", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public R insertConfigCategory(@RequestBody Frame_ConfigCateGory configCateGory) {
        //生成uuid作为rowguid
        String uuid = UUID.randomUUID().toString();
        configCateGory.setRowGuid(uuid);
        configCategoryService.insertConfigCategory(configCateGory);
        return R.ok();
    }

    /**
     * 更新系统参数类别
     * <p>Title: update</p>
     * <p>Description: 更新系统参数类别</p>
     *
     * @param id
     * @param configCateGory
     * @return
     * @author wzl
     */
    @ApiOperation(value = "更新系统参数类别")
    @ResponseBody
    @RequestMapping(value = "/updateConfigCategory/{id}", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public R updateConfigCategory(@PathVariable("id") Integer id, @RequestBody Frame_ConfigCateGory configCateGory) {
        configCateGory.setRowId(id);
        configCategoryService.updateConfigCateory(configCateGory);
        return R.ok();
    }

    /**
     * 删除系统参数类别
     * <p>Title: delete</p>
     * <p>Description: 系统参数类别</p>
     *
     * @param rowGuid
     * @return
     * @author wzl
     */
    @ApiOperation(value = "删除系统参数类别")
    @ResponseBody
    @RequestMapping(value = "/deleteConfigCategory/{rowGuid}", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public R deleteConfigCategory(@PathVariable("rowGuid") String rowGuid) {
        configService.deleteConfigByCategoryGuid(rowGuid);
        configCategoryService.deleteConfigCategory(rowGuid);
        return R.ok();
    }

    /**
     * 获取所有参数类别
     * <p>Title：getAllCategory</p>
     *
     * @return
     */
    @ApiOperation(value = "获取所有参数类别")
    @ResponseBody
    @RequestMapping(value = "/getAllCategory", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    public R getAllCategory() {
        List<Frame_ConfigCateGory> list = configCategoryService.getAllCategory();
        return R.ok().put("data", list);
    }

    /**
     * 验证参数类别重复性
     * <p>Title:checkCategoryName</p>
     *
     * @param t
     * @param <T>
     * @return
     * @author wzl
     */
    @ApiOperation(value = "验证类别名重复性")
    @ResponseBody
    @RequestMapping(value = "/checkCategoryName", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public <T> R checkCategoryName(@RequestBody T t) {
        int count = configCategoryService.checkCategoryName(t);
        if (count >= 1) {
            return R.error("您输入的值已存在，请重新输入");
        }
        return R.ok();
    }
}
