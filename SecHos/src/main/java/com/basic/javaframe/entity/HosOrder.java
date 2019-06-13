package com.basic.javaframe.entity;

import com.basic.javaframe.common.utils.SpringContextUtils;
import com.basic.javaframe.service.Frame_CodeValueService;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;



 /**
 * <p>Title: HosOrder</p>
 * <p>Description:</p>
 * @author 
 */
public class HosOrder implements Serializable {
	private static final long serialVersionUID = 1L;
	Frame_CodeValueService codeValueService = (Frame_CodeValueService) SpringContextUtils.getBean("codeValueService");
	/****/
	private Integer rowId;
	/****/
	private String rowGuid;
	/****/
	@JsonFormat( pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date createTime;
	/****/
	private Integer delFlag;
	/****/
	private Integer sortSq;
	/**订单流水号**/
	private String orderNumber;
	/**下订单所属用户标识**/
	private String orderUserGuid;
	/**订单商品标识**/
	private String orderItemGuid;
	/**收货人姓名**/
	private String consigneeName;
	/**病区**/
	private String consigneeInpatient;
	/**楼层**/
	private String consigneeStorey;
	/**床位号**/
	private String consigneeBedNumber;
	/**收货人手机号**/
	private String consigneeMobile;
	/**订单状态:0:待支付，1：已支付，2：已完成，3：已取消**/
	private Integer orderStatus;
	
	private String orderStatusText;

	private List<HosOrderitem> hosOrderitems;
	
	public List<HosOrderitem> getHosOrderitems() {
		return hosOrderitems;
	}

	public void setHosOrderitems(List<HosOrderitem> hosOrderitems) {
		this.hosOrderitems = hosOrderitems;
	}

	public String getOrderStatusText() {
		//根据对应的value获取对应的值
		if (orderStatus!= null) {
			return codeValueService.getCodeByNameAndValue("订单状态", String.valueOf(orderStatus));
		}else
			return "";
	}

	 public void setOrderStatusText(String orderStatusText) {
		 this.orderStatusText = orderStatusText;
	 }

	/**备注**/
	private String remark;
	/**预定时间(送达时间)**/
	@JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
	private Date reserveTime;
	/**预定时间+早/中/晚**/
	private String reserveTimeSuffix;
	/**总金额**/
	private BigDecimal orderMoney;
	/**商户订单号**/
	private String merchantNumber;
	/**支付时间**/
	private Date payTime;

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
	 * 设置：订单流水号
	 */
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	/**
	 * 获取：订单流水号
	 */
	public String getOrderNumber() {
		return orderNumber;
	}
	/**
	 * 设置：下订单所属用户标识
	 */
	public void setOrderUserGuid(String orderUserGuid) {
		this.orderUserGuid = orderUserGuid;
	}
	/**
	 * 获取：下订单所属用户标识
	 */
	public String getOrderUserGuid() {
		return orderUserGuid;
	}
	/**
	 * 设置：订单商品标识
	 */
	public void setOrderItemGuid(String orderItemGuid) {
		this.orderItemGuid = orderItemGuid;
	}
	/**
	 * 获取：订单商品标识
	 */
	public String getOrderItemGuid() {
		return orderItemGuid;
	}
	/**
	 * 设置：收货人姓名
	 */
	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}
	/**
	 * 获取：收货人姓名
	 */
	public String getConsigneeName() {
		return consigneeName;
	}
	/**
	 * 设置：病区
	 */
	public void setConsigneeInpatient(String consigneeInpatient) {
		this.consigneeInpatient = consigneeInpatient;
	}
	/**
	 * 获取：病区
	 */
	public String getConsigneeInpatient() {
		return consigneeInpatient;
	}
	/**
	 * 设置：楼层
	 */
	public void setConsigneeStorey(String consigneeStorey) {
		this.consigneeStorey = consigneeStorey;
	}
	/**
	 * 获取：楼层
	 */
	public String getConsigneeStorey() {
		return consigneeStorey;
	}
	/**
	 * 设置：床位号
	 */
	public void setConsigneeBedNumber(String consigneeBedNumber) {
		this.consigneeBedNumber = consigneeBedNumber;
	}
	/**
	 * 获取：床位号
	 */
	public String getConsigneeBedNumber() {
		return consigneeBedNumber;
	}
	/**
	 * 设置：收货人手机号
	 */
	public void setConsigneeMobile(String consigneeMobile) {
		this.consigneeMobile = consigneeMobile;
	}
	/**
	 * 获取：收货人手机号
	 */
	public String getConsigneeMobile() {
		return consigneeMobile;
	}
	/**
	 * 设置：订单状态:0:待支付，1：已支付，2：已完成，3：已取消
	 */
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	/**
	 * 获取：订单状态:0:待支付，1：已支付，2：已完成，3：已取消
	 */
	public Integer getOrderStatus() {
		return orderStatus;
	}
	/**
	 * 设置：备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：备注
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * 设置：预定时间(送达时间)
	 */
	public void setReserveTime(Date reserveTime) {
		this.reserveTime = reserveTime;
	}
	/**
	 * 获取：预定时间(送达时间)
	 */
	public Date getReserveTime() {
		return reserveTime;
	}
	/**
	 * 设置：预定时间+早/中/晚
	 */
	public void setReserveTimeSuffix(String reserveTimeSuffix) {
		this.reserveTimeSuffix = reserveTimeSuffix;
	}
	/**
	 * 获取：预定时间+早/中/晚
	 */
	public String getReserveTimeSuffix() {
		return reserveTimeSuffix;
	}
	/**
	 * 设置：总金额
	 */
	public void setOrderMoney(BigDecimal orderMoney) {
		this.orderMoney = orderMoney;
	}
	/**
	 * 获取：总金额
	 */
	public BigDecimal getOrderMoney() {
		return orderMoney;
	}
	/**
	 * 设置：商户订单号
	 */
	public void setMerchantNumber(String merchantNumber) {
		this.merchantNumber = merchantNumber;
	}
	/**
	 * 获取：商户订单号
	 */
	public String getMerchantNumber() {
		return merchantNumber;
	}
	/**
	 * 设置：支付时间
	 */
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	/**
	 * 获取：支付时间
	 */
	public Date getPayTime() {
		return payTime;
	}

	@Override
	public String toString() {
		return "HosOrder [codeValueService=" + codeValueService + ", rowId=" + rowId + ", rowGuid=" + rowGuid
				+ ", createTime=" + createTime + ", delFlag=" + delFlag + ", sortSq=" + sortSq + ", orderNumber="
				+ orderNumber + ", orderUserGuid=" + orderUserGuid + ", orderItemGuid=" + orderItemGuid
				+ ", consigneeName=" + consigneeName + ", consigneeInpatient=" + consigneeInpatient
				+ ", consigneeStorey=" + consigneeStorey + ", consigneeBedNumber=" + consigneeBedNumber
				+ ", consigneeMobile=" + consigneeMobile + ", orderStatus=" + orderStatus + ", orderStatusText="
				+ orderStatusText + ", remark=" + remark + ", reserveTime=" + reserveTime + ", reserveTimeSuffix="
				+ reserveTimeSuffix + ", orderMoney=" + orderMoney + ", merchantNumber=" + merchantNumber + ", payTime="
				+ payTime + "]";
	}
	
	
}
