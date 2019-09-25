package com.basic.javaframe.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.basic.javaframe.common.utils.*;
import com.basic.javaframe.entity.Sechos_Outbound;
import com.basic.javaframe.entity.Sechos_Outboundm2m;
import com.basic.javaframe.service.Sechos_Outboundm2mService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.basic.javaframe.common.customclass.PassToken;
import com.basic.javaframe.service.Sechos_OutboundService;


/**
 * 
 * 
 * @author 
 * @date 2019-09-25 09:45:11
 */
@Api(value = "出库单据列表")
@RestController
@CrossOrigin
@RequestMapping("sys/sechosoutbound")
public class Sechos_OutboundController {
	@Autowired
	private Sechos_OutboundService sechosOutboundService;

	@Autowired
	private Sechos_Outboundm2mService sechosOutboundm2mService;
	/**
	 * 列表数据
	 */
	@PassToken
	@ApiOperation(value="获取列表数据")
    @ResponseBody
	@RequestMapping(value="/listData",produces="application/json;charset=utf-8",method=RequestMethod.GET)
	public LayuiUtil listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<Sechos_Outbound> sechosOutboundList = sechosOutboundService.getList(query);
		int total = sechosOutboundService.getCount(query);
		PageUtils pageUtil = new PageUtils(sechosOutboundList, total, query.getLimit(), query.getPage());
		return LayuiUtil.data(pageUtil.getTotalCount(), pageUtil.getList());
	}

    /**
     * 新增
     **/
    @ApiOperation(value="新增出库单")
    @ResponseBody
    @RequestMapping(value="/add/{personGuid}",produces="application/json;charset=utf-8",method=RequestMethod.POST)
    public R add(@PathVariable("personGuid") String personGuid){
    	Sechos_Outbound sechosOutbound = new Sechos_Outbound();
    	//如果排序号为空，则自动转为0
    	if (sechosOutbound.getSortSq() == null) {
			sechosOutbound.setSortSq(0);
		}
    	//生成uuid作为rowguid
        String uuid = java.util.UUID.randomUUID().toString();
		sechosOutbound.setRowGuid(uuid);
		Date createTime = DateUtil.changeDate(new Date());
		sechosOutbound.setCreateTime(createTime);
		String outboundNumber = RandomNumber.GetOutRandom();
		sechosOutbound.setOutboundOrderNum(outboundNumber);
		sechosOutbound.setDelFlag(0);
		sechosOutbound.setOutboundStatus(0);
		sechosOutbound.setPersonGuid(personGuid);
		sechosOutboundService.save(sechosOutbound);
        return R.ok();  
    }

	/**
	 * 修改
	 */
	@ApiOperation(value="材料出库审批")
    @ResponseBody
	@RequestMapping(value="/update", produces = "application/json; charset=utf-8", method=RequestMethod.POST)
	public R update(@RequestBody Sechos_Outbound sechosOutbound){
		String row = sechosOutbound.getRowGuid();
		List<Sechos_Outboundm2m> sechosOutboundm2mList = sechosOutboundm2mService.getListByPGuid(row);
		BigDecimal money = new BigDecimal("0");
		for(int i = 0;i<sechosOutboundm2mList.size();i++){
			Sechos_Outboundm2m sechos_outboundm2m = sechosOutboundm2mList.get(i);
			money = money.add(sechos_outboundm2m.getDrugTotalPrice());
		}
		sechosOutbound.setOutboundPrice(money);
		sechosOutboundService.update(sechosOutbound);
		return R.ok();
	}

	/**
	 * 修改
	 */
	@ApiOperation(value="材料审批通过")
	@ResponseBody
	@RequestMapping(value="/approval", produces = "application/json; charset=utf-8", method=RequestMethod.POST)
	public R approval(@RequestBody Sechos_Outbound sechosOutbound){
		String row = sechosOutbound.getRowGuid();
		List<Sechos_Outboundm2m> sechosOutboundm2mList = sechosOutboundm2mService.getListByPGuid(row);
		BigDecimal money = new BigDecimal("0");
		for(int i = 0;i<sechosOutboundm2mList.size();i++){
			Sechos_Outboundm2m sechos_outboundm2m = sechosOutboundm2mList.get(i);
			money = money.add(sechos_outboundm2m.getDrugTotalPrice());
		}
		sechosOutbound.setOutboundPrice(money);
		sechosOutboundService.update(sechosOutbound);
		return R.ok();
	}

	/**
	 * 删除
	 */
    @ApiOperation(value="")
	@ResponseBody
	@RequestMapping(value="/delete",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R delete(@RequestBody String[] rowGuids){
		sechosOutboundService.deleteBatch(rowGuids);
		return R.ok();
	}

	@PassToken
	@ApiOperation(value="获取列表数据2")
	@ResponseBody
	@RequestMapping(value="/listData2",produces="application/json;charset=utf-8",method=RequestMethod.GET)
	public LayuiUtil listData2(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		List<Sechos_Outbound> sechosOutboundList = sechosOutboundService.getList2(query);
		int total = sechosOutboundService.getCount2(query);
		PageUtils pageUtil = new PageUtils(sechosOutboundList, total, query.getLimit(), query.getPage());
		return LayuiUtil.data(pageUtil.getTotalCount(), pageUtil.getList());
	}
	
}
