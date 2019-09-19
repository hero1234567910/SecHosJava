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
import com.basic.javaframe.entity.SechosDrug;
import com.basic.javaframe.service.SechosDrugService;
import com.basic.javaframe.common.utils.PageUtils;
import com.basic.javaframe.common.utils.Query;
import com.basic.javaframe.common.utils.R;


/**
 * 
 * 
 * @author 
 * @date 2019-09-18 14:20:57
 */
@Api(value = "")
@RestController
@CrossOrigin
@RequestMapping("sechosdrug")
public class SechosDrugController {
	@Autowired
	private SechosDrugService sechosDrugService;
	
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
		List<SechosDrug> sechosDrugList = sechosDrugService.getList(query);
		int total = sechosDrugService.getCount(query);
		PageUtils pageUtil = new PageUtils(sechosDrugList, total, query.getLimit(), query.getPage());
		return LayuiUtil.data(pageUtil.getTotalCount(), pageUtil.getList());
	}

    /**
     * 新增
     **/
    @ApiOperation(value="")
    @ResponseBody
    @RequestMapping(value="/add",produces="application/json;charset=utf-8",method=RequestMethod.POST)
    public R add(@RequestBody SechosDrug sechosDrug){
    	//如果排序号为空，则自动转为0
    	if (sechosDrug.getSortSq() == null) {
			sechosDrug.setSortSq(0);
		}
    	//生成uuid作为rowguid
        String uuid = java.util.UUID.randomUUID().toString();
		sechosDrug.setRowGuid(uuid);
		Date createTime = DateUtil.changeDate(new Date());
		sechosDrug.setCreateTime(createTime);
		sechosDrugService.save(sechosDrug);
        return R.ok();  
    }

	/**
	 * 修改
	 */
	@ApiOperation(value="")
    @ResponseBody
	@RequestMapping(value="/update", produces = "application/json; charset=utf-8", method=RequestMethod.PUT)
	public R update(@RequestBody SechosDrug sechosDrug){
		sechosDrugService.update(sechosDrug);
		return R.ok();
	}

	/**
	 * 删除
	 */
    @ApiOperation(value="")
	@ResponseBody
	@RequestMapping(value="/delete",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R delete(@RequestBody String[] rowGuids){
		sechosDrugService.deleteBatch(rowGuids);
		return R.ok();
	}
	
}
