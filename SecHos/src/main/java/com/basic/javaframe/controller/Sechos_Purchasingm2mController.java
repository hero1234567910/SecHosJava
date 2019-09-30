package com.basic.javaframe.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.basic.javaframe.common.utils.*;
import com.basic.javaframe.entity.Sechos_Drugmaterial;
import com.basic.javaframe.entity.Sechos_Procurement;
import com.basic.javaframe.service.Sechos_DrugmaterialService;
import com.basic.javaframe.service.Sechos_ProcurementService;
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
import com.basic.javaframe.entity.Sechos_Purchasingm2m;
import com.basic.javaframe.service.Sechos_Purchasingm2mService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 
 * 
 * @author 
 * @date 2019-09-20 10:55:46
 */
@Api(value = "采购多对多表管理")
@RestController
@CrossOrigin
@RequestMapping("sys/sechospurchasingm2m")
public class Sechos_Purchasingm2mController {
	@Autowired
	private Sechos_Purchasingm2mService sechosPurchasingm2mService;

	@Autowired
	private Sechos_ProcurementService sechosProcurementService;
	/**
	 * 列表数据
	 */
	@PassToken
	@ApiOperation(value="采购多对多表数据")
    @ResponseBody
	@RequestMapping(value="/listData",produces="application/json;charset=utf-8",method=RequestMethod.GET)
	public LayuiUtil listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<Sechos_Purchasingm2m> sechosPurchasingm2mList = sechosPurchasingm2mService.getList(query);
		int total = sechosPurchasingm2mService.getCount(query);
		PageUtils pageUtil = new PageUtils(sechosPurchasingm2mList, total, query.getLimit(), query.getPage());
		return LayuiUtil.data(pageUtil.getTotalCount(), pageUtil.getList());
	}

    /**
     * 新增
     **/
    @ApiOperation(value="新增采购多对多表")
    @ResponseBody
    @RequestMapping(value="/add",produces="application/json;charset=utf-8",method=RequestMethod.POST)
    public R add(@RequestBody Sechos_Purchasingm2m sechosPurchasingm2m){
    	//如果排序号为空，则自动转为0
    	if (sechosPurchasingm2m.getSortSq() == null) {
			sechosPurchasingm2m.setSortSq(0);
		}
    	//生成uuid作为rowguid
        String uuid = java.util.UUID.randomUUID().toString();
		sechosPurchasingm2m.setRowGuid(uuid);
		Date createTime = DateUtil.changeDate(new Date());
		sechosPurchasingm2m.setCreateTime(createTime);
		sechosPurchasingm2m.setDelFlag(0);
		sechosPurchasingm2mService.save(sechosPurchasingm2m);

		String guid= sechosPurchasingm2m.getPurchaseGuid();
		Sechos_Procurement sechosProcurement = new Sechos_Procurement();
		sechosProcurement.setRowGuid(guid);
		sechosProcurement.setPurchaseStatus(1);
		sechosProcurement.setPurchaseDate(createTime);
		sechosProcurementService.update(sechosProcurement);
        return R.ok();  
    }

	/**
	 * 修改
	 */
	@ApiOperation(value="修改")
    @ResponseBody
	@RequestMapping(value="/update", produces = "application/json; charset=utf-8", method=RequestMethod.POST)
	public R update(@RequestBody Sechos_Purchasingm2m sechosPurchasingm2m){

		sechosPurchasingm2mService.update(sechosPurchasingm2m);
		return R.ok();
	}

	/**
	 * 删除
	 */
    @ApiOperation(value="删除")
	@ResponseBody
	@RequestMapping(value="/delete",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R delete(@RequestBody String[] rowGuids){
		sechosPurchasingm2mService.deleteBatch(rowGuids);
		return R.ok();
	}

	/**
	 * 根据订单Guid获取采购详细数据
	 */
	@ApiOperation(value="根据订单Guid获取采购详细数据")
	@ResponseBody
	@RequestMapping(value="/getListByPGuid",produces="application/json;charset=utf-8",method=RequestMethod.GET)
	public R getListByPGuid(@RequestBody String purchaseGuid){
		List<Sechos_Purchasingm2m> sechosPurchasingm2mList = sechosPurchasingm2mService.getListByPGuid(purchaseGuid);
		return R.ok().put("data",sechosPurchasingm2mList);
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
	@RequestMapping(value = "/exportExcel/{purchaseGuid}", produces = "application/json; charset=utf-8", method = RequestMethod.GET)
	public void exportExcel(HttpServletRequest request, HttpServletResponse response,@PathVariable("purchaseGuid") String purchaseGuid) {
		try {
			SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd");
			Sechos_Procurement procurement = sechosProcurementService.getDetailByGuid(purchaseGuid);
			String excelHead = procurement.getPurchaseOrderNum();
			//String[] secondHead = {procurement.getPurchaseDate().toString(),procurement.getInboundDate().toString(),procurement.getPurchaseNote()};
			String[] secondTitle = {"下单日期","入库日期","采购备注"};
			String[] firstTitle = {"材料名称", "材料单价", "材料数量", "材料总价", "材料过期时间"};
			HSSFWorkbook workbook = ExcelUtil.makeFirstHead("采购详单", firstTitle);
			HSSFSheet sheet = workbook.createSheet("采购总览");
			sheet.setDefaultColumnWidth(20);
			HSSFRow row1=sheet.createRow(0);
			//创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
			HSSFCell cell=row1.createCell(0);
			//设置单元格内容
			cell.setCellValue("采购总览表");
			//合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
			sheet.addMergedRegion(new CellRangeAddress(0,0,0,3));
			//在sheet里创建第二行
			HSSFRow row2=sheet.createRow(1);
			//创建单元格并设置单元格内容
 			row2.createCell(0).setCellValue("下单日期");
			row2.createCell(1).setCellValue("入库日期");
	 		row2.createCell(2).setCellValue("采购备注");
			row2.createCell(3).setCellValue("总金额");

			HSSFRow row3=sheet.createRow(2);
			//System.out.println(procurement.toString());
			if(null==procurement.getInboundDate()){
				String InboundDate="";
				String PurchaseNote;
				if(null==procurement.getPurchaseNote()){
					PurchaseNote = "";
				}else{
					PurchaseNote = procurement.getPurchaseNote();
				}
				String date=sDateFormat.format(procurement.getPurchaseDate());
				row3.createCell(0).setCellValue(date);
				row3.createCell(1).setCellValue(InboundDate);
				row3.createCell(2).setCellValue(PurchaseNote);
				row3.createCell(3).setCellValue(procurement.getPurchasePrice().toString());
			}else{
				String date=sDateFormat.format(procurement.getPurchaseDate());
				String date2=sDateFormat.format(procurement.getInboundDate());
				row3.createCell(0).setCellValue(date);
				row3.createCell(1).setCellValue(date2);
				row3.createCell(2).setCellValue(procurement.getPurchaseNote());
				row3.createCell(3).setCellValue(procurement.getPurchasePrice().toString());
			}

			String[] beanProperty = {"drugName", "drugPrice", "drugAmount", "drugTotalPrice", "drugOverdue"};

			List<Sechos_Purchasingm2m> sechosPurchasingm2mList = sechosPurchasingm2mService.getListByPGuid(purchaseGuid);
			//System.out.println(sechosPurchasingm2mList.toString());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			for (Sechos_Purchasingm2m sechos_purchasingm2m : sechosPurchasingm2mList) {
				Date time = sechos_purchasingm2m.getDrugOverdue();
				String fTime = sdf.format(time);
				Date newDate = sdf.parse(fTime);
				java.sql.Date resultDate = new java.sql.Date(newDate.getTime());
				sechos_purchasingm2m.setDrugOverdue(resultDate);
			}
			HSSFWorkbook workbook1 = ExcelUtil.exportExcelData(workbook,sechosPurchasingm2mList,beanProperty);

			String fileName = "采购表_"+procurement.getPurchaseOrderNum()+".xls";
			//提示浏览器以下载文件的形式接受
			response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
			OutputStream outputStream = response.getOutputStream();
			workbook1.write(outputStream);
			outputStream.flush();
			outputStream.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
