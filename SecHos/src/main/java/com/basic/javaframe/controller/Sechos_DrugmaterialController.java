package com.basic.javaframe.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import com.basic.javaframe.common.utils.DateUtil;
import com.basic.javaframe.common.utils.LayuiUtil;
import com.basic.javaframe.entity.Sechos_Drugmaterial;
import com.basic.javaframe.entity.Sechos_Repair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.basic.javaframe.common.customclass.PassToken;
import com.basic.javaframe.service.Sechos_DrugmaterialService;
import com.basic.javaframe.common.utils.PageUtils;
import com.basic.javaframe.common.utils.Query;
import com.basic.javaframe.common.utils.R;


/**
 * 
 * 
 * @author 
 * @date 2019-09-19 10:22:39
 */
@Api(value = "药品材料管理")
@RestController
@CrossOrigin
@RequestMapping("sys/sechosdrugmaterial")
public class Sechos_DrugmaterialController {
	@Autowired
	private Sechos_DrugmaterialService sechosDrugmaterialService;
	
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
		List<Sechos_Drugmaterial> sechosDrugmaterialList = sechosDrugmaterialService.getList(query);
		int total = sechosDrugmaterialService.getCount(query);
		PageUtils pageUtil = new PageUtils(sechosDrugmaterialList, total, query.getLimit(), query.getPage());
		return LayuiUtil.data(pageUtil.getTotalCount(), pageUtil.getList());
	}

    /**
     * 新增
     **/
    @ApiOperation(value="")
    @ResponseBody
    @RequestMapping(value="/add",produces="application/json;charset=utf-8",method=RequestMethod.POST)
    public R add(@RequestBody Sechos_Drugmaterial sechosDrugmaterial){
    	//如果排序号为空，则自动转为0
    	if (sechosDrugmaterial.getSortSq() == null) {
			sechosDrugmaterial.setSortSq(0);
		}
    	//生成uuid作为rowguid
        String uuid = java.util.UUID.randomUUID().toString();
		sechosDrugmaterial.setRowGuid(uuid);
		Date createTime = DateUtil.changeDate(new Date());
		sechosDrugmaterial.setCreateTime(createTime);
		sechosDrugmaterial.setDelFlag(0);
		sechosDrugmaterialService.save(sechosDrugmaterial);
        return R.ok();  
    }

	/**
	 * 修改
	 */
	@ApiOperation(value="")
    @ResponseBody
	@RequestMapping(value="/update", produces = "application/json; charset=utf-8", method=RequestMethod.POST)
	public R update(@RequestBody Sechos_Drugmaterial sechosDrugmaterial){
		sechosDrugmaterialService.update(sechosDrugmaterial);
		return R.ok();
	}

	/**
	 * 删除
	 */
    @ApiOperation(value="删除材料信息")
	@ResponseBody
	@RequestMapping(value="/delete",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R delete(@RequestBody String[] rowGuids){
		sechosDrugmaterialService.deleteBatch(rowGuids);
		return R.ok();
	}

	@ApiOperation(value="通过rowGuid获取一条记录")
	@ResponseBody
	@RequestMapping(value="/getDetailByGuid",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R getDetailByGuid(@RequestBody String rowGuid){
		Sechos_Drugmaterial sechosDrugMaterial = sechosDrugmaterialService.getDetailByGuid(rowGuid);
		return R.ok().put("data",sechosDrugMaterial);
	}

	@ApiOperation(value="获取所有药品代码")
	@ResponseBody
	@RequestMapping(value="/getDrugCodes",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R getDrugCodes(){
		List<String> stringList = sechosDrugmaterialService.getDrugCodes();
		return R.ok().put("data",stringList);
	}

	@ApiOperation(value="根据药品代码获取名称")
	@ResponseBody
	@RequestMapping(value="/getDrugName",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R getDrugName(@RequestBody String drugCode){
		Sechos_Drugmaterial sechosDrugmaterial = sechosDrugmaterialService.getDrugName(drugCode);
		return R.ok().put("data",sechosDrugmaterial);
	}

	/**
	 * 通过药品代码名检验重复
	 * <p>Title: checkDrugCode</p>
	 * <p>Description: 用户</p>
	 *
	 * @param
	 * @return
	 * @author wzl
	 */
	@ApiOperation(value = "通过药品代码名检验重复")
	@ResponseBody
	@RequestMapping(value = "/checkDrugCode", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
	public <T> R checkDrugCode(@RequestBody T t){
		int count = sechosDrugmaterialService.checkDrugCode(t);
		if (count >= 1) {
			return R.error("您输入的值已存在，请重新输入");
		}
		return R.ok();
	}
}
