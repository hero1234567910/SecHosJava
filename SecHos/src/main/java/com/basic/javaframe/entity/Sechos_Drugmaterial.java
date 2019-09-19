package com.basic.javaframe.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



 /**
 * <p>Title: Sechos_Drugmaterial</p>
 * <p>Description:</p>
 * @author 
 */
public class Sechos_Drugmaterial implements Serializable {
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
	/**药品名称**/
	private String drugName;
	/**药品代码**/
	private String drugCode;
	/**药品单价**/

	/**药品保质期时长**/
	private Integer drugShelfLife;
	/**药品出厂日期**/
	@JsonFormat( pattern="yyyy-MM-dd",timezone = "GMT+8")
	private Date drugManufactureDate;

	 public Date getDrugManufactureDate() {
		 return drugManufactureDate;
	 }

	 public void setDrugManufactureDate(Date drugManufactureDate) {
		 this.drugManufactureDate = drugManufactureDate;
	 }

	 public Date getDrugExpirationDate() {
		 return drugExpirationDate;
	 }

	 public void setDrugExpirationDate(Date drugExpirationDate) {
		 this.drugExpirationDate = drugExpirationDate;
	 }

	 /**药品过期日期**/
	 @JsonFormat( pattern="yyyy-MM-dd",timezone = "GMT+8")
	 private Date drugExpirationDate;

	private BigDecimal drugPrice;
	/**药品类别**/
	private Integer drugCategory;
	/**药品生产厂家**/
	private String drugManufacturer;
	/**药品状态**/
	private Integer drugStatus;

	/**备注**/
	private String drugNote;

	 public String getDrugNote() {
		 return drugNote;
	 }

	 public void setDrugNote(String drugNote) {
		 this.drugNote = drugNote;
	 }

	 public Integer getDrugShelfLife() {
		 return drugShelfLife;
	 }

	 public void setDrugShelfLife(Integer drugShelfLife) {
		 this.drugShelfLife = drugShelfLife;
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
	 * 设置：药品名称
	 */
	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}
	/**
	 * 获取：药品名称
	 */
	public String getDrugName() {
		return drugName;
	}
	/**
	 * 设置：药品代码
	 */
	public void setDrugCode(String drugCode) {
		this.drugCode = drugCode;
	}
	/**
	 * 获取：药品代码
	 */
	public String getDrugCode() {
		return drugCode;
	}

	 public void setDrugPrice(BigDecimal drugPrice) {
		 this.drugPrice = drugPrice;
	 }

	 public BigDecimal getDrugPrice() {
		 return drugPrice;
	 }

	 /**
	 * 设置：药品类别
	 */
	public void setDrugCategory(Integer drugCategory) {
		this.drugCategory = drugCategory;
	}
	/**
	 * 获取：药品类别
	 */
	public Integer getDrugCategory() {
		return drugCategory;
	}
	/**
	 * 设置：药品生产厂家
	 */
	public void setDrugManufacturer(String drugManufacturer) {
		this.drugManufacturer = drugManufacturer;
	}
	/**
	 * 获取：药品生产厂家
	 */
	public String getDrugManufacturer() {
		return drugManufacturer;
	}
	/**
	 * 设置：药品状态
	 */
	public void setDrugStatus(Integer drugStatus) {
		this.drugStatus = drugStatus;
	}
	/**
	 * 获取：药品状态
	 */
	public Integer getDrugStatus() {
		return drugStatus;
	}
}
