package com.basic.javaframe.entity;

/**
 * <p>Title: Frame_Codes</p>
 * <p>Description: 代码项值实体</p>
 * @author wzl
 */
public class Frame_CodeValue {
    //行号
    private Integer rowId;

    //行唯一标识
    private String rowGuid;

    //代码项值
    private String itemValue;

    //代码项文本
    private String itemText;

    //排序号
    private Integer sortSq;

    //代码项唯一标识
    private String codeGuid;

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

    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }

    public String getItemText() {
        return itemText;
    }

    public void setItemText(String itemText) {
        this.itemText = itemText;
    }

    public Integer getSortSq() {
        return sortSq;
    }

    public void setSortSq(Integer sortSq) {
        this.sortSq = sortSq;
    }

    public String getCodeGuid() {
        return codeGuid;
    }

    public void setCodeGuid(String codeGuid) {
        this.codeGuid = codeGuid;
    }
}
