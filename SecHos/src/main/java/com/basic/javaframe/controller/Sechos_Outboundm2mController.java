package com.basic.javaframe.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.basic.javaframe.common.utils.*;
import com.basic.javaframe.entity.Sechos_Outbound;
import com.basic.javaframe.entity.Sechos_Outboundm2m;
import com.basic.javaframe.entity.Sechos_Procurement;
import com.basic.javaframe.entity.Sechos_Purchasingm2m;
import com.basic.javaframe.service.Sechos_OutboundService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.basic.javaframe.common.customclass.PassToken;
import com.basic.javaframe.service.Sechos_Outboundm2mService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 
 * 
 * @author 
 * @date 2019-09-25 10:43:02
 */
@Api(value = "出库材料多对多管理")
@RestController
@CrossOrigin
@RequestMapping("sys/sechosoutboundm2m")
public class Sechos_Outboundm2mController {
	@Autowired
	private Sechos_Outboundm2mService sechosOutboundm2mService;

	@Autowired
	private Sechos_OutboundService sechosOutboundService;
	/**
	 * 列表数据
	 */
	@PassToken
	@ApiOperation(value="出库材料多对多数据列表")
    @ResponseBody
	@RequestMapping(value="/listData",produces="application/json;charset=utf-8",method=RequestMethod.GET)
	public LayuiUtil listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<Sechos_Outboundm2m> sechosOutboundm2mList = sechosOutboundm2mService.getList(query);
		int total = sechosOutboundm2mService.getCount(query);
		PageUtils pageUtil = new PageUtils(sechosOutboundm2mList, total, query.getLimit(), query.getPage());
		return LayuiUtil.data(pageUtil.getTotalCount(), pageUtil.getList());
	}

    /**
     * 新增
     **/
    @ApiOperation(value="新增出库多对多表")
    @ResponseBody
    @RequestMapping(value="/add",produces="application/json;charset=utf-8",method=RequestMethod.POST)
    public R add(@RequestBody Sechos_Outboundm2m sechosOutboundm2m){
    	//如果排序号为空，则自动转为0
    	if (sechosOutboundm2m.getSortSq() == null) {
			sechosOutboundm2m.setSortSq(0);
		}
    	//生成uuid作为rowguid
        String uuid = java.util.UUID.randomUUID().toString();
		sechosOutboundm2m.setRowGuid(uuid);
		Date createTime = DateUtil.changeDate(new Date());
		sechosOutboundm2m.setCreateTime(createTime);
		sechosOutboundm2m.setDelFlag(0);
		String guid = sechosOutboundm2m.getOutboundGuid();
		Sechos_Outboundm2m sechosOutboundm2m1 = new Sechos_Outboundm2m();
		sechosOutboundm2m1.setOutboundGuid(guid);
		sechosOutboundm2m1.setDrugCode(sechosOutboundm2m.getDrugCode());
		int count = sechosOutboundm2mService.getCountByGuid(sechosOutboundm2m1);
		if(count>=1){
			return R.error("请勿重复添加相同类别的出库项！");
		}
		sechosOutboundm2mService.save(sechosOutboundm2m);


		Sechos_Outbound sechosOutbound = new Sechos_Outbound();
		sechosOutbound.setRowGuid(guid);
		sechosOutbound.setOutboundStatus(1);
		sechosOutbound.setOrderDate(createTime);
		sechosOutboundService.update(sechosOutbound);
        return R.ok();  
    }

	/**
	 * 修改
	 */
	@ApiOperation(value="")
    @ResponseBody
	@RequestMapping(value="/update", produces = "application/json; charset=utf-8", method=RequestMethod.POST)
	public R update(@RequestBody Sechos_Outboundm2m sechosOutboundm2m){
		sechosOutboundm2mService.update(sechosOutboundm2m);
		return R.ok();
	}

	/**
	 * 删除
	 */
    @ApiOperation(value="")
	@ResponseBody
	@RequestMapping(value="/delete",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R delete(@RequestBody String[] rowGuids){
		sechosOutboundm2mService.deleteBatch(rowGuids);
		return R.ok();
	}

	/**
	 * 根据订单Guid获取采购详细数据
	 */
	@ApiOperation(value="根据订单Guid获取采购详细数据")
	@ResponseBody
	@RequestMapping(value="/getListByPGuid",produces="application/json;charset=utf-8",method=RequestMethod.GET)
	public R getListByPGuid(@RequestBody String purchaseGuid){
		List<Sechos_Outboundm2m> sechosOutboundm2mList = sechosOutboundm2mService.getListByPGuid(purchaseGuid);
		return R.ok().put("data",sechosOutboundm2mList);
	}

//	/**
//	 * excel导出
//	 *
//	 * @param @param request
//	 * @param @param response    设定文件
//	 * @return void    返回类型
//	 * @throws
//	 * @Title: exportExcel
//	 * @Description: excel导出
//	 */
//	@PassToken
//	@ApiOperation(value = "导出Excel")
//	@ResponseBody
//	@RequestMapping(value = "/exportExcel/{outboundGuid}", produces = "application/json; charset=utf-8", method = RequestMethod.GET)
//	public void exportExcel(HttpServletRequest request, HttpServletResponse response, @PathVariable("outboundGuid") String outboundGuid) {
//		try {
//			SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd");
//			Sechos_Outbound sechosOutbound = sechosOutboundService.getDetailByGuid(outboundGuid);
//			String excelHead = sechosOutbound.getOutboundOrderNum();
//			//String[] secondHead = {procurement.getPurchaseDate().toString(),procurement.getInboundDate().toString(),procurement.getPurchaseNote()};
//			String[] secondTitle = {"下单日期","出库日期","采购备注"};
//			String[] firstTitle = {"材料名称", "材料单价", "材料数量", "材料总价"};
//			HSSFWorkbook workbook = ExcelUtil.makeFirstHead("出库详单", firstTitle);
//			HSSFSheet sheet = workbook.createSheet("采购总览");
//			sheet.setDefaultColumnWidth(20);
//			HSSFRow row1=sheet.createRow(0);
//			//创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
//			HSSFCell cell=row1.createCell(0);
//			//设置单元格内容
//			cell.setCellValue("出库总览表");
//			//合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
//			sheet.addMergedRegion(new CellRangeAddress(0,0,0,3));
//			//在sheet里创建第二行
//			HSSFRow row2=sheet.createRow(1);
//			//创建单元格并设置单元格内容
//			row2.createCell(0).setCellValue("下单日期");
//			row2.createCell(1).setCellValue("出库日期");
//			row2.createCell(2).setCellValue("出库备注");
//			row2.createCell(3).setCellValue("总金额");
//
//			HSSFRow row3=sheet.createRow(2);
//			//System.out.println(procurement.toString());
//			if(null==sechosOutbound.getOutboundDate()){
//				String InboundDate="";
//				String PurchaseNote;
//				if(null==sechosOutbound.getOrderDate()){
//					PurchaseNote = "";
//				}else{
//					PurchaseNote = sechosOutbound.getOutboundNote();
//				}
//				String date=sDateFormat.format(sechosOutbound.getOrderDate());
//				row3.createCell(0).setCellValue(date);
//				row3.createCell(1).setCellValue(InboundDate);
//				row3.createCell(2).setCellValue(PurchaseNote);
//				row3.createCell(3).setCellValue(sechosOutbound.getOutboundPrice().toString());
//			}else{
//				String date=sDateFormat.format(sechosOutbound.getOrderDate());
//				String date2=sDateFormat.format(sechosOutbound.getOutboundDate());
//				row3.createCell(0).setCellValue(date);
//				row3.createCell(1).setCellValue(date2);
//				row3.createCell(2).setCellValue(sechosOutbound.getOutboundNote());
//				row3.createCell(3).setCellValue(sechosOutbound.getOutboundPrice().toString());
//			}
//
//			String[] beanProperty = {"drugName", "drugPrice", "drugAmount", "drugTotalPrice"};
//
//			List<Sechos_Outboundm2m> sechosOutboundm2mList = sechosOutboundm2mService.getListByPGuid(outboundGuid);
//			//System.out.println(sechosPurchasingm2mList.toString());
//			HSSFWorkbook workbook1 = ExcelUtil.exportExcelData(workbook,sechosOutboundm2mList,beanProperty);
//
//			String fileName = "出库表_"+sechosOutbound.getOutboundOrderNum()+".xls";
//			//提示浏览器以下载文件的形式接受
//			response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
//			OutputStream outputStream = response.getOutputStream();
//			workbook1.write(outputStream);
//			outputStream.flush();
//			outputStream.close();
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
}
