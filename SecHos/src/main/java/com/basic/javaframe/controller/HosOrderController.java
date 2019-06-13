package com.basic.javaframe.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.*;
import java.util.UUID;

import com.basic.javaframe.common.utils.*;
import com.basic.javaframe.service.HosGoodsService;
import com.basic.javaframe.service.HosOrderitemService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.alibaba.fastjson.JSONArray;
import com.basic.javaframe.common.customclass.PassToken;
import com.basic.javaframe.common.enumresource.OrderStatuEnum;
import com.basic.javaframe.entity.HosOrder;
import com.basic.javaframe.service.HosOrderService;


/**
 * @author my
 * @date 2019-04-29 08:39:45
 */
@Api(value = "订单管理")
@RestController
@CrossOrigin
@RequestMapping("sys/hosorder")
public class HosOrderController {
    private final static Logger logger = LoggerFactory.getLogger(HosOrderController.class);
    @Autowired
    private HosOrderService hosOrderService;

    @Autowired
    private HosOrderitemService hosOrderitemService;

    @Autowired
    private HosGoodsService hosGoodsService;
    /**
     * 列表数据
     */
    @PassToken
    @ApiOperation(value = "查询订单列表")
    @ResponseBody
    @RequestMapping(value = "/listData", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    public LayuiUtil listData(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<HosOrder> hosOrderList = hosOrderService.getList(query);
        int total = hosOrderService.getCount(query);
        PageUtils pageUtil = new PageUtils(hosOrderList, total, query.getLimit(), query.getPage());
        return LayuiUtil.data(pageUtil.getTotalCount(), pageUtil.getList());
    }

    /**
     * 新增
     **/
    @ApiOperation(value = "新增订单列表")
    @ResponseBody
    @RequestMapping(value = "/add", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public R add(@RequestBody HosOrder hosOrder) {
        //如果排序号为空，则自动转为0
        if (hosOrder.getSortSq() == null) {
            hosOrder.setSortSq(0);
        }
        //生成uuid作为rowguid
        String uuid = java.util.UUID.randomUUID().toString();
        hosOrder.setRowGuid(uuid);
        hosOrder.setOrderNumber(UUID.randomUUID().toString());
        hosOrder.setDelFlag(0);
        Date createTime = DateUtil.changeDate(new Date());
        hosOrder.setCreateTime(createTime);
        hosOrderService.save(hosOrder);
        return R.ok();
    }

    /**
     * 根据商户订单号查询
     * <p>Title: queryByOrderNumber</p>
     * <p>Description: </p>
     *
     * @return
     * @author hero
     */
    @ApiOperation(value = "根据商户订单号查询")
    @ResponseBody
    @RequestMapping(value = "", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public R queryByOrderNumber(@RequestParam String orderNumber) {
        HosOrder hosOrder = hosOrderService.queryByOrderNumber(orderNumber);

        return R.ok().put("data", hosOrder);
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改订单列表")
    @ResponseBody
    @RequestMapping(value = "/update", produces = "application/json; charset=utf-8", method = RequestMethod.PUT)
    public R update(@RequestBody HosOrder hosOrder) {
        hosOrderService.update(hosOrder);
        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除订单列表")
    @ResponseBody
    @RequestMapping(value = "/delete", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public R delete(@RequestBody String[] rowGuids) {
        hosOrderService.deleteBatch(rowGuids);
        return R.ok();
    }

    /**
     * excel导出
     *
     * @param @param request
     * @param @param response    设定文件
     * @return void    返回类型
     * @throws
     * @Title: exportExcel
     * @Description: excel导出
     */
    @PassToken
    @ApiOperation(value = "导出Excel")
    @ResponseBody
    @RequestMapping(value = "/exportExcel", produces = "application/json; charset=utf-8", method = RequestMethod.GET)
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) {
        try {
            String[] firstTitle = {"订单流水号", "收货人姓名", "收货人手机号", "订单状态", "预定送达时间", "预定时间", "总金额", "备注"};
            HSSFWorkbook workbook = ExcelUtil.makeFirstHead("订单表", firstTitle);
            String[] beanProperty = {"orderNumber", "consigneeName", "consigneeMobile", "orderStatus", "reserveTime", "reserveTimeSuffix", "orderMoney", "remark"};
            Map map = new HashMap();
            List<HosOrder> hosOrderList = hosOrderService.getList(map);
            
            HSSFWorkbook workbook1 = ExcelUtil.exportExcelData(workbook,hosOrderList,beanProperty);

            String fileName = "订单表_"+DateUtil.getY_m_d(new Date())+".xls";
            //提示浏览器以下载文件的形式接受
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
            OutputStream outputStream = response.getOutputStream();
            workbook1.write(outputStream);
            outputStream.flush();
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过用户Guid查询用户订单信息
     * <p>Title: getOrderListByUserGuid</p>
     * <p>Description: 用户</p>
     * @param orderUserGuid
     * @return
     */
    @ApiOperation(value="通过用户Guid查询用户订单信息")
    @ResponseBody
    @RequestMapping(value="/getOrderListByUserGuid",produces="application/json;charset=utf-8",method=RequestMethod.POST)
    public R getOrderListByUserGuid(@RequestBody String orderUserGuid){
        List<HosOrder> hosOrderList = hosOrderService.getOrderListByUserGuid(orderUserGuid);
//        Map<String,Object> map = new HashMap<>();
//        for (int i=0;i<hosOrderList.size();i++){
//            String rowGuid = hosOrderList.get(i).getRowGuid();
//            String rowGuid1 = hosOrderitemService.getGoodsByItemGuid(rowGuid);
//            String name = hosGoodsService.getGoodsNameByGuid(rowGuid1);
//            map.put("goodsName",name);
//
//        }
        return R.ok().put("data",hosOrderList);
    }
    
//    @ApiOperation(value="打印用户订单")
//    @ResponseBody
//    @RequestMapping(value="/printOrder",method=RequestMethod.POST)
//    public R printOrder(@RequestBody String rowGuid){
//    	hosOrderService.printOrder(rowGuid);
//    	return R.ok("打印成功");
//    }

    /**
     * 通过订单Guid查询订单详细信息
     * <p>Title: getOrderDetailByGuid</p>
     * <p>Description: 用户</p>
     * @param rowGuid
     * @return
     */
    @PassToken
    @ApiOperation(value="通过订单Guid查询订单详细信息")
    @ResponseBody
    @RequestMapping(value="/getOrderDetailByGuid",produces="application/json;charset=utf-8",method=RequestMethod.POST)
    public R getOrderDetailByGuid(@RequestBody String rowGuid){
       HosOrder hosOrder = hosOrderService.getOrderDetailByGuid(rowGuid);
        return R.ok().put("data",hosOrder);
    }
    
    /**
     * 根据商户订单查询
     * <p>Title: queryByMNumber</p>  
     * <p>Description: </p>
     * @author hero  
     * @return
     */
    @PassToken
    @ApiOperation(value="根据商户订单号查询")
    @ResponseBody
    @RequestMapping(value="/queryByMNumber",produces="application/json;charset=utf-8",method=RequestMethod.POST)
    public R queryByMNumber(){
    	HosOrder order = hosOrderService.queryByOrderNumber("1558315748576x7egkrr");
    	return R.ok().put("data", order);
    }
    
    /**
     * 统计
     * <p>Title: statistical</p>  
     * <p>Description: </p>
     * @author hero  
     * @return
     */
    @ApiOperation(value="统计")
    @ResponseBody
    @RequestMapping(value="/statistical",produces="application/json;charset=utf-8",method=RequestMethod.GET)
    public R statistical(){
    	List<HosOrder> hosOrderList = hosOrderService.selectAllOrder();
    	int receiveCount = 0;
    	int readyPayCount = 0;
    	int alreadyDeliveryCount = 0;
    	Map<String, Object> map = new HashMap<>();
    	map.put("receiveCount", receiveCount);
    	map.put("readyPayCount", readyPayCount);
    	map.put("alreadyDeliveryCount", alreadyDeliveryCount);
    	if(hosOrderList == null){
    		return R.ok().put("data", map);
    	}
    	for (int i = 0; i < hosOrderList.size(); i++) {
    		if (hosOrderList.get(i) != null) {
    			if (OrderStatuEnum.RECEIVEDORDER.getCode() == hosOrderList.get(i).getOrderStatus()) {
    				receiveCount++;
    			}
    			if (OrderStatuEnum.READYPAY.getCode() == hosOrderList.get(i).getOrderStatus()) {
    				readyPayCount++;
    			}
    			if (OrderStatuEnum.COMPLETED.getCode() == hosOrderList.get(i).getOrderStatus()) {
    				alreadyDeliveryCount++;
    			}
			}
		}
    	//查询待配送订单（订单状态为已接单）
    	
    	//查询待待支付订单
    	
    	//查询已完成订单（已配送）
    	map.put("receiveCount", receiveCount);
    	map.put("readyPayCount", readyPayCount);
    	map.put("alreadyDeliveryCount", alreadyDeliveryCount);
    	return R.ok().put("data", map);
    }
    
    /**
     * 统计半小时订单数量
     * <p>Title: orderStatistical</p>  
     * <p>Description: </p>
     * @author hero  
     * @return
     */
    @ApiOperation(value="每隔半小时统计订单数量")
    @ResponseBody
    @RequestMapping(value="/orderStatistical",produces="application/json;charset=utf-8",method=RequestMethod.GET)
    public R orderStatistical(){
    	List<Map<String, Object>>  maps = hosOrderService.selectOrderStatisical();
    	return R.ok().put("data", maps);
    }
    
    /**
     * 批量改变订单状态为已完成
     * <p>Title: updateOrderStatus</p>  
     * <p>Description: </p>
     * @author hero  
     * @return
     */
    @ApiOperation(value="批量改变订单状态为已完成")
    @ResponseBody
    @RequestMapping(value="/updateOrderStatus",produces="application/json;charset=utf-8",method=RequestMethod.PUT)
    public R updateOrderStatus(@RequestBody String[] rowGuids){
    	hosOrderService.updateOrderStatus(rowGuids);
    	return R.ok();
    }
    
//    /**
//     * 通过rowGuid查询订单
//     * <p>Title: getOrderInfoByRowGuid</p>  
//     * <p>Description: </p>
//     * @author hero  
//     * @return
//     */
//    @ApiOperation(value="通过rowGuid查询订单")
//    @ResponseBody
//    @RequestMapping(value="/getOrderInfoByRowGuid",produces="application/json;charset=utf-8",method=RequestMethod.GET)
//    public R getOrderInfoByRowGuid(@RequestParam String rowGuid){
//    	HosOrder hosOrder = hosOrderService.getOrderInfoByRowGuid(rowGuid);
//    	return R.ok().put("data", hosOrder);
//    }

}
