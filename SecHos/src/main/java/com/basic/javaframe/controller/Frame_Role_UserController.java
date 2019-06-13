package com.basic.javaframe.controller;


import com.basic.javaframe.common.customclass.PassToken;
import com.basic.javaframe.common.utils.*;
import com.basic.javaframe.entity.Frame_Role_User;
import com.basic.javaframe.service.Frame_Role_UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 用户与角色关系管理 信息操作处理
 * 角色控制层
 * @author my
 * @date 2019-03-12
 */
@CrossOrigin
@Api(value = "用户与角色关系管理")
@RestController
@RequestMapping("sys/role_user")
public class Frame_Role_UserController {
    private final static Logger logger = LoggerFactory.getLogger(Frame_Role_UserController.class);

    @Autowired
    private Frame_Role_UserService role_userService;

    /**
     * 查询角色列表
     * <p>Title: getRole</p>
     * <p>Description: 角色</p>
     * @author my
     * @param params
     * @return
     */
    @PassToken
    @ApiOperation(value = "查询用户与角色关系列表")
    @ResponseBody
    @RequestMapping(value = "/getRole_User",produces="application/json;charset=utf-8",method= RequestMethod.GET)
    public LayuiUtil getRoleUser(@RequestParam Map<String, Object> params){
        Query query = new Query(params);
        List<Frame_Role_User> list = role_userService.selectFrameRoleUserList(query);
        int total = role_userService.getCount(query);
        PageUtils pageUtil = new PageUtils(list, total, query.getLimit(), query.getPage());
        return LayuiUtil.data(pageUtil.getTotalCount(), pageUtil.getList());
    }

    /**
     * 新增角色
     * <p>Title: addRoleUser</p>
     * <p>Description: 角色</p>
     * @author my
     * @param frame_role_user
     * @return
     */
    @ApiOperation(value = "新增角色")
    @ResponseBody
    @RequestMapping(value = "/add",produces="application/json;charset=utf-8",method= RequestMethod.POST)
    public R addRoleUser(@RequestBody Frame_Role_User frame_role_user){
        role_userService.insertFrameRoleUser(frame_role_user);
        return R.ok();
    }

    /**
     * 修改角色
     * <p>Title: updateRoleUser</p>
     * <p>Description: 角色</p>
     * @author my
     * @param frameRoleUser
     * @return
     */
    @ApiOperation(value = "修改角色")
    @ResponseBody
    @RequestMapping(value = "/updateRole_User/{id}",produces="application/json;charset=utf-8",method= RequestMethod.PUT)
    public R updateRoleUser(@PathVariable("id") Integer id,@RequestBody Frame_Role_User frameRoleUser){
        frameRoleUser.setRowId(id);
        role_userService.updateFrameRoleUser(frameRoleUser);
        return R.ok();
    }

    /**
     * 删除多个角色
     * <p>Title: deleteRoleUser</p>
     * <p>Description: 角色</p>
     * @author my
     * @param ids
     * @return
     */
    @ApiOperation(value="删除多个角色")
    @ResponseBody
    @RequestMapping(value="/deleteRole_User/{id}",produces="application/json;charset=utf-8",method=RequestMethod.POST)
    public R deleteRoleUser(@PathVariable("id")Integer[] ids ){
        role_userService.deleteFrameRoleUserById(ids);
        return R.ok();
    }

    /**
     * 通过角色名查询
     * <p>Title: findFrameRoleUserByName</p>
     * <p>Description: 角色名</p>
     * @author my
     * @param
     * @return
     */
    @ApiOperation(value="通过角色名查询")
    @ResponseBody
    @RequestMapping(value="/findFrameRoleUserByName/{name}",produces="application/json;charset=utf-8",method=RequestMethod.POST)
    public R selectFrameRoleByName(@PathVariable("name")String roleName ){
        role_userService.selectFrameRoleUserByName(roleName);
        return R.ok();
    }

}
