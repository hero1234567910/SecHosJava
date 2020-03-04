package com.basic.javaframe.wxcontroller;

import com.basic.javaframe.common.customclass.PassToken;
import com.basic.javaframe.common.utils.*;
import com.basic.javaframe.entity.SecHos_Patient;
import com.basic.javaframe.entity.Sechos_Consultation;
import com.basic.javaframe.service.SecHos_PatientService;
import com.basic.javaframe.service.Sechos_ConsultationService;
import com.basic.javaframe.service.Sechos_PopuPersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author wzl
 * @date 2019-06-26 14:30:13
 */
@Api(value = "微信获取门诊咨询信息")
@RestController
@CrossOrigin
@RequestMapping("wx/sys/sechosconsultation")
public class Wx_SecHos_ConsultationController {

    @Autowired
    private SecHos_PatientService secHosPatientService;

    @Autowired
    private Sechos_PopuPersonService sechosPopuPersonService;

    @Autowired
    private Sechos_ConsultationService sechosConsultationService;

    /**
     * 列表数据
     */
    @PassToken
    @ApiOperation(value = "获取咨询列表")
    @ResponseBody
    @RequestMapping(value = "/listData", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    public LayuiUtil listData(@RequestParam Map<String, Object> params) {
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
    @ApiOperation(value = "新增咨询信息")
    @ResponseBody
    @RequestMapping(value = "/add", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public R add(@RequestBody Sechos_Consultation sechosConsultation) {
        //如果排序号为空，则自动转为0
        if (sechosConsultation.getSortSq() == null) {
            sechosConsultation.setSortSq(0);
        }
        //生成uuid作为rowguid
        sechosConsultation.setDelFlag(0);
        String uuid = java.util.UUID.randomUUID().toString();
        sechosConsultation.setRowGuid(uuid);
        Date createTime = DateUtil.changeDate(new Date());
        Date consultationTime = DateUtil.changeDate(new Date());
        sechosConsultation.setCreateTime(createTime);
        sechosConsultation.setReplyStatus(0);
        sechosConsultation.setDelFlag(0);
        sechosConsultation.setConsultationTime(consultationTime);
        sechosConsultationService.save(sechosConsultation);
        return R.ok();
    }

    /**
     * 根据rowguid查询
     * <p>Title: queryByGuid</p>
     * <p>Description: </p>
     *
     * @return
     * @author hero
     */
    @ApiOperation(value = "根据rowGuid查询")
    @ResponseBody
    @RequestMapping(value = "/queryByGuid", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public R queryByGuid(@RequestParam String rowGuid) {
        Sechos_Consultation sechosConsultation = sechosConsultationService.queryByGuid(rowGuid);
        return R.ok().put("data", sechosConsultation);
    }

    /**
     * 查询推广次数
     *
     * @param params
     * @return
     */
    @ApiOperation(value = "查询推广次数")
    @ResponseBody
    @RequestMapping(value = "/getMyPopuCount", produces = "application/json; charset=utf-8", method = RequestMethod.POST)
    public R getMyPopuCount(@RequestParam Map<String, Object> params) {
        String promotersGuid = params.get("promotersGuid").toString();
        int count = sechosPopuPersonService.getMyPopuCount(promotersGuid);
        return R.ok().put("data", count);
    }

    /**
     * 查询被推广人列表
     *
     * @param params
     * @return
     */
    @ApiOperation(value = "查询被推广人列表")
    @ResponseBody
    @RequestMapping(value = "/getPatientListByOpenId", produces = "application/json; charset=utf-8", method = RequestMethod.POST)
    public R getPatientListByOpenId(@RequestParam Map<String, Object> params) {
        String promotersGuid = params.get("promotersGuid").toString();
        Integer offset = Integer.parseInt(params.get("offset").toString());
        Integer limit = Integer.parseInt(params.get("limit").toString());
        System.out.println(promotersGuid+"--"+offset+"--"+limit);
        List<SecHos_Patient> secHosPatientList = secHosPatientService.getPatientListByOpenId(promotersGuid, offset, limit);
        return R.ok().put("data", secHosPatientList);
    }

}
