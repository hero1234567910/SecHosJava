package com.basic.javaframe.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import com.basic.javaframe.common.utils.DateUtil;
import com.basic.javaframe.common.utils.LayuiUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import javax.servlet.http.HttpServletRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.basic.javaframe.common.customclass.PassToken;
import com.basic.javaframe.entity.HosAddress;
import com.basic.javaframe.service.HosAddressService;
import com.basic.javaframe.common.utils.PageUtils;
import com.basic.javaframe.common.utils.Query;
import com.basic.javaframe.common.utils.R;


/**
 * 
 * 
 * @author wzl
 * @date 2019-05-10 09:20:48
 */
@Api(value = "用户地址信息")
@RestController
@CrossOrigin
@RequestMapping("sys/hosaddress")
public class HosAddressController {
	@Autowired
	private HosAddressService hosAddressService;
	
	/**
	 * 列表数据
	 */
	@PassToken
	@ApiOperation(value="地址列表")
    @ResponseBody
	@RequestMapping(value="/listData",produces="application/json;charset=utf-8",method=RequestMethod.GET)
	public LayuiUtil listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<HosAddress> hosAddressList = hosAddressService.getList(query);
		int total = hosAddressService.getCount(query);
		PageUtils pageUtil = new PageUtils(hosAddressList, total, query.getLimit(), query.getPage());
		return LayuiUtil.data(pageUtil.getTotalCount(), pageUtil.getList());
	}

    /**
     * 新增
     **/
    @ApiOperation(value="新增地址")
    @ResponseBody
    @RequestMapping(value="/add",produces="application/json;charset=utf-8",method=RequestMethod.POST)
    public R add(@RequestBody HosAddress hosAddress){
    	//如果排序号为空，则自动转为0
    	if (hosAddress.getSortSq() == null) {
			hosAddress.setSortSq(0);
		}
    	//生成uuid作为rowguid
        String uuid = java.util.UUID.randomUUID().toString();
		hosAddress.setRowGuid(uuid);
		hosAddress.setDelFlag(0);
		Date createTime = DateUtil.changeDate(new Date());
		hosAddress.setCreateTime(createTime);
		hosAddressService.save(hosAddress);
        return R.ok();  
    }

	/**
	 * 修改
	 */
	@ApiOperation(value="修改地址")
    @ResponseBody
	@RequestMapping(value="/update", produces = "application/json; charset=utf-8", method=RequestMethod.PUT)
	public R update(@RequestBody HosAddress hosAddress){
		hosAddressService.update(hosAddress);
		return R.ok();
	}

	/**
	 * 删除
	 */
    @ApiOperation(value="删除地址")
	@ResponseBody
	@RequestMapping(value="/delete",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R delete(@RequestBody String[] rowGuids){
		hosAddressService.deleteBatch(rowGuids);
		return R.ok();
	}

	/**
	 * 通过用户Guid查询用户地址信息
	 * <p>Title: getAddressListByUserGuid</p>
	 * <p>Description: 用户</p>
	 * @param hosUserGuid
	 * @return
	 */
	@ApiOperation(value="通过用户Guid查询用户地址信息")
	@ResponseBody
	@RequestMapping(value="/getAddressListByUserGuid",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R getAddressListByUserGuid(@RequestBody String hosUserGuid){
		List<HosAddress> hosAddressList= hosAddressService.getAddressListByUserGuid(hosUserGuid);
		return R.ok().put("data",hosAddressList);
	}
}
