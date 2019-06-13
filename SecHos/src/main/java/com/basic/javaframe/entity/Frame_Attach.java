package com.basic.javaframe.entity;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
/**
 * <p>Title: Frame_TableInfo</p>
 * <p>Description: 附件表</p>
 * @author my
 */
public class Frame_Attach {
    //行标识
    private int rowId;
    //记录唯一标识
    private String rowGuid;
    //创建时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    //删除标识
    private int delFlag;
    //排序号
    private int sortSq;
    //附件名
    private String attachName;
    //文件类型
    private String contentType;
    //文件长度
    private int contentLength;
    //文件地址或内容
    private String contentUrl;
    //与记录关联唯一标识
    private String formRowGuid;
    
    private String url;

    public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getRowId() {
        return rowId;
    }

    public void setRowId(int rowId) {
        this.rowId = rowId;
    }

    public String getRowGuid() {
        return rowGuid;
    }

    public void setRowGuid(String rowGuid) {
        this.rowGuid = rowGuid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }

    public int getSortSq() {
        return sortSq;
    }

    public void setSortSq(int sortSq) {
        this.sortSq = sortSq;
    }

    public String getAttachName() {
        return attachName;
    }

    public void setAttachName(String attachName) {
        this.attachName = attachName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public String getFormRowGuid() {
        return formRowGuid;
    }

    public void setFormRowGuid(String formRowGuid) {
        this.formRowGuid = formRowGuid;
    }
}
