package com.basic.javaframe.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;


/**
 * <p>Title: Sechos_Professor</p>
 * <p>Description:</p>
 *
 * @author wzl
 */
public class Sechos_Professor implements Serializable {
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
     * 专家名称
     **/
    private String professorName;
    /**
     * 专家图片guid
     **/
    private String picGuid;

    /**
     * 专家描述
     */
    private String professorContent;

    /**
     * 专家所属医院
     */
    private String professorHos;

    /**
     * 专家职称
     */
    private String professorTitle;

    /**
     * 科室Guid
     */
    private String departmentGuid;

    /**
     *
     */
    private String uploadImgGuid;

    /**
     * 所属科室名称
     */
    private String departmentName;

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

    public String getPicGuid() {
        return picGuid;
    }

    public void setPicGuid(String picGuid) {
        this.picGuid = picGuid;
    }

    public String getProfessorName() {
        return professorName;
    }

    public void setProfessorName(String professorName) {
        this.professorName = professorName;
    }

    public String getProfessorContent() {
        return professorContent;
    }

    public void setProfessorContent(String professorContent) {
        this.professorContent = professorContent;
    }

    public String getProfessorHos() {
        return professorHos;
    }

    public void setProfessorHos(String professorHos) {
        this.professorHos = professorHos;
    }

    public String getProfessorTitle() {
        return professorTitle;
    }

    public void setProfessorTitle(String professorTitle) {
        this.professorTitle = professorTitle;
    }

    public String getDepartmentGuid() {
        return departmentGuid;
    }

    public void setDepartmentGuid(String departmentGuid) {
        this.departmentGuid = departmentGuid;
    }

    public String getUploadImgGuid() {
        return uploadImgGuid;
    }

    public void setUploadImgGuid(String uploadImgGuid) {
        this.uploadImgGuid = uploadImgGuid;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
