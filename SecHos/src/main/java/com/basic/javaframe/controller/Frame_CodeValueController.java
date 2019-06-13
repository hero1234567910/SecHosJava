package com.basic.javaframe.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.basic.javaframe.common.customclass.PassToken;
import com.basic.javaframe.common.utils.*;
import com.basic.javaframe.entity.Frame_CodeValue;
import com.basic.javaframe.entity.Frame_Codes;
import com.basic.javaframe.service.Frame_CodeValueService;
import com.basic.javaframe.service.Frame_CodesService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@CrossOrigin
@Api(value = "代码值参数")
@RestController
@RequestMapping("sys/codeValue")
public class Frame_CodeValueController {
    private final static Logger logger = LoggerFactory.getLogger(Frame_CodeValueController.class);

    @Autowired
    private Frame_CodeValueService codeValueService;
    
    @Autowired
    private Frame_CodesService codesService;

    private Frame_CodeValue frame_codeValue;

    /**
     * 获取所有代码值
     * <p>Title: getCodeValue</p>
     * <p>Description: 代码值参数类别</p>
     *
     * @param params
     * @return
     * @author wzl
     */
    @PassToken
    @ApiOperation(value = "获取所有代码值")
    @ResponseBody
    @RequestMapping(value = "/getCodeValue", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    public LayuiUtil getCodeValue(@RequestParam Map<String, Object> params) {
        Query query = new Query(params);
        List<Frame_CodeValue> codeValueVList = codeValueService.getCodeValue(query);
        int total = codeValueService.getCount(query);
        PageUtils pageUtil = new PageUtils(codeValueVList, total, query.getLimit(), query.getPage());
        return LayuiUtil.data(pageUtil.getTotalCount(), pageUtil.getList());
    }


    /**
     * 获取关联代码值
     * <p>Title: getCodeValue</p>
     * <p>Description: 代码值参数类别</p>
     *
     * @param params
     * @return
     * @author wzl
     */
    @ApiOperation(value = "获取关联代码值")
    @ResponseBody
    @RequestMapping(value = "/getCodesToValue", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    public LayuiUtil getCodesToValue(@RequestParam Map<String, Object> params) {
        Query query = new Query(params);
        List<Frame_CodeValue> codesToValueVList = codeValueService.getCodesToValue(query);
        int total = codeValueService.getCount(query);
        PageUtils pageUtil = new PageUtils(codesToValueVList, total, query.getLimit(), query.getPage());
        return LayuiUtil.data(pageUtil.getTotalCount(), pageUtil.getList());
    }

    /**
     * 新增代码值
     * <p>Title: insertCodeValue</p>
     * <p>Description: 新增代码值</p>
     *
     * @param params
     * @return
     * @author wzl
     */
    @ApiOperation(value = "新增代码值")
    @ResponseBody
    @RequestMapping(value = "/insertCodeValue", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public R insertCodeValue(@RequestBody Map<String, Object> params) {

        frame_codeValue = JSON.parseObject(JSON.toJSONString(params.get("field")), Frame_CodeValue.class);
        //生成uuid作为rowguid
        String uuid = UUID.randomUUID().toString();
        frame_codeValue.setRowGuid(uuid);
        System.out.println(params.get("rowGuid").toString());
        frame_codeValue.setCodeGuid(params.get("rowGuid").toString());
        codeValueService.insertCodeValue(frame_codeValue);
        return R.ok();
    }

    /**
     * 更新代码值
     * <p>Title: updateCodesBatch</p>
     * <p>Description: 更新代码值</p>
     *
     * @param frameCodeValue
     * @return
     * @author wzl
     */
    @PassToken
    @ApiOperation(value = "更新代码值参数")
    @ResponseBody
    @RequestMapping(value = "/updateCodeValue/{id}", produces = "application/json;charset=utf-8", method = RequestMethod.PUT)
    public R updateCodeValueBatch(@PathVariable("id") Integer id, @RequestBody Frame_CodeValue frameCodeValue) {
        frameCodeValue.setRowId(id);
        codeValueService.updateCodeValue(frameCodeValue);
        return R.ok();
    }

    /**
     * 删除代码值
     * <p>Title: deleteCodes</p>
     * <p>Description: 批量删除代码值</p>
     *
     * @param ids
     * @return
     * @author wzl
     */
    @ApiOperation(value = "删除多个代码值参数")
    @ResponseBody
    @RequestMapping(value = "/deleteCodeValue/{id}", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public R deleteCodeValue(@PathVariable("id") Integer[] ids) {
        codeValueService.deleteCodeValue(ids);
        return R.ok();
    }
    
    /**
     * 获取对应代码项的值
     * <p>Title: getCodeValue</p>  
     * <p>Description: </p>
     * @author hero  
     * @return
     */
    @ApiOperation(value="获取对应代码项的值")
    @ResponseBody
    @RequestMapping(value="/getCodeByValue",produces="application/json;charset=utf-8",method=RequestMethod.GET)
    public R getCodeByValue(@RequestParam String codeName,String val){
    	List<Frame_Codes> codes = codesService.getCodesByName(codeName);
    	if (codes != null && codes.size() > 1) {
			return R.error("该代码项存在多个相同的值");
		}
    	List<Frame_CodeValue> value = codeValueService.getCodeByValue(codes.get(0).getRowGuid());
    	
    	if (value == null || value.size() == 0) {
			return R.ok().put("data", "");
		}
    	JSONArray array = new JSONArray();
    	for (Frame_CodeValue v : value) {
    		JSONObject obj = new JSONObject();
    		if (val != null && val.equals(v.getItemValue())) {
    			obj.put("name", v.getItemText());
    			obj.put("selected", "selected");
    			obj.put("value", v.getItemValue());
    			obj.put("disabled", "");
			}else{
				obj.put("name", v.getItemText());
	        	obj.put("value", v.getItemValue());
	        	obj.put("selected", "");
	        	obj.put("disabled", "");
			}
        	array.add(obj);
		}
    	
    	return R.ok().put("data", array);
    }
    
    @ApiOperation(value="获取对应代码项的值")
    @ResponseBody
    @RequestMapping(value="/getCodeValueToMap",produces="application/json;charset=utf-8",method=RequestMethod.GET)
    public R getCodeValueToMap(@RequestParam String codeName){
    	List<Frame_Codes> codes = codesService.getCodesByName(codeName);
    	if (codes != null && codes.size() > 1) {
			return R.error("该代码项存在多个相同的值");
		}
    	List<Frame_CodeValue> codeValue = codeValueService.getCodeValueByName(codes.get(0).getRowGuid());
    	Map<String, Object> map = new HashMap<>();
    	if (codeValue == null || codeValue.size() == 0) {
			return R.error("未找到任何代码项");
		}
    	for (int i = 0; i < codeValue.size(); i++) {
			map.put(codeValue.get(i).getItemText(), codeValue.get(i).getItemValue());
		}
    	return R.ok().put("data", map);
    }
    
    @PassToken
    @RequestMapping(value="/ceshi",method=RequestMethod.POST)
    @ApiOperation(value="测试")
    public void ceshi(){
    	System.out.println(codeValueService.getCodeByNameAndValue("订单状态", "1"));
    }

}
