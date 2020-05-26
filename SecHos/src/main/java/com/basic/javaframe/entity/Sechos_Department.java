package com.basic.javaframe.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;


/**
 * <p>Title: Sechos_Department</p>
 * <p>Description:</p>
 *
 * @author wzl
 */
public class Sechos_Department implements Serializable {
    private static final long serialVersionUID = 1L;

    /****/
    private Integer rowId;
    /****/
    private String rowGuid;
    /****/
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;
    /****/
    private Integer delFlag;
    /****/
    private Integer sortSq;
    /**
     * 科室名称
     **/
    private String departmentName;
    /**
     * 科室图片guid
     **/
    private String picGuid;

    private String uploadImgGuid;

    private String imgUrl;

    public Integer getRowId() {
        return rowId;
    }

    public void setRowId(Integer rowId) {
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

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getSortSq() {
        return sortSq;
    }

    public void setSortSq(Integer sortSq) {
        this.sortSq = sortSq;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getPicGuid() {
        return picGuid;
    }

    public void setPicGuid(String picGuid) {
        this.picGuid = picGuid;
    }

    public String getUploadImgGuid() {
        return uploadImgGuid;
    }

    public void setUploadImgGuid(String uploadImgGuid) {
        this.uploadImgGuid = uploadImgGuid;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
