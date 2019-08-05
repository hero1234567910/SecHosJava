package com.basic.javaframe.wxcontroller;

import com.basic.javaframe.common.customclass.PassToken;
import com.basic.javaframe.common.utils.*;
import com.basic.javaframe.entity.Sechos_Evaluate;
import com.basic.javaframe.service.Sechos_EvaluateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 
 * 
 * @author wzl
 * @date 2019-07-30 14:29:09
 */
@Api(value = "微信住院病人评价信息")
@RestController
@CrossOrigin
@RequestMapping("wx/sys/sechosevaluate")
public class Wx_Sechos_EvaluateController {
	@Autowired
	private Sechos_EvaluateService sechosEvaluateService;
	
	/**
	 * 列表数据
	 */
	@PassToken
	@ApiOperation(value="病人评价列表")
    @ResponseBody
	@RequestMapping(value="/listData",produces="application/json;charset=utf-8",method=RequestMethod.GET)
	public LayuiUtil listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<Sechos_Evaluate> sechosEvaluateList = sechosEvaluateService.getList(query);
		int total = sechosEvaluateService.getCount(query);
		PageUtils pageUtil = new PageUtils(sechosEvaluateList, total, query.getLimit(), query.getPage());
		return LayuiUtil.data(pageUtil.getTotalCount(), pageUtil.getList());
	}

    /**
     * 新增
     **/
    @ApiOperation(value="病人新增评价")
    @ResponseBody
    @RequestMapping(value="/add",produces="application/json;charset=utf-8",method=RequestMethod.POST)
    public R add(@RequestBody Sechos_Evaluate sechosEvaluate){
    	if(sechosEvaluate.getPatientRowGuid() == null || "".equals(sechosEvaluate.getPatientRowGuid())){
    		return R.error("缺失参数guid");
    	}
//    	//根据guid去查询是否已经评价
//    	Sechos_Evaluate s = sechosEvaluateService.selectByGuid(sechosEvaluate.getPatientRowGuid());
//    	if (s != null) {
//			return R.error("您已评价，请勿重复评价");
//		}
    	//如果排序号为空，则自动转为0
    	if (sechosEvaluate.getSortSq() == null) {
			sechosEvaluate.setSortSq(0);
		}
    	//生成uuid作为rowguid
		sechosEvaluate.setDelFlag(0);
        String uuid = java.util.UUID.randomUUID().toString();
		sechosEvaluate.setRowGuid(uuid);
		Date createTime = DateUtil.changeDate(new Date());
		sechosEvaluate.setCreateTime(createTime);
		sechosEvaluateService.save(sechosEvaluate);
        return R.ok();  
    }

	/**
	 * 修改
	 */
	@ApiOperation(value="病人修改评价")
    @ResponseBody
	@RequestMapping(value="/update", produces = "application/json; charset=utf-8", method=RequestMethod.POST)
	public R update(@RequestBody Sechos_Evaluate sechosEvaluate){
		sechosEvaluateService.update(sechosEvaluate);
		return R.ok();
	}

	
}
