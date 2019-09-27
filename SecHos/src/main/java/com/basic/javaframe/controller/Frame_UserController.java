package com.basic.javaframe.controller;

import java.io.Console;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.basic.javaframe.common.customclass.PassToken;
import com.basic.javaframe.common.customclass.UserLoginToken;
import com.basic.javaframe.common.utils.*;
import com.basic.javaframe.service.*;
import io.swagger.models.auth.In;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.basic.javaframe.entity.Frame_Role_User;
import com.basic.javaframe.entity.Frame_User;
import com.mysql.jdbc.authentication.Sha256PasswordPlugin;

import java.util.UUID;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.crypto.spec.SecretKeySpec;


/**
 * <p>Title: WxappAgreementController</p>
 * <p>Description: 用户控制层</p>
 *
 * @author hero
 */

@CrossOrigin
@Api(value = "用户")
@RestController
@RequestMapping("sys/user")
public class Frame_UserController {

    //定义一个全局的记录器，通过LoggerFactory获取
    private final static Logger logger = LoggerFactory.getLogger(Frame_UserController.class);

    @Autowired
    private Frame_UserService userService;

    @Autowired
    private Frame_Role_UserService roleUserService;

    @Autowired
    private Frame_ModuleRightService moduleRightService;

    @Autowired
    private Frame_ConfigService configService;

