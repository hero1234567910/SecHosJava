package com.basic.javaframe.wxcontroller;

import com.alibaba.fastjson.JSONArray;
import com.basic.javaframe.common.utils.R;
import com.basic.javaframe.entity.Sechos_Department;
import com.basic.javaframe.service.Frame_AttachService;
import com.basic.javaframe.service.Sechos_DepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author wzl
 * @date 2019-08-28 10:11:37
 */
@Api(value = "科室管理")
@RestController
@CrossOrigin
@RequestMapping("wx/sys/sechosdepartment")
public class Wx_Sechos_DepartmentController {

    private final static Logger logger = LoggerFactory.getLogger(Wx_Sechos_DepartmentController.class);
    @Autowired
    private Sechos_DepartmentService sechosDepartmentService;

    @Autowired
    private Frame_AttachService attachService;

    @ApiOperation(value = "通过rowGuid获取一条记录")
    @ResponseBody
    @RequestMapping(value = "/getDetailByGuid", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public R getDetailByGuid(@RequestBody String rowGuid) {
        Sechos_Department sechosDepartment = sechosDepartmentService.getDetailByGuid(rowGuid);
        return R.ok().put("data", sechosDepartment);
    }

    @ApiOperation(value = "获取tree")
    @ResponseBody
    @RequestMapping(value = "/getDepartmentList", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    public R getDepartmentList() {
        JSONArray trees = sechosDepartmentService.getDepartmentList();
        return R.ok().put("data", trees);
    }

    @ApiOperation(value = "getDepartmentListToWx")
    @ResponseBody
    @RequestMapping(value = "/getDepartmentListToWx", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    public R getDepartmentListToWx() {
        List<Sechos_Department> sechosDepartmentList = sechosDepartmentService.getDepartmentListToWx();
        return R.ok().put("data", sechosDepartmentList);
    }
}
