package com.basic.javaframe.entity;

import java.io.Serializable;
import java.util.Date;



 /**
 * <p>Title: InformationCategory</p>
 * <p>Description:</p>
 * @author 
 */
public class InformationCategory implements Serializable {
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
	/**栏目编号**/
	private String categoryCode;
	/**栏目名称**/
	private String categoryName;
	/**是否单条信息**/
	private Integer isSingle;
	/**是否需要审核**/
	private Integer isNeedAudit;
	/**描述**/
	private String description;
	/**父栏目编号**/
	private String pcategoryCode;

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
	 * 设置：栏目编号
	 */
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	/**
	 * 获取：栏目编号
	 */
	public String getCategoryCode() {
		return categoryCode;
	}
	/**
	 * 设置：栏目名称
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	/**
	 * 获取：栏目名称
	 */
	public String getCategoryName() {
		return categoryName;
	}
	/**
	 * 设置：是否单条信息
	 */
	public void setIsSingle(Integer isSingle) {
		this.isSingle = isSingle;
	}
	/**
	 * 获取：是否单条信息
	 */
	public Integer getIsSingle() {
		return isSingle;
	}
	/**
	 * 设置：是否需要审核
	 */
	public void setIsNeedAudit(Integer isNeedAudit) {
		this.isNeedAudit = isNeedAudit;
	}
	/**
	 * 获取：是否需要审核
	 */
	public Integer getIsNeedAudit() {
		return isNeedAudit;
	}
	/**
	 * 设置：描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 获取：描述
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 设置：父栏目编号
	 */
	public void setPcategoryCode(String pcategoryCode) {
		this.pcategoryCode = pcategoryCode;
	}
	/**
	 * 获取：父栏目编号
	 */
	public String getPcategoryCode() {
		return pcategoryCode;
	}
}
