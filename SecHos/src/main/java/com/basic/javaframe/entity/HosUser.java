package com.basic.javaframe.entity;

import java.io.Serializable;
import java.util.Date;



 /**
 * <p>Title: HosUser</p>
 * <p>Description:</p>
 * @author 
 */
public class HosUser implements Serializable {
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
	/**订餐用户名称**/
	private String hosUserName;
	/**微信绑定ID**/
	private String openid;
	/**订餐用户手机号**/
	private String hosUserMobile;
	/**用户默认地址标识**/
	private String addressGuid;

	 public String getAddressGuid() {
		 return addressGuid;
	 }

	 public void setAddressGuid(String addressGuid) {
		 this.addressGuid = addressGuid;
	 }

	 /**用户微信头像**/
	private String hosHeadImgUrl;

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
	 * 设置：订餐用户名称
	 */
	public void setHosUserName(String hosUserName) {
		this.hosUserName = hosUserName;
	}
	/**
	 * 获取：订餐用户名称
	 */
	public String getHosUserName() {
		return hosUserName;
	}
	/**
	 * 设置：微信绑定ID
	 */
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	/**
	 * 获取：微信绑定ID
	 */
	public String getOpenid() {
		return openid;
	}
	/**
	 * 设置：订餐用户手机号
	 */
	public void setHosUserMobile(String hosUserMobile) {
		this.hosUserMobile = hosUserMobile;
	}
	/**
	 * 获取：订餐用户手机号
	 */
	public String getHosUserMobile() {
		return hosUserMobile;
	}
	/**
	 * 设置：订餐用户病区
	 */
	 public void setHosHeadImgUrl(String hosHeadImgUrl) {
		 this.hosHeadImgUrl = hosHeadImgUrl;
	 }

	 public String getHosHeadImgUrl() {
		 return hosHeadImgUrl;
	 }
 }
