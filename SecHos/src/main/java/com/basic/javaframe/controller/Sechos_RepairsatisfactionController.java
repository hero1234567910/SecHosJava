package com.basic.javaframe.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import com.basic.javaframe.common.utils.DateUtil;
import com.basic.javaframe.common.utils.LayuiUtil;
import com.basic.javaframe.entity.Sechos_Repair;
import com.basic.javaframe.entity.Sechos_Repairsatisfaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.basic.javaframe.common.customclass.PassToken;
import com.basic.javaframe.service.Sechos_RepairsatisfactionService;
import com.basic.javaframe.common.utils.PageUtils;
import com.basic.javaframe.common.utils.Query;
import com.basic.javaframe.common.utils.R;


/**
 * 
 * 
 * @author 
 * @date 2019-09-05 16:14:01
 */
@Api(value = "报修评价列表")
@RestController
@CrossOrigin
@RequestMapping("sys/sechosrepairsatisfaction")
public class Sechos_RepairsatisfactionController {
	@Autowired
	private Sechos_RepairsatisfactionService sechosRepairsatisfactionService;
	
	/**
	 * 列表数据
	 */
	@PassToken
	@ApiOperation(value="报修评价列表数据")
    @ResponseBody
	@RequestMapping(value="/listData",produces="application/json;charset=utf-8",method=RequestMethod.GET)
	public LayuiUtil listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<Sechos_Repairsatisfaction> sechosRepairsatisfactionList = sechosRepairsatisfactionService.getList(query);
		int total = sechosRepairsatisfactionService.getCount(query);
		PageUtils pageUtil = new PageUtils(sechosRepairsatisfactionList, total, query.getLimit(), query.getPage());
		return LayuiUtil.data(pageUtil.getTotalCount(), pageUtil.getList());
	}

    /**
     * 新增
     **/
    @ApiOperation(value="")
    @ResponseBody
    @RequestMapping(value="/add",produces="application/json;charset=utf-8",method=RequestMethod.POST)
    public R add(@RequestBody Sechos_Repairsatisfaction sechosRepairsatisfaction){
    	//如果排序号为空，则自动转为0
    	if (sechosRepairsatisfaction.getSortSq() == null) {
			sechosRepairsatisfaction.setSortSq(0);
		}
    	//生成uuid作为rowguid
        String uuid = java.util.UUID.randomUUID().toString();
		sechosRepairsatisfaction.setRowGuid(uuid);
		sechosRepairsatisfaction.setDelFlag(0);
		Date createTime = DateUtil.changeDate(new Date());
		sechosRepairsatisfaction.setCreateTime(createTime);
		sechosRepairsatisfactionService.save(sechosRepairsatisfaction);
        return R.ok();  
    }

	/**
	 * 修改
	 */
	@ApiOperation(value="")
    @ResponseBody
	@RequestMapping(value="/update", produces = "application/json; charset=utf-8", method=RequestMethod.PUT)
	public R update(@RequestBody Sechos_Repairsatisfaction sechosRepairsatisfaction){
		sechosRepairsatisfactionService.update(sechosRepairsatisfaction);
		return R.ok();
	}

	/**
	 * 删除
	 */
    @ApiOperation(value="删除评价")
	@ResponseBody
	@RequestMapping(value="/delete",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R delete(@RequestBody String[] rowGuids){
		sechosRepairsatisfactionService.deleteBatch(rowGuids);
		return R.ok();
	}

	@ApiOperation(value="通过rowGuid获取一条记录")
	@ResponseBody
	@RequestMapping(value="/getDetailByGuid",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R getDetailByGuid(@RequestBody String rowGuid){
		Sechos_Repairsatisfaction sechosRepairsatisfaction = sechosRepairsatisfactionService.getDetailByGuid(rowGuid);
		return R.ok().put("data",sechosRepairsatisfaction);
	}
	
}
