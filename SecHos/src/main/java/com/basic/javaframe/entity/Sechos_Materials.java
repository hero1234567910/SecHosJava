package com.basic.javaframe.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.models.auth.In;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



 /**
 * <p>Title: Sechos_Materials</p>
 * <p>Description:</p>
 * @author 
 */
public class Sechos_Materials implements Serializable {
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
	/**材料名称**/
	private String materialName;
	/**材料代码**/
	private String materialCode;
	/**材料单价**/
	private BigDecimal materialPrice;
	/**材料类别**/
	private Integer materialCategory;
	/**材料生产厂家**/
	private String materialManufacturer;
	/**材料状态**/
	private Integer materialStatus;

	/**材料备注**/
	private String materialNote;

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
	 * 设置：材料名称
	 */
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	/**
	 * 获取：材料名称
	 */
	public String getMaterialName() {
		return materialName;
	}
	/**
	 * 设置：材料代码
	 */
	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}
	/**
	 * 获取：材料代码
	 */
	public String getMaterialCode() {
		return materialCode;
	}
	/**
	 * 设置：材料单价
	 */
	public void setMaterialPrice(BigDecimal materialPrice) {
		this.materialPrice = materialPrice;
	}
	/**
	 * 获取：材料单价
	 */
	public BigDecimal getMaterialPrice() {
		return materialPrice;
	}
	/**
	 * 设置：材料类别
	 */
	public void setMaterialCategory(Integer materialCategory) {
		this.materialCategory = materialCategory;
	}
	/**
	 * 获取：材料类别
	 */
	public Integer getMaterialCategory() {
		return materialCategory;
	}
	/**
	 * 设置：材料生产厂家
	 */
	public void setMaterialManufacturer(String materialManufacturer) {
		this.materialManufacturer = materialManufacturer;
	}
	/**
	 * 获取：材料生产厂家
	 */
	public String getMaterialManufacturer() {
		return materialManufacturer;
	}
	/**
	 * 设置：材料状态
	 */
	public void setMaterialStatus(Integer materialStatus) {
		this.materialStatus = materialStatus;
	}
	/**
	 * 获取：材料状态
	 */
	public Integer getMaterialStatus() {
		return materialStatus;
	}

	 public String getMaterialNote() {
		 return materialNote;
	 }

	 public void setMaterialNote(String materialNote) {
		 this.materialNote = materialNote;
	 }
 }
