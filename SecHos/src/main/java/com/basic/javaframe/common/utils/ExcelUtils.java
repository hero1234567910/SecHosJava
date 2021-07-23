package com.basic.javaframe.common.utils;

import com.basic.javaframe.entity.Sechos_Outboundm2m;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.jxls.builder.xls.XlsCommentAreaBuilder;
import org.jxls.common.Context;
import org.jxls.expression.JexlExpressionEvaluator;
import org.jxls.transform.Transformer;
import org.jxls.transform.poi.PoiTransformer;
import org.jxls.util.JxlsHelper;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
  * Excel 工具类
  * @ClassName: ExcelUtil
  * @Description: Excel 工具类
  * @author keeny
  * @date 2018年9月28日 下午3:21:19
  *
 */
public class ExcelUtils {
	public static final String OFFICE_EXCEL_2003_POSTFIX = "xls";

	public static final String OFFICE_EXCEL_2010_POSTFIX = "xlsx";

	public static final String EMPTY = "";

	public static final String POINT = ".";

	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

//    /**
//     * 导出excel头部标题
//     * @param title
//     * @param cellRangeAddressLength
//     * @return
//     */
//    public static HSSFWorkbook makeExcelHead(String title, int cellRangeAddressLength){
//        HSSFWorkbook workbook = new HSSFWorkbook();
//        HSSFCellStyle styleTitle = createStyle(workbook, (short)18);
//        HSSFSheet sheet = workbook.createSheet(title);
//        sheet.setDefaultColumnWidth(25);
//        CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, cellRangeAddressLength);
//        sheet.addMergedRegion(cellRangeAddress);
//        HSSFRow rowTitle = sheet.createRow(0);
//        rowTitle.setHeight((short)1000);
//        HSSFCell cellTitle = rowTitle.createCell(0);
//        // 为标题设置背景颜色
////        styleTitle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
////        styleTitle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
//        cellTitle.setCellValue(title);
//        cellTitle.setCellStyle(styleTitle);
//        return workbook;
//    }
//    /**
//     * 设定二级标题
//     * @param workbook
//     * @param secondTitles
//     * @return
//     */
//    public static HSSFWorkbook makeSecondHead(HSSFWorkbook workbook, String[] secondTitles){
//        // 创建用户属性栏
//        HSSFSheet sheet = workbook.getSheetAt(0);
//        HSSFRow rowField = sheet.createRow(1);
//        HSSFCellStyle styleField = createStyle(workbook, (short)13);
//        for (int i = 0; i < secondTitles.length; i++) {
//            HSSFCell cell = rowField.createCell(i);
//            cell.setCellValue(secondTitles[i]);
//            cell.setCellStyle(styleField);
//        }
//        return workbook;
//    }
//
//    /**
//       * 设定一级级标题
//     * @param workbook
//     * @param secondTitles
//     * @return
//     */
//    public static HSSFWorkbook makeFirstHead(HSSFWorkbook workbook,String title, String[] firstTitles,String[] columnWidth){
////    	HSSFWorkbook workbook = new HSSFWorkbook();
//        HSSFCellStyle styleTitle = createStyle(workbook, (short)14);
//        HSSFSheet sheet = workbook.getSheetAt(0);
//        for (int i = 0; i < columnWidth.length; i++) {
//        	sheet.setColumnWidth(i, Integer.valueOf(columnWidth[i]));
//		}
//        // 创建用户属性栏
//        HSSFRow rowField = sheet.createRow(1);
//        styleTitle.setBorderTop(HSSFCellStyle.BORDER_THIN);
//        styleTitle.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
//        rowField.setHeight((short)600);
//        styleTitle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
//        HSSFFont font = workbook.createFont();
//        font.setFontHeightInPoints((short) 13);
//        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//        font.setFontName("仿宋");
//        styleTitle.setFont(font);
//        for (int i = 0; i < firstTitles.length; i++) {
//            HSSFCell cell = rowField.createCell(i);
//            cell.setCellValue(firstTitles[i]);
//            cell.setCellStyle(styleTitle);
//        }
//        return workbook;
//    }
//    /**
//     * 插入数据
//     * @param workbook
//     * @param dataList
//     * @param beanPropertys
//     * @return
//     */
//    public static <T> HSSFWorkbook exportExcelData(HSSFWorkbook workbook, List<T> dataList, String[] beanPropertys) {
//        HSSFSheet sheet = workbook.getSheetAt(0);
//        // 填充数据
//        HSSFCellStyle styleData = workbook.createCellStyle();
//        styleData.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//        styleData.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//        //自动换行
//        styleData.setWrapText(true);
//        HSSFFont font = workbook.createFont();
//        font.setFontHeightInPoints((short) 12);
//        font.setFontName("仿宋");
//        styleData.setFont(font);
//        HSSFDataFormat df = workbook.createDataFormat();
//        styleData.setDataFormat(df.getFormat("yyyy-MM-dd"));
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//
//        for (int j = 0; j < dataList.size(); j++) {
//            HSSFRow rowData = sheet.createRow(j + 2);
////            rowData.setHeight((short)500);
//            T t = dataList.get(j);
//            for(int k=0; k<beanPropertys.length; k++){
//                String value = "";
//				try {
//					value = BeanUtils.getProperty(t, beanPropertys[k]);
//				} catch (IllegalAccessException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (InvocationTargetException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (NoSuchMethodException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				//判断是否为日期
//				 HSSFCell cellData = rowData.createCell(k);
//				if (com.basic.xszfbdc.common.utils.DateUtil.isValidDate(value)) {
//					try {
//						cellData.setCellValue(format.format(new Date(value)));
//					} catch (Exception e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//	                cellData.setCellStyle(styleData);
//				}else{
//					cellData.setCellValue(value);
//	                cellData.setCellStyle(styleData);
//				}
//            }
//        }
//        return workbook;
//    }
/**
 * 使用批量导入方法时，请注意需要导入的Bean的字段和excel的列一一对应
 * @param clazz
 * @param file
 * @param beanPropertys
 * @return
 */
//    public static <T> List<T> parserExcel(Class<T> clazz, File file, String[] beanPropertys) {
//        // 得到workbook
//        List<T> list = new ArrayList<T>();
//        try {
//			Workbook workbook = WorkbookFactory.create(file);
//            Sheet sheet = workbook.getSheetAt(0);
//            // 直接从第三行开始获取数据
//            int rowSize = sheet.getPhysicalNumberOfRows();
//            if(rowSize > 1){
//                for (int i = 1; i < rowSize; i++) {
//                    T t = clazz.newInstance();
//                    Row row = sheet.getRow(i);
//                    int cellSize = row.getPhysicalNumberOfCells();
//                    for(int j=0; j<cellSize; j++){
//                        Object cellValue = getCellValue(row.getCell(j));
//                        BeanUtils.copyProperty(t, beanPropertys[j], cellValue);
//                        }
//
//                    list.add(t);
//
//                }
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return list;
//
//    }

