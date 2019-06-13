package com.basic.javaframe.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * <p>Title: Frame_Dept</p>
 * <p>Description: 部门管理实体</p>
 * @author wzl
 */
public class Frame_Dept {
    //行标识
    private Integer rowId;
    //记录唯一标识
    private String rowGuid;
    //删除标识
    private int delFlag;
    //创建时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;
    //部门编号
    private String deptCode;
    //上级部门编号
    private String pdeptCode;
    //子部门数量
    private int hasChild;
    //部门名称
    private String deptName;
    //部门简称
    private String shortName;
    //部门编码
    private String oucode;
    //排序号
    private int sortSq;
    //电话
    private String tel;
    //传真
    private String fax;
    //地址
    private String address;
    //描述
    private String description;
    
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
	public int getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getPdeptCode() {
		return pdeptCode;
	}
	public void setPdeptCode(String pdeptCode) {
		this.pdeptCode = pdeptCode;
	}
	public int getHasChild() {
		return hasChild;
	}
	public void setHasChild(int hasChild) {
		this.hasChild = hasChild;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getOucode() {
		return oucode;
	}
	public void setOucode(String oucode) {
		this.oucode = oucode;
	}
	public int getSortSq() {
		return sortSq;
	}
	public void setSortSq(int sortSq) {
		this.sortSq = sortSq;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

   
}
