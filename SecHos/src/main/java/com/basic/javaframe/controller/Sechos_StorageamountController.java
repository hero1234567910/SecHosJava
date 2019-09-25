package com.basic.javaframe.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import com.basic.javaframe.common.utils.DateUtil;
import com.basic.javaframe.common.utils.LayuiUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.basic.javaframe.common.customclass.PassToken;
import com.basic.javaframe.entity.Sechos_Storageamount;
import com.basic.javaframe.service.Sechos_StorageamountService;
import com.basic.javaframe.common.utils.PageUtils;
import com.basic.javaframe.common.utils.Query;
import com.basic.javaframe.common.utils.R;


/**
 * 
 * 
 * @author 
 * @date 2019-09-24 16:23:08
 */
@Api(value = "材料仓库数量管理")
@RestController
@CrossOrigin
@RequestMapping("sys/sechosstorageamount")
public class Sechos_StorageamountController {
	@Autowired
	private Sechos_StorageamountService sechosStorageamountService;
	
	/**
	 * 列表数据
	 */
	@PassToken
	@ApiOperation(value="材料仓库数量列表")
    @ResponseBody
	@RequestMapping(value="/listData",produces="application/json;charset=utf-8",method=RequestMethod.GET)
	public LayuiUtil listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<Sechos_Storageamount> sechosStorageamountList = sechosStorageamountService.getList(query);
		int total = sechosStorageamountService.getCount(query);
		PageUtils pageUtil = new PageUtils(sechosStorageamountList, total, query.getLimit(), query.getPage());
		return LayuiUtil.data(pageUtil.getTotalCount(), pageUtil.getList());
	}

    /**
     * 新增
     **/
    @ApiOperation(value="")
    @ResponseBody
    @RequestMapping(value="/add",produces="application/json;charset=utf-8",method=RequestMethod.POST)
    public R add(@RequestBody Sechos_Storageamount sechosStorageamount){
    	//如果排序号为空，则自动转为0
    	if (sechosStorageamount.getSortSq() == null) {
			sechosStorageamount.setSortSq(0);
		}
    	//生成uuid作为rowguid
        String uuid = java.util.UUID.randomUUID().toString();
		sechosStorageamount.setRowGuid(uuid);
		Date createTime = DateUtil.changeDate(new Date());
		sechosStorageamount.setCreateTime(createTime);
		sechosStorageamountService.save(sechosStorageamount);
        return R.ok();  
    }

	/**
	 * 修改
	 */
	@ApiOperation(value="")
    @ResponseBody
	@RequestMapping(value="/update", produces = "application/json; charset=utf-8", method=RequestMethod.PUT)
	public R update(@RequestBody Sechos_Storageamount sechosStorageamount){
		sechosStorageamountService.update(sechosStorageamount);
		return R.ok();
	}

	/**
	 * 删除
	 */
    @ApiOperation(value="")
	@ResponseBody
	@RequestMapping(value="/delete",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R delete(@RequestBody String[] rowGuids){
		sechosStorageamountService.deleteBatch(rowGuids);
		return R.ok();
	}

	/**
	 * 根据Guid获取总数
	 */
	@ApiOperation(value="根据Guid获取总数")
	@ResponseBody
	@RequestMapping(value="/getSumByGuid",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R getSumByGuid(@RequestBody String drugGuid){
		Integer num = sechosStorageamountService.getSumByGuid(drugGuid);
		return R.ok().put("data",num);
	}
	
}
