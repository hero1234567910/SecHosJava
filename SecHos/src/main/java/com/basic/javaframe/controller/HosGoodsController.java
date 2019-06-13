package com.basic.javaframe.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.basic.javaframe.common.utils.*;
import com.basic.javaframe.entity.HosGoods;
import com.basic.javaframe.service.Frame_AttachService;
import com.basic.javaframe.service.HosGoodsService;
import com.basic.javaframe.service.HosGoodstypeService;
import com.basic.javaframe.service.HosOrderitemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.basic.javaframe.common.customclass.PassToken;


/**
 * @author wzl
 * @date 2019-04-28 10:46:49
 */
@Api(value = "食品信息")
@RestController
@CrossOrigin
@RequestMapping("sys/hosgoods")
public class HosGoodsController {

    private final static Logger logger = LoggerFactory.getLogger(HosGoodsController.class);
    @Autowired
    private HosGoodsService hosGoodsService;

    @Autowired
    private HosGoodstypeService hosGoodstypeService;

    @Autowired
    private HosOrderitemService hosOrderitemService;
    
    @Autowired
    private Frame_AttachService attachService;
    /**
     * 列表数据
     */
    @PassToken
    @ApiOperation(value = "查询食品信息列表")
    @ResponseBody
    @RequestMapping(value = "/listData", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    public LayuiUtil listData(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<HosGoods> hosGoodsList = hosGoodsService.getList(query);
        int total = hosGoodsService.getCount(query);
        PageUtils pageUtil = new PageUtils(hosGoodsList, total, query.getLimit(), query.getPage());
        return LayuiUtil.data(pageUtil.getTotalCount(), pageUtil.getList());
    }

    /**
     * 新增
     **/
    @Transactional
    @ApiOperation(value = "新增菜品")
    @ResponseBody
    @RequestMapping(value = "/add", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public R add(@RequestBody HosGoods hosGoods) {
        //如果排序号为空，则自动转为0
        if (hosGoods.getSortSq() == null) {
            hosGoods.setSortSq(0);
        }
        //生成uuid作为rowguid
        String uuid = java.util.UUID.randomUUID().toString();
        hosGoods.setRowGuid(uuid);
        Date createTime = DateUtil.changeDate(new Date());
        hosGoods.setCreateTime(createTime);
        hosGoods.setDelFlag(0);
        String goodsImgGuid = java.util.UUID.randomUUID().toString();
        hosGoods.setGoodsImgGuid(goodsImgGuid);
        hosGoodsService.save(hosGoods);
        //更新附件
        String[] guids = {hosGoods.getUploadImgGuid()};
        attachService.updateAttach(goodsImgGuid,guids);
        return R.ok();
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改菜品信息")
    @ResponseBody
    @RequestMapping(value = "/update", produces = "application/json; charset=utf-8", method = RequestMethod.PUT)
    public R update(@RequestBody HosGoods hosGoods) {
        hosGoodsService.update(hosGoods);
        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除菜品信息")
    @ResponseBody
    @RequestMapping(value = "/delete", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public R delete(@RequestBody String[] rowGuids) {
        hosGoodsService.deleteBatch(rowGuids);
        return R.ok();
    }

    /**
     * 验证重复性
     * <p>Title: checkGoodName</p>
     * <p>Description: </p>
     *
     * @param <T>
     * @return
     * @author hero
     */
    @ApiOperation(value = "验证菜品重复性")
    @ResponseBody
    @RequestMapping(value = "/checkGoods", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public <T> R checkGoods(@RequestBody T t) {
        //泛型无法判断传入的值是否为空
        int count = hosGoodsService.checkGoods(t);
        if (count >= 1) {
            return R.error("您输入的值已存在，请重新输入");
        }
        return R.ok();
    }

    /**
     * 查询菜单类别
     * <p>Title: getTypeNameByGuid</p>
     * <p>Description: </p>
     *
     * @return
     * @author wzl
     */
    @ApiOperation(value = "查询上级菜单类别")
    @ResponseBody
    @RequestMapping(value = "/getTypeNameByGuid/{goodsTypeGuid}", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    public R getTypeNameByGuid(@PathVariable("goodsTypeGuid") String goodsTypeGuid) {
        String name = hosGoodstypeService.getTypeNameByGuid(goodsTypeGuid);
        return R.ok().put("data", name);
    }

    /**
     * 批量上架
     */
    @ApiOperation(value = "批量上架菜品")
    @ResponseBody
    @RequestMapping(value = "/goodsUpShelf", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public R goodsUpShelf(@RequestBody String[] rowGuids) {
        hosGoodsService.goodsUpShelf(rowGuids);
        return R.ok();
    }

    /**
     * 批量下架
     */
    @ApiOperation(value = "批量下架菜品")
    @ResponseBody
    @RequestMapping(value = "/goodsDownShelf", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public R goodsDownShelf(@RequestBody String[] rowGuids) {
        hosGoodsService.goodsDownShelf(rowGuids);
        return R.ok();
    }

//    /**
//     * 通过orderItemGuid查询菜单名称
//     * <p>Title: getGoodsNameByItemGuid</p>
//     * <p>Description: 用户</p>
//     * @param orderGuid
//     * @return
//     */
//    @ApiOperation(value="通过orderItemGuid查询菜品名称")
//    @ResponseBody
//    @RequestMapping(value="/getGoodsNameByItemGuid",produces="application/json;charset=utf-8",method=RequestMethod.POST)
//    public R getGoodsNameByItemGuid(@RequestBody String orderGuid){
//        String rowGuid = hosOrderitemService.getGoodsByItemGuid(orderGuid);
//        String name = hosGoodsService.getGoodsNameByGuid(rowGuid);
//        return R.ok().put("data",name);
//    }
}