    @Autowired
    private Frame_DeptService frameDeptService;
    /**
     * 获取所有正常用户
     * <p>Title: getUser</p>
     * <p>Description: 用户</p>
     *
     * @param params
     * @return
     * @author hero
     */
    @PassToken
    @ApiOperation(value = "获取所有正常用户")
    @ResponseBody
    @RequestMapping(value = "/getAll", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    public LayuiUtil getUser(@RequestParam Map<String, Object> params) {
        Query query = new Query(params);
        List<Frame_User> userList = userService.getUser(query);
        int total = userService.getCount(query);
        PageUtils pageUtil = new PageUtils(userList, total, query.getLimit(), query.getPage());
        return LayuiUtil.data(pageUtil.getTotalCount(), pageUtil.getList());
    }

    /**
     * 新增用户
     * <p>Title: insert</p>
     * <p>Description: 新增用户</p>
     *
     * @param params
     * @return
     * @author hero
     */
    @ApiOperation(value = "新增用户")
    @ResponseBody
    @RequestMapping(value = "/add", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public R insert(@RequestBody Map<String, Object> params) {
        Frame_User user = JSON.parseObject(JSON.toJSONString(params.get("user")), Frame_User.class);
        //生成uuid作为rowguid
        String uuid = UUID.randomUUID().toString();
        user.setRowGuid(uuid);
        //String cu= ChineseCharacterUtil.getLowerCase(user.getUserName().toLowerCase(),false);//生成小写简拼
        //user.setLoginId(cu);
        Date createTime = DateUtil.changeDate(new Date());
        user.setCreateTime(createTime);
        //密码hash加密
        String password = AESUtil.encrypt(user.getPassword(),"expsoft1234");
        if (password == null) {
            String pass1 = configService.getDefaultPassWord();
            String pass2 = AESUtil.encrypt(pass1, "expsoft1234");
            user.setPassword(pass2);
            userService.insert(user);
        } else {
            user.setPassword(password);
            userService.insert(user);
        }

        //插入用户与角色关系
        List<String> roles = (List<String>) params.get("roles");
        if (roles.size() == 0) {
            return R.ok();
        }
        List<Frame_Role_User> roUserList = new ArrayList<>();
        for (String role : roles) {
            Frame_Role_User roUser = new Frame_Role_User();
            roUser.setUserGuid(uuid);
            roUser.setRoleGuid(role);
            roUserList.add(roUser);
        }
        roleUserService.insertBatch(roUserList);
        return R.ok();
    }

    /**
     * 更新用户
     * <p>Title: update</p>
     * <p>Description: 更新用户</p>
     *
     * @param params
     * @param
     * @return
     * @author hero
     */
    @ApiOperation(value = "更新用户")
    @ResponseBody
    @RequestMapping(value = "/update", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public R update(@RequestBody Map<String, Object> params) {
        List<String> roleGuidList = (List<String>) params.get("roleGuids");
        Frame_User user = JSON.parseObject(JSON.toJSONString(params.get("roleUser")), Frame_User.class);
        if (!"".equals(user.getPassword())) {
        	 //密码加密
            String password = AESUtil.encrypt(user.getPassword(), "expsoft1234");
            user.setPassword(password);
		}else {
			user.setPassword(null);
		}
        //先全部删除所有有关角色
        roleUserService.deleteByUserId(user.getRowGuid());
        if (roleGuidList != null && roleGuidList.size() != 0) {
        	 //再新增有关角色
            List<Frame_Role_User> roUserList = new ArrayList<>();
            for (String role : roleGuidList) {
                Frame_Role_User roUser = new Frame_Role_User();
                roUser.setUserGuid(user.getRowGuid());
                roUser.setRoleGuid(role);
                roUserList.add(roUser);
            }
            roleUserService.insertBatch(roUserList);
		}
//        String cu = ChineseCharacterUtil.getLowerCase(user.getUserName().toLowerCase(), false);//生成小写简拼
//        user.setLoginId(cu);
        userService.update(user);
        return R.ok();
    }

    /**
     * 根据用户guid查询角色list
     * <p>Title: getCheckedRole</p>
     * <p>Description: </p>
     *
     * @return
     * @author hero
     */
    @ApiOperation(value = "根据用户guid查询角色list")
    @ResponseBody
    @RequestMapping(value = "/getCheckedRole", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    public R getCheckedRole(@RequestParam String userGuid) {
        List<String> roles = roleUserService.getCheckedRole(userGuid);
        return R.ok().put("data", roles);
    }

    /**
     * 点击角色获取用户列表
     *
     * @param roleGuid
     * @return
     */
    @PassToken
    @ApiOperation(value = "点击角色获取用户列表")
    @ResponseBody
    @RequestMapping(value = "/getUserFromRole", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    public LayuiUtil getUserFromRole(@RequestParam String roleGuid, @RequestParam Map<String, Object> params) {
        Query query = new Query(params);
        List<Frame_User> list = userService.getUserFromRole(roleGuid);
        int total = list.size();
        PageUtils pageUtil = new PageUtils(list, total, query.getLimit(), query.getPage());
        return LayuiUtil.data(pageUtil.getTotalCount(), pageUtil.getList());
    }

    /**
     * 根据用户名查询所有模块
     * <p>Title: getMenuByUserGuid</p>
     * <p>Description: </p>
     *
     * @param userGuid
     * @return
     * @author hero
     */
    @ApiOperation(value = "根据用户名查询所有模块")
    @ResponseBody
    @RequestMapping(value = "/getMenuByUserGuid", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    public R getMenuByUserGuid(@RequestParam String userGuid) {
        //根据用户guid查询角色list
        List<String> roles = roleUserService.getCheckedRole(userGuid);
        JSONArray obj = new JSONArray();
        if (roles != null && roles.size() != 0) {
            for (String roleGuid : roles) {
                //通过角色查询所有模块
                JSONArray arr = moduleRightService.selectModuleByRole(roleGuid);
                if (arr != null) {
                    for (int i = 0; i < arr.size(); i++) {
                        obj.add(arr.getJSONObject(i));
                    }
                }
            }
        }
        //去重合并
//		System.out.println(roles);
//		System.out.println(obj);
        //求并集
        for (int i = 0; i < obj.size(); i++) {
            for (int j = obj.size() - 1; j > i; j--) {
                JSONObject ijs = obj.getJSONObject(i);
                JSONObject js = obj.getJSONObject(j);
                if (ijs.getString("title").equals(js.getString("title"))) {
                    js.getJSONArray("data").removeAll(ijs.getJSONArray("data"));
                    ijs.getJSONArray("data").addAll(js.getJSONArray("data"));
                    js.getJSONArray("data").clear();
                }
            }
        }
        //遍历以后删除空集合
        for (int i = 0; i < obj.size(); i++) {
        	System.out.println(i);
            JSONObject js = obj.getJSONObject(i);
            if (js.getJSONArray("data") == null || js.getJSONArray("data").size() == 0) {
                obj.remove(i);
                i--;
            }
        }
        return R.ok().put("data", obj);
    }

//	/**
//	 * 递归
//	 * <p>Title: forGet</p>
//	 * <p>Description: </p>
//	 * @author hero
//	 * @param array
//	 * @return
//	 */
//	public JSONArray forGet(JSONArray array){
////		JSONArray marray = new JSONArray();
//		for (int i = 0; i < array.size(); i++) {
////			JSONObject min = new JSONObject();
//			JSONObject m = array.getJSONObject(i);
//			if (!m.containsKey("checked")) {
//				array.remove(i);
//			}else{
//				JSONArray ar = m.getJSONArray("data");
//				forGet(ar);
//			}
//		}
//		return array;
//	}

    /**
     * 重置用户密码
     * <p>Title: update</p>
     * <p>Description: 重置用户密码</p>
     *
     * @param ids
     * @param ids
     * @return
     * @author hero
     */
    @ApiOperation(value = "重置用户密码")
    @ResponseBody
    @RequestMapping(value = "/resetPassword/{id}", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public R resetPassword(@PathVariable("id") Integer[] ids, String password) {
        password = configService.getDefaultPassWord();
        System.out.println(password);
        String pass1 = AESUtil.encrypt(password, "expsoft1234");
        userService.resetPasswordById(pass1, ids);
        return R.ok();
    }


    /**
     * 删除多个用户
     * <p>Title: deleteUser</p>
     * <p>Description: 用户</p>
     *
     * @param rowGuids
     * @return
     * @author my
     */
    @ApiOperation(value = "删除多个用户")
    @ResponseBody
    @RequestMapping(value = "/deleteUser/{rowGuid}", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public R deleteUser(@PathVariable("rowGuid") String[] rowGuids) {
        userService.deleteUserById(rowGuids);
        roleUserService.deleteRoleUserByGuid(rowGuids);
        return R.ok();
    }

    /**
     * 启用多个用户
     * <p>Title: enableUser</p>
     * <p>Description: 用户</p>
     *
     * @param ids
     * @return
     * @author wzl
     */
    @ApiOperation(value = "启用多个用户")
    @ResponseBody
    @RequestMapping(value = "/enableUser/{id}", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public R enableUser(@PathVariable("id") Integer[] ids) {
        userService.enableUserById(ids);
        return R.ok();
    }

    /**
     * 禁用多个用户
     * <p>Title: forbidUser</p>
     * <p>Description: 用户</p>
     *
     * @param ids
     * @return
     * @author wzl
     */
    @ApiOperation(value = "禁用多个用户")
    @ResponseBody
    @RequestMapping(value = "/forbidUser/{id}", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public R forbidUser(@PathVariable("id") Integer[] ids) {
        userService.forbidUserById(ids);
        return R.ok();
    }

    /**
     * 保存排序号
     * <p>Title: enableUser</p>
     * <p>Description: 用户</p>
     *
     * @param sortSq
     * @return
     * @author wzl
     */
    @ApiOperation(value = "保存排序号")
    @ResponseBody
    @RequestMapping(value = "/saveSortSq/{id}/{sortSq}", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public R saveSortSq(@PathVariable("id") Integer[] rowId, @PathVariable("sortSq") Integer[] sortSq) {
        userService.saveSortSq(rowId, sortSq);
        return R.ok();
    }

    /**
     * 通过登录名查询用户
     * <p>Title: findFrameUserByLoginId</p>
     * <p>Description: 用户</p>
     *
     * @param
     * @return
     * @author wzl
     */
    @ApiOperation(value = "通过登录名查询用户")
    @ResponseBody
    @RequestMapping(value = "/findFrameUserByLoginId/{loginId}", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public R getFrameUserByLoginId(@PathVariable("loginId") String loginId) {
        userService.getFrameUserByLoginId(loginId);
        //System.out.println(loginId);
        return R.ok();
    }

    /**
     * 通过登录名检验用户重复
     * <p>Title: checkFrameUserByLoginId</p>
     * <p>Description: 用户</p>
     *
     * @param
     * @return
     * @author wzl
     */
    @ApiOperation(value = "通过登录名检验用户")
    @ResponseBody
    @RequestMapping(value = "/checkUser", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public <T> R checkUser(@RequestBody T t){
        int count = userService.checkUser(t);
        if (count >= 1) {
            return R.error("您输入的值已存在，请重新输入");
        }
        return R.ok();
    }

    /**
     * 旧密码验证
     * <p>Title: changePassword</p>
     * <p>Description: 用户</p>
     *
     * @param
     * @return
     * @author wzl
     */
    @ApiOperation(value = "用户旧密码验证")
    @ResponseBody
    @RequestMapping(value = "/checkOldPassword/{old_pass}", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    public R checkOldPassword(String rowGuid,@PathVariable("old_pass")String old_pass){
        String old_pass2=AESUtil.encrypt(old_pass, "expsoft1234");
        System.out.println(old_pass2);
        String oldPass = userService.checkOldPassword(rowGuid);
        System.out.println(oldPass);
        if(!old_pass2.equals(oldPass)){
            return R.error("旧密码错误");
        }
        return R.ok();
    }

    /**
     * 写入新密码
     * <p>Title: changePassword</p>
     * <p>Description: 用户</p>
     *
     * @param
     * @return
     * @author wzl
     */
    @ApiOperation(value = "用户新密码写入")
    @ResponseBody
    @RequestMapping(value = "/updateNewPassword/{rowGuid}/{new_pass}", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public R updateNewPassword(@PathVariable("rowGuid")String rowGuid,@PathVariable("new_pass") String new_pass){
        String new_pass2=AESUtil.encrypt(new_pass, "expsoft1234");
        userService.updateNewPassword(rowGuid,new_pass2);
        return R.ok();
    }

    @ApiOperation(value = "更新管理员")
    @ResponseBody
    @RequestMapping(value = "/updateAdmin", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public R updateAdmin(@RequestBody Frame_User user) {
        userService.updateAdmin(user);
        return R.ok();
    }

    @ApiOperation(value = "更新用户登录时间")
    @ResponseBody
    @RequestMapping(value = "/updateLoginTime", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public R updateLoginTime(@RequestBody Frame_User frame_user){
        Date loginTime = DateUtil.changeDate(new Date());
        frame_user.setLastloginTime(loginTime);
        userService.updateLoginTime(frame_user);
        return R.ok();
    }

    @ApiOperation(value="通过Openid获取用户信息")
    @ResponseBody
    @RequestMapping(value="/getUserByOpenid",produces="application/json;charset=utf-8",method=RequestMethod.POST)
    public R getUserByOpenid(@RequestBody String openid){
        Frame_User frameUser = userService.getUserByOpenid(openid);
        return R.ok().put("data",frameUser);
    }

}

