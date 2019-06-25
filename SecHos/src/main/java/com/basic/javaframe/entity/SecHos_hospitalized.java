package com.basic.javaframe.entity;

import java.io.Serializable;
import java.util.Date;



 /**
 * <p>Title: SechosHospitalized</p>
 * <p>Description:</p>
 * @author 
 */
public class SecHos_hospitalized implements Serializable {
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
	/**住院病历号**/
	private String medicalNumber;
	/**住院患者id**/
	private String patid;
	/**患者guid**/
	private String patientRowGuid;
	/**住院状态**/
	private Integer hospitalizedStatus;

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
	 * 设置：住院病历号
	 */
	public void setMedicalNumber(String medicalNumber) {
		this.medicalNumber = medicalNumber;
	}
	/**
	 * 获取：住院病历号
	 */
	public String getMedicalNumber() {
		return medicalNumber;
	}
	/**
	 * 设置：住院患者id
	 */
	public void setPatid(String patid) {
		this.patid = patid;
	}
	/**
	 * 获取：住院患者id
	 */
	public String getPatid() {
		return patid;
	}
	/**
	 * 设置：患者guid
	 */
	public void setPatientRowGuid(String patientRowGuid) {
		this.patientRowGuid = patientRowGuid;
	}
	/**
	 * 获取：患者guid
	 */
	public String getPatientRowGuid() {
		return patientRowGuid;
	}
	/**
	 * 设置：住院状态
	 */
	public void setHospitalizedStatus(Integer hospitalizedStatus) {
		this.hospitalizedStatus = hospitalizedStatus;
	}
	/**
	 * 获取：住院状态
	 */
	public Integer getHospitalizedStatus() {
		return hospitalizedStatus;
	}
}
