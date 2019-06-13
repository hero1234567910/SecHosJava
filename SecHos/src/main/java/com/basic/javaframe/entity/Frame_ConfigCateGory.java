package com.basic.javaframe.entity;

import io.swagger.models.auth.In;

/**
 * <p>Title: Frame_ConfigGory</p>
 * <p>Description: 系统参数类别实体</p>
 * @author wzl
 */
public class Frame_ConfigCateGory {
    //行标识
    private Integer rowId;
    //记录唯一标识
    private String rowGuid;
    //类别名称
    private String categoryName;
    //排序号
    private int sortSq;

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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getSortSq() {
        return sortSq;
    }

    public void setSortSq(int sortSq) {
        this.sortSq = sortSq;
    }
}
