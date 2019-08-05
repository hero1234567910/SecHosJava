package com.basic.javaframe.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import com.basic.javaframe.common.utils.DateUtil;
import com.basic.javaframe.common.utils.LayuiUtil;
import com.basic.javaframe.entity.Sechos_Consultation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.basic.javaframe.common.customclass.PassToken;
import com.basic.javaframe.service.Sechos_ConsultationService;
import com.basic.javaframe.common.utils.PageUtils;
import com.basic.javaframe.common.utils.Query;
import com.basic.javaframe.common.utils.R;


/**
 * 
 * 
 * @author wzl
 * @date 2019-06-26 14:30:13
 */
@Api(value = "门诊咨询信息")
@RestController
@CrossOrigin
@RequestMapping("sys/sechosconsultation")
public class Sechos_ConsultationController {
	@Autowired
	private Sechos_ConsultationService sechosConsultationService;
	
	/**
	 * 列表数据
	 */
	@PassToken
	@ApiOperation(value="获取咨询列表")
    @ResponseBody
	@RequestMapping(value="/listData",produces="application/json;charset=utf-8",method=RequestMethod.GET)
	public LayuiUtil listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<Sechos_Consultation> sechosConsultationList = sechosConsultationService.getList(query);
		int total = sechosConsultationService.getCount(query);
		PageUtils pageUtil = new PageUtils(sechosConsultationList, total, query.getLimit(), query.getPage());
		return LayuiUtil.data(pageUtil.getTotalCount(), pageUtil.getList());
	}

    /**
     * 新增
     **/
    @ApiOperation(value="新增咨询信息")
    @ResponseBody
    @RequestMapping(value="/add",produces="application/json;charset=utf-8",method=RequestMethod.POST)
    public R add(@RequestBody Sechos_Consultation sechosConsultation){
    	//如果排序号为空，则自动转为0
    	if (sechosConsultation.getSortSq() == null) {
			sechosConsultation.setSortSq(0);
		}
    	//生成uuid作为rowguid
        String uuid = java.util.UUID.randomUUID().toString();
		sechosConsultation.setRowGuid(uuid);
		Date createTime = DateUtil.changeDate(new Date());
		Date consultationTime = DateUtil.changeDate(new Date());
		sechosConsultation.setCreateTime(createTime);
		sechosConsultation.setDelFlag(0);
		sechosConsultation.setReplyStatus(0);
		sechosConsultation.setConsultationTime(consultationTime);
		sechosConsultationService.save(sechosConsultation);
        return R.ok();  
    }

	/**
	 * 修改
	 */
	@ApiOperation(value="修改咨询信息")
    @ResponseBody
	@RequestMapping(value="/update", produces = "application/json; charset=utf-8", method=RequestMethod.POST)
	public R update(@RequestBody Sechos_Consultation sechosConsultation){
		sechosConsultationService.update(sechosConsultation);
		return R.ok();
	}

	/**
	 * 删除
	 */
    @ApiOperation(value="删除咨询信息")
	@ResponseBody
	@RequestMapping(value="/delete",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R delete(@RequestBody String[] rowGuids){
		sechosConsultationService.deleteBatch(rowGuids);
		return R.ok();
	}

	/**
	 * 回复咨询信息
	 */
	@ApiOperation(value="回复咨询信息")
	@ResponseBody
	@RequestMapping(value="/reply", produces = "application/json; charset=utf-8", method=RequestMethod.POST)
	public R reply(@RequestBody Sechos_Consultation sechosConsultation){
		Date replyTime = DateUtil.changeDate(new Date());
		sechosConsultation.setReplyTime(replyTime);
		sechosConsultation.setReplyStatus(1);
		sechosConsultationService.reply(sechosConsultation);
		return R.ok();
	}

	@ApiOperation(value="获得未回复咨询信息数量")
	@ResponseBody
	@RequestMapping(value="/getReplyCount", produces = "application/json; charset=utf-8", method=RequestMethod.GET)
	public R getReplyCount(){
		int count = sechosConsultationService.getReplyCount();
		return R.ok().put("data",count);
	}

}
