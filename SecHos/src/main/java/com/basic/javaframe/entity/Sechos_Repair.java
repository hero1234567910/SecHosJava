package com.basic.javaframe.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;



 /**
 * <p>Title: Sechos_Repair</p>
 * <p>Description:</p>
 * @author 
 */
public class Sechos_Repair implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/****/
	private Integer rowId;
	/****/
	private String rowGuid;
	/****/
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	private Date createTime;
	/****/
	private Integer delFlag;
	/****/
	private Integer sortSq;
	/**报修人姓名**/
	private String repairName;
	/**报修人guid**/
	private String repairGuid;
	/**报修人电话**/
	private String repairPhone;
	/**设备名称**/
	private String deviceName;
	/**设备位置**/
	private String devicePlace;
	/**损坏部位**/
	private String damagedParts;
	/**报修内容**/
	private String reportContent;
	/**报修状态**/
	private Integer repairStatus;
	/**报修图片guid**/
	private String picGuid;
	/**维修人员Guid**/
	private String maintainGuid;

	private String uploadImgGuid;

	 public String getUploadImgGuid() {
		 return uploadImgGuid;
	 }

	 public void setUploadImgGuid(String uploadImgGuid) {
		 this.uploadImgGuid = uploadImgGuid;
	 }

	 public Date getMaintainTime() {
		 return maintainTime;
	 }

	 public void setMaintainTime(Date maintainTime) {
		 this.maintainTime = maintainTime;
	 }

	 /**维修人员姓名**/
	private String maintainName;
	/**维修完成时间**/
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	private Date maintainTime;

	 public String getMaintainGuid() {
		 return maintainGuid;
	 }

	 public void setMaintainGuid(String maintainGuid) {
		 this.maintainGuid = maintainGuid;
	 }

	 public String getMaintainName() {
		 return maintainName;
	 }

	 public void setMaintainName(String maintainName) {
		 this.maintainName = maintainName;
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
	 * 设置：报修人姓名
	 */
	public void setRepairName(String repairName) {
		this.repairName = repairName;
	}
	/**
	 * 获取：报修人姓名
	 */
	public String getRepairName() {
		return repairName;
	}
	/**
	 * 设置：报修人guid
	 */
	public void setRepairGuid(String repairGuid) {
		this.repairGuid = repairGuid;
	}
	/**
	 * 获取：报修人guid
	 */
	public String getRepairGuid() {
		return repairGuid;
	}
	/**
	 * 设置：报修人电话
	 */
	public void setRepairPhone(String repairPhone) {
		this.repairPhone = repairPhone;
	}
	/**
	 * 获取：报修人电话
	 */
	public String getRepairPhone() {
		return repairPhone;
	}
	/**
	 * 设置：设备名称
	 */
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	/**
	 * 获取：设备名称
	 */
	public String getDeviceName() {
		return deviceName;
	}
	/**
	 * 设置：设备位置
	 */
	public void setDevicePlace(String devicePlace) {
		this.devicePlace = devicePlace;
	}
	/**
	 * 获取：设备位置
	 */
	public String getDevicePlace() {
		return devicePlace;
	}
	/**
	 * 设置：损坏部位
	 */
	public void setDamagedParts(String damagedParts) {
		this.damagedParts = damagedParts;
	}
	/**
	 * 获取：损坏部位
	 */
	public String getDamagedParts() {
		return damagedParts;
	}
	/**
	 * 设置：报修内容
	 */
	public void setReportContent(String reportContent) {
		this.reportContent = reportContent;
	}
	/**
	 * 获取：报修内容
	 */
	public String getReportContent() {
		return reportContent;
	}
	/**
	 * 设置：报修状态
	 */
	public void setRepairStatus(Integer repairStatus) {
		this.repairStatus = repairStatus;
	}
	/**
	 * 获取：报修状态
	 */
	public Integer getRepairStatus() {
		return repairStatus;
	}
	/**
	 * 设置：报修图片guid
	 */
	public void setPicGuid(String picGuid) {
		this.picGuid = picGuid;
	}
	/**
	 * 获取：报修图片guid
	 */
	public String getPicGuid() {
		return picGuid;
	}
}