    /**
     * MultipartFile 转 File
     *
     * @param file
     * @throws Exception
     */
    public static File multipartFileToFile(MultipartFile file) throws Exception {

        File toFile = null;
        if (file.equals("") || file.getSize() <= 0) {
            file = null;
        } else {
            InputStream ins = null;
            ins = file.getInputStream();
            toFile = new File(file.getOriginalFilename());
            inputStreamToFile(ins, toFile);
            ins.close();
        }
        return toFile;
    }

    //获取流文件
    private static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除本地临时文件
     * @param file
     */
    public static void delteTempFile(File file) {
	    if (file != null) {
	        File del = new File(file.toURI());
	        del.delete();
	    }
    }


//    /**
//     * 通用的读取excel单元格的处理方法
//     * @param cell
//     * @return
//     */
//    private static Object getCellValue(Cell cell) {
//        Object result = null;
//        if (cell != null) {
//            switch (cell.getCellType()) {
//            case STRING:
//                result = cell.getStringCellValue();
//                break;
//            case NUMERIC:
//                //对日期进行判断和解析
//                if(HSSFDateUtil.isCellDateFormatted(cell)){
//                    double cellValue = cell.getNumericCellValue();
//                    result = HSSFDateUtil.getJavaDate(cellValue);
//                }else {
//                	cell.setCellType(CellType.STRING);
//					result = cell.getStringCellValue();
//				}
//                break;
//            case BOOLEAN:
//                result = cell.getBooleanCellValue();
//                break;
//            case FORMULA:
//                result = cell.getCellFormula();
//                break;
//            case ERROR:
//                result = cell.getErrorCellValue();
//                break;
//            case BLANK:
//                break;
//            default:
//                break;
//            }
//        }
//        return result;
//    }
//
//    /**
//     * 提取公共的样式
//     * @param workbook
//     * @param fontSize
//     * @return
//     */
//    private static HSSFCellStyle createStyle(HSSFWorkbook workbook, short fontSize){
//        HSSFCellStyle style = workbook.createCellStyle();
//        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//        // 创建一个字体样式
//        HSSFFont font = workbook.createFont();
//        font.setFontHeightInPoints(fontSize);
//        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//        style.setFont(font);
//        return style;
//    }
//
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
//	/**
//	 * 单元格格式
//	 * @param hssfCell
//	 * @return
//	 */
//	@SuppressWarnings({ "static-access", "deprecation" })
//	public static String getHValue(HSSFCell hssfCell){
//		 if (hssfCell.getCellType() == CellType.BOOLEAN) {
//			 return String.valueOf(hssfCell.getBooleanCellValue());
//		 } else if (hssfCell.getCellType() == CellType.NUMERIC) {
//			 String cellValue = "";
//			 if(HSSFDateUtil.isCellDateFormatted(hssfCell)){
//				 Date date = HSSFDateUtil.getJavaDate(hssfCell.getNumericCellValue());
//				 cellValue = sdf.format(date);
//			 }else{
//				 DecimalFormat df = new DecimalFormat("#.##");
//				 cellValue = df.format(hssfCell.getNumericCellValue());
//				 String strArr = cellValue.substring(cellValue.lastIndexOf(POINT)+1,cellValue.length());
//				 if(strArr.equals("00")){
//					 cellValue = cellValue.substring(0, cellValue.lastIndexOf(POINT));
//				 }
//			 }
//			 return cellValue;
//		 } else {
//			return String.valueOf(hssfCell.getStringCellValue());
//		 }
//	}


//    public void excelTempExport(List<Object> list,String count,String exportExcelName,String templateName, HttpServletRequest request,HttpServletResponse response) throws Exception {
//     // 表格使用的数据
//        Map map = new HashMap();
//        map.put("data",list);
//        map.put("count",count);
////        map.put("val",);
//        // 获取模板文件
//        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath()+"templates/excelTemplate/"+templateName+".xlsx";
//        InputStream is = new FileInputStream(new File(path));
//        // 实例化 XLSTransformer 对象
//        XLSTransformer xlsTransformer = new XLSTransformer();
//        // 获取 Workbook ，传入 模板 和 数据
//        System.out.println(is + ">>>> "+ JSONObject.toJSONString(map));
//        Workbook workbook =  xlsTransformer.transformXLS(is,map);
//
//        // 设置文件名
//        response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(exportExcelName+".xlsx" ,"UTF-8"));
//        // 写出文件
//        OutputStream os = new BufferedOutputStream( response.getOutputStream() );
//        // 输出j
//        workbook.write(os);
//        // 关闭和刷新管道，不然可能会出现表格数据不齐，打不开之类的问题
//        is.close();
//        os.flush();
//        os.close();
//    }


//    /***
//     * excel导出公共方法
//     * @param fileName      导出文件名
//     * @param templateFile  模板文件地址
//     * @param list          数据集合
//     * @param response      response
//     * @param request       request
//     */
//    public void exportExcelJXLXS2(String fileName, String templateName, List<?> list,
//                                   HttpServletResponse response, HttpServletRequest request) {
//        response.reset();
//        response.setHeader("Accept-Ranges", "bytes");
//        OutputStream os = null;
//        try {
//            // 解决各浏览器的中文乱码问题
//            String userAgent = request.getHeader("User-Agent");
//
//            // fileName.getBytes("UTF-8")处理safari的乱码问题
//            byte[] bytes = userAgent.contains("MSIE") ? fileName.getBytes() : fileName.getBytes("UTF-8");
//
//            // 各浏览器基本都支持ISO编码
//            fileName = new String(bytes, "ISO-8859-1");
//            response.setHeader("Content-disposition", String.format("attachment; filename=\"%s\"", fileName+".xlsx"));
//            response.setContentType("application/octet-stream;charset=UTF-8");
//
//            Context context = new Context();
//
//            //设置参数变量
//            context.putVar("list", list);
//
//            Map<String , Object> myFunction = new HashMap<>();
//            myFunction.put("my", new ExcelUtil());
//
//            os = response.getOutputStream();
//
//            // 启动新的jxls-api 加载自定义方法
//            String path = Thread.currentThread().getContextClassLoader().getResource("").getPath()+"templates/excelTemplate/"+templateName+".xlsx";
//            InputStream is = new FileInputStream(new File(path));
//            System.out.println(is+" >>>"+os);
////            InputStream is = this.getClass().getClassLoader().getResourceAsStream("templates/excelTemplate/"+templateName+".xlsx");
//            Transformer trans = TransformerFactory.createTransformer(is, os);
//
//            JexlExpressionEvaluator evaluator = (JexlExpressionEvaluator) trans.getTransformationConfig().getExpressionEvaluator();
//            evaluator.getJexlEngine().setFunctions(myFunction);
//
//            // 载入模板、处理导出
//            AreaBuilder areaBuilder = new XlsCommentAreaBuilder(trans);
//            List<Area> areaList = areaBuilder.build();
//            System.out.println(context+"  666");
//            areaList.get(0).applyAt(new CellRef("Hello!A1"), context);
//            trans.write();
//            is.close();
//        } catch (Exception e) {
//
//        } finally {
//            try {
//                if (os != null) {
//                    os.flush();
//                    os.close();
//                }
//            } catch (Exception e) {
//            	throw new MyException("");
//            }
//        }
//    }
//
//    // 格式化时间
//    public Object formatDate(Long time){
//        if(time != null){
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            String dateStr = sdf.format(time);
//            return dateStr;
//        }
//        return "--";
//    }
//
//    // 时延-处理执行时间 ms -> s 且保留两位
//    public Object timeChange(Long time){
//        if(time != null){
//            DecimalFormat df = new DecimalFormat("0.00");
//            String result = df.format((double)time / 1000);
//            return result.equals("0.00") ? "--" : result + "s";
//        }
//        return "--";
//    }
//
//    // 超链接方法
//    public WritableCellValue myLink(String address, String title) {
//        return new WritableHyperlink(address, title);
//    }

//    // 告警类型转换 -> MonitorTypeEnum枚举类
//    public Object alarmType(Integer type){
//        if(type != null){
//            return MonitorTypeEnum.valueOf(type).getLabel();
//        }
//        return "--";
//    }

//    // 告警状态转换 -> AlarmStatusEnum枚举类
//    public Object statusType(Integer type){
//        if(type != null){
//            return AlarmStatusEnum.valueOf(type).getLabel();
//        }
//        return "--";
//    }

