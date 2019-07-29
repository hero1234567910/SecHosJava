package com.basic.javaframe.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;



 /**
 * <p>Title: SecHos_Patient</p>
 * <p>Description:</p>
 * @author 
 */
public class SecHos_Patient implements Serializable {
	@Override
	public String toString() {
		return "SecHos_Patient [rowId=" + rowId + ", rowGuid=" + rowGuid + ", createTime=" + createTime + ", delFlag="
				+ delFlag + ", sortSq=" + sortSq + ", patientBirth=" + patientBirth + ", patientName=" + patientName
				+ ", patientSex=" + patientSex + ", patientIdcard=" + patientIdcard + ", patientAddress="
				+ patientAddress + ", patientMobile=" + patientMobile + ", openid=" + openid + ", patientStatus="
				+ patientStatus + ", accessToken=" + accessToken + ", refreshToken=" + refreshToken + ", headImgUrl="
				+ headImgUrl + ", hospitalizedList=" + hospitalizedList + ", outpatients=" + outpatients + "]";
	}
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
	/**患者出生日期**/
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	private Date patientBirth;
	/**患者姓名**/
	private String patientName;
	/**患者性别**/
	private Integer patientSex;
	/**患者证件号**/
	private String patientIdcard;
	/**患者联系地址**/
	private String patientAddress;
	/**患者联系电话**/
	private String patientMobile;
	/**微信唯一标识**/
	private String openid;
	/**就诊类别*/
	private Integer patientStatus;
	
	private String accessToken;
	
	private String refreshToken;
	
	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public Integer getPatientStatus() {
		return patientStatus;
	}

	public void setPatientStatus(Integer patientStatus) {
		this.patientStatus = patientStatus;
	}
	/**微信头像地址**/
	private String headImgUrl;
	
	private List<SecHos_hospitalized> hospitalizedList;
	
	private List<SecHos_Outpatient> outpatients;

	 public List<SecHos_Outpatient> getOutpatients() {
		return outpatients;
	}

	public void setOutpatients(List<SecHos_Outpatient> outpatients) {
		this.outpatients = outpatients;
	}

	public List<SecHos_hospitalized> getHospitalizedList() {
		return hospitalizedList;
	}

	public void setHospitalizedList(List<SecHos_hospitalized> hospitalizedList) {
		this.hospitalizedList = hospitalizedList;
	}

	public String getHeadImgUrl() {
		 return headImgUrl;
	 }

	 public void setHeadImgUrl(String headImgUrl) {
		 this.headImgUrl = headImgUrl;
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
	 * 设置：患者出生日期
	 */
	public void setPatientBirth(Date patientBirth) {
		this.patientBirth = patientBirth;
	}
	/**
	 * 获取：患者出生日期
	 */
	public Date getPatientBirth() {
		return patientBirth;
	}
	/**
	 * 设置：患者姓名
	 */
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	/**
	 * 获取：患者姓名
	 */
	public String getPatientName() {
		return patientName;
	}
	/**
	 * 设置：患者性别
	 */
	public void setPatientSex(Integer patientSex) {
		this.patientSex = patientSex;
	}
	/**
	 * 获取：患者性别
	 */
	public Integer getPatientSex() {
		return patientSex;
	}
	/**
	 * 设置：患者证件号
	 */
	public void setPatientIdcard(String patientIdcard) {
		this.patientIdcard = patientIdcard;
	}
	/**
	 * 获取：患者证件号
	 */
	public String getPatientIdcard() {
		return patientIdcard;
	}
	/**
	 * 设置：患者联系地址
	 */
	public void setPatientAddress(String patientAddress) {
		this.patientAddress = patientAddress;
	}
	/**
	 * 获取：患者联系地址
	 */
	public String getPatientAddress() {
		return patientAddress;
	}
	/**
	 * 设置：患者联系电话
	 */
	public void setPatientMobile(String patientMobile) {
		this.patientMobile = patientMobile;
	}
	/**
	 * 获取：患者联系电话
	 */
	public String getPatientMobile() {
		return patientMobile;
	}
	/**
	 * 设置：微信唯一标识
	 */
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	/**
	 * 获取：微信唯一标识
	 */
	public String getOpenid() {
		return openid;
	}
//	/**
//	 * 设置：卫宁患者id
//	 */
//	public void setPatid(String patid) {
//		this.patid = patid;
//	}
//	/**
//	 * 获取：卫宁患者id
//	 */
//	public String getPatid() {
//		return patid;
//	}
}
