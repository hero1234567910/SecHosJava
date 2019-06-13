package com.basic.javaframe.wxcontroller;

import com.basic.javaframe.common.customclass.PassToken;
import com.basic.javaframe.common.utils.*;
import com.basic.javaframe.entity.HosOrderitem;
import com.basic.javaframe.service.HosOrderitemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 
 * 
 * @author my
 * @date 2019-04-29 13:49:14
 */
@Api(value = "订单商品管理")
@RestController
@CrossOrigin
@RequestMapping("wx/sys/hosorderitem")
public class WxHosOrderitemController {
	private final static Logger logger = LoggerFactory.getLogger( WxHosOrderitemController.class);
	@Autowired
	private HosOrderitemService hosOrderitemService;
	
	/**
	 * 列表数据
	 */
	@PassToken
	@ApiOperation(value="订单商品列表")
    @ResponseBody
	@RequestMapping(value="/listData",produces="application/json;charset=utf-8",method=RequestMethod.GET)
	public LayuiUtil listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<HosOrderitem> hosOrderitemList = hosOrderitemService.getList(query);
		int total = hosOrderitemService.getCount(query);
		PageUtils pageUtil = new PageUtils(hosOrderitemList, total, query.getLimit(), query.getPage());
		return LayuiUtil.data(pageUtil.getTotalCount(), pageUtil.getList());
	}

    /**
     * 新增
     **/
    @ApiOperation(value="添加订单商品列表")
    @ResponseBody
    @RequestMapping(value="/add",produces="application/json;charset=utf-8",method=RequestMethod.POST)
    public R add(@RequestBody HosOrderitem hosOrderitem){
    	//如果排序号为空，则自动转为0
    	if (hosOrderitem.getSortSq() == null) {
			hosOrderitem.setSortSq(0);
		}
    	//生成uuid作为rowguid
        String uuid = java.util.UUID.randomUUID().toString();
		hosOrderitem.setRowGuid(uuid);
		hosOrderitem.setDelFlag(0);
		Date createTime = DateUtil.changeDate(new Date());
		hosOrderitem.setCreateTime(createTime);
		hosOrderitemService.save(hosOrderitem);
        return R.ok();  
    }

	/**
	 * 修改
	 */
	@ApiOperation(value="修改订单商品列表")
    @ResponseBody
	@RequestMapping(value="/update", produces = "application/json; charset=utf-8", method=RequestMethod.PUT)
	public R update(@RequestBody HosOrderitem hosOrderitem){
		hosOrderitemService.update(hosOrderitem);
		return R.ok();
	}

	/**
	 * 删除
	 */
    @ApiOperation(value="删除订单商品列表")
	@ResponseBody
	@RequestMapping(value="/delete",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R delete(@RequestBody String[] rowGuids){
		hosOrderitemService.deleteBatch(rowGuids);
		return R.ok();
	}
	
}
