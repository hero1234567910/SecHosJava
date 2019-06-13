package com.basic.javaframe.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;



 /**
 * <p>Title: WxHosGoods</p>
 * <p>Description:</p>
 * @author 
 */
public class HosGoods implements Serializable {
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
	/**商品名称**/
	private String goodsName;
	/**商品类型标识**/
	private String goodsTypeGuid;
	/**商品价格**/
	private BigDecimal goodsPrice;
	/**商品图片标识**/
	private String goodsImgGuid;
	/**商品信息**/
	private String goodsInfo;
	/**是否上架**/
	private Integer isShelf;
	
	private String uploadImgGuid;
	
	private String goodsUrl;
	
	public String getUploadImgGuid() {
		return uploadImgGuid;
	}
	public void setUploadImgGuid(String uploadImgGuid) {
		this.uploadImgGuid = uploadImgGuid;
	}
	public String getGoodsUrl() {
		return "http://ey.nxjnjc.com/file/"+goodsUrl;
	}
	public void setGoodsUrl(String goodsUrl) {
		this.goodsUrl = goodsUrl;
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
	 * 设置：商品名称
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	/**
	 * 获取：商品名称
	 */
	public String getGoodsName() {
		return goodsName;
	}
	/**
	 * 设置：商品类型标识
	 */
	public void setGoodsTypeGuid(String goodsTypeGuid) {
		this.goodsTypeGuid = goodsTypeGuid;
	}
	/**
	 * 获取：商品类型标识
	 */
	public String getGoodsTypeGuid() {
		return goodsTypeGuid;
	}
	/**
	 * 设置：商品价格
	 */
	public void setGoodsPrice(BigDecimal goodsPrice) {
		this.goodsPrice = goodsPrice;
	}
	/**
	 * 获取：商品价格
	 */
	public BigDecimal getGoodsPrice() {
		return goodsPrice;
	}
	/**
	 * 设置：商品图片标识
	 */
	public void setGoodsImgGuid(String goodsImgGuid) {
		this.goodsImgGuid = goodsImgGuid;
	}
	/**
	 * 获取：商品图片标识
	 */
	public String getGoodsImgGuid() {
		return goodsImgGuid;
	}
	/**
	 * 设置：商品信息
	 */
	public void setGoodsInfo(String goodsInfo) {
		this.goodsInfo = goodsInfo;
	}
	/**
	 * 获取：商品信息
	 */
	public String getGoodsInfo() {
		return goodsInfo;
	}
	/**
	 * 设置：是否上架
	 */
	public void setIsShelf(Integer isShelf) {
		this.isShelf = isShelf;
	}
	/**
	 * 获取：是否上架
	 */
	public Integer getIsShelf() {
		return isShelf;
	}
}
