package com.basic.javaframe.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.basic.javaframe.common.utils.*;
import com.basic.javaframe.entity.Sechos_Procurement;
import com.basic.javaframe.entity.Sechos_Purchasingm2m;
import com.basic.javaframe.service.Sechos_Purchasingm2mService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.basic.javaframe.common.customclass.PassToken;
import com.basic.javaframe.service.Sechos_ProcurementService;


/**
 * 
 * 
 * @author 
 * @date 2019-09-19 15:26:43
 */
@Api(value = "采购单数据列表")
@RestController
@CrossOrigin
@RequestMapping("sys/sechosprocurement")
public class Sechos_ProcurementController {
	@Autowired
	private Sechos_ProcurementService sechosProcurementService;

	@Autowired
	private Sechos_Purchasingm2mService purchasingm2mService;
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
		List<Sechos_Procurement> sechosProcurementList = sechosProcurementService.getList(query);
		int total = sechosProcurementService.getCount(query);
		PageUtils pageUtil = new PageUtils(sechosProcurementList, total, query.getLimit(), query.getPage());
		return LayuiUtil.data(pageUtil.getTotalCount(), pageUtil.getList());
	}

    /**
     * 新增
     **/
    @ApiOperation(value="新增采购单")
    @ResponseBody
    @RequestMapping(value="/add/{personGuid}",produces="application/json;charset=utf-8",method=RequestMethod.POST)
    public R add(@PathVariable("personGuid") String personGuid){
		Sechos_Procurement sechosProcurement =new Sechos_Procurement();
    	//如果排序号为空，则自动转为0
    	if (sechosProcurement.getSortSq() == null) {
			sechosProcurement.setSortSq(0);
		}
    	//生成uuid作为rowguid
        String uuid = java.util.UUID.randomUUID().toString();
		sechosProcurement.setRowGuid(uuid);
		Date createTime = DateUtil.changeDate(new Date());
		sechosProcurement.setCreateTime(createTime);
		String purchaseNumber = RandomNumber.GetRandom();
		//System.out.println(purchaseNumber);
		sechosProcurement.setPurchaseOrderNum(purchaseNumber);
		sechosProcurement.setDelFlag(0);
		sechosProcurement.setPurchaseStatus(0);
		sechosProcurement.setPersonGuid(personGuid);
		sechosProcurementService.save(sechosProcurement);
        return R.ok();  
    }

	/**
	 * 修改
	 */
	@ApiOperation(value="审批通过")
    @ResponseBody
	@RequestMapping(value="/approval", produces = "application/json; charset=utf-8", method=RequestMethod.POST)
	public R approval(@RequestBody Sechos_Procurement sechosProcurement){
		String row = sechosProcurement.getRowGuid();
		List<Sechos_Purchasingm2m> sechosPurchasingm2mList = purchasingm2mService.getListByPGuid(row);
		BigDecimal money = new BigDecimal("0");
		for(int i = 0;i<sechosPurchasingm2mList.size();i++){
			Sechos_Purchasingm2m sechosPurchasingm2m = sechosPurchasingm2mList.get(i);
			money = money.add(sechosPurchasingm2m.getDrugTotalPrice());
		}
		System.out.println(money);
		sechosProcurement.setPurchasePrice(money);
		sechosProcurementService.update(sechosProcurement);
		return R.ok();
	}

	/**
	 * 修改
	 */
	@ApiOperation(value="材料入库审批")
	@ResponseBody
	@RequestMapping(value="/update", produces = "application/json; charset=utf-8", method=RequestMethod.POST)
	public R update(@RequestBody Sechos_Procurement sechosProcurement){
		String row = sechosProcurement.getRowGuid();
		List<Sechos_Purchasingm2m> sechosPurchasingm2mList = purchasingm2mService.getListByPGuid(row);
		BigDecimal money = new BigDecimal("0");
		for(int i = 0;i<sechosPurchasingm2mList.size();i++){
			Sechos_Purchasingm2m sechosPurchasingm2m = sechosPurchasingm2mList.get(i);
			money = money.add(sechosPurchasingm2m.getDrugTotalPrice());
		}
		//System.out.println(money);
		sechosProcurement.setPurchasePrice(money);
		sechosProcurementService.update(sechosProcurement);
		return R.ok();
	}

	/**
	 * 删除
	 */
    @ApiOperation(value="")
	@ResponseBody
	@RequestMapping(value="/delete",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R delete(@RequestBody String[] rowGuids){
		sechosProcurementService.deleteBatch(rowGuids);
		return R.ok();
	}

	/**
	 * 列表数据2
	 */
	@PassToken
	@ApiOperation(value="获取列表数据2")
	@ResponseBody
	@RequestMapping(value="/listData2",produces="application/json;charset=utf-8",method=RequestMethod.GET)
	public LayuiUtil listData2(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		List<Sechos_Procurement> sechosProcurementList = sechosProcurementService.getList2(query);
		int total = sechosProcurementService.getCount2(query);
		PageUtils pageUtil = new PageUtils(sechosProcurementList, total, query.getLimit(), query.getPage());
		return LayuiUtil.data(pageUtil.getTotalCount(), pageUtil.getList());
	}

	/**
	 * 修改近效期状态
	 */
	@ApiOperation(value="修改近效期状态")
	@ResponseBody
	@RequestMapping(value="/changeOverDueMark",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R changeOverDueMark(@RequestBody String[] rowGuids){
		sechosProcurementService.changeOverDueMark(rowGuids);
		return R.ok();
	}
}
