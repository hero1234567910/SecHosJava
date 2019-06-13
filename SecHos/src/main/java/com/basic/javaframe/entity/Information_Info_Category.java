package com.basic.javaframe.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>Title: Information_Info_Category</p>
 * <p>Description:</p>
 * @author
 */
public class Information_Info_Category implements Serializable {
    private static final long serialVersionUID = 1L;
    //行标
    private Integer rowId;
    //行唯一标识
    private String rowGuid;
    //创建时间
    private Date createTime;
    //删除标识
    private Integer delFlag;
    //排序
    private Integer sortSq;
    //信息唯一标识
    private String infoGuid;
    //栏目唯一标识
    private String categoryGuid;

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getSortSq() {
        return sortSq;
    }

    public void setSortSq(Integer sortSq) {
        this.sortSq = sortSq;
    }

    public String getInfoGuid() {
        return infoGuid;
    }

    public void setInfoGuid(String infoGuid) {
        this.infoGuid = infoGuid;
    }

    public String getCategoryGuid() {
        return categoryGuid;
    }

    public void setCategoryGuid(String categoryGuid) {
        this.categoryGuid = categoryGuid;
    }
}
