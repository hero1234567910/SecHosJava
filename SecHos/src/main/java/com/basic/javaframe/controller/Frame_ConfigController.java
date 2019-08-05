package com.basic.javaframe.controller;

import com.basic.javaframe.common.customclass.PassToken;
import com.basic.javaframe.common.utils.*;
import com.basic.javaframe.entity.Frame_Config;
import com.basic.javaframe.service.Frame_CodeValueService;
import com.basic.javaframe.service.Frame_ConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@CrossOrigin
@Api(value = "系统参数")
@RestController
@RequestMapping("sys/config")
public class Frame_ConfigController {
    private final static Logger logger = LoggerFactory.getLogger(Frame_ConfigController.class);

    @Autowired
    private Frame_ConfigService configService;
    
    @Autowired
    private Frame_CodeValueService codeValueService;

    /**
     * 获取所有系统参数
     * <p>Title: getConfig</p>
     * <p>Description: 系统参数类别</p>
     *
     * @param params
     * @return
     * @author wzl
     */
    @PassToken
    @ApiOperation(value = "获取所有系统参数")
    @ResponseBody
    @RequestMapping(value = "/getConfig", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    public LayuiUtil getConfig(@RequestParam Map<String, Object> params) {
        Query query = new Query(params);
        List<Frame_Config> configList = configService.getConfig(query);
        int total = configService.getCount(query);
        PageUtils pageUtil = new PageUtils(configList, total, query.getLimit(), query.getPage());
        return LayuiUtil.data(pageUtil.getTotalCount(), pageUtil.getList());
    }

    /**
     * 新增系统参数
     * <p>Title: insert</p>
     * <p>Description: 新增系统参数类别</p>
     *
     * @param config
     * @return
     * @author wzl
     */
    @ApiOperation(value = "新增系统参数")
    @ResponseBody
    @RequestMapping(value = "/insertConfig", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public R insertConfig(@RequestBody Frame_Config config) {
        //生成uuid作为rowguid
        String uuid = UUID.randomUUID().toString();
        config.setRowGuid(uuid);
        configService.insertConfig(config);
        return R.ok();
    }

    /**
     * 更新系统参数类别
     * <p>Title: update</p>
     * <p>Description: 更新系统参数</p>
     *
     * @param id
     * @param config
     * @return
     * @author wzl
     */
    @ApiOperation(value = "更新系统参数")
    @ResponseBody
    @RequestMapping(value = "/updateConfig/{id}", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public R updateConfig(@PathVariable("id") Integer id, @RequestBody Frame_Config config) {
        config.setRowId(id);
        configService.updateConfig(config);
        return R.ok();
    }

    /**
     * 删除系统参数
     * <p>Title: delete</p>
     * <p>Description: 系统参数</p>
     *
     * @param ids
     * @return
     * @author wzl
     */
    @ApiOperation(value = "删除多个系统参数")
    @ResponseBody
    @RequestMapping(value = "/deleteConfig/{id}", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public R deleteConfig(@PathVariable("id") Integer[] ids) {
        configService.deleteConfig(ids);
        return R.ok();
    }

    /**
     * 验证参数名重复性
     * @param t
     * @param <T>
     * @return
     */
    @ApiOperation(value = "验证参数名重复性")
    @ResponseBody
    @RequestMapping(value = "/checkConfigName", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public <T> R checkConfigName(@RequestBody T t) {
        int count = configService.checkConfigName(t);
        if (count >= 1) {
            return R.error("您输入的值已存在，请重新输入");
        }
        return R.ok();
    }
    
    /**
     * 是否接单
     * <p>Title: changeAccept</p>  
     * <p>Description: </p>
     * @author hero  
     * @return
     */
    @ApiOperation(value="改变接单状态")
    @ResponseBody
    @RequestMapping(value="/changeAccept",produces="application/json;charset=utf-8",method = RequestMethod.POST)
    public R changeAccept(@RequestBody String code){
    	//获取当前配置
    	Frame_Config config = configService.getConfigByName("是否接单");
    	if (code.equals(config.getConfigValue())) {
			return R.ok();
		}else{
			config.setConfigValue(code);
			configService.updateConfig(config);
		}
    	return R.ok();
    }
    
    /**
     * 查询订单接收状态
     * <p>Title: queryReceiveOrder</p>  
     * <p>Description: </p>
     * @author hero  
     * @return
     */
    @ApiOperation(value="查询订单接收状态")
    @ResponseBody
    @RequestMapping(value="/queryReceiveOrder",produces="application/json;charset=utf-8",method=RequestMethod.GET)
    public R queryReceiveOrder(){
    	//获取当前配置
    	Frame_Config config = configService.getConfigByName("是否接单");
    	return R.ok().put("data", config);
    }

}
