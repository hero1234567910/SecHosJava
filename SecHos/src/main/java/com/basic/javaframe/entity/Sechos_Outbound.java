package com.basic.javaframe.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



 /**
 * <p>Title: Sechos_Outbound</p>
 * <p>Description:</p>
 * @author 
 */
public class Sechos_Outbound implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/****/
	private Integer rowId;
	/****/
	private String rowGuid;
	/****/
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;
	/****/
	private Integer delFlag;
	/****/
	private Integer sortSq;
	/**出库单号**/
	private String outboundOrderNum;
	/**下单日期**/
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date orderDate;
	/**出库日期**/
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date outboundDate;
	/**出库状态**/
	private Integer outboundStatus;
	/**出库备注**/
	private String outboundNote;
	/**出库总价**/
	private BigDecimal outboundPrice;
	/**出库人Guid**/
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
	 * 设置：出库单号
	 */
	public void setOutboundOrderNum(String outboundOrderNum) {
		this.outboundOrderNum = outboundOrderNum;
	}
	/**
	 * 获取：出库单号
	 */
	public String getOutboundOrderNum() {
		return outboundOrderNum;
	}
	/**
	 * 设置：下单日期
	 */
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	/**
	 * 获取：下单日期
	 */
	public Date getOrderDate() {
		return orderDate;
	}
	/**
	 * 设置：出库日期
	 */
	public void setOutboundDate(Date outboundDate) {
		this.outboundDate = outboundDate;
	}
	/**
	 * 获取：出库日期
	 */
	public Date getOutboundDate() {
		return outboundDate;
	}
	/**
	 * 设置：出库状态
	 */
	public void setOutboundStatus(Integer outboundStatus) {
		this.outboundStatus = outboundStatus;
	}
	/**
	 * 获取：出库状态
	 */
	public Integer getOutboundStatus() {
		return outboundStatus;
	}
	/**
	 * 设置：出库备注
	 */
	public void setOutboundNote(String outboundNote) {
		this.outboundNote = outboundNote;
	}
	/**
	 * 获取：出库备注
	 */
	public String getOutboundNote() {
		return outboundNote;
	}
	/**
	 * 设置：出库总价
	 */
	public void setOutboundPrice(BigDecimal outboundPrice) {
		this.outboundPrice = outboundPrice;
	}
	/**
	 * 获取：出库总价
	 */
	public BigDecimal getOutboundPrice() {
		return outboundPrice;
	}
	/**
	 * 设置：出库人Guid
	 */
	public void setPersonGuid(String personGuid) {
		this.personGuid = personGuid;
	}
	/**
	 * 获取：出库人Guid
	 */
	public String getPersonGuid() {
		return personGuid;
	}
}
