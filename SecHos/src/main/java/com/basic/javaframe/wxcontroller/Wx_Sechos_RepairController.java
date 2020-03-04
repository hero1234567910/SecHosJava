package com.basic.javaframe.wxcontroller;

import com.basic.javaframe.common.JWT.TokenService;
import com.basic.javaframe.common.customclass.PassToken;
import com.basic.javaframe.common.utils.*;
import com.basic.javaframe.entity.Frame_User;
import com.basic.javaframe.entity.Sechos_Repair;
import com.basic.javaframe.entity.Sechos_Repairsatisfaction;
import com.basic.javaframe.service.Frame_AttachService;
import com.basic.javaframe.service.Frame_UserService;
import com.basic.javaframe.service.Sechos_RepairService;
import com.basic.javaframe.service.Sechos_RepairsatisfactionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 
 * 
 * @author wzl
 * @date 2019-08-28 10:11:37
 */
@Api(value = "设备报修管理")
@RestController
@CrossOrigin
@RequestMapping("wx/sys/sechosrepair")
public class Wx_Sechos_RepairController {
	@Autowired
	private Sechos_RepairService sechosRepairService;

	@Autowired
	private Sechos_RepairsatisfactionService sechosRepairsatisfactionService;

	@Autowired
	private Frame_AttachService attachService;
	
	@Autowired
	private Frame_UserService userService;
	
	@Autowired
    TokenService tokenService;
	
	//登录
    @PassToken
    @ApiOperation(value="登录接口")
    @ResponseBody
    @RequestMapping(value="/login",produces="application/json;charset=utf-8",method=RequestMethod.POST)
    public R login(@RequestBody Frame_User user){
        Frame_User userForBase=userService.getFrameUserByLoginId(user.getLoginId());
        if(userForBase==null){
            
        	//判断是否存于oa用户中
        	Frame_User userOA = userService.getOAUserByLoginId(user.getLoginId());
        	if (userOA == null) {
        		return R.error("登录失败,用户不存在");
			}else{
				Map<String, String> parmas = new HashMap<>();
//				parmas.put("password", user.getPassword());
//				String password = HttpUtil.sendPostWithXWwwForm("http://10.20.200.62:8081/iis/DecryptApi/Encryp/getEncrypPassword",parmas);
//				JSONObject jsonObject = JSONObject.parseObject(password);
//				System.out.println(userOA.getPassword()+" >>>>>"+jsonObject.getString("data"));
//				if (!userOA.getPassword().equals(jsonObject.getString("data"))) {
//					return R.error("登录失败,密码错误");
//				}
//				String token = tokenService.getToken(userForBase);
                //根据用户行标获取部门名部门行标
                Map<String, Object> map = new HashMap<>();
                map.put("token", "");
                map.put("userName", userOA.getUserName());
                map.put("loginId", userOA.getLoginId());
                map.put("mobile", userOA.getMobile());
                map.put("sex", "");
                map.put("userRowGuid", userOA.getRowGuid());
                map.put("deptGuid","");
                map.put("deptName", "");
                return R.ok().put("data", map);
			}
        	
            
        }else {
           //if (!userForBase.getPassword().equals(Sha256.SHA(user.getPassword(), "SHA-256"))){
           // System.out.println(userForBase.getPassword()+">>>>>>"+AESUtil.encrypt(user.getPassword(), "expsoft1234"));
            if (!userForBase.getPassword().equals(AESUtil.encrypt(user.getPassword(), "expsoft1234"))){
                return R.error("登录失败,密码错误");
            }else {
                if(userForBase.getIsForbid()==1){
                    return R.error("用户已经禁用");
                }
                if(userForBase.getDelFlag()==1){
                    return R.error("用户不存在");
                }
                String token = tokenService.getToken(userForBase);
                //根据用户行标获取部门名部门行标
                Map<String, Object> maparams = userService.getDeptByGuid(userForBase.getRowGuid());
                Map<String, Object> map = new HashMap<>();
                map.put("token", token);
                map.put("userName", userForBase.getUserName());
                map.put("loginId", userForBase.getLoginId());
                map.put("mobile", userForBase.getMobile());
                map.put("sex", userForBase.getSex());
                map.put("userRowGuid", userForBase.getRowGuid());
                map.put("deptGuid", userForBase.getDeptGuid());
                map.put("deptName", maparams.get("deptName"));
                return R.ok().put("data", map);


            }
        }

    }
	
