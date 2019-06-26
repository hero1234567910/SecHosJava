package com.basic.javaframe.wxcontroller;

import com.basic.javaframe.common.customclass.PassToken;
import com.basic.javaframe.common.utils.*;
import com.basic.javaframe.entity.Sechos_Consultation;
import com.basic.javaframe.service.Sechos_ConsultationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author wzl
 * @date 2019-06-26 14:30:13
 */
@Api(value = "微信获取门诊咨询信息")
@RestController
@CrossOrigin
@RequestMapping("wx/sys/sechosconsultation")
public class Wx_SecHos_ConsultationController {

    @Autowired
    private Sechos_ConsultationService sechosConsultationService;
    /**
     * 列表数据
     */
    @PassToken
    @ApiOperation(value="获取咨询列表")
    @ResponseBody
    @RequestMapping(value="/listData",produces="application/json;charset=utf-8",method= RequestMethod.GET)
    public LayuiUtil listData(@RequestParam Map<String, Object> params){
        //查询列表数据
        Query query = new Query(params);
        List<Sechos_Consultation> sechosConsultationList = sechosConsultationService.getList(query);
        int total = sechosConsultationService.getCount(query);
        PageUtils pageUtil = new PageUtils(sechosConsultationList, total, query.getLimit(), query.getPage());
        return LayuiUtil.data(pageUtil.getTotalCount(), pageUtil.getList());
    }

    /**
     * 新增
     **/
    @ApiOperation(value="新增咨询信息")
    @ResponseBody
    @RequestMapping(value="/add",produces="application/json;charset=utf-8",method=RequestMethod.POST)
    public R add(@RequestBody Sechos_Consultation sechosConsultation){
        //如果排序号为空，则自动转为0
        if (sechosConsultation.getSortSq() == null) {
            sechosConsultation.setSortSq(0);
        }
        //生成uuid作为rowguid
        String uuid = java.util.UUID.randomUUID().toString();
        sechosConsultation.setRowGuid(uuid);
        Date createTime = DateUtil.changeDate(new Date());
        Date consultationTime = DateUtil.changeDate(new Date());
        sechosConsultation.setCreateTime(createTime);
        sechosConsultation.setConsultationTime(consultationTime);
        sechosConsultationService.save(sechosConsultation);
        return R.ok();
    }


}
