package com.basic.javaframe.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;



 /**
 * <p>Title: Sechos_Repairsatisfaction</p>
 * <p>Description:</p>
 * @author 
 */
public class Sechos_Repairsatisfaction implements Serializable {
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
	/**维修人Guid**/
	private String repairGuid;
	/**维修人姓名**/
	private String repairName;
	/**报修速度**/
	private Integer repairSpeed;
	/**维修人员态度**/
	private Integer repairAttitude;
	/**维修人员技能水平**/
	private Integer skillLevels;
	/**维修意见**/
	private String repairAdvice;
	/**评价状态**/
	private Integer evaluationStatus;
	/**报修设备名称**/
	private String deviceName;
	/**报修记录Guid**/
	private String recordGuid;
	/**维修人姓名**/
	private String maintainName;
	/**维修人Guid**/
	private String maintainGuid;

	 public String getRepairName() {
		 return repairName;
	 }

	 public void setRepairName(String repairName) {
		 this.repairName = repairName;
	 }

	 public String getMaintainName() {
		 return maintainName;
	 }

	 public void setMaintainName(String maintainName) {
		 this.maintainName = maintainName;
	 }

	 public String getMaintainGuid() {
		 return maintainGuid;
	 }

	 public void setMaintainGuid(String maintainGuid) {
		 this.maintainGuid = maintainGuid;
	 }

	 public Sechos_Repairsatisfaction() {
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
	 * 设置：报修人Guid
	 */
	public void setRepairGuid(String repairGuid) {
		this.repairGuid = repairGuid;
	}
	/**
	 * 获取：报修人Guid
	 */
	public String getRepairGuid() {
		return repairGuid;
	}
	/**
	 * 设置：报修速度
	 */
	public void setRepairSpeed(Integer repairSpeed) {
		this.repairSpeed = repairSpeed;
	}
	/**
	 * 获取：报修速度
	 */
	public Integer getRepairSpeed() {
		return repairSpeed;
	}
	/**
	 * 设置：维修人员态度
	 */
	public void setRepairAttitude(Integer repairAttitude) {
		this.repairAttitude = repairAttitude;
	}
	/**
	 * 获取：维修人员态度
	 */
	public Integer getRepairAttitude() {
		return repairAttitude;
	}
	/**
	 * 设置：维修人员技能水平
	 */
	public void setSkillLevels(Integer skillLevels) {
		this.skillLevels = skillLevels;
	}
	/**
	 * 获取：维修人员技能水平
	 */
	public Integer getSkillLevels() {
		return skillLevels;
	}
	/**
	 * 设置：维修意见
	 */
	public void setRepairAdvice(String repairAdvice) {
		this.repairAdvice = repairAdvice;
	}
	/**
	 * 获取：维修意见
	 */
	public String getRepairAdvice() {
		return repairAdvice;
	}
	/**
	 * 设置：评价状态
	 */
	public void setEvaluationStatus(Integer evaluationStatus) {
		this.evaluationStatus = evaluationStatus;
	}
	/**
	 * 获取：评价状态
	 */
	public Integer getEvaluationStatus() {
		return evaluationStatus;
	}
	/**
	 * 设置：报修设备名称
	 */
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	/**
	 * 获取：报修设备名称
	 */
	public String getDeviceName() {
		return deviceName;
	}
	/**
	 * 设置：报修记录Guid
	 */
	public void setRecordGuid(String recordGuid) {
		this.recordGuid = recordGuid;
	}
	/**
	 * 获取：报修记录Guid
	 */
	public String getRecordGuid() {
		return recordGuid;
	}
}
