package com.basic.javaframe.common.customclass;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 需要登录才能进行操作的注解
* <p>Title: UserLoginToken</p>  
* <p>Description: </p>  
* @author hero
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserLoginToken {
    boolean required() default true;
}	
