package com.basic.javaframe.common.config;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.basic.javaframe.common.customclass.PassToken;
import com.basic.javaframe.common.customclass.UserLoginToken;
import com.basic.javaframe.common.exception.MyException;
import com.basic.javaframe.entity.Frame_User;
import com.basic.javaframe.service.Frame_UserService;

/**
 * 拦截器去获取token并验证token
* <p>Title: AuthenticationInterceptor</p>  
* <p>Description: </p>  
* @author hero
 */
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    Frame_UserService userService;
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
        // 如果不是映射到方法直接通过
        if(!(object instanceof HandlerMethod)){
            return true;
        }
//        System.out.println((object instanceof HandlerMethod));
        HandlerMethod handlerMethod=(HandlerMethod)object;
        Method method=handlerMethod.getMethod();
        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) { 
                return true; 
            }    
        }            
        else{     
        	PassToken passToken = method.getAnnotation(PassToken.class);
        	if (token == null) {
        		System.out.println(token); 
                throw new MyException("无token，请重新登录",401);
            }
        	 String userId;  
             try {
                 userId = JWT.decode(token).getAudience().get(0);
             } catch (JWTDecodeException j) {
                 throw new MyException("token格式错误，请重新登录",401);
             }
             Frame_User user = userService.findUserByGuid(userId);
             if (user == null) {
                 throw new MyException("用户不存在，请重新登录",401);
             }
             // 验证 token
             JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
             try {
                 jwtVerifier.verify(token);
             } catch (JWTVerificationException e) {
                 throw new MyException("token已过期，请重新登录",401);
             }
             return true;
        }
        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(UserLoginToken.class)) {
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if (userLoginToken.required()) {
                // 执行认证
                if (token == null) { 
                    throw new MyException("无token，请重新登录",401);
                }
                // 获取 token 中的 user id
                String userId;
                try {
                     userId = JWT.decode(token).getAudience().get(0);
                } catch (JWTDecodeException j) {
                    throw new MyException("401",401);
                }
                Frame_User user = userService.findUserByGuid(userId);
                if (user == null) {
                    throw new MyException("用户不存在，请重新登录",401);
                }
                // 验证 token
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
                try {
                    jwtVerifier.verify(token);
                } catch (JWTVerificationException e) {
                    throw new MyException("token已过期，请重新登录",401);
                }
                return true;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, 
                                  HttpServletResponse httpServletResponse, 
                            Object o, ModelAndView modelAndView) throws Exception {

    }
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, 
                                          HttpServletResponse httpServletResponse, 
                                          Object o, Exception e) throws Exception {
    }
}