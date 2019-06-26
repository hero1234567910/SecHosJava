package com.basic.javaframe.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;



 /**
 * <p>Title: Sechos_Consultation</p>
 * <p>Description:</p>
 * @author 
 */
public class Sechos_Consultation implements Serializable {
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
	/**咨询时间**/
	@JsonFormat( pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date consultationTime;
	/**回复时间**/
	@JsonFormat( pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date replyTime;
	/**咨询标题**/
	private String consultationTitle;
	/**咨询内容**/
	private String consultationContent;
	/**咨询人姓名**/
	private String consultationName;
	/**咨询人手机号**/
	private String consultationMobile;
	/**回复内容**/
	private String replyContent;

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
	 * 设置：咨询时间
	 */
	public void setConsultationTime(Date consultationTime) {
		this.consultationTime = consultationTime;
	}
	/**
	 * 获取：咨询时间
	 */
	public Date getConsultationTime() {
		return consultationTime;
	}
	/**
	 * 设置：回复时间
	 */
	public void setReplyTime(Date replyTime) {
		this.replyTime = replyTime;
	}
	/**
	 * 获取：回复时间
	 */
	public Date getReplyTime() {
		return replyTime;
	}
	/**
	 * 设置：咨询标题
	 */
	public void setConsultationTitle(String consultationTitle) {
		this.consultationTitle = consultationTitle;
	}
	/**
	 * 获取：咨询标题
	 */
	public String getConsultationTitle() {
		return consultationTitle;
	}
	/**
	 * 设置：咨询内容
	 */
	public void setConsultationContent(String consultationContent) {
		this.consultationContent = consultationContent;
	}
	/**
	 * 获取：咨询内容
	 */
	public String getConsultationContent() {
		return consultationContent;
	}
	/**
	 * 设置：咨询人姓名
	 */
	public void setConsultationName(String consultationName) {
		this.consultationName = consultationName;
	}
	/**
	 * 获取：咨询人姓名
	 */
	public String getConsultationName() {
		return consultationName;
	}
	/**
	 * 设置：咨询人手机号
	 */
	public void setConsultationMobile(String consultationMobile) {
		this.consultationMobile = consultationMobile;
	}
	/**
	 * 获取：咨询人手机号
	 */
	public String getConsultationMobile() {
		return consultationMobile;
	}
	/**
	 * 设置：回复内容
	 */
	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}
	/**
	 * 获取：回复内容
	 */
	public String getReplyContent() {
		return replyContent;
	}
}