	/**
	 * 列表数据
	 */
	@PassToken
	@ApiOperation(value="获取设备报修表")
    @ResponseBody
	@RequestMapping(value="/listData",produces="application/json;charset=utf-8",method=RequestMethod.GET)
	public LayuiUtil listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<Sechos_Repair> sechosRepairList = sechosRepairService.getList(query);
		int total = sechosRepairService.getCount(query);
		PageUtils pageUtil = new PageUtils(sechosRepairList, total, query.getLimit(), query.getPage());
		return LayuiUtil.data(pageUtil.getTotalCount(), pageUtil.getList());
	}

    /**
     * 新增
     **/
    @ApiOperation(value="新增设备报修信息")
    @ResponseBody
    @RequestMapping(value="/add",produces="application/json;charset=utf-8",method=RequestMethod.POST)
    public R add(@RequestBody Sechos_Repair sechosRepair){
    	//如果排序号为空，则自动转为0
    	if (sechosRepair.getSortSq() == null) {
			sechosRepair.setSortSq(0);
		}
    	//生成uuid作为rowguid
        String uuid = java.util.UUID.randomUUID().toString();
		sechosRepair.setRowGuid(uuid);
		sechosRepair.setDelFlag(0);
		sechosRepair.setRepairStatus(0);
		Date createTime = DateUtil.changeDate(new Date());
		sechosRepair.setCreateTime(createTime);
		String imgGuid = java.util.UUID.randomUUID().toString();
		sechosRepair.setPicGuid(imgGuid);
		sechosRepairService.save(sechosRepair);

		String[] guids = {sechosRepair.getUploadImgGuid()};
		attachService.updateAttach(imgGuid,guids);
		return R.ok();
    }

	/**
	 * 修改
	 */
	@ApiOperation(value="修改设备报修信息")
    @ResponseBody
	@RequestMapping(value="/update", produces = "application/json; charset=utf-8", method=RequestMethod.POST)
	public R update(@RequestBody Sechos_Repair sechosRepair){
		sechosRepairService.update(sechosRepair);
		return R.ok();
	}

	/**
	 * 删除
	 */
    @ApiOperation(value="删除设备报修信息")
	@ResponseBody
	@RequestMapping(value="/delete",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R delete(@RequestBody String[] rowGuids){
		sechosRepairService.deleteBatch(rowGuids);
		return R.ok();
	}

	@ApiOperation(value="通过rowGuid获取一条记录")
	@ResponseBody
	@RequestMapping(value="/getDetailByGuid",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R getDetailByGuid(@RequestBody String rowGuid){
		Sechos_Repair sechosRepair = sechosRepairService.getDetailByGuid(rowGuid);
		return R.ok().put("data",sechosRepair);
	}

	@ApiOperation(value="通过rowGuid获取个人报修记录")
	@ResponseBody
	@RequestMapping(value="/getListByGuid",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R getListByGuid(@RequestBody String rowGuid){
		List<Sechos_Repair> sechosRepairList = sechosRepairService.getListByGuid(rowGuid);
		return R.ok().put("data",sechosRepairList);
	}

	/**
	 * 取消设备报修
	 */
	@ApiOperation(value="取消设备报修")
	@ResponseBody
	@RequestMapping(value="/cancelRepair", produces = "application/json; charset=utf-8", method=RequestMethod.POST)
	public R cancelRepair(@RequestBody String rowGuid){
		sechosRepairService.cancelRepair(rowGuid);
		return R.ok();
	}

	/**
	 * 列表数据
	 */
	@PassToken
	@ApiOperation(value="获取设备维修表")
	@ResponseBody
	@RequestMapping(value="/listMaintainData",produces="application/json;charset=utf-8",method=RequestMethod.GET)
	public LayuiUtil listMaintainData(@RequestParam Map<String, Object> params){
		//查询列表数据
		//System.out.println(params);
		Query query = new Query(params);
		List<Sechos_Repair> sechosRepairList = sechosRepairService.getMaintainList(query);
		int total = sechosRepairService.getMaintainCount(query);
		PageUtils pageUtil = new PageUtils(sechosRepairList, total, query.getLimit(), query.getPage());
		return LayuiUtil.data(pageUtil.getTotalCount(), pageUtil.getList());
	}

	/**
	 * 完成设备报修
	 */
	@ApiOperation(value="完成设备报修")
	@ResponseBody
	@RequestMapping(value="/successRepair", produces = "application/json; charset=utf-8", method=RequestMethod.POST)
	public R successRepair(@RequestParam Map<String, Object> params){
		//System.out.println(params);
		Date createTime = DateUtil.changeDate(new Date());
		params.put("maintainTime",createTime);
		sechosRepairService.successRepair(params);
		String rowGuid = params.get("rowGuid").toString();
		String maintainGuid = params.get("maintainGuid").toString();
		String maintainName = params.get("maintainName").toString();
		//每完成一次报修就生成一次评价
		Sechos_Repair sechosRepair = sechosRepairService.getDetailByGuid(rowGuid);
		Sechos_Repairsatisfaction sechosRepairsatisfaction = new Sechos_Repairsatisfaction();

		if (sechosRepairsatisfaction.getSortSq() == null) {
			sechosRepairsatisfaction.setSortSq(0);
		}
		//生成uuid作为rowguid
		String uuid = java.util.UUID.randomUUID().toString();
		sechosRepairsatisfaction.setRowGuid(uuid);
		sechosRepairsatisfaction.setDelFlag(0);
		sechosRepairsatisfaction.setCreateTime(createTime);
		sechosRepairsatisfaction.setRepairGuid(sechosRepair.getRepairGuid());
		sechosRepairsatisfaction.setRepairName(sechosRepair.getRepairName());
		sechosRepairsatisfaction.setDeviceName(sechosRepair.getDeviceName());
		sechosRepairsatisfaction.setRecordGuid(rowGuid);
		sechosRepairsatisfaction.setMaintainGuid(maintainGuid);
		sechosRepairsatisfaction.setMaintainName(maintainName);
		sechosRepairsatisfaction.setEvaluationStatus(0);
		sechosRepairsatisfactionService.save(sechosRepairsatisfaction);
		return R.ok();
	}

	/**
	 * 列表数据
	 */
	@PassToken
	@ApiOperation(value="获取我的设备维修记录")
	@ResponseBody
	@RequestMapping(value="/listMyData",produces="application/json;charset=utf-8",method=RequestMethod.GET)
	public LayuiUtil listMyData(@RequestParam Map<String, Object> params){
		//查询列表数据
		//System.out.println(params);
		Query query = new Query(params);
		List<Sechos_Repair> sechosRepairList = sechosRepairService.getMyList(query);
		int total = sechosRepairService.getMyCount(query);
		PageUtils pageUtil = new PageUtils(sechosRepairList, total, query.getLimit(), query.getPage());
		return LayuiUtil.data(pageUtil.getTotalCount(), pageUtil.getList());
	}

	@ApiOperation(value="获取待维修数据数量")
	@ResponseBody
	@RequestMapping(value="/countRepairs",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R countRepairs(@RequestBody String maintainGuid){
		Integer counts = sechosRepairService.countRepairs(maintainGuid);
		return R.ok().put("data",counts);
	}
}
