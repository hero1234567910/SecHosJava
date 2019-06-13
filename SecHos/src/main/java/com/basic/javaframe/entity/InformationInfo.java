package com.basic.javaframe.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;



 /**
 * <p>Title: InformationInfo</p>
 * <p>Description:</p>
 * @author 
 */
public class InformationInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/****/
	private Integer rowId;
	/****/
	private String rowGuid;
	/****/
	private Date createTime;
	/****/
	private Integer delFlag;
	/****/
	private Integer sortSq;
	/**标题**/
	private String title;
	/**信息类别**/
	private Integer infoType;
	/**链接地址**/
	private String infoUrl;
	/**内容**/
	private String content;
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	/**信息日期**/
	private Date infoDate;
	/**创建人**/
	private String createUserName;
	/**创建人rowguid**/
	private String createUserGuid;
	/**所属部门**/
	private String createDeptName;
	/**所属部门guid**/
	private String createDeptGuid;
	/**状态**/
	private Integer status;
	/**附件**/
	private String attachGuid;
	/**点击次数**/
	private Integer clickTimes;
	/**栏目Guid**/
	private String categoryGuid;
	 /**信息类型名称**/
	 private String typeName;
	
	private String[] attachRowGuids;
	
	private String[] categoryGuids;

	 public String getTypeName() {
		 return typeName;
	 }

	 public void setTypeName(String typeName) {
		 this.typeName = typeName;
	 }

	 public String[] getCategoryGuids() {
		return categoryGuids;
	}

	public void setCategoryGuids(String[] categoryGuids) {
		this.categoryGuids = categoryGuids;
	}

	public String[] getAttachRowGuids() {
		return attachRowGuids;
	}

	public void setAttachRowGuids(String[] attachRowGuids) {
		this.attachRowGuids = attachRowGuids;
	}

	public String getCategoryGuid() {
		 return categoryGuid;
	 }

	 public void setCategoryGuid(String categoryGuid) {
		 this.categoryGuid = categoryGuid;
	 }

	 /**
	 * 设置：
	 */
	public void setRowId(Integer rowId) {
		this.rowId = rowId;
	}
	/**
	 * 获取：
	 */
	public Integer getRowId() {
		return rowId;
	}
	/**
	 * 设置：
	 */
	public void setRowGuid(String rowGuid) {
		this.rowGuid = rowGuid;
	}
	/**
	 * 获取：
	 */
	public String getRowGuid() {
		return rowGuid;
	}
	/**
	 * 设置：
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：
	 */
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	/**
	 * 获取：
	 */
	public Integer getDelFlag() {
		return delFlag;
	}
	/**
	 * 设置：
	 */
	public void setSortSq(Integer sortSq) {
		this.sortSq = sortSq;
	}
	/**
	 * 获取：
	 */
	public Integer getSortSq() {
		return sortSq;
	}
	/**
	 * 设置：标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取：标题
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置：信息类别
	 */
	public void setInfoType(Integer infoType) {
		this.infoType = infoType;
	}
	/**
	 * 获取：信息类别
	 */
	public Integer getInfoType() {
		return infoType;
	}
	/**
	 * 设置：链接地址
	 */
	public void setInfoUrl(String infoUrl) {
		this.infoUrl = infoUrl;
	}
	/**
	 * 获取：链接地址
	 */
	public String getInfoUrl() {
		return infoUrl;
	}
	/**
	 * 设置：内容
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取：内容
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置：信息日期
	 */
	public void setInfoDate(Date infoDate) {
		this.infoDate = infoDate;
	}
	/**
	 * 获取：信息日期
	 */
	public Date getInfoDate() {
		return infoDate;
	}
	/**
	 * 设置：创建人
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	/**
	 * 获取：创建人
	 */
	public String getCreateUserName() {
		return createUserName;
	}
	/**
	 * 设置：创建人rowguid
	 */
	public void setCreateUserGuid(String createUserGuid) {
		this.createUserGuid = createUserGuid;
	}
	/**
	 * 获取：创建人rowguid
	 */
	public String getCreateUserGuid() {
		return createUserGuid;
	}
	/**
	 * 设置：所属部门
	 */
	public void setCreateDeptName(String createDeptName) {
		this.createDeptName = createDeptName;
	}
	/**
	 * 获取：所属部门
	 */
	public String getCreateDeptName() {
		return createDeptName;
	}
	/**
	 * 设置：所属部门guid
	 */
	public void setCreateDeptGuid(String createDeptGuid) {
		this.createDeptGuid = createDeptGuid;
	}
	/**
	 * 获取：所属部门guid
	 */
	public String getCreateDeptGuid() {
		return createDeptGuid;
	}
	/**
	 * 设置：状态
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：状态
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：附件
	 */
	public void setAttachGuid(String attachGuid) {
		this.attachGuid = attachGuid;
	}
	/**
	 * 获取：附件
	 */
	public String getAttachGuid() {
		return attachGuid;
	}
	/**
	 * 设置：点击次数
	 */
	public void setClickTimes(Integer clickTimes) {
		this.clickTimes = clickTimes;
	}
	/**
	 * 获取：点击次数
	 */
	public Integer getClickTimes() {
		return clickTimes;
	}
}
