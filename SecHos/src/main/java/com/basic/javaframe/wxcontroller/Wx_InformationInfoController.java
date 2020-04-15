package com.basic.javaframe.wxcontroller;

import com.basic.javaframe.common.customclass.PassToken;
import com.basic.javaframe.common.utils.*;
import com.basic.javaframe.entity.InformationInfo;
import com.basic.javaframe.service.Frame_AttachService;
import com.basic.javaframe.service.Frame_UserService;
import com.basic.javaframe.service.InformationInfoService;
import com.basic.javaframe.service.Information_Info_CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * <p>InformationInfoController</p>
 * <p>微信端信息查询控制层</p>
 *
 * @author wzl
 * @date 2019-06-20 15:11:17
 */
@Api(value = "微信端信息查询")
@RestController
@CrossOrigin
@RequestMapping("wx/sys/informationinfo")
public class Wx_InformationInfoController {

    private final static Logger logger = LoggerFactory.getLogger(Wx_InformationInfoController.class);
    @Autowired
    private InformationInfoService informationInfoService;

    @Autowired
    private Frame_AttachService frameAttachService;

    @Autowired
    private Information_Info_CategoryService infoCategoryService;
    @Autowired
    private Frame_UserService userService;
    
    /**
	 * 获取发布的通告信息
	 * <p>Title: getInforMation</p>  
	 * <p>Description: </p>
	 * @author hero  
	 * @return
	 */
	@ApiOperation(value="获取发布的通告信息")
	@ResponseBody
	@RequestMapping(value="/getInforMation",produces="application/json;charset=utf-8",method=RequestMethod.GET)
	public R getInforMation(){
		String info = informationInfoService.getMInfoMation();
		return R.ok().put("data", info);
	}
	
	/**
	 * 获取微信端展示的通告信息
	 * <p>Title: getInforMation</p>  
	 * <p>Description: </p>
	 * @author hero  
	 * @return
	 */
	@ApiOperation(value="获取发布的通告信息")
	@ResponseBody
	@RequestMapping(value="/getWxInformation",produces="application/json;charset=utf-8",method=RequestMethod.GET)
	public R getWxInformation(){
		InformationInfo informationInfo = informationInfoService.getWxInformation();
		return R.ok().put("data", informationInfo);
	}
	
