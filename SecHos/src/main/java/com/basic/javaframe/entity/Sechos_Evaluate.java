package com.basic.javaframe.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;



 /**
 * <p>Title: Sechos_Evaluate</p>
 * <p>Description:</p>
 * @author 
 */
public class Sechos_Evaluate implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/****/
	private Integer rowId;
	/****/
	private String rowGuid;
	/****/
	@JsonFormat( pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date createTime;
	/****/
	private Integer delFlag;
	/****/
	private Integer sortSq;
	/**看护评分**/
	private Integer evaluateNurse;
	/**医生评分**/
	private Integer evaluateDoc;
	/**设施评分**/
	private Integer evaluateFacilities;
	/**病人意见**/
	private String evaluateOpinion;
	/**病人guid**/
	private String patientRowGuid;
	
	private String patid;

	public String getPatid() {
		return patid;
	}
	public void setPatid(String patid) {
		this.patid = patid;
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
	 * 设置：看护评分
	 */
	public void setEvaluateNurse(Integer evaluateNurse) {
		this.evaluateNurse = evaluateNurse;
	}
	/**
	 * 获取：看护评分
	 */
	public Integer getEvaluateNurse() {
		return evaluateNurse;
	}
	/**
	 * 设置：医生评分
	 */
	public void setEvaluateDoc(Integer evaluateDoc) {
		this.evaluateDoc = evaluateDoc;
	}
	/**
	 * 获取：医生评分
	 */
	public Integer getEvaluateDoc() {
		return evaluateDoc;
	}
	/**
	 * 设置：设施评分
	 */
	public void setEvaluateFacilities(Integer evaluateFacilities) {
		this.evaluateFacilities = evaluateFacilities;
	}
	/**
	 * 获取：设施评分
	 */
	public Integer getEvaluateFacilities() {
		return evaluateFacilities;
	}
	/**
	 * 设置：病人意见
	 */
	public void setEvaluateOpinion(String evaluateOpinion) {
		this.evaluateOpinion = evaluateOpinion;
	}
	/**
	 * 获取：病人意见
	 */
	public String getEvaluateOpinion() {
		return evaluateOpinion;
	}
	/**
	 * 设置：病人guid
	 */
	public void setPatientRowGuid(String patientRowGuid) {
		this.patientRowGuid = patientRowGuid;
	}
	/**
	 * 获取：病人guid
	 */
	public String getPatientRowGuid() {
		return patientRowGuid;
	}
}
