package com.basic.javaframe.controller;

import java.util.Date;
import java.util.HashMap;
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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.basic.javaframe.common.customclass.PassToken;
import com.basic.javaframe.entity.Frame_CodeValue;
import com.basic.javaframe.entity.InformationCategory;
import com.basic.javaframe.service.InformationCategoryService;
import com.basic.javaframe.common.utils.PageUtils;
import com.basic.javaframe.common.utils.Query;
import com.basic.javaframe.common.utils.R;
import com.basic.javaframe.common.utils.StringUtil;
import com.basic.javaframe.common.utils.UUID;


/**
 * 
 * 
 * @author 
 * @date 2019-04-09 11:30:38
 */
@Api(value = "/栏目")
@RestController
@CrossOrigin
@RequestMapping("sys/informationcategory")
public class InformationCategoryController {
	@Autowired
	private InformationCategoryService informationCategoryService;
	
	/**
	 * 列表数据
	 */
	@PassToken
	@ApiOperation(value="栏目列表数据")
    @ResponseBody
	@RequestMapping(value="/listData",produces="application/json;charset=utf-8",method=RequestMethod.GET)
	public LayuiUtil listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<InformationCategory> informationCategoryList = informationCategoryService.getList(query);
		int total = informationCategoryService.getCount(query);
		PageUtils pageUtil = new PageUtils(informationCategoryList, total, query.getLimit(), query.getPage());
		return LayuiUtil.data(pageUtil.getTotalCount(), pageUtil.getList());
	}

    /**
     * 新增
     **/
    @ApiOperation(value="栏目新增")
    @ResponseBody
    @RequestMapping(value="/add",produces="application/json;charset=utf-8",method=RequestMethod.POST)
    public R add(@RequestBody InformationCategory informationCategory){
    	//如果排序号为空，则自动转为0
    	if (informationCategory.getSortSq() == null) {
			informationCategory.setSortSq(0);
		}
    	//生成uuid作为rowguid
        String uuid = java.util.UUID.randomUUID().toString();
		informationCategory.setRowGuid(uuid);
		Date createTime = DateUtil.changeDate(new Date());
		informationCategory.setCreateTime(createTime);
		//生成编号
		String code = UUID.getCode(8);
		informationCategory.setCategoryCode(code);
		if (StringUtil.isBlank(informationCategory.getPcategoryCode())) {
			informationCategory.setPcategoryCode("0");
		}
		informationCategoryService.save(informationCategory);
        return R.ok();  
    }


	/**
	 * 修改
	 */
	@ApiOperation(value="栏目修改")
    @ResponseBody
	@RequestMapping(value="/update", produces = "application/json; charset=utf-8", method=RequestMethod.PUT)
	public R update(@RequestBody InformationCategory informationCategory){
		informationCategoryService.update(informationCategory);
		return R.ok();
	}

	/**
	 * 删除
	 */
    @ApiOperation(value="栏目删除")
	@ResponseBody
	@RequestMapping(value="/delete",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R delete(@RequestBody String[] rowGuids){
		informationCategoryService.deleteBatch(rowGuids);
		return R.ok();
	}	
	
    /**
     * 查询栏目树
     * <p>Title: getCategoryTrees</p>  
     * <p>Description: </p>
     * @author hero  
     * @return
     */
    @ApiOperation(value="查询栏目树")
	@ResponseBody
	@RequestMapping(value="/getCategoryTrees",produces="application/json;charset=utf-8",method=RequestMethod.POST)
    public R getdeptTrees(){
    	JSONArray trees = informationCategoryService.findTrees();
    	return R.ok().put("data", trees);
    }
    
    /**
     * 获取所有栏目（下拉框准备数据）
     * <p>Title: getAllCategory</p>  
     * <p>Description: </p>
     * @author hero  
     * @return
     */
    @ApiOperation(value="获取所有栏目")
    @ResponseBody
    @RequestMapping(value="/getAllCategory",produces="application/json;charset=utf-8",method=RequestMethod.POST)
    public R getAllCategory(){
		List<InformationCategory> informationCategoryList = informationCategoryService.getAllList();
		if (informationCategoryList == null) {
			return R.ok().put("data", "");
		}
		JSONArray array = new JSONArray();
    	for (InformationCategory v : informationCategoryList) {
    		JSONObject obj = new JSONObject();
        	obj.put("name", v.getCategoryName());
        	obj.put("value", v.getRowGuid());
        	obj.put("selected", "");
        	obj.put("disabled", "");
        	obj.put("is_needAudit", v.getIsNeedAudit());
        	array.add(obj);
		}
		return R.ok().put("data", array);
    }
    
    /**
     * 获取对应栏目
     * <p>Title: getCategoryByGuid</p>  
     * <p>Description: </p>
     * @author hero  
     * @return
     */
    @ApiOperation(value="获取对应栏目")
    @ResponseBody
    @RequestMapping(value="/getCategoryByGuid",produces="application/json;charset=utf-8",method=RequestMethod.GET)
    public R getCategoryByGuid(@RequestParam String categoryGuid){
		List<InformationCategory> informationCategoryList = informationCategoryService.getCategoryByGuid(categoryGuid);
		if (informationCategoryList == null) {
			return R.ok().put("data", "");
		}
		JSONArray array = new JSONArray();
    	for (InformationCategory v : informationCategoryList) {
    		JSONObject obj = new JSONObject();
        	obj.put("name", v.getCategoryName());
        	obj.put("value", v.getRowGuid());
        	obj.put("selected", "");
        	obj.put("disabled", "");
        	obj.put("is_needAudit", v.getIsNeedAudit());
        	array.add(obj);
		}
		return R.ok().put("data", array);
    }
    
}
