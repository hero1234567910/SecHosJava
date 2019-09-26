package com.basic.javaframe.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import com.basic.javaframe.common.utils.DateUtil;
import com.basic.javaframe.common.utils.LayuiUtil;
import com.basic.javaframe.entity.Sechos_Drugmaterial;
import com.basic.javaframe.entity.Sechos_Materials;
import com.basic.javaframe.entity.Sechos_Storageamount;
import com.basic.javaframe.service.Sechos_StorageamountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.basic.javaframe.common.customclass.PassToken;
import com.basic.javaframe.service.Sechos_MaterialsService;
import com.basic.javaframe.common.utils.PageUtils;
import com.basic.javaframe.common.utils.Query;
import com.basic.javaframe.common.utils.R;


/**
 * 
 * 
 * @author 
 * @date 2019-09-26 10:51:41
 */
@Api(value = "普通材料管理")
@RestController
@CrossOrigin
@RequestMapping("sys/sechosmaterials")
public class Sechos_MaterialsController {
	@Autowired
	private Sechos_MaterialsService sechosMaterialsService;

	@Autowired
	private Sechos_StorageamountService sechosStorageamountService;
	/**
	 * 列表数据
	 */
	@PassToken
	@ApiOperation(value="材料列表数据")
    @ResponseBody
	@RequestMapping(value="/listData",produces="application/json;charset=utf-8",method=RequestMethod.GET)
	public LayuiUtil listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<Sechos_Materials> sechosMaterialsList = sechosMaterialsService.getList(query);
		int total = sechosMaterialsService.getCount(query);
		PageUtils pageUtil = new PageUtils(sechosMaterialsList, total, query.getLimit(), query.getPage());
		return LayuiUtil.data(pageUtil.getTotalCount(), pageUtil.getList());
	}

    /**
     * 新增
     **/
    @ApiOperation(value="")
    @ResponseBody
    @RequestMapping(value="/add",produces="application/json;charset=utf-8",method=RequestMethod.POST)
    public R add(@RequestBody Sechos_Materials sechosMaterials){
    	//如果排序号为空，则自动转为0
    	if (sechosMaterials.getSortSq() == null) {
			sechosMaterials.setSortSq(0);
		}
    	//生成uuid作为rowguid
        String uuid = java.util.UUID.randomUUID().toString();
		sechosMaterials.setRowGuid(uuid);
		Date createTime = DateUtil.changeDate(new Date());
		sechosMaterials.setCreateTime(createTime);
		sechosMaterials.setDelFlag(0);
		sechosMaterialsService.save(sechosMaterials);

		Sechos_Storageamount sechosStorageamount = new Sechos_Storageamount();
		if (sechosStorageamount.getSortSq() == null) {
			sechosStorageamount.setSortSq(0);
		}
		//生成uuid作为rowguid
		String uuid2 = java.util.UUID.randomUUID().toString();
		sechosStorageamount.setRowGuid(uuid2);
		Date createTime2 = DateUtil.changeDate(new Date());
		sechosStorageamount.setCreateTime(createTime2);
		sechosStorageamount.setDelFlag(0);
		sechosStorageamount.setDrugName(sechosMaterials.getMaterialName());
		sechosStorageamount.setDrugCode(sechosMaterials.getMaterialCode());
		sechosStorageamount.setDrugGuid(sechosMaterials.getRowGuid());
		sechosStorageamount.setDrugSum(0);
		sechosStorageamountService.save(sechosStorageamount);
        return R.ok();  
    }

	/**
	 * 修改
	 */
	@ApiOperation(value="")
    @ResponseBody
	@RequestMapping(value="/update", produces = "application/json; charset=utf-8", method=RequestMethod.POST)
	public R update(@RequestBody Sechos_Materials sechosMaterials){
		sechosMaterialsService.update(sechosMaterials);
		return R.ok();
	}

	/**
	 * 删除
	 */
    @ApiOperation(value="")
	@ResponseBody
	@RequestMapping(value="/delete",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R delete(@RequestBody String[] rowGuids){
		sechosMaterialsService.deleteBatch(rowGuids);
		return R.ok();
	}

	@ApiOperation(value="通过rowGuid获取一条记录")
	@ResponseBody
	@RequestMapping(value="/getDetailByGuid",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R getDetailByGuid(@RequestBody String rowGuid){
		Sechos_Materials sechosMaterials = sechosMaterialsService.getDetailByGuid(rowGuid);
		return R.ok().put("data",sechosMaterials);
	}

	@ApiOperation(value="获取所有材料代码")
	@ResponseBody
	@RequestMapping(value="/getMaterialCodes",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R getMaterialCodes(){
		List<String> stringList = sechosMaterialsService.getMaterialCodes();
		return R.ok().put("data",stringList);
	}

	@ApiOperation(value="根据材料代码获取名称")
	@ResponseBody
	@RequestMapping(value="/getMaterialName",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R getMaterialName(@RequestBody String materialCode){
		Sechos_Materials sechosMaterials = sechosMaterialsService.getMaterialName(materialCode);
		return R.ok().put("data",sechosMaterials);
	}

	/**
	 * 通过材料代码名检验重复
	 * <p>Title: checkMaterialCode</p>
	 * <p>Description: 用户</p>
	 *
	 * @param
	 * @return
	 * @author wzl
	 */
	@ApiOperation(value = "通过药品代码名检验重复")
	@ResponseBody
	@RequestMapping(value = "/checkMaterialCode", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
	public <T> R checkMaterialCode(@RequestBody T t){
		int count = sechosMaterialsService.checkMaterialCode(t);
		if (count >= 1) {
			return R.error("您输入的值已存在，请重新输入");
		}
		return R.ok();
	}
}
