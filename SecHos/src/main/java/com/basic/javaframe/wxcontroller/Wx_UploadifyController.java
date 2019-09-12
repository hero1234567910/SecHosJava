package com.basic.javaframe.wxcontroller;

import com.alibaba.fastjson.JSONObject;
import com.basic.javaframe.common.customclass.PassToken;
import com.basic.javaframe.common.utils.DateUtil;
import com.basic.javaframe.common.utils.R;
import com.basic.javaframe.controller.BaseController;
import com.basic.javaframe.entity.Frame_Attach;
import com.basic.javaframe.service.Frame_AttachService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 
 * 
 * @author hero
 * @date 2019-04-09 11:30:38
 */
@Api(value = "/上传控件")
@RestController
@CrossOrigin
@RequestMapping("wx/sys/common")
public class Wx_UploadifyController extends BaseController{
	
	private final static Logger logger = LoggerFactory.getLogger(Wx_UploadifyController.class);
	
	@Autowired
	Frame_AttachService frame_AttachService;
	
	@PassToken
	@ApiOperation(value="上传控件")
	@RequestMapping(value="/uploadFile") 
	public void uploadFile(  
	    @RequestParam(value = "uploadify", required = false) MultipartFile file,  
	    HttpServletRequest request, HttpServletResponse response) throws IOException {  
	    String fileName = file.getOriginalFilename();  
	    Map<String, Object> dataMap = new HashMap<String, Object>();  
	    //文件存放路径  
	    String path = filePath;
	    String uuid = UUID.randomUUID().toString();  
	    String filePath = uuid + fileName.substring(fileName.lastIndexOf("."));  
	    File targetFile = new File(path, filePath);  
	    if (!targetFile.exists()) {  
	    	 File dir = new File(targetFile.getParent());
             dir.mkdirs();
             targetFile.createNewFile();
	    }  
	    try {
	        file.transferTo(targetFile);
	        
//	      //获取文件图标并返回
//	        FileSystemView fsv=new JFileChooser().getFileSystemView();
//	        Icon icon=fsv.getSystemIcon(targetFile);
//	    	Icon icon = SystemIconUtil.getSmallIcon(targetFile);
	        dataMap.put("fileName", fileName);
	        //往附件表中插入记录，返回唯一标识
	        
	        Frame_Attach frame_Attach = new Frame_Attach();
	        String uuid1 = UUID.randomUUID().toString();
	        frame_Attach.setRowGuid(uuid1);
	        frame_Attach.setAttachName(fileName);
	        frame_Attach.setCreateTime(DateUtil.changeDate(new Date()));
	        frame_Attach.setContentType(fileName.substring(fileName.lastIndexOf(".")));
	        frame_Attach.setContentUrl(filePath);
	        frame_Attach.setContentLength((int) targetFile.length());
	        frame_AttachService.insertFrameAttach(frame_Attach);
	        
	        dataMap.put("attachRowguid", frame_Attach.getRowGuid());
	        dataMap.put("url", fileUrl+"/file/"+frame_Attach.getContentUrl());
	        dataMap.put("type", frame_Attach.getContentType());
	        logger.info("文件上传成功"); 
	    } catch (Exception e) {  
	            logger.error(e.getMessage());
	    }  
	    ObjectMapper mapper = new ObjectMapper(); 
	    String data =mapper.writeValueAsString(dataMap);  
	    response.setContentType("text/html;charset=UTF-8"); 
	    response.getWriter().print(data); 
	}
	
