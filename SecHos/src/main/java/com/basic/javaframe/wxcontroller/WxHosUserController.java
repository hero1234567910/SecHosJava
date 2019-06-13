package com.basic.javaframe.wxcontroller;

import com.basic.javaframe.common.customclass.PassToken;
import com.basic.javaframe.common.utils.*;
import com.basic.javaframe.entity.HosUser;
import com.basic.javaframe.service.HosUserService;
import com.basic.javaframe.service.InformationInfoService;

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
 * @date 2019-04-29 13:49:42
 */
@Api(value = "订餐用户信息")
@RestController
@CrossOrigin
@RequestMapping("wx/sys/hosuser")
public class WxHosUserController {
	@Autowired
	private HosUserService hosUserService;
	@Autowired
    private InformationInfoService informationInfoService;
	
	/**
	 * 列表数据
	 */
	@PassToken
	@ApiOperation(value="查询订餐用户信息列表")
    @ResponseBody
	@RequestMapping(value="/listData",produces="application/json;charset=utf-8",method=RequestMethod.GET)
	public LayuiUtil listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<HosUser> hosUserList = hosUserService.getList(query);
		int total = hosUserService.getCount(query);
		PageUtils pageUtil = new PageUtils(hosUserList, total, query.getLimit(), query.getPage());
		return LayuiUtil.data(pageUtil.getTotalCount(), pageUtil.getList());
	}

    /**
     * 新增
     **/
    @ApiOperation(value="新增用户信息")
    @ResponseBody
    @RequestMapping(value="/add",produces="application/json;charset=utf-8",method=RequestMethod.POST)
    public R add(@RequestBody HosUser hosUser){
    	//如果排序号为空，则自动转为0
    	if (hosUser.getSortSq() == null) {
			hosUser.setSortSq(0);
		}
    	//生成uuid作为rowguid
        String uuid = java.util.UUID.randomUUID().toString();
		hosUser.setRowGuid(uuid);
		Date createTime = DateUtil.changeDate(new Date());
		hosUser.setCreateTime(createTime);
		hosUser.setDelFlag(0);
		hosUserService.save(hosUser);
        return R.ok();  
    }

	/**
	 * 修改
	 */
	@ApiOperation(value="修改用户信息")
    @ResponseBody
	@RequestMapping(value="/update", produces = "application/json; charset=utf-8", method=RequestMethod.PUT)
	public R update(@RequestBody HosUser hosUser){
		hosUserService.update(hosUser);
		return R.ok();
	}

	/**
	 * 删除
	 */
    @ApiOperation(value="删除用户信息")
	@ResponseBody
	@RequestMapping(value="/delete",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R delete(@RequestBody String[] rowGuids){
		hosUserService.deleteBatch(rowGuids);
		return R.ok();
	}

	/**
	 * 通过openId查询用户信息
	 * <p>Title: getUserByOpenid</p>
	 * <p>Description: 用户</p>
	 * @param openid
	 * @return
	 */
	@ApiOperation(value="通过openid查询用户信息")
	@ResponseBody
	@RequestMapping(value="/getUserByOpenid",produces="application/json;charset=utf-8",method=RequestMethod.GET)
	public R getUserByOpenid(@RequestBody String openid){
    	hosUserService.getUserByOpenid(openid);
    	return R.ok();
	}
	
	/**
	 * 获取发布的通告信息
	 * <p>Title: getInforMation</p>  
	 * <p>Description: </p>
	 * @author hero  
	 * @return
	 */
	@ApiOperation(value="获取发布的通告信息")
	@ResponseBody
	@RequestMapping(value="/getInforMation",produces="application/json;charset=utf-8",method=RequestMethod.GET)
	public R getInforMation(){
		String info = informationInfoService.getMInfoMation();
		return R.ok().put("data", info);
	}
}
