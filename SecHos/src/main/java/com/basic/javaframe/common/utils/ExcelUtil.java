package com.basic.javaframe.common.utils;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;

/**
  * Excel 工具类
  * @ClassName: ExcelUtil 
  * @Description: Excel 工具类 
  * @author keeny
  * @date 2018年9月28日 下午3:21:19 
  *
 */
public class ExcelUtil {

	public static final String OFFICE_EXCEL_2003_POSTFIX = "xls";

	public static final String OFFICE_EXCEL_2010_POSTFIX = "xlsx";
	
	public static final String EMPTY = "";
	
	public static final String POINT = ".";
	
	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	
    /**
     * 导出excel头部标题
     * @param title
     * @param cellRangeAddressLength
     * @return
     */
    public static HSSFWorkbook makeExcelHead(String title, int cellRangeAddressLength){
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFCellStyle styleTitle = createStyle(workbook, (short)16);
        HSSFSheet sheet = workbook.createSheet(title);
        sheet.setDefaultColumnWidth(25);
        CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, cellRangeAddressLength);
        sheet.addMergedRegion(cellRangeAddress);
        HSSFRow rowTitle = sheet.createRow(0);
        HSSFCell cellTitle = rowTitle.createCell(0);
        // 为标题设置背景颜色
        styleTitle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        styleTitle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cellTitle.setCellValue(title);
        cellTitle.setCellStyle(styleTitle);
        return workbook;
    }
    /**
     * 设定二级标题
     * @param workbook
     * @param secondTitles
     * @return
     */
    public static HSSFWorkbook makeSecondHead(HSSFWorkbook workbook, String[] secondTitles){
        // 创建用户属性栏
        HSSFSheet sheet = workbook.getSheetAt(0);
        HSSFRow rowField = sheet.createRow(1);
        HSSFCellStyle styleField = createStyle(workbook, (short)13);        
        for (int i = 0; i < secondTitles.length; i++) {
            HSSFCell cell = rowField.createCell(i);
            cell.setCellValue(secondTitles[i]);
            cell.setCellStyle(styleField);            
        }
        return workbook;
    }
    
    /**
       * 设定一级级标题
     * @param workbook
     * @param secondTitles
     * @return
     */
    public static HSSFWorkbook makeFirstHead(String title, String[] firstTitles){
    	HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFCellStyle styleTitle = createStyle(workbook, (short)14);
        HSSFSheet sheet = workbook.createSheet(title);
        sheet.setDefaultColumnWidth(25);
        // 创建用户属性栏
        HSSFRow rowField = sheet.createRow(0);
        //HSSFCellStyle styleField = createStyle(workbook, (short)13);   
        styleTitle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        styleTitle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        for (int i = 0; i < firstTitles.length; i++) {
            HSSFCell cell = rowField.createCell(i);
            cell.setCellValue(firstTitles[i]);
            cell.setCellStyle(styleTitle);    
        }
        return workbook;
    }
    /**
     * 插入数据
     * @param workbook
     * @param dataList
     * @param beanPropertys
     * @return
     */
    public static <T> HSSFWorkbook exportExcelData(HSSFWorkbook workbook, List<T> dataList, String[] beanPropertys) {
        HSSFSheet sheet = workbook.getSheetAt(0);
        // 填充数据
        HSSFCellStyle styleData = workbook.createCellStyle();
        styleData.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleData.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        for (int j = 0; j < dataList.size(); j++) {
            HSSFRow rowData = sheet.createRow(j + 1);
            T t = dataList.get(j);
            for(int k=0; k<beanPropertys.length; k++){
                String value = "";
				try {
					value = BeanUtils.getProperty(t, beanPropertys[k]);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                HSSFCell cellData = rowData.createCell(k);
                
                cellData.setCellValue(value);
                            
                cellData.setCellStyle(styleData);
            }            
        }
        return workbook;
    }
/**
 * 使用批量导入方法时，请注意需要导入的Bean的字段和excel的列一一对应
 * @param clazz
 * @param file
 * @param beanPropertys
 * @return
 */
    public static <T> List<T> parserExcel(Class<T> clazz, File file, String[] beanPropertys) {
        // 得到workbook
        List<T> list = new ArrayList<T>();
        try {
            Workbook workbook = WorkbookFactory.create(file);
            Sheet sheet = workbook.getSheetAt(0);
            // 直接从第三行开始获取数据
            int rowSize = sheet.getPhysicalNumberOfRows();
            if(rowSize > 2){                
                for (int i = 2; i < rowSize; i++) {
                    T t = clazz.newInstance();
                    Row row = sheet.getRow(i);
                    int cellSize = row.getPhysicalNumberOfCells();
                    for(int j=0; j<cellSize; j++){
                        
                        Object cellValue = getCellValue(row.getCell(j));
                        BeanUtils.copyProperty(t, beanPropertys[j], cellValue);
                        }                        
                                    
                    list.add(t);

                }
            }            

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;

    }
    /**
     * 通用的读取excel单元格的处理方法
     * @param cell
     * @return
     */
    private static Object getCellValue(Cell cell) {
        Object result = null;
        if (cell != null) {
            switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                result = cell.getStringCellValue();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                //对日期进行判断和解析
                if(HSSFDateUtil.isCellDateFormatted(cell)){
                    double cellValue = cell.getNumericCellValue();
                    result = HSSFDateUtil.getJavaDate(cellValue);
                }
                
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                result = cell.getBooleanCellValue();
                break;
            case Cell.CELL_TYPE_FORMULA:
                result = cell.getCellFormula();
                break;
            case Cell.CELL_TYPE_ERROR:
                result = cell.getErrorCellValue();
                break;
            case Cell.CELL_TYPE_BLANK:
                break;
            default:
                break;
            }
        }
        return result;
    }

    /**
     * 提取公共的样式
     * @param workbook
     * @param fontSize
     * @return
     */
    private static HSSFCellStyle createStyle(HSSFWorkbook workbook, short fontSize){
        HSSFCellStyle style = workbook.createCellStyle();        
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 创建一个字体样式
        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints(fontSize);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        style.setFont(font);
        return style;
    }
    
	/**
	 * 获得path的后缀名
	 * @param path
	 * @return
	 */
	public static String getPostfix(String path){
		if(path==null || EMPTY.equals(path.trim())){
			return EMPTY;
		}
		if(path.contains(POINT)){
			return path.substring(path.lastIndexOf(POINT)+1,path.length());
		}
		return EMPTY;
	}
	/**
	 * 单元格格式
	 * @param hssfCell
	 * @return
	 */
	@SuppressWarnings({ "static-access", "deprecation" })
	public static String getHValue(HSSFCell hssfCell){
		 if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
			 return String.valueOf(hssfCell.getBooleanCellValue());
		 } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
			 String cellValue = "";
			 if(HSSFDateUtil.isCellDateFormatted(hssfCell)){				
				 Date date = HSSFDateUtil.getJavaDate(hssfCell.getNumericCellValue());
				 cellValue = sdf.format(date);
			 }else{
				 DecimalFormat df = new DecimalFormat("#.##");
				 cellValue = df.format(hssfCell.getNumericCellValue());
				 String strArr = cellValue.substring(cellValue.lastIndexOf(POINT)+1,cellValue.length());
				 if(strArr.equals("00")){
					 cellValue = cellValue.substring(0, cellValue.lastIndexOf(POINT));
				 }	
			 }
			 return cellValue;
		 } else {
			return String.valueOf(hssfCell.getStringCellValue());
		 }
	}
	/**
	 * 单元格格式
	 * @param xssfCell
	 * @return
	 */
	public static String getXValue(XSSFCell xssfCell){
		 if (xssfCell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
			 return String.valueOf(xssfCell.getBooleanCellValue());
		 } else if (xssfCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			 String cellValue = "";
			 if(XSSFDateUtil.isCellDateFormatted(xssfCell)){
				 Date date = XSSFDateUtil.getJavaDate(xssfCell.getNumericCellValue());
				 cellValue = sdf.format(date);
			 }else{
				 DecimalFormat df = new DecimalFormat("#.##");
				 cellValue = df.format(xssfCell.getNumericCellValue());
				 String strArr = cellValue.substring(cellValue.lastIndexOf(POINT)+1,cellValue.length());
				 if(strArr.equals("00")){
					 cellValue = cellValue.substring(0, cellValue.lastIndexOf(POINT));
				 }	
			 }
			 return cellValue;
		 } else {
			return String.valueOf(xssfCell.getStringCellValue());
		 }
	}	
    
}

/**
 * 自定义xssf日期工具类
 * @author lp
 *
 */
class XSSFDateUtil extends DateUtil{
	protected static int absoluteDay(Calendar cal, boolean use1904windowing) {  
        return DateUtil.absoluteDay(cal, use1904windowing);  
    } 
}