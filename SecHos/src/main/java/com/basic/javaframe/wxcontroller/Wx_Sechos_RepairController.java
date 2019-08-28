package com.basic.javaframe.wxcontroller;

import com.basic.javaframe.common.customclass.PassToken;
import com.basic.javaframe.common.utils.*;
import com.basic.javaframe.entity.Sechos_Repair;
import com.basic.javaframe.service.Sechos_RepairService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 
 * 
 * @author wzl
 * @date 2019-08-28 10:11:37
 */
@Api(value = "设备报修管理")
@RestController
@CrossOrigin
@RequestMapping("wx/sys/sechosrepair")
public class Wx_Sechos_RepairController {
	@Autowired
	private Sechos_RepairService sechosRepairService;
	
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
		sechosRepairService.save(sechosRepair);
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
}
