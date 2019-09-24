package com.basic.javaframe.entity;

import java.io.Serializable;
import java.util.Date;



 /**
 * <p>Title: Sechos_Storageamount</p>
 * <p>Description:</p>
 * @author 
 */
public class Sechos_Storageamount implements Serializable {
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
	/**材料Guid**/
	private String drugGuid;
	/**材料名称**/
	private String drugName;
	/**材料代码**/
	private String drugCode;
	/**材料总数**/
	private Integer drugSum;

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
	 * 设置：材料Guid
	 */
	public void setDrugGuid(String drugGuid) {
		this.drugGuid = drugGuid;
	}
	/**
	 * 获取：材料Guid
	 */
	public String getDrugGuid() {
		return drugGuid;
	}
	/**
	 * 设置：材料名称
	 */
	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}
	/**
	 * 获取：材料名称
	 */
	public String getDrugName() {
		return drugName;
	}
	/**
	 * 设置：材料代码
	 */
	public void setDrugCode(String drugCode) {
		this.drugCode = drugCode;
	}
	/**
	 * 获取：材料代码
	 */
	public String getDrugCode() {
		return drugCode;
	}
	/**
	 * 设置：材料总数
	 */
	public void setDrugSum(Integer drugSum) {
		this.drugSum = drugSum;
	}
	/**
	 * 获取：材料总数
	 */
	public Integer getDrugSum() {
		return drugSum;
	}
}
