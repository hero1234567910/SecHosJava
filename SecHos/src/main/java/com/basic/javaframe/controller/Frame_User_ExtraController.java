package com.basic.javaframe.controller;

import com.basic.javaframe.common.customclass.PassToken;
import com.basic.javaframe.common.utils.*;
import com.basic.javaframe.entity.Frame_User_Extra;
import com.basic.javaframe.service.Frame_User_ExtraService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * <p>Title: UserExtraController</p>
 * <p>Description: 用户额外信息控制层</p>
 *
 * @author wzl
 */

@CrossOrigin
@Api(value = "用户额外")
@RestController
@RequestMapping("sys/userExtra")
public class Frame_User_ExtraController {
    private final static Logger logger = LoggerFactory.getLogger(Frame_User_ExtraController.class);

    @Autowired
    private Frame_User_ExtraService userExtraService;

    /**
     * 获取所有用户额外信息
     * <p>Title: getUserExtra</p>
     * <p>Description: 用户额外</p>
     *
     * @param params
     * @return
     * @author wzl
     */
    @PassToken
    @ApiOperation(value = "获取所有用户额外信息")
    @ResponseBody
    @RequestMapping(value = "/getAllExtra", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    public LayuiUtil getUserExtra(@RequestParam Map<String, Object> params) {
        Query query = new Query(params);
        List<Frame_User_Extra> userExtraList = userExtraService.getUserExtra(query);
        int total = userExtraService.getCount(query);
        PageUtils pageUtils = new PageUtils(userExtraList, total, query.getLimit(), query.getPage());
        return LayuiUtil.data(pageUtils.getTotalCount(), pageUtils.getList());
    }

    /**
     * 新增用户额外信息
     * <p>Title: insertUserExtra</p>
     * <p>Description: 新增用户额外</p>
     *
     * @param frameUserExtra
     * @return
     * @author wzl
     */
    @ApiOperation(value="新增用户额外信息")
    @ResponseBody
    @RequestMapping(value="/insertExtra",produces="application/json;charset=utf-8",method=RequestMethod.POST)
    public R insertUserExtra(@RequestBody Frame_User_Extra frameUserExtra){
        String uuid = java.util.UUID.randomUUID().toString();
        //System.out.println(uuid);
        frameUserExtra.setRowGuid(uuid);
        String uuid2 = UUID.randomUUID().toString();
        frameUserExtra.setUserGuid(uuid2);
        Date createTime = DateUtil.changeDate(new Date());
        frameUserExtra.setCreateTime(createTime);
        userExtraService.insertExtra(frameUserExtra);
        return R.ok();
    }

    /**
     * 更新用户额外信息
     * <p>Title: updateUserExtra</p>
     * <p>Description: 更新用户额外</p>
     *
     * @param id
     * @return
     * @author wzl
     */
    @ApiOperation(value = "更新用户额外")
    @ResponseBody
    @RequestMapping(value="/updateExtra/{id}",produces="application/json;charset=utf-8",method=RequestMethod.POST)
    public R updateExtra(@PathVariable("id") Integer id,@RequestBody Frame_User_Extra userExtra){
        userExtra.setRowId(id);
        userExtraService.updateExtra(userExtra);
        return R.ok();
    }

    /**
     * 删除多个用户额外信息
     * <p>Title: deleteUserExtra</p>
     * <p>Description: 删除用户额外</p>
     *
     * @param ids
     * @return
     * @author wzl
     */
    @ApiOperation(value = "删除多个用户额外信息")
    @ResponseBody
    @RequestMapping(value="/deleteExtraS/{id}",produces="application/json;charset=utf-8",method=RequestMethod.POST)
    public R deleteExtraS(@PathVariable("id")Integer[] ids){
        userExtraService.deleteExtraBatch(ids);
        return R.ok();
    }

}
