package com.basic.javaframe.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import com.basic.javaframe.common.utils.DateUtil;
import com.basic.javaframe.common.utils.LayuiUtil;
import com.basic.javaframe.service.Frame_AttachService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.basic.javaframe.common.customclass.PassToken;
import com.basic.javaframe.entity.Sechos_Repair;
import com.basic.javaframe.service.Sechos_RepairService;
import com.basic.javaframe.common.utils.PageUtils;
import com.basic.javaframe.common.utils.Query;
import com.basic.javaframe.common.utils.R;


/**
 * 
 * 
 * @author 
 * @date 2019-08-28 10:11:37
 */
@Api(value = "设备报修管理")
@RestController
@CrossOrigin
@RequestMapping("sys/sechosrepair")
public class Sechos_RepairController {

	private final static Logger logger = LoggerFactory.getLogger(Sechos_RepairController.class);
	@Autowired
	private Sechos_RepairService sechosRepairService;

	@Autowired
	private Frame_AttachService attachService;
	/**
	 * 列表数据
	 */
	@PassToken
	@ApiOperation(value="获取设备报修表")
    @ResponseBody
	@RequestMapping(value="/listData",produces="application/json;charset=utf-8",method=RequestMethod.GET)
	public LayuiUtil listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<Sechos_Repair> sechosRepairList = sechosRepairService.getList(query);
		int total = sechosRepairService.getCount(query);
		PageUtils pageUtil = new PageUtils(sechosRepairList, total, query.getLimit(), query.getPage());
		return LayuiUtil.data(pageUtil.getTotalCount(), pageUtil.getList());
	}

    /**
     * 新增
     **/
    @ApiOperation(value="新增设备报修信息")
    @ResponseBody
    @RequestMapping(value="/add",produces="application/json;charset=utf-8",method=RequestMethod.POST)
    public R add(@RequestBody Sechos_Repair sechosRepair){
    	//如果排序号为空，则自动转为0
    	if (sechosRepair.getSortSq() == null) {
			sechosRepair.setSortSq(0);
		}
    	//生成uuid作为rowguid
        String uuid = java.util.UUID.randomUUID().toString();
		sechosRepair.setRowGuid(uuid);
		sechosRepair.setDelFlag(0);
		Date createTime = DateUtil.changeDate(new Date());
		sechosRepair.setCreateTime(createTime);
		sechosRepair.setRepairStatus(0);
		String imgGuid = java.util.UUID.randomUUID().toString();
		sechosRepair.setPicGuid(imgGuid);
		sechosRepairService.save(sechosRepair);

		String[] guids = {sechosRepair.getUploadImgGuid()};
		attachService.updateAttach(imgGuid,guids);
        return R.ok();  
    }

	/**
	 * 修改
	 */
	@ApiOperation(value="修改设备报修信息")
    @ResponseBody
	@RequestMapping(value="/update", produces = "application/json; charset=utf-8", method=RequestMethod.POST)
	public R update(@RequestBody Sechos_Repair sechosRepair){
		sechosRepairService.update(sechosRepair);
		return R.ok();
	}

	/**
	 * 删除
	 */
    @ApiOperation(value="删除设备报修信息")
	@ResponseBody
	@RequestMapping(value="/delete",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R delete(@RequestBody String[] rowGuids){
		sechosRepairService.deleteBatch(rowGuids);
		return R.ok();
	}

	@ApiOperation(value="通过rowGuid获取一条记录")
	@ResponseBody
	@RequestMapping(value="/getDetailByGuid",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R getDetailByGuid(@RequestBody String rowGuid){
		Sechos_Repair sechosRepair = sechosRepairService.getDetailByGuid(rowGuid);
		return R.ok().put("data",sechosRepair);
	}

	@ApiOperation(value="通过rowGuid获取个人报修记录")
	@ResponseBody
	@RequestMapping(value="/getListByGuid",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R getListByGuid(@RequestBody String rowGuid){
		List<Sechos_Repair> sechosRepairList = sechosRepairService.getListByGuid(rowGuid);
		return R.ok().put("data",sechosRepairList);
	}
}