	public static void exportExcel(String fileName, String templateName, Map<String, Object> model,HttpServletResponse response, HttpServletRequest request) throws IOException{
        Context context = PoiTransformer.createInitialContext();
        if (model != null) {
            for (String key : model.keySet()) {
                context.putVar(key, model.get(key));
            }
        }

        response.reset();
      response.setHeader("Accept-Ranges", "bytes");
      OutputStream os = null;
          // 解决各浏览器的中文乱码问题
          String userAgent = request.getHeader("User-Agent");

          // fileName.getBytes("UTF-8")处理safari的乱码问题
          byte[] bytes = userAgent.contains("MSIE") ? fileName.getBytes() : fileName.getBytes("UTF-8");

          // 各浏览器基本都支持ISO编码
          fileName = new String(bytes, "ISO-8859-1");
          response.setHeader("Content-disposition", String.format("attachment; filename=\"%s\"", fileName+".xlsx"));
          response.setContentType("application/octet-stream;charset=UTF-8");

        os = response.getOutputStream();

//      启动新的jxls-api 加载自定义方法
//      String path = Thread.currentThread().getContextClassLoader().getResource("").getPath()+"templates/excelTemplate/"+templateName+".xlsx";
//      InputStream is = new FileInputStream(new File(path));
      InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("templates/excelTemplate/"+templateName+".xlsx");
      System.out.println(is+" >>>"+os);

        JxlsHelper jxlsHelper = JxlsHelper.getInstance();
        Transformer transformer  = jxlsHelper.createTransformer(is, os);
        //获得配置
        JexlExpressionEvaluator evaluator = (JexlExpressionEvaluator)transformer.getTransformationConfig().getExpressionEvaluator();
        //设置静默模式，不报警告
        //evaluator.getJexlEngine().setSilent(true);
        //函数强制，自定义功能
        Map<String, Object> funcs = new HashMap<String, Object>();
//        funcs.put("utils", new com.basic.xszfbdc.common.utils.ExcelUtil());    //添加自定义功能
        evaluator.getJexlEngine().setFunctions(funcs);
        //必须要这个，否者表格函数统计会错乱
        jxlsHelper.setUseFastFormulaProcessor(false).processTemplate(context, transformer);
    }

