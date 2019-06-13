package com.basic.javaframe.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * <p>Title: Frame_Module</p>
 * <p>Description: 模块管理实体</p>
 * @author wzl
 */
public class Frame_Module {
    //行标识
    private Integer rowId;
    //记录唯一标识
    private String rowGuid;
    //删除标识
    private int delFlag;
    //创建时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;
    //模块名称
    private String moduleName;
    //模块编号
    private String moduleCode;
    //上级模块编号
    private String pmoduleCode;
    //子模块数量
    private int hasChild;
    //模块地址
    private String moduleAddr;
    //小图标
    private String smallIcon;
    //大图标
    private String bigIcon;
    //目标框架
    private String target;
    //是否隐藏
    private int isVisible;
    //排序号
    private int sortSq;
    
    private String pmoduleName;
	public String getPmoduleName() {
		return pmoduleName;
	}
	public void setPmoduleName(String pmoduleName) {
		this.pmoduleName = pmoduleName;
	}
	public Integer getRowId() {
		return rowId;
	}
	public void setRowId(Integer rowId) {
		this.rowId = rowId;
	}
	public String getRowGuid() {
		return rowGuid;
	}
	public void setRowGuid(String rowGuid) {
		this.rowGuid = rowGuid;
	}
	public int getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getModuleCode() {
		return moduleCode;
	}
	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}
	
	public String getPmoduleCode() {
		return pmoduleCode;
	}
	public void setPmoduleCode(String pmoduleCode) {
		this.pmoduleCode = pmoduleCode;
	}
	public int getHasChild() {
		return hasChild;
	}
	public void setHasChild(int hasChild) {
		this.hasChild = hasChild;
	}
	public String getModuleAddr() {
		return moduleAddr;
	}
	public void setModuleAddr(String moduleAddr) {
		this.moduleAddr = moduleAddr;
	}
	public String getSmallIcon() {
		return smallIcon;
	}
	public void setSmallIcon(String smallIcon) {
		this.smallIcon = smallIcon;
	}
	public String getBigIcon() {
		return bigIcon;
	}
	public void setBigIcon(String bigIcon) {
		this.bigIcon = bigIcon;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public int getIsVisible() {
		return isVisible;
	}
	public void setIsVisible(int isVisible) {
		this.isVisible = isVisible;
	}
	public int getsortSq() {
		return sortSq;
	}
	public void setsortSq(int sortSq) {
		this.sortSq = sortSq;
	}

   
}
