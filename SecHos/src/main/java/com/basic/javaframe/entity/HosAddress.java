package com.basic.javaframe.entity;

import java.io.Serializable;
import java.util.Date;



 /**
 * <p>Title: HosAddress</p>
 * <p>Description:</p>
 * @author 
 */
public class HosAddress implements Serializable {
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
	/**病区**/
	private String hosInpatient;
	/**楼层**/
	private String hosStorey;
	/**床位号**/
	private String hosBedNumber;
	/**用户标识**/
	private String hosUserGuid;
	
	private String hosUserName;
	
	private String hosUserMobile;
	
	private String fullAddress;
	
	
	public String getFullAddress() {
		return hosInpatient+" "+hosStorey+" "+hosBedNumber;
	}
	public void setFullAddress(String fullAddress) {
		this.fullAddress = fullAddress;
	}
	public String getHosUserName() {
		return hosUserName;
	}
	public void setHosUserName(String hosUserName) {
		this.hosUserName = hosUserName;
	}
	public String getHosUserMobile() {
		return hosUserMobile;
	}
	public void setHosUserMobile(String hosUserMobile) {
		this.hosUserMobile = hosUserMobile;
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
	 * 设置：病区
	 */
	public void setHosInpatient(String hosInpatient) {
		this.hosInpatient = hosInpatient;
	}
	/**
	 * 获取：病区
	 */
	public String getHosInpatient() {
		return hosInpatient;
	}
	/**
	 * 设置：楼层
	 */
	public void setHosStorey(String hosStorey) {
		this.hosStorey = hosStorey;
	}
	/**
	 * 获取：楼层
	 */
	public String getHosStorey() {
		return hosStorey;
	}
	/**
	 * 设置：床位号
	 */
	public void setHosBedNumber(String hosBedNumber) {
		this.hosBedNumber = hosBedNumber;
	}
	/**
	 * 获取：床位号
	 */
	public String getHosBedNumber() {
		return hosBedNumber;
	}
	/**
	 * 设置：用户标识
	 */
	public void setHosUserGuid(String hosUserGuid) {
		this.hosUserGuid = hosUserGuid;
	}
	/**
	 * 获取：用户标识
	 */
	public String getHosUserGuid() {
		return hosUserGuid;
	}
}
