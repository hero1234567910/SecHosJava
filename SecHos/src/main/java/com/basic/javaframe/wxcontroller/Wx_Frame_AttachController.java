package com.basic.javaframe.wxcontroller;

import com.basic.javaframe.common.customclass.PassToken;
import com.basic.javaframe.common.utils.LayuiUtil;
import com.basic.javaframe.common.utils.PageUtils;
import com.basic.javaframe.common.utils.Query;
import com.basic.javaframe.common.utils.R;
import com.basic.javaframe.controller.BaseController;
import com.basic.javaframe.entity.Frame_Attach;
import com.basic.javaframe.service.Frame_AttachService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Api(value = "/微信获取附件表")
@RestController
@CrossOrigin
@RequestMapping("wx/sys/frameAttach")
public class Wx_Frame_AttachController extends BaseController{
    @Autowired
    private Frame_AttachService frameAttachService;
    /**
     * 列表数据
     */
    @PassToken
    @ApiOperation(value="微信附件表数据")
    @ResponseBody
    @RequestMapping(value="/listData",produces="application/json;charset=utf-8",method= RequestMethod.GET)
    public LayuiUtil listData(@RequestParam Map<String, Object> params){
        Query query = new Query(params);
        List<Frame_Attach> list = frameAttachService.selectFrameAttachList(query);
        int total = frameAttachService.getCount(query);
        PageUtils pageUtil = new PageUtils(list, total, query.getLimit(), query.getPage());
        return LayuiUtil.data(pageUtil.getTotalCount(), pageUtil.getList());
    }

    @PassToken
    @ApiOperation(value="微信端根据关联guid获取附件列表")
    @ResponseBody
    @RequestMapping(value="/getAttachList",produces="application/json;charset=utf-8",method=RequestMethod.POST)
    public R getAttachList(@RequestBody Map<String,Object> params){
        String guid = params.get("guid").toString();
    	List<Frame_Attach> attachList = frameAttachService.getAttachList(guid);
    	if (attachList!=null && attachList.size()>0) {
    		for (int i = 0; i < attachList.size(); i++) {
    			attachList.get(i).setUrl(fileUrl+"/file/"+attachList.get(i).getContentUrl());
    		}
		}
    	return R.ok().put("data", attachList);
    }

}
