package com.basic.javaframe.controller;

import com.alibaba.fastjson.JSONArray;
import com.basic.javaframe.common.customclass.PassToken;
import com.basic.javaframe.common.utils.DateUtil;
import com.basic.javaframe.common.utils.LayuiUtil;
import com.basic.javaframe.common.utils.PageUtils;
import com.basic.javaframe.common.utils.Query;
import com.basic.javaframe.common.utils.R;
import com.basic.javaframe.common.utils.StringUtil;
import com.basic.javaframe.entity.Frame_Dept;
import com.basic.javaframe.entity.Frame_User;
import com.basic.javaframe.service.Frame_DeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 部门管理 信息操作处理
 * 部门控制层
 * @author wzl
 * @date 2019-03-06
 */
@CrossOrigin
@Api(value = "部门")
@RestController
@RequestMapping("sys/dept")
public class Frame_DeptController {
    //定义一个全局的记录器，通过LoggerFactory获取
    private final static Logger logger = LoggerFactory.getLogger(Frame_DeptController.class);

    @Autowired
    private Frame_DeptService deptService;

    /**
     * 查询部门列表
     * <p>Title: getDept</p>
     * <p>Description: 部门</p>
     * @author hero
     * @param params
     * @return
     */
    @PassToken
    @ApiOperation(value = "查询部门列表")
    @ResponseBody
    @RequestMapping(value = "/getDept",produces="application/json;charset=utf-8",method= RequestMethod.GET)
    public LayuiUtil getDept(@RequestParam Map<String, Object> params){
    	Query query = new Query(params);
    	List<Frame_Dept> list = deptService.selectFrameDeptList(query);
		int total = deptService.getCount(query);
		PageUtils pageUtil = new PageUtils(list, total, query.getLimit(), query.getPage());
		return LayuiUtil.data(pageUtil.getTotalCount(), pageUtil.getList());
    }

    /**
     * 新增部门
     * <p>Title: addDept</p>
     * <p>Description: 部门</p>
     * @author hero
     * @param frame_dept
     * @return
     */
    @ApiOperation(value = "新增部门")
    @ResponseBody
    @RequestMapping(value = "/add",produces="application/json;charset=utf-8",method= RequestMethod.POST)
    public R addDept(@RequestBody Frame_Dept frame_dept){
    	//生成uuid作为rowguid
        String uuid = UUID.randomUUID().toString();
		frame_dept.setRowGuid(uuid);
		Date createTime = DateUtil.changeDate(new Date());
		
		//如果pdeptCode为空，则将其赋值0作为顶级菜单
		frame_dept.setCreateTime(createTime);
		if (StringUtil.isBlank(frame_dept.getPdeptCode())) {
			frame_dept.setPdeptCode("0");
		}
        deptService.insertFrameDept(frame_dept);
        return R.ok();  
    }

    /**
     * 修改部门
     * <p>Title: addDept</p>
     * <p>Description: 部门</p>
     * @author hero
     * @param frameDept
     * @return
     */
    @ApiOperation(value = "修改部门")
    @ResponseBody
    @RequestMapping(value = "/updateDept/{id}",produces="application/json;charset=utf-8",method= RequestMethod.POST)
    public R updateDept(@PathVariable("id") Integer id,@RequestBody Frame_Dept frameDept){
    	frameDept.setRowId(id);
        deptService.updateFrameDept(frameDept);
        return R.ok();
    }

    /**
     * 删除部门
     * <p>Title: deleteDept</p>
     * <p>Description: 部门</p>
     * @author hero
     * @param Ids
     * @return
     */
    @ApiOperation(value="删除部门")
	@ResponseBody
	@RequestMapping(value="/deleteDept/{ids}",produces="application/json;charset=utf-8",method=RequestMethod.POST)
    public R deleteDept(@PathVariable("ids") Integer[] Ids){
        deptService.deleteFrameDeptById(Ids);
        return R.ok();
    }
    
    /**
     * 查询部门树
     * <p>Title: getdeptTrees</p>  
     * <p>Description: </p>
     * @author hero  
     * @return
     */
    @ApiOperation(value="查询部门树")
	@ResponseBody
	@RequestMapping(value="/getDeptTrees",produces="application/json;charset=utf-8",method=RequestMethod.POST)
    public R getdeptTrees(){
    	JSONArray trees = deptService.findTopDepts();
    	return R.ok().put("data", trees);
    }

    /**
     * 查询上级部门
     * <p>Title: getByDeptCode</p>
     * <p>Description: </p>
     * @author wzl
     * @return
     */
    @ApiOperation(value = "查询上级部门")
    @ResponseBody
    @RequestMapping(value="/getByDeptCode/{deptCode}",produces="application/json;charset=utf-8",method=RequestMethod.GET)
    public R getByDeptCode(@PathVariable("deptCode")String deptCode){
        String name = deptService.getByDeptCode(deptCode);
        System.out.println(deptCode);
        return R.ok().put("data",name);
    }
    
    /**
     * 验证重复性
     * <p>Title: checkModuleNmae</p>  
     * <p>Description: </p>
     * @author hero  
     * @param <T>
     * @return
     */
    @ApiOperation(value="验证重复性")
    @ResponseBody
    @RequestMapping(value="/checkDept",produces="application/json;charset=utf-8",method=RequestMethod.POST)
    public <T> R checkDept(@RequestBody T t){
    	//泛型无法判断传入的值是否为空
    	int count = deptService.checkDept(t);
    	if (count >= 1) {
			return R.error("您输入的值已存在，请重新输入");
		}
    	return R.ok();
    }
}
