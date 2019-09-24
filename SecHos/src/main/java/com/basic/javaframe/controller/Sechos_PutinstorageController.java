package com.basic.javaframe.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import com.basic.javaframe.common.utils.DateUtil;
import com.basic.javaframe.common.utils.LayuiUtil;
import com.basic.javaframe.entity.Sechos_Procurement;
import com.basic.javaframe.entity.Sechos_Putinstorage;
import com.basic.javaframe.service.Sechos_ProcurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.basic.javaframe.common.customclass.PassToken;
import com.basic.javaframe.service.Sechos_PutinstorageService;
import com.basic.javaframe.common.utils.PageUtils;
import com.basic.javaframe.common.utils.Query;
import com.basic.javaframe.common.utils.R;


/**
 * 
 * 
 * @author 
 * @date 2019-09-24 09:43:42
 */
@Api(value = "材料入库管理")
@RestController
@CrossOrigin
@RequestMapping("sys/sechosputinstorage")
public class Sechos_PutinstorageController {
	@Autowired
	private Sechos_PutinstorageService sechosPutinstorageService;

	@Autowired
	private Sechos_ProcurementService sechosProcurementService;
	/**
	 * 列表数据
	 */
	@PassToken
	@ApiOperation(value="入库列表数据")
    @ResponseBody
	@RequestMapping(value="/listData",produces="application/json;charset=utf-8",method=RequestMethod.GET)
	public LayuiUtil listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<Sechos_Putinstorage> sechosPutinstorageList = sechosPutinstorageService.getList(query);
		int total = sechosPutinstorageService.getCount(query);
		PageUtils pageUtil = new PageUtils(sechosPutinstorageList, total, query.getLimit(), query.getPage());
		return LayuiUtil.data(pageUtil.getTotalCount(), pageUtil.getList());
	}

    /**
     * 新增
     **/
    @ApiOperation(value="新增材料入库")
    @ResponseBody
    @RequestMapping(value="/add",produces="application/json;charset=utf-8",method=RequestMethod.POST)
    public R add(@RequestBody Map<String, Object> params){
    	Sechos_Procurement sechosProcurement = new Sechos_Procurement();
    	Sechos_Putinstorage sechosPutinstorage = new Sechos_Putinstorage();
		String purchaseGuid = params.get("purchaseGuid").toString();
		String personGuid = params.get("personGuid").toString();
		Date createTime = DateUtil.changeDate(new Date());
		sechosPutinstorage.setPersonGuid(personGuid);
		sechosPutinstorage.setPurchaseGuid(purchaseGuid);
		sechosPutinstorage.setDrugInDate(createTime);

		sechosPutinstorageService.insertStorage(purchaseGuid);
		sechosPutinstorageService.insertInDate(sechosPutinstorage);
		sechosProcurement.setRowGuid(purchaseGuid);
		sechosProcurement.setPurchaseStatus(4);
		sechosProcurement.setInboundDate(createTime);
		sechosProcurementService.update(sechosProcurement);
        return R.ok();  
    }

	/**
	 * 修改
	 */
	@ApiOperation(value="")
    @ResponseBody
	@RequestMapping(value="/update", produces = "application/json; charset=utf-8", method=RequestMethod.POST)
	public R update(@RequestBody Sechos_Putinstorage sechosPutinstorage){
		sechosPutinstorageService.update(sechosPutinstorage);
		return R.ok();
	}

	/**
	 * 删除
	 */
    @ApiOperation(value="")
	@ResponseBody
	@RequestMapping(value="/delete",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R delete(@RequestBody String[] rowGuids){
		sechosPutinstorageService.deleteBatch(rowGuids);
		return R.ok();
	}

	/**
	 * 获取有将近过期材料的单号
	 */
	@ApiOperation(value="获取有将近过期材料的单号")
	@ResponseBody
	@RequestMapping(value="/getStorageOverdue",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R getStorageOverdue(){
		List<String> stringList = sechosPutinstorageService.getStorageOverdue();
		return R.ok().put("data",stringList);
	}
}
