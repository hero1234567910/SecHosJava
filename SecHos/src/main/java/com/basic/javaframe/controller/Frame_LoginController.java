package com.basic.javaframe.controller;

import java.util.HashMap;
import java.util.Map;

import com.basic.javaframe.common.utils.AESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.basic.javaframe.common.JWT.TokenService;
import com.basic.javaframe.common.customclass.PassToken;
import com.basic.javaframe.common.customclass.UserLoginToken;
import com.basic.javaframe.common.utils.R;
import com.basic.javaframe.common.utils.Sha256;
import com.basic.javaframe.entity.Frame_User;
import com.basic.javaframe.service.Frame_UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/sys")
@Api(value="登录")
@CrossOrigin
public class Frame_LoginController {
	
	@Autowired
    Frame_UserService userService;
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
            
            return R.error("登录失败,用户不存在");
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
    @UserLoginToken
    @GetMapping("/getMessage")
    public String getMessage(){
        return "你已通过验证";
    }
	
}
