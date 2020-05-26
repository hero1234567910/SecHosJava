package com.basic.javaframe.wxcontroller;

import com.basic.javaframe.common.utils.R;
import com.basic.javaframe.entity.Sechos_Professor;
import com.basic.javaframe.service.Frame_AttachService;
import com.basic.javaframe.service.Sechos_DepartmentService;
import com.basic.javaframe.service.Sechos_ProfessorService;
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
@Api(value = "专家管理")
@RestController
@CrossOrigin
@RequestMapping("wx/sys/sechosprofessor")
public class Wx_Sechos_ProfessorController {

    private final static Logger logger = LoggerFactory.getLogger(Wx_Sechos_ProfessorController.class);

    @Autowired
    private Sechos_ProfessorService sechosProfessorService;

    @Autowired
    private Sechos_DepartmentService sechosDepartmentService;

    @Autowired
    private Frame_AttachService attachService;

    @ApiOperation(value = "通过rowGuid获取一条记录")
    @ResponseBody
    @RequestMapping(value = "/getDetailByGuid", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public R getDetailByGuid(@RequestBody String rowGuid) {
        Sechos_Professor sechosProfessor = sechosProfessorService.getDetailByGuid(rowGuid);
        return R.ok().put("data", sechosProfessor);
    }

    @ApiOperation(value = "根据科室获取专家列表")
    @ResponseBody
    @RequestMapping(value = "/getListByGuid", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public R getListByGuid(@RequestBody String departmentGuid) {
        List<Sechos_Professor> sechosProfessorList = sechosProfessorService.getListByGuid(departmentGuid);
        return R.ok().put("data", sechosProfessorList);
    }
}
