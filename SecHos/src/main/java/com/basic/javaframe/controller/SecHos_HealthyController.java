package com.basic.javaframe.controller;

import com.basic.javaframe.common.customclass.PassToken;
import com.basic.javaframe.common.utils.*;
import com.basic.javaframe.entity.SecHos_Healthy;
import com.basic.javaframe.entity.SecHos_hospitalized;
import com.basic.javaframe.service.SecHos_HealthyService;
import com.basic.javaframe.service.SecHos_hospitalizedService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("sys/sechosHealthy")
public class SecHos_HealthyController {

    @Autowired
    private SecHos_HealthyService secHos_HealthyService;

    /**
     * 列表数据
     */
    @PassToken
    @ResponseBody
    @RequestMapping(value="/listData",produces="application/json;charset=utf-8",method= RequestMethod.POST)
    public LayuiUtil listData(@RequestBody Map<String, Object> params){
        //查询列表数据
        Query query = new Query(params);
        List<SecHos_Healthy> SecHos_HealthyList = secHos_HealthyService.getList(query);
        int total = secHos_HealthyService.getCount(query);
        PageUtils pageUtil = new PageUtils(SecHos_HealthyList, total, query.getLimit(), query.getPage());
        return LayuiUtil.data(pageUtil.getTotalCount(), pageUtil.getList());
    }

    /**
     * 新增
     **/
    @PassToken
    @ResponseBody
    @RequestMapping(value="/add",produces="application/json;charset=utf-8",method=RequestMethod.POST)
    public R add(@RequestBody SecHos_Healthy secHos_Healthy){
        //如果排序号为空，则自动转为0
        if (secHos_Healthy.getSortSq() == null) {
            secHos_Healthy.setSortSq(0);
        }
        //生成uuid作为rowguid
        String uuid = java.util.UUID.randomUUID().toString();
        secHos_Healthy.setRowGuid(uuid);
        Date createTime = DateUtil.changeDate(new Date());
        secHos_Healthy.setCreateTime(createTime);
        secHos_Healthy.setDelFlag(0);
        secHos_HealthyService.save(secHos_Healthy);
        return R.ok();
    }

    /**
     * 修改
     */
    @PassToken
    @ResponseBody
    @RequestMapping(value="/update", produces = "application/json; charset=utf-8", method=RequestMethod.POST)
    public R update(@RequestBody SecHos_Healthy secHos_Healthy){
        secHos_HealthyService.update(secHos_Healthy);
        return R.ok();
    }

    /**
     * 删除
     */
    @PassToken
    @ResponseBody
    @RequestMapping(value="/delete",produces="application/json;charset=utf-8",method=RequestMethod.POST)
    public R delete(@RequestBody String[] rowGuids){
        secHos_HealthyService.deleteBatch(rowGuids);
        return R.ok();
    }

    /**
     * 查询
     */
    @PassToken
    @ResponseBody
    @RequestMapping(value="/getByDetail",produces="application/json;charset=utf-8",method=RequestMethod.POST)
    public R getByDetail(@RequestBody SecHos_Healthy secHos_Healthy){
        SecHos_Healthy healthy = secHos_HealthyService.getByDetail(secHos_Healthy.getOpenId(),secHos_Healthy.getDate());
        if(healthy != null){
            return R.error("今日已填报,无需再次填报");
        }else {
            return R.ok();
        }
    }

    /**
     * 查询
     */
    @PassToken
    @ResponseBody
    @RequestMapping(value="/getByOpenId",produces="application/json;charset=utf-8",method=RequestMethod.POST)
    public R getByOpenId(@RequestParam String openId){
        SecHos_Healthy healthy = secHos_HealthyService.getByOpenId(openId);
        return R.ok().put("data",healthy);
    }

    /**
     * 查询
     */
    @PassToken
    @ResponseBody
    @RequestMapping(value="/getByRowGuid",produces="application/json;charset=utf-8",method=RequestMethod.POST)
    public R getByRowGuid(@RequestParam String rowGuid){
        SecHos_Healthy healthy = secHos_HealthyService.getByRowGuid(rowGuid);
        return R.ok().put("data",healthy);
    }

    /**
     * 导出记录
     */
    @PassToken
    @ResponseBody
    @RequestMapping(value="/exportInfo")
    public void exportInfo(@RequestParam String name,HttpServletRequest request,HttpServletResponse response){

        List<SecHos_Healthy> healList = secHos_HealthyService.getListByName(name);

        if(healList == null){
            throw new RuntimeException("暂无数据");
        }

        for (SecHos_Healthy heal : healList) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String time = "";
            if (heal.getDate() != null) {
                time = sdf.format(heal.getDate());
            }
            //类型不同，参数借用存放
            heal.setRowGuid(time);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("data", healList);
        map.put("name",healList.get(0).getName());
        map.put("height",healList.get(0).getHeight());
        map.put("sex",healList.get(0).getSex());
        try {
            ExcelUtils.exportExcel("填报信息表", "填报信息表", map, response, request);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 导出记录
     */
    @PassToken
    @ResponseBody
    @RequestMapping(value="/exportInfos")
    public void exportInfos(@RequestParam String dateTime,HttpServletRequest request,HttpServletResponse response){


        SimpleDateFormat sdf1 =new SimpleDateFormat("yyyy-MM-dd");
        List<SecHos_Healthy> healList = new ArrayList<>();
        try {
            Date date = sdf1.parse(dateTime);

            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.DAY_OF_MONTH,-1);


            healList  = secHos_HealthyService.getListByCurrentDay(date);
        }catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }



        if(healList == null){
            throw new RuntimeException("暂无数据");
        }

        for (SecHos_Healthy heal : healList) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String time = "";
            if (heal.getDate() != null) {
                time = sdf.format(heal.getDate());
            }
            //类型不同，参数借用存放
            heal.setRowGuid(time);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("data", healList);
//        map.put("name",healList.get(0).getName());
//        map.put("height",healList.get(0).getHeight());
//        map.put("sex",healList.get(0).getSex());
        try {
            ExcelUtils.exportExcel("填报信息表全部", "填报信息表全部", map, response, request);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
