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
import com.basic.javaframe.entity.Patient;
import com.basic.javaframe.service.PatientService;
import com.basic.javaframe.common.utils.PageUtils;
import com.basic.javaframe.common.utils.Query;
import com.basic.javaframe.common.utils.R;


/**
 * 
 * 
 * @author wzl
 * @date 2019-06-14 13:57:13
 */
@Api(value = "患者管理")
@RestController
@CrossOrigin
@RequestMapping("sys/patient")
public class PatientController {
	@Autowired
	private PatientService patientService;
	
	/**
	 * 列表数据
	 */
	@PassToken
	@ApiOperation(value="获取患者列表")
    @ResponseBody
	@RequestMapping(value="/listData",produces="application/json;charset=utf-8",method=RequestMethod.GET)
	public LayuiUtil listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<Patient> patientList = patientService.getList(query);
		int total = patientService.getCount(query);
		PageUtils pageUtil = new PageUtils(patientList, total, query.getLimit(), query.getPage());
		return LayuiUtil.data(pageUtil.getTotalCount(), pageUtil.getList());
	}

    /**
     * 新增
     **/
    @ApiOperation(value="添加患者")
    @ResponseBody
    @RequestMapping(value="/add",produces="application/json;charset=utf-8",method=RequestMethod.POST)
    public R add(@RequestBody Patient patient){
    	//如果排序号为空，则自动转为0
    	if (patient.getSortSq() == null) {
			patient.setSortSq(0);
		}
    	//生成uuid作为rowguid
        String uuid = java.util.UUID.randomUUID().toString();
		patient.setRowGuid(uuid);
		Date createTime = DateUtil.changeDate(new Date());
		patient.setCreateTime(createTime);
		patientService.save(patient);
        return R.ok();  
    }

	/**
	 * 修改
	 */
	@ApiOperation(value="修改患者")
    @ResponseBody
	@RequestMapping(value="/update", produces = "application/json; charset=utf-8", method=RequestMethod.PUT)
	public R update(@RequestBody Patient patient){
		patientService.update(patient);
		return R.ok();
	}

	/**
	 * 删除
	 */
    @ApiOperation(value="删除患者")
	@ResponseBody
	@RequestMapping(value="/delete",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R delete(@RequestBody String[] rowGuids){
		patientService.deleteBatch(rowGuids);
		return R.ok();
	}
	
}
