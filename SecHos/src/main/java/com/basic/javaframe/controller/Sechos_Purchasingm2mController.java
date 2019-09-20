package com.basic.javaframe.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.basic.javaframe.common.utils.DateUtil;
import com.basic.javaframe.common.utils.LayuiUtil;
import com.basic.javaframe.entity.Sechos_Drugmaterial;
import com.basic.javaframe.entity.Sechos_Procurement;
import com.basic.javaframe.service.Sechos_DrugmaterialService;
import com.basic.javaframe.service.Sechos_ProcurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.basic.javaframe.common.customclass.PassToken;
import com.basic.javaframe.entity.Sechos_Purchasingm2m;
import com.basic.javaframe.service.Sechos_Purchasingm2mService;
import com.basic.javaframe.common.utils.PageUtils;
import com.basic.javaframe.common.utils.Query;
import com.basic.javaframe.common.utils.R;


/**
 * 
 * 
 * @author 
 * @date 2019-09-20 10:55:46
 */
@Api(value = "采购多对多表管理")
@RestController
@CrossOrigin
@RequestMapping("sys/sechospurchasingm2m")
public class Sechos_Purchasingm2mController {
	@Autowired
	private Sechos_Purchasingm2mService sechosPurchasingm2mService;

	@Autowired
	private Sechos_ProcurementService sechosProcurementService;
	/**
	 * 列表数据
	 */
	@PassToken
	@ApiOperation(value="采购多对多表数据")
    @ResponseBody
	@RequestMapping(value="/listData",produces="application/json;charset=utf-8",method=RequestMethod.GET)
	public LayuiUtil listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<Sechos_Purchasingm2m> sechosPurchasingm2mList = sechosPurchasingm2mService.getList(query);
		int total = sechosPurchasingm2mService.getCount(query);
		PageUtils pageUtil = new PageUtils(sechosPurchasingm2mList, total, query.getLimit(), query.getPage());
		return LayuiUtil.data(pageUtil.getTotalCount(), pageUtil.getList());
	}

    /**
     * 新增
     **/
    @ApiOperation(value="新增采购多对多表")
    @ResponseBody
    @RequestMapping(value="/add",produces="application/json;charset=utf-8",method=RequestMethod.POST)
    public R add(@RequestBody Sechos_Purchasingm2m sechosPurchasingm2m){
    	//如果排序号为空，则自动转为0
    	if (sechosPurchasingm2m.getSortSq() == null) {
			sechosPurchasingm2m.setSortSq(0);
		}
    	//生成uuid作为rowguid
        String uuid = java.util.UUID.randomUUID().toString();
		sechosPurchasingm2m.setRowGuid(uuid);
		Date createTime = DateUtil.changeDate(new Date());
		sechosPurchasingm2m.setCreateTime(createTime);
		sechosPurchasingm2m.setDelFlag(0);
		sechosPurchasingm2mService.save(sechosPurchasingm2m);

		String guid= sechosPurchasingm2m.getPurchaseGuid();
		Sechos_Procurement sechosProcurement = new Sechos_Procurement();
		sechosProcurement.setRowGuid(guid);
		sechosProcurement.setPurchaseStatus(1);
		sechosProcurement.setPurchaseDate(createTime);
		sechosProcurementService.update(sechosProcurement);
        return R.ok();  
    }

	/**
	 * 修改
	 */
	@ApiOperation(value="修改")
    @ResponseBody
	@RequestMapping(value="/update", produces = "application/json; charset=utf-8", method=RequestMethod.POST)
	public R update(@RequestBody Sechos_Purchasingm2m sechosPurchasingm2m){

		sechosPurchasingm2mService.update(sechosPurchasingm2m);
		return R.ok();
	}

	/**
	 * 删除
	 */
    @ApiOperation(value="删除")
	@ResponseBody
	@RequestMapping(value="/delete",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R delete(@RequestBody String[] rowGuids){
		sechosPurchasingm2mService.deleteBatch(rowGuids);
		return R.ok();
	}
	
}
