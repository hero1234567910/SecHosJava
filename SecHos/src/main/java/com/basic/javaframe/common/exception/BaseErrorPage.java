//package com.basic.javaframe.common.exception;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.web.servlet.error.ErrorController;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//
///**
//  *  错误页面 控制器
//  * @ClassName: BaseErrorPage 
//  * @Description: 错误页面 控制器
//  * @author lhq
//  * @date 2018年11月8日 上午10:30:28 
//  *
// */
//@Controller
//@RequestMapping("/error")
//public class BaseErrorPage  implements ErrorController {

//    Logger logger = LoggerFactory.getLogger(ErrorController.class);
//
//    @Override
//    public String getErrorPath() {
//        logger.info("进入自定义错误页面");
//        return "";
//    }
//
//    @RequestMapping
//    public String error() {
//        return getErrorPath();
//    }
//}