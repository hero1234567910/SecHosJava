package com.basic.javaframe.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



 /**
 * <p>Title: Sechos_Purchasingm2m</p>
 * <p>Description:</p>
 * @author 
 */
public class Sechos_Purchasingm2m implements Serializable {
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
	/**材料数量**/
	private Integer drugAmount;
	 /**材料单价**/
	 private BigDecimal drugPrice;
	/**材料总价**/
	private BigDecimal drugTotalPrice;
	/**采购表Guid**/
	private String purchaseGuid;

	/**材料过期日期**/
	@JsonFormat( pattern="yyyy-MM-dd",timezone = "GMT+8")
	private Date drugOverdue;

	 public Date getDrugOverdue() {
		 return drugOverdue;
	 }

	 public void setDrugOverdue(Date drugOverdue) {
		 this.drugOverdue = drugOverdue;
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

	 public BigDecimal getDrugPrice() {
		 return drugPrice;
	 }

	 public void setDrugPrice(BigDecimal drugPrice) {
		 this.drugPrice = drugPrice;
	 }

	 @Override
	 public String toString() {
		 return "Sechos_Purchasingm2m{" +
				 "rowId=" + rowId +
				 ", rowGuid='" + rowGuid + '\'' +
				 ", createTime=" + createTime +
				 ", delFlag=" + delFlag +
				 ", sortSq=" + sortSq +
				 ", drugGuid='" + drugGuid + '\'' +
				 ", drugName='" + drugName + '\'' +
				 ", drugAmount=" + drugAmount +
				 ", drugPrice=" + drugPrice +
				 ", drugTotalPrice=" + drugTotalPrice +
				 ", purchaseGuid='" + purchaseGuid + '\'' +
				 ", drugOverdue=" + drugOverdue +
				 '}';
	 }
 }
