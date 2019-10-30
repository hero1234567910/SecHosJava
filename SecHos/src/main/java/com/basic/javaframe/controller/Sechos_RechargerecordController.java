package com.basic.javaframe.controller;

import com.basic.javaframe.common.customclass.PassToken;
import com.basic.javaframe.common.enumresource.PayTypeEnum;
import com.basic.javaframe.common.utils.*;
import com.basic.javaframe.entity.Sechos_Drugmaterial;
import com.basic.javaframe.entity.Sechos_Rechargerecord;
import com.basic.javaframe.service.Sechos_RechargerecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author
 * @date 2019-07-10 16:02:23
 */
@Api(value = "充值记录管理")
@RestController
@CrossOrigin
@RequestMapping("sys/sechosrechargerecord")
public class Sechos_RechargerecordController {
    @Autowired
    private Sechos_RechargerecordService sechosRechargerecordService;

    /**
     * 列表数据
     */
    @PassToken
    @ApiOperation(value = "获取充值记录表")
    @ResponseBody
    @RequestMapping(value = "/listData", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    public LayuiUtil listData(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<Sechos_Rechargerecord> sechosRechargerecordList = sechosRechargerecordService.getList(query);
        int total = sechosRechargerecordService.getCount(query);
        PageUtils pageUtil = new PageUtils(sechosRechargerecordList, total, query.getLimit(), query.getPage());
        return LayuiUtil.data(pageUtil.getTotalCount(), pageUtil.getList());
    }

    /**
     * 新增
     **/
    @ApiOperation(value = "新增充值记录")
    @ResponseBody
    @RequestMapping(value = "/add", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public R add(@RequestBody Sechos_Rechargerecord sechosRechargerecord) {
        //如果排序号为空，则自动转为0
        if (sechosRechargerecord.getSortSq() == null) {
            sechosRechargerecord.setSortSq(0);
        }
        //生成uuid作为rowguid
        String uuid = java.util.UUID.randomUUID().toString();
        sechosRechargerecord.setRowGuid(uuid);
        sechosRechargerecord.setDelFlag(0);
        Date createTime = DateUtil.changeDate(new Date());
        sechosRechargerecord.setCreateTime(createTime);
        sechosRechargerecordService.save(sechosRechargerecord);
        return R.ok();
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改充值记录")
    @ResponseBody
    @RequestMapping(value = "/update", produces = "application/json; charset=utf-8", method = RequestMethod.POST)
    public R update(@RequestBody Sechos_Rechargerecord sechosRechargerecord) {
        sechosRechargerecordService.update(sechosRechargerecord);
        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除充值记录")
    @ResponseBody
    @RequestMapping(value = "/delete", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public R delete(@RequestBody String[] rowGuids) {
        sechosRechargerecordService.deleteBatch(rowGuids);
        return R.ok();
    }

    /**
     * 统计半小时订单数量
     * <p>Title: orderStatistical</p>
     * <p>Description: </p>
     *
     * @return
     * @author wzl
     */
    @ApiOperation(value = "每隔半小时统计门诊挂号数量")
    @ResponseBody
    @RequestMapping(value = "/selectRechargeStatisical", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    public R orderStatistical() {
        List<Map<String, Object>> maps = sechosRechargerecordService.selectRechargeStatisical();
        return R.ok().put("data", maps);
    }

    /**
     * 统计
     * <p>Title: statistical</p>
     * <p>Description: </p>
     *
     * @return
     * @author wzl
     */
    @ApiOperation(value = "统计门诊等数量")
    @ResponseBody
    @RequestMapping(value = "/statistical", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    public R statistical() {
        List<Sechos_Rechargerecord> sechosRechargerecordList = sechosRechargerecordService.selectAllRecharge();
        int yjjCount = 0;
        int ghCount = 0;
        int mzCount = 0;
        Map<String, Object> map = new HashMap<>();
        map.put("yjjCount", yjjCount);
        map.put("ghCount", ghCount);
        map.put("mzCount", mzCount);
        if (sechosRechargerecordList == null) {
            return R.ok().put("data", map);
        }
        for (int i = 0; i < sechosRechargerecordList.size(); i++) {
            if (sechosRechargerecordList.get(i) != null) {
                if (PayTypeEnum.ADVANCEPAY.getCode() == sechosRechargerecordList.get(i).getPayType()) {
                    yjjCount++;
                }
                if (PayTypeEnum.GHPAY.getCode() == sechosRechargerecordList.get(i).getPayType()) {
                    ghCount++;
                }
                if (PayTypeEnum.MZPAY.getCode() == sechosRechargerecordList.get(i).getPayType()) {
                    mzCount++;
                }
            }
        }

        map.put("yjjCount", yjjCount);
        map.put("ghCount", ghCount);
        map.put("mzCount", mzCount);
        return R.ok().put("data", map);
    }

    @ApiOperation(value="通过rowGuid获取一条记录")
    @ResponseBody
    @RequestMapping(value="/getDetailByGuid",produces="application/json;charset=utf-8",method=RequestMethod.POST)
    public R getDetailByGuid(@RequestBody String rowGuid){
        Sechos_Rechargerecord sechosRechargerecord = sechosRechargerecordService.getDetailByGuid(rowGuid);
        return R.ok().put("data",sechosRechargerecord);
    }
}
