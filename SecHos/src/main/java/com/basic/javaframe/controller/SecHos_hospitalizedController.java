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
import com.basic.javaframe.entity.SecHos_hospitalized;
import com.basic.javaframe.service.SecHos_hospitalizedService;
import com.basic.javaframe.common.utils.PageUtils;
import com.basic.javaframe.common.utils.Query;
import com.basic.javaframe.common.utils.R;


/**
 * 住院患者
* <p>Title: SechosHospitalizedController</p>  
* <p>Description: </p>  
* @author hero
 */
@Api(value = "住院患者")
@RestController
@CrossOrigin
@RequestMapping("sys/sechoshospitalized")
public class SecHos_hospitalizedController {
	@Autowired
	private SecHos_hospitalizedService sechosHospitalizedService;
	
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
		List<SecHos_hospitalized> sechosHospitalizedList = sechosHospitalizedService.getList(query);
		int total = sechosHospitalizedService.getCount(query);
		PageUtils pageUtil = new PageUtils(sechosHospitalizedList, total, query.getLimit(), query.getPage());
		return LayuiUtil.data(pageUtil.getTotalCount(), pageUtil.getList());
	}

    /**
     * 新增
     **/
    @ApiOperation(value="")
    @ResponseBody
    @RequestMapping(value="/add",produces="application/json;charset=utf-8",method=RequestMethod.POST)
    public R add(@RequestBody SecHos_hospitalized sechosHospitalized){
    	//如果排序号为空，则自动转为0
    	if (sechosHospitalized.getSortSq() == null) {
			sechosHospitalized.setSortSq(0);
		}
    	//生成uuid作为rowguid
        String uuid = java.util.UUID.randomUUID().toString();
		sechosHospitalized.setRowGuid(uuid);
		Date createTime = DateUtil.changeDate(new Date());
		sechosHospitalized.setCreateTime(createTime);
		sechosHospitalizedService.save(sechosHospitalized);
        return R.ok();  
    }

	/**
	 * 修改
	 */
	@ApiOperation(value="")
    @ResponseBody
	@RequestMapping(value="/update", produces = "application/json; charset=utf-8", method=RequestMethod.PUT)
	public R update(@RequestBody SecHos_hospitalized sechosHospitalized){
		sechosHospitalizedService.update(sechosHospitalized);
		return R.ok();
	}

	/**
	 * 删除
	 */
    @ApiOperation(value="")
	@ResponseBody
	@RequestMapping(value="/delete",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R delete(@RequestBody String[] rowGuids){
		sechosHospitalizedService.deleteBatch(rowGuids);
		return R.ok();
	}
	
}
