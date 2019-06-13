package com.basic.javaframe.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



 /**
 * <p>Title: HosOrderitem</p>
 * <p>Description:</p>
 * @author 
 */
public class HosOrderitem implements Serializable {
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
	/**商品标识**/
	private String goodsGuid;
	/**数量**/
	private Integer count;
	/**小计**/
	private BigDecimal totalMoney;
	/**订单标识**/
	private String orderGuid;
	/**订单商品单价**/
	private BigDecimal itemPrice;
	
	private HosGoods hosGoods;
	
	public HosGoods getHosGoods() {
		return hosGoods;
	}
	public void setHosGoods(HosGoods hosGoods) {
		this.hosGoods = hosGoods;
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
	 * 设置：商品标识
	 */
	public void setGoodsGuid(String goodsGuid) {
		this.goodsGuid = goodsGuid;
	}
	/**
	 * 获取：商品标识
	 */
	public String getGoodsGuid() {
		return goodsGuid;
	}
	/**
	 * 设置：数量
	 */
	public void setCount(Integer count) {
		this.count = count;
	}
	/**
	 * 获取：数量
	 */
	public Integer getCount() {
		return count;
	}
	/**
	 * 设置：小计
	 */
	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}
	/**
	 * 获取：小计
	 */
	public BigDecimal getTotalMoney() {
		return totalMoney;
	}
	/**
	 * 设置：订单标识
	 */
	public void setOrderGuid(String orderGuid) {
		this.orderGuid = orderGuid;
	}
	/**
	 * 获取：订单标识
	 */
	public String getOrderGuid() {
		return orderGuid;
	}
	/**
	 * 设置：订单商品单价
	 */
	public void setItemPrice(BigDecimal itemPrice) {
		this.itemPrice = itemPrice;
	}
	/**
	 * 获取：订单商品单价
	 */
	public BigDecimal getItemPrice() {
		return itemPrice;
	}
}
