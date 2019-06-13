package com.basic.javaframe.entity;

import java.io.Serializable;
import java.util.Date;



 /**
 * <p>Title: HosGoodstype</p>
 * <p>Description:</p>
 * @author 
 */
public class HosGoodstype implements Serializable {
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
	/**类型名称**/
	private String typeName;
	/**食品上级类型编号**/
	private String pgoodsTypeCode;
	/**食品类型编号**/
	private String goodsTypeCode;
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
	 * 设置：类型名称
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	/**
	 * 获取：类型名称
	 */
	public String getTypeName() {
		return typeName;
	}
	/**
	 * 设置：食品上级类型编号
	 */
	public void setPgoodsTypeCode(String pgoodsTypeCode) {
		this.pgoodsTypeCode = pgoodsTypeCode;
	}
	/**
	 * 获取：食品上级类型编号
	 */
	public String getPgoodsTypeCode() {
		return pgoodsTypeCode;
	}
	/**
	 * 设置：食品类型编号
	 */
	public void setGoodsTypeCode(String goodsTypeCode) {
		this.goodsTypeCode = goodsTypeCode;
	}
	/**
	 * 获取：食品类型编号
	 */
	public String getGoodsTypeCode() {
		return goodsTypeCode;
	}

}
