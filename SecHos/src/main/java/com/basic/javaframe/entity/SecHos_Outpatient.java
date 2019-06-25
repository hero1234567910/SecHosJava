package com.basic.javaframe.entity;

import java.io.Serializable;
import java.util.Date;



 /**
 * <p>Title: SechosOutpatient</p>
 * <p>Description:</p>
 * @author 
 */
public class SecHos_Outpatient implements Serializable {
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
	/**门诊病历号**/
	private String medicalNumberMZ;
	/**卫宁病人id**/
	private String patidMZ;
	/**患者guid**/
	private String patientRowGuidMZ;

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
	public String getMedicalNumberMZ() {
		return medicalNumberMZ;
	}
	public void setMedicalNumberMZ(String medicalNumberMZ) {
		this.medicalNumberMZ = medicalNumberMZ;
	}
	public String getPatidMZ() {
		return patidMZ;
	}
	public void setPatidMZ(String patidMZ) {
		this.patidMZ = patidMZ;
	}
	public String getPatientRowGuidMZ() {
		return patientRowGuidMZ;
	}
	public void setPatientRowGuidMZ(String patientRowGuidMZ) {
		this.patientRowGuidMZ = patientRowGuidMZ;
	}
	
}
