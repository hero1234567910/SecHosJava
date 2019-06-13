package com.basic.javaframe.common.exception;


import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.basic.javaframe.common.utils.R;


/**
   *  统一异常处理类
  * @ClassName: GlobalExceptionHandler 
  * @Description: 统一异常处理类
  * @author lhq
  * @date 2018年11月8日 上午10:31:14 
  *
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    @ResponseBody
    @ExceptionHandler(MyException.class)
    public R handleMyException(HttpServletRequest req,MyException e){
        R r = new R();
        r.put("code", e.getCode());
        r.put("msg", e.getMsg());
        r.put("detail",e.getMessage());
        r.put("url",req.getRequestURL().toString());

        return r;
    }
}
