package com.basic.javaframe.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.basic.javaframe.common.utils.*;
import com.basic.javaframe.entity.Sechos_Procurement;
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
    @RequestMapping(value="/add",produces="application/json;charset=utf-8",method=RequestMethod.POST)
    public R add(){
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
		sechosProcurementService.save(sechosProcurement);
        return R.ok();  
    }

	/**
	 * 修改
	 */
	@ApiOperation(value="")
    @ResponseBody
	@RequestMapping(value="/update", produces = "application/json; charset=utf-8", method=RequestMethod.POST)
	public R update(@RequestBody Sechos_Procurement sechosProcurement){
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
	
}
