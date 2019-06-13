package com.basic.javaframe.wxcontroller;

import com.basic.javaframe.common.customclass.PassToken;
import com.basic.javaframe.common.utils.*;
import com.basic.javaframe.entity.HosOrder;
import com.basic.javaframe.service.HosGoodsService;
import com.basic.javaframe.service.HosOrderService;
import com.basic.javaframe.service.HosOrderitemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.UUID;
import java.util.*;


/**
 * @author my
 * @date 2019-04-29 08:39:45
 */
@Api(value = "订单管理")
@RestController
@CrossOrigin
@RequestMapping("wx/sys/hosorder")
public class WxHosOrderController {
    private final static Logger logger = LoggerFactory.getLogger(WxHosOrderController.class);
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
        String uuid = UUID.randomUUID().toString();
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

}