    /**
     * 审核通过列表数据
     */
    @ApiOperation(value = "获取审核通过的发布信息")
    @ResponseBody
    @RequestMapping(value = "/listData2", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public LayuiUtil listData2(@RequestBody Map<String, Object> params) {
        InformationInfo informationInfo = new InformationInfo();
        informationInfoService.getTypeName(informationInfo);
        if (params.get("categorys") != null && params.get("categorys") != "") {
            String[] arr = params.get("categorys").toString().split(",");
            params.put("categorys", arr);
        }
        Query query = new Query(params);
        List<InformationInfo> informationInfoList = informationInfoService.getList2(query);
        int total = informationInfoService.getCount2(query);
        PageUtils pageUtil = new PageUtils(informationInfoList, total, query.getLimit(), query.getPage());
        return LayuiUtil.data(pageUtil.getTotalCount(), pageUtil.getList());

    }
    
    /**
     * 列表数据
     */
    @PassToken
    @ApiOperation(value = "获取所有发布信息")
    @ResponseBody
    @RequestMapping(value = "/listData", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    public LayuiUtil listData(@RequestParam Map<String, Object> params) {
        InformationInfo informationInfo = new InformationInfo();
        informationInfoService.getTypeName(informationInfo);
        if (params.get("categorys") != null && params.get("categorys") != "") {
            String[] arr = params.get("categorys").toString().split(",");
            params.put("categorys", arr);
        }
        //查询列表数据
        Query query = new Query(params);
        List<InformationInfo> informationInfoList = informationInfoService.getList(query);
        int total = informationInfoService.getCount(query);
        PageUtils pageUtil = new PageUtils(informationInfoList, total, query.getLimit(), query.getPage());
        return LayuiUtil.data(pageUtil.getTotalCount(), pageUtil.getList());
    }

    /**
     * 信息发布到微信端
     */
    @ApiOperation(value = "信息发布到微信端")
    @ResponseBody
    @RequestMapping(value = "/infoDelivery/{rowId}", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public R auditFailInfo(@PathVariable("rowId") Integer rowId) {
        informationInfoService.infoOff(rowId);
        informationInfoService.infoOn(rowId);
        return R.ok();
    }

    /**
     * 微信端获取医院简介信息
     * @param categoryGuid
     * @return
     */
    @PassToken
    @ApiOperation(value = "微信端获取医院简介信息")
    @ResponseBody
    @RequestMapping(value = "/getHosIntroduction",produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    public R getHosIntroduction(String categoryGuid){
        categoryGuid = "e83ae009-d5f3-45ee-9646-6c55e713efc1";
        String infoGuid = infoCategoryService.getSingleInfoByCateGuid(categoryGuid);
        //System.out.println(infoGuid);
        InformationInfo informationInfo = informationInfoService.getHosIntroduction(infoGuid);
        //System.out.println(informationInfo);
        return R.ok().put("data",informationInfo);
    }

    /**
     * 微信端获取门诊流程信息
     * @param categoryGuid
     * @return
     */
    @PassToken
    @ApiOperation(value = "微信端获取门诊流程信息")
    @ResponseBody
    @RequestMapping(value = "/getOutpatientProcess",produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    public R getOutpatientProcess(String categoryGuid){
        categoryGuid = "06311ef8-b1a6-451e-a9dd-c0f72f3e303b";
        String infoGuid = infoCategoryService.getSingleInfoByCateGuid(categoryGuid);
        InformationInfo informationInfo = informationInfoService.getHosIntroduction(infoGuid);
        return R.ok().put("data",informationInfo);
    }

    /**
     * 微信端获取楼层分布信息
     * @param categoryGuid
     * @return
     */
    @PassToken
    @ApiOperation(value = "微信端获取楼层分布信息")
    @ResponseBody
    @RequestMapping(value = "/getFloorDistribution",produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    public R getFloorDistribution(String categoryGuid){
        categoryGuid = "0e647242-225e-4e09-8864-4c6ba2d031c3";
        String infoGuid = infoCategoryService.getSingleInfoByCateGuid(categoryGuid);
        InformationInfo informationInfo = informationInfoService.getHosIntroduction(infoGuid);
        return R.ok().put("data",informationInfo);
    }

    /**
     * 微信端获取就诊须知信息
     * @param categoryGuid
     * @return
     */
    @PassToken
    @ApiOperation(value = "微信端获取就诊须知信息")
    @ResponseBody
    @RequestMapping(value = "/getPatientNeedtoKnow",produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    public R getPatientsNeedtoKnow(String categoryGuid){
        categoryGuid = "870d1b38-5615-4f42-9c3f-09ce31c33d45";
        String infoGuid = infoCategoryService.getSingleInfoByCateGuid(categoryGuid);
        InformationInfo informationInfo = informationInfoService.getHosIntroduction(infoGuid);
        return R.ok().put("data",informationInfo);
    }

    /**
     * 微信端获取入院导引信息
     * @param categoryGuid
     * @return
     */
    @PassToken
    @ApiOperation(value = "微信端获取入院导引信息")
    @ResponseBody
    @RequestMapping(value = "/getDirectAdmission",produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    public R getDirectAdmission(String categoryGuid){
        categoryGuid = "b4ccf311-761b-4a86-a203-21b9a7e17039";
        String infoGuid = infoCategoryService.getSingleInfoByCateGuid(categoryGuid);
        InformationInfo informationInfo = informationInfoService.getHosIntroduction(infoGuid);
        return R.ok().put("data",informationInfo);
    }

    /**
     * 微信端获取住院须知信息
     * @param categoryGuid
     * @return
     */
    @PassToken
    @ApiOperation(value = "微信端获取住院须知信息")
    @ResponseBody
    @RequestMapping(value = "/getHospitalisation",produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    public R getHospitalisation(String categoryGuid){
        categoryGuid = "eff540e6-10cf-4012-8508-22d893dd0f9a";
        String infoGuid = infoCategoryService.getSingleInfoByCateGuid(categoryGuid);
        InformationInfo informationInfo = informationInfoService.getHosIntroduction(infoGuid);
        return R.ok().put("data",informationInfo);
    }

    /**
     * 微信端获取出院导引信息
     * @param categoryGuid
     * @return
     */
    @PassToken
    @ApiOperation(value = "微信端获取出院导引信息")
    @ResponseBody
    @RequestMapping(value = "/getOutHospitalInfo",produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    public R getOutHospitalInfo(String categoryGuid){
        categoryGuid = "914fcc0a-8db4-4ba4-a1d2-38e0d5bdf12e";
        String infoGuid = infoCategoryService.getSingleInfoByCateGuid(categoryGuid);
        InformationInfo informationInfo = informationInfoService.getHosIntroduction(infoGuid);
        return R.ok().put("data",informationInfo);
    }

    /**
     * 微信端获取出院须知信息
     * @param categoryGuid
     * @return
     */
    @PassToken
    @ApiOperation(value = "微信端获取出院须知信息")
    @ResponseBody
    @RequestMapping(value = "/getOutNeedKnow",produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    public R getOutNeedKnow(String categoryGuid){
        categoryGuid = "6b6b12c7-943e-4e11-8837-35d67d948f22";
        String infoGuid = infoCategoryService.getSingleInfoByCateGuid(categoryGuid);
        InformationInfo informationInfo = informationInfoService.getHosIntroduction(infoGuid);
        return R.ok().put("data",informationInfo);
    }

    /**
     * 微信端获取科室简介信息
     * @param categoryGuid
     * @return
     */
    @PassToken
    @ApiOperation(value = "微信端获取科室简介信息")
    @ResponseBody
    @RequestMapping(value = "/getDepartmentsIntroduction",produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    public R getDepartmentsIntroduction(String categoryGuid){
        categoryGuid = "e1cd7e1d-bc09-42c4-9eed-6f8d8181fb06";
        String infoGuid = infoCategoryService.getSingleInfoByCateGuid(categoryGuid);
        InformationInfo informationInfo = informationInfoService.getHosIntroduction(infoGuid);
        return R.ok().put("data",informationInfo);
    }

    /**
     * 微信端获取健康教育信息
     * @param categoryGuid
     * @return
     */
    @PassToken
    @ApiOperation(value = "微信端获取健康教育信息")
    @ResponseBody
    @RequestMapping(value = "/getHealthEducation",produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    public R getHealthEducation(String categoryGuid){
        categoryGuid = "7d1c5ff5-869d-4014-b1b1-6a757963efbb";
        String infoGuid = infoCategoryService.getSingleInfoByCateGuid(categoryGuid);
        InformationInfo informationInfo = informationInfoService.getHosIntroduction(infoGuid);
        return R.ok().put("data",informationInfo);
    }

    /**
     * 微信端获取导诊台信息
     * @param categoryGuid
     * @return
     */
    @PassToken
    @ApiOperation(value = "微信端获取导诊台信息")
    @ResponseBody
    @RequestMapping(value = "/getHospitalGuide",produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    public R getHospitalGuide(String categoryGuid){
        categoryGuid = "1e582b7f-e01e-4d79-9633-e268467cd944";
        String infoGuid = infoCategoryService.getSingleInfoByCateGuid(categoryGuid);
        InformationInfo informationInfo = informationInfoService.getHosIntroduction(infoGuid);
        return R.ok().put("data",informationInfo);
    }

    /**
     * 微信端获取出院引导信息
     * @param categoryGuid
     * @return
     */
    @PassToken
    @ApiOperation(value = "微信端获取出院引导信息")
    @ResponseBody
    @RequestMapping(value = "/getOutHospitalGuide",produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    public R getOutHospitalGuide(String categoryGuid){
        categoryGuid = "0b385514-a21f-4124-af84-6fda97baa342";
        String infoGuid = infoCategoryService.getSingleInfoByCateGuid(categoryGuid);
        InformationInfo informationInfo = informationInfoService.getHosIntroduction(infoGuid);
        return R.ok().put("data",informationInfo);
    }

    /**
     * 微信端获取复诊预约信息
     * @param categoryGuid
     * @return
     */
    @PassToken
    @ApiOperation(value = "微信端获取复诊预约信息")
    @ResponseBody
    @RequestMapping(value = "/getFollowAppointment",produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    public R getFollowAppointment(String categoryGuid){
        categoryGuid = "c769abe9-9990-44f6-bb65-adde3b920404";
        String infoGuid = infoCategoryService.getSingleInfoByCateGuid(categoryGuid);
        InformationInfo informationInfo = informationInfoService.getHosIntroduction(infoGuid);
        return R.ok().put("data",informationInfo);
    }

    /**
     * 微信端获取体检预约信息
     * @param categoryGuid
     * @return
     */
    @PassToken
    @ApiOperation(value = "微信端获取体检预约信息")
    @ResponseBody
    @RequestMapping(value = "/getMedicalAppointment",produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    public R getMedicalAppointment(String categoryGuid){
        categoryGuid = "eea0b2f9-ca27-4da6-82de-679957646e6a";
        String infoGuid = infoCategoryService.getSingleInfoByCateGuid(categoryGuid);
        InformationInfo informationInfo = informationInfoService.getHosIntroduction(infoGuid);
        return R.ok().put("data",informationInfo);
    }

    /**
     * 微信端获取通知公告
     * @param categoryGuid
     * @return
     */
    @PassToken
    @ApiOperation(value = "微信端获取通知公告")
    @ResponseBody
    @RequestMapping(value = "/getAnnouncements",produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    public R getAnnouncements(String categoryGuid){
        categoryGuid = "2a05a798-cd06-46ed-ad06-9a6dfb3d7757";
        String infoGuid = infoCategoryService.getSingleInfoByCateGuid(categoryGuid);
        InformationInfo informationInfo = informationInfoService.getHosIntroduction(infoGuid);
        return R.ok().put("data",informationInfo);
    }

    /**
     * 微信端获取门诊介绍
     * @param
     * @return
     */
    @PassToken
    @ApiOperation(value = "微信端获取门诊介绍")
    @ResponseBody
    @RequestMapping(value = "/getWxInfo",produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    public R getWxInfo(){
        String categoryGuid = "c5e6a261-c690-4ac6-9fce-7a9d74d0ff0d";
        String infoGuid = infoCategoryService.getSingleInfoByCateGuid(categoryGuid);
        InformationInfo informationInfo = informationInfoService.getHosIntroduction(infoGuid);
        return R.ok().put("data",informationInfo);
    }

    /**
     * 微信端获取住院介绍
     * @param
     * @return
     */
    @PassToken
    @ApiOperation(value = "微信端获取住院介绍")
    @ResponseBody
    @RequestMapping(value = "/getWxInfo2",produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    public R getWxInfo2(){
        String categoryGuid = "cac1c7c3-2c5d-4d96-bb51-b0c236239bf3";
        String infoGuid = infoCategoryService.getSingleInfoByCateGuid(categoryGuid);
        InformationInfo informationInfo = informationInfoService.getHosIntroduction(infoGuid);
        return R.ok().put("data",informationInfo);
    }
}
