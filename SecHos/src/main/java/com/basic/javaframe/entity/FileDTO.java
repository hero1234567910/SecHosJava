package com.basic.javaframe.entity;

import java.io.Serializable;

/**
 * 上传文件实体
* <p>Title: FileDTO</p>  
* <p>Description: </p>  
* @author hero
 */
public class FileDTO implements Serializable{  
	  
    private static final long serialVersionUID = -2487063172455865142L;  
  
    private int id;  
    private String fileName;  
    private String filePath; 
    public int getId() { 
        return id;  
    }  
    public void setId(int id) {  
        this.id = id;  
    }  
    public String getFileName() {  
        return fileName;  
    }  
    public void setFileName(String fileName) {  
        this.fileName = fileName;  
    }  
    public String getFilePath() {  
        return filePath;  
    }  
    public void setFilePath(String filePath) {  
        this.filePath = filePath;  
    }  
      
}