	//单元格合并
	static{
		//添加自定义指令（可覆盖jxls原指令）
		//合并单元格(模板已经做过合并单元格操作的单元格无法再次合并)
//		XlsCommentAreaBuilder.addCommandMapping("merge", MergeCommand.class);
	}

    //获取jxls模版文件
    public static File getTemplate(String path){
        File template = new File(path);
        if(template.exists()){
            return template;
        }
        return null;
    }

    public static HSSFWorkbook makeFirstHead(String 出库详单, String[] firstTitle) {
	    return new HSSFWorkbook();
    }

    public static HSSFWorkbook exportExcelData(HSSFWorkbook workbook, List<Sechos_Outboundm2m> sechosOutboundm2mList, String[] beanProperty) {
        return new HSSFWorkbook();
	}

    // 日期格式化
    public String dateFmt(Date date, String fmt) {
        if (date == null) {
            return "";
        }
        try {
            SimpleDateFormat dateFmt = new SimpleDateFormat(fmt);
            return dateFmt.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    // if判断
    public Object ifelse(boolean b, Object o1, Object o2) {
        return b ? o1 : o2;
    }

}

///**
// * 自定义xssf日期工具类
// * @author lp
// *
// */
//class XSSFDateUtil extends DateUtil {
//	protected static int absoluteDay(Calendar cal, boolean use1904windowing) {
//        return DateUtil.absoluteDay(cal, use1904windowing);
//    }
//}