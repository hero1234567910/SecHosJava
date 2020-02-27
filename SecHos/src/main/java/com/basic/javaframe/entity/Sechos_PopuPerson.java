package com.basic.javaframe.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * <p>Title: Frame_Module</p>
 * <p>Description: 模块管理实体</p>
 * @author wzl
 */
public class Sechos_PopuPerson {
    //行标识
    private Integer rowId;
    //记录唯一标识
    private String rowGuid;
    //删除标识
    private int delFlag;	
    //创建时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
    //推广人
    private String promoters;
    
    private String promotersGuid;
    
    private String popuPersonGuid;
    //被推广人
    private String popuPerson;

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

	public String getPromoters() {
		return promoters;
	}

	public void setPromoters(String promoters) {
		this.promoters = promoters;
	}

	public String getPromotersGuid() {
		return promotersGuid;
	}

	public void setPromotersGuid(String promotersGuid) {
		this.promotersGuid = promotersGuid;
	}

	public String getPopuPersonGuid() {
		return popuPersonGuid;
	}

	public void setPopuPersonGuid(String popuPersonGuid) {
		this.popuPersonGuid = popuPersonGuid;
	}

	public String getPopuPerson() {
		return popuPerson;
	}

	public void setPopuPerson(String popuPerson) {
		this.popuPerson = popuPerson;
	}
    
    
   

   
}
