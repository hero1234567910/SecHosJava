package com.basic.javaframe.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.basic.javaframe.common.utils.*;
import com.basic.javaframe.service.HosGoodsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import javax.servlet.http.HttpServletRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.basic.javaframe.common.customclass.PassToken;
import com.basic.javaframe.entity.HosGoodstype;
import com.basic.javaframe.service.HosGoodstypeService;


/**
 * 食品类别管理
 * 食品类别控制层
 * @author wzl
 * @date 2019-04-26 13:44:20
 */
@Api(value = "食品类别")
@RestController
@CrossOrigin
@RequestMapping("sys/hosgoodstype")
public class HosGoodstypeController {

	private final static Logger logger = LoggerFactory.getLogger(HosGoodstypeController.class);

	@Autowired
	private HosGoodstypeService hosGoodstypeService;

	@Autowired
	private HosGoodsService hosGoodsService;
	
	/**
	 * 列表数据
	 */
	@PassToken
	@ApiOperation(value="查询食品类别列表")
    @ResponseBody
	@RequestMapping(value="/listData",produces="application/json;charset=utf-8",method=RequestMethod.GET)
	public LayuiUtil listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<HosGoodstype> hosGoodstypeList = hosGoodstypeService.getList(query);
		int total = hosGoodstypeService.getCount(query);
		PageUtils pageUtil = new PageUtils(hosGoodstypeList, total, query.getLimit(), query.getPage());
		return LayuiUtil.data(pageUtil.getTotalCount(), pageUtil.getList());
	}

    /**
     * 新增
     **/
    @ApiOperation(value="新增食品类别")
    @ResponseBody
    @RequestMapping(value="/add",produces="application/json;charset=utf-8",method=RequestMethod.POST)
    public R add(@RequestBody Map<String,Object> params){
    	HosGoodstype hosGoodstype = JSON.parseObject(JSON.toJSONString(params.get("field")), HosGoodstype.class);
    	//如果排序号为空，则自动转为0
    	if (hosGoodstype.getSortSq() == null) {
			hosGoodstype.setSortSq(0);
		}
    	//生成uuid作为rowguid
        String uuid = java.util.UUID.randomUUID().toString();
		hosGoodstype.setRowGuid(uuid);

		String uuid1 = params.get("goodsTypeCode").toString();
		hosGoodstype.setGoodsTypeCode(uuid1);
		Date createTime = DateUtil.changeDate(new Date());
		hosGoodstype.setCreateTime(createTime);
		if (StringUtil.isBlank(hosGoodstype.getPgoodsTypeCode())) {
			hosGoodstype.setPgoodsTypeCode("0");
		}
		hosGoodstype.setDelFlag(0);
		hosGoodstypeService.save(hosGoodstype);
        return R.ok();  
    }

	/**
	 * 修改
	 */
	@ApiOperation(value="修改食品类别")
    @ResponseBody
	@RequestMapping(value="/update", produces = "application/json; charset=utf-8", method=RequestMethod.PUT)
	public R update(@RequestBody HosGoodstype hosGoodstype){
		hosGoodstypeService.update(hosGoodstype);
		return R.ok();
	}

	/**
	 * 删除
	 */
    @ApiOperation(value="删除食品类别")
	@ResponseBody
	@RequestMapping(value="/delete",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R delete(@RequestBody String[] rowGuids){
		hosGoodsService.deleteGoods(rowGuids);
		hosGoodstypeService.deleteBatch(rowGuids);
		return R.ok();
	}

	/**
	 * 查询食品类别树
	 * <p>Title: getGoodsTypeTrees</p>
	 * <p>Description: </p>
	 * @author hero
	 * @return
	 */
	@ApiOperation(value="查询部门树")
	@ResponseBody
	@RequestMapping(value="/getGoodsTypeTrees",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R getGoodsTypeTrees(){
		JSONArray trees = hosGoodstypeService.findTopGoodsTypes();
		return R.ok().put("data", trees);
	}

	/**
	 * 查询上级菜单类别
	 * <p>Title: getByGoodsTypeCode</p>
	 * <p>Description: </p>
	 * @author wzl
	 * @return
	 */
	@ApiOperation(value = "查询上级菜单类别")
	@ResponseBody
	@RequestMapping(value="/getByGoodsTypeCode/{goodsTypeCode}",produces="application/json;charset=utf-8",method=RequestMethod.GET)
	public R getByGoodsTypeCode(@PathVariable("goodsTypeCode")String goodsTypeCode){
		String name = hosGoodstypeService.getByGoodsTypeCode(goodsTypeCode);
		return R.ok().put("data",name);
	}

	/**
	 * 验证重复性
	 * <p>Title: checkGoodsType</p>
	 * <p>Description: </p>
	 * @author wzl
	 * @param <T>
	 * @return
	 */
	@ApiOperation(value="验证菜品类型重复性")
	@ResponseBody
	@RequestMapping(value="/checkGoodsType",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public <T> R checkDept(@RequestBody T t){
		//泛型无法判断传入的值是否为空
		int count = hosGoodstypeService.checkGoodsType(t);
		if (count >= 1) {
			return R.error("您输入的值已存在，请重新输入");
		}
		return R.ok();
	}
	
	/**
	 * 查询指定类别的所有菜品
	 * <p>Title: getHosGoodsByPtypeCoed</p>  
	 * <p>Description: </p>
	 * @author hero  
	 * @return
	 */
	@ApiOperation(value="查询指定类别的所有菜品")
	@ResponseBody
	@RequestMapping(value="/getHosGoodsByPcode",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R getHosGoodsByPtypeCoed(@RequestBody String code){
		JSONArray array = hosGoodstypeService.getHosGoodsByPtypeCoed(code);
		return R.ok().put("data", array);
	}
	
	/**
	 * 获取根目录
	 * <p>Title: getTopGoodsType</p>  
	 * <p>Description: </p>
	 * @author hero  
	 * @return
	 */
	@ApiOperation(value="获取根目录")
	@ResponseBody
	@RequestMapping(value="/getTopGoodsType",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R getTopGoodsType(){
		List<HosGoodstype> array = hosGoodstypeService.getTopGoodsTypes();
		return R.ok().put("data", array);
	}
	
}
