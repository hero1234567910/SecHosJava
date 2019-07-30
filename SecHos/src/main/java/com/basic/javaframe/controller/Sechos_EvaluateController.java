package com.basic.javaframe.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import com.basic.javaframe.common.utils.DateUtil;
import com.basic.javaframe.common.utils.LayuiUtil;
import com.basic.javaframe.entity.Sechos_Evaluate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.basic.javaframe.common.customclass.PassToken;
import com.basic.javaframe.service.Sechos_EvaluateService;
import com.basic.javaframe.common.utils.PageUtils;
import com.basic.javaframe.common.utils.Query;
import com.basic.javaframe.common.utils.R;


/**
 * 
 * 
 * @author wzl
 * @date 2019-07-30 14:29:09
 */
@Api(value = "住院患者评价")
@RestController
@CrossOrigin
@RequestMapping("sys/sechosevaluate")
public class Sechos_EvaluateController {
	@Autowired
	private Sechos_EvaluateService sechosEvaluateService;
	
	/**
	 * 列表数据
	 */
	@PassToken
	@ApiOperation(value="病人评价列表")
    @ResponseBody
	@RequestMapping(value="/listData",produces="application/json;charset=utf-8",method=RequestMethod.GET)
	public LayuiUtil listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<Sechos_Evaluate> sechosEvaluateList = sechosEvaluateService.getList(query);
		int total = sechosEvaluateService.getCount(query);
		PageUtils pageUtil = new PageUtils(sechosEvaluateList, total, query.getLimit(), query.getPage());
		return LayuiUtil.data(pageUtil.getTotalCount(), pageUtil.getList());
	}

    /**
     * 新增
     **/
    @ApiOperation(value="病人新增评价")
    @ResponseBody
    @RequestMapping(value="/add",produces="application/json;charset=utf-8",method=RequestMethod.POST)
    public R add(@RequestBody Sechos_Evaluate sechosEvaluate){
    	//如果排序号为空，则自动转为0
    	if (sechosEvaluate.getSortSq() == null) {
			sechosEvaluate.setSortSq(0);
		}
    	//生成uuid作为rowguid
        String uuid = java.util.UUID.randomUUID().toString();
		sechosEvaluate.setRowGuid(uuid);
		Date createTime = DateUtil.changeDate(new Date());
		sechosEvaluate.setCreateTime(createTime);
		sechosEvaluateService.save(sechosEvaluate);
        return R.ok();  
    }

	/**
	 * 修改
	 */
	@ApiOperation(value="病人修改评价")
    @ResponseBody
	@RequestMapping(value="/update", produces = "application/json; charset=utf-8", method=RequestMethod.PUT)
	public R update(@RequestBody Sechos_Evaluate sechosEvaluate){
		sechosEvaluateService.update(sechosEvaluate);
		return R.ok();
	}

	/**
	 * 删除
	 */
    @ApiOperation(value="删除评价")
	@ResponseBody
	@RequestMapping(value="/delete",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R delete(@RequestBody String[] rowGuids){
		sechosEvaluateService.deleteBatch(rowGuids);
		return R.ok();
	}
	
}
