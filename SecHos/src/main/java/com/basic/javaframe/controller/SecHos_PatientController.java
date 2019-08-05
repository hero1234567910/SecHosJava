package com.basic.javaframe.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import com.basic.javaframe.common.utils.DateUtil;
import com.basic.javaframe.common.utils.LayuiUtil;
import com.basic.javaframe.entity.SecHos_Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.basic.javaframe.common.customclass.PassToken;
import com.basic.javaframe.service.SecHos_PatientService;
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
public class SecHos_PatientController {
	@Autowired
	private SecHos_PatientService secHosPatientService;
	
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
		List<SecHos_Patient> secHosPatientList = secHosPatientService.getList(query);
		int total = secHosPatientService.getCount(query);
		PageUtils pageUtil = new PageUtils(secHosPatientList, total, query.getLimit(), query.getPage());
		return LayuiUtil.data(pageUtil.getTotalCount(), pageUtil.getList());
	}

    /**
     * 新增
     **/
    @ApiOperation(value="添加患者")
    @ResponseBody
    @RequestMapping(value="/add",produces="application/json;charset=utf-8",method=RequestMethod.POST)
    public R add(@RequestBody SecHos_Patient secHosPatient){
    	//如果排序号为空，则自动转为0
    	if (secHosPatient.getSortSq() == null) {
			secHosPatient.setSortSq(0);
		}
    	//生成uuid作为rowguid
        String uuid = java.util.UUID.randomUUID().toString();
		secHosPatient.setRowGuid(uuid);
		Date createTime = DateUtil.changeDate(new Date());
		secHosPatient.setCreateTime(createTime);
		secHosPatientService.save(secHosPatient);
        return R.ok();  
    }

	/**
	 * 修改
	 */
	@ApiOperation(value="修改患者")
    @ResponseBody
	@RequestMapping(value="/update", produces = "application/json; charset=utf-8", method=RequestMethod.POST)
	public R update(@RequestBody SecHos_Patient secHosPatient){
		secHosPatientService.update(secHosPatient);
		return R.ok();
	}

	/**
	 * 删除
	 */
    @ApiOperation(value="删除患者")
	@ResponseBody
	@RequestMapping(value="/delete",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R delete(@RequestBody String[] rowGuids){
		secHosPatientService.deleteBatch(rowGuids);
		return R.ok();
	}

	/**
	 * 查找患者
	 */
	@ApiOperation(value="查找患者")
	@ResponseBody
	@RequestMapping(value="/getPatientByGuid/{rowGuid}",produces="application/json;charset=utf-8",method=RequestMethod.GET)
	public R getPatientByGuid(@PathVariable("rowGuid") String rowGuid){
		SecHos_Patient secHosPatient = secHosPatientService.getPatientByGuid(rowGuid);
		return R.ok().put("data",secHosPatient);
	}
}
