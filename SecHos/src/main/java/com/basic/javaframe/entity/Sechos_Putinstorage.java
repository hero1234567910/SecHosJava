package com.basic.javaframe.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



 /**
 * <p>Title: Sechos_Putinstorage</p>
 * <p>Description:</p>
 * @author 
 */
public class Sechos_Putinstorage implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/****/
	private Integer rowId;
	/****/
	private String rowGuid;
	/****/
	@JsonFormat( pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
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
	/**材料数量**/
	private Integer drugAmount;
	/**材料总价**/
	private BigDecimal drugTotalPrice;
	/**采购表Guid**/
	private String purchaseGuid;
	/**材料过期时间**/
	@JsonFormat( pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date drugOverdue;
	/**材料入库时间**/
	@JsonFormat( pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date drugInDate;
	/**入库人Guid**/
	private String personGuid;

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
	 * 设置：材料数量
	 */
	public void setDrugAmount(Integer drugAmount) {
		this.drugAmount = drugAmount;
	}
	/**
	 * 获取：材料数量
	 */
	public Integer getDrugAmount() {
		return drugAmount;
	}
	/**
	 * 设置：材料总价
	 */
	public void setDrugTotalPrice(BigDecimal drugTotalPrice) {
		this.drugTotalPrice = drugTotalPrice;
	}
	/**
	 * 获取：材料总价
	 */
	public BigDecimal getDrugTotalPrice() {
		return drugTotalPrice;
	}
	/**
	 * 设置：采购表Guid
	 */
	public void setPurchaseGuid(String purchaseGuid) {
		this.purchaseGuid = purchaseGuid;
	}
	/**
	 * 获取：采购表Guid
	 */
	public String getPurchaseGuid() {
		return purchaseGuid;
	}
	/**
	 * 设置：材料过期时间
	 */
	public void setDrugOverdue(Date drugOverdue) {
		this.drugOverdue = drugOverdue;
	}
	/**
	 * 获取：材料过期时间
	 */
	public Date getDrugOverdue() {
		return drugOverdue;
	}
	/**
	 * 设置：材料入库时间
	 */
	public void setDrugInDate(Date drugInDate) {
		this.drugInDate = drugInDate;
	}
	/**
	 * 获取：材料入库时间
	 */
	public Date getDrugInDate() {
		return drugInDate;
	}
	/**
	 * 设置：入库人Guid
	 */
	public void setPersonGuid(String personGuid) {
		this.personGuid = personGuid;
	}
	/**
	 * 获取：入库人Guid
	 */
	public String getPersonGuid() {
		return personGuid;
	}
}
