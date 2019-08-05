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
import com.basic.javaframe.entity.SecHos_Outpatient;
import com.basic.javaframe.service.SecHos_OutpatientService;
import com.basic.javaframe.common.utils.PageUtils;
import com.basic.javaframe.common.utils.Query;
import com.basic.javaframe.common.utils.R;


/**
 * 
 * 
 * @author 
 * @date 2019-06-18 08:46:50
 */
@Api(value = "")
@RestController
@CrossOrigin
@RequestMapping("sys/sechosoutpatient")
public class SecHos_OutpatientController {
	@Autowired
	private SecHos_OutpatientService sechosOutpatientService;
	
	/**
	 * 列表数据
	 */
	@PassToken
	@ApiOperation(value="")
    @ResponseBody
	@RequestMapping(value="/listData",produces="application/json;charset=utf-8",method=RequestMethod.GET)
	public LayuiUtil listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<SecHos_Outpatient> sechosOutpatientList = sechosOutpatientService.getList(query);
		int total = sechosOutpatientService.getCount(query);
		PageUtils pageUtil = new PageUtils(sechosOutpatientList, total, query.getLimit(), query.getPage());
		return LayuiUtil.data(pageUtil.getTotalCount(), pageUtil.getList());
	}

    /**
     * 新增
     **/
    @ApiOperation(value="")
    @ResponseBody
    @RequestMapping(value="/add",produces="application/json;charset=utf-8",method=RequestMethod.POST)
    public R add(@RequestBody SecHos_Outpatient sechosOutpatient){
    	//如果排序号为空，则自动转为0
    	if (sechosOutpatient.getSortSq() == null) {
			sechosOutpatient.setSortSq(0);
		}
    	//生成uuid作为rowguid
        String uuid = java.util.UUID.randomUUID().toString();
		sechosOutpatient.setRowGuid(uuid);
		Date createTime = DateUtil.changeDate(new Date());
		sechosOutpatient.setCreateTime(createTime);
		sechosOutpatientService.save(sechosOutpatient);
        return R.ok();  
    }

	/**
	 * 修改
	 */
	@ApiOperation(value="")
    @ResponseBody
	@RequestMapping(value="/update", produces = "application/json; charset=utf-8", method=RequestMethod.POST)
	public R update(@RequestBody SecHos_Outpatient sechosOutpatient){
		sechosOutpatientService.update(sechosOutpatient);
		return R.ok();
	}

	/**
	 * 删除
	 */
    @ApiOperation(value="")
	@ResponseBody
	@RequestMapping(value="/delete",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R delete(@RequestBody String[] rowGuids){
		sechosOutpatientService.deleteBatch(rowGuids);
		return R.ok();
	}
	
}