	@PassToken
	@ApiOperation(value="上传控件")
	@RequestMapping(value="/upload") 
	public void upload(@RequestParam MultipartFile file,  
	    HttpServletRequest request, HttpServletResponse response) throws IOException {  
		System.out.println(file);
	    String fileName = file.getOriginalFilename();  
	    System.out.println(fileName);
	    Map<String, Object> dataMap = new HashMap<String, Object>();  
	    //文件存放路径  
	    String path = filePath;
	    String uuid = UUID.randomUUID().toString();  
	    String filePath = uuid + fileName.substring(fileName.lastIndexOf("."));  
	    File targetFile = new File(path, filePath);  
	    if (!targetFile.exists()) {  
	    	 File dir = new File(targetFile.getParent());
             dir.mkdirs();
             targetFile.createNewFile();
	    }  
	    try {
	        file.transferTo(targetFile);
			dataMap.put("fileName", fileName);
			//往附件表中插入记录，返回唯一标识

			Frame_Attach frame_Attach = new Frame_Attach();
			String uuid1 = UUID.randomUUID().toString();
			frame_Attach.setRowGuid(uuid1);
			frame_Attach.setAttachName(fileName);
			frame_Attach.setCreateTime(DateUtil.changeDate(new Date()));
			frame_Attach.setContentType(fileName.substring(fileName.lastIndexOf(".")));
			frame_Attach.setContentUrl(filePath);
			frame_Attach.setContentLength((int) targetFile.length());
			frame_AttachService.insertFrameAttach(frame_Attach);

			dataMap.put("attachRowguid", frame_Attach.getRowGuid());
			dataMap.put("url", fileUrl+"/file/"+frame_Attach.getContentUrl());
			dataMap.put("type", frame_Attach.getContentType());
	        logger.info("文件上传成功"); 
	    } catch (Exception e) {  
	            logger.error(e.getMessage());
	    }
		ObjectMapper mapper = new ObjectMapper();
		String data =mapper.writeValueAsString(dataMap);
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(data);
	}
	
	
	@PassToken
	@RequestMapping(value="/downLoadFile",produces="application/json;charset=utf-8",method=RequestMethod.GET)  
    public R downLoadFile(HttpServletRequest req,HttpServletResponse resp,@RequestParam("guid") String guid) throws IOException{  
//        logger.info("/file/downLoadFile");
//        PrintWriter pw = null;  
//        resp.setContentType("text/html,charset=utf-8");
//        resp.setCharacterEncoding("UTF-8");
//        try {  
//            String path = req.getSession().getServletContext().getRealPath("/files")+ File.separator +fileDTO.getFilePath();  
//            System.out.println(path);
//            File dir = new File(path);  
//            if (!dir.exists()) {  
//                pw=resp.getWriter();  
//                pw.print("<script>alert('抱歉,文件不存在');location.href='javascript:history.go(-1)';</script>);");  
//                return;  
//            }  
//            FileInputStream in = new FileInputStream(path); 
//            OutputStream fos = resp.getOutputStream();  
//            resp.reset();  
//            resp.setContentType("application/x-download");  
//            resp.setHeader("Content-disposition", "attachment;filename="+getFileName(req,fileDTO.getFileName())); 
//            byte[] b = new byte[2048];  
//            int read; 
//            while ((read = in.read(b)) != -1) {  
//                fos.write(b,0,read);  
//            }  
//            fos.flush();  
//            in.close();  
//            fos.close();  
//         } catch (FileNotFoundException e) {  
//            System.out.println(e.getMessage());  
//            pw=resp.getWriter();  
//            pw.print("<script>alert('抱歉,文件下载失败');location.href='javascript:history.go(-1)';</script>);");  
//         }  
		
		//根据attachguid去获取路径与文件名
		Frame_Attach attachList = frame_AttachService.getByFormGuid(guid);
		if (attachList == null) {
			return R.error("未找到文件");
		}
		JSONObject obj = new JSONObject();
		obj.put("fileName", attachList.getAttachName());
		obj.put("url", fileUrl+"/file/"+attachList.getContentUrl());
			
		return R.ok().put("data", obj); 
			
    }  

	
    public String getFileName(HttpServletRequest req,String channelFileName) throws UnsupportedEncodingException{  
        String fileName="";  
        String userAgent = req.getHeader("User-Agent");   
         //针对IE或者以IE为内核的浏览器,加上win10自带的Edge浏览器  
         if (userAgent.contains("MSIE")||userAgent.contains("Trident")||userAgent.contains("Edge")) {  
         fileName = java.net.URLEncoder.encode(channelFileName, "UTF-8");  
        } else {  
         //非IE浏览器的处理： 
         fileName = new String(channelFileName.getBytes("UTF-8"),"ISO-8859-1");  
        }  
         return fileName; 
    }
    
    
    	
}
