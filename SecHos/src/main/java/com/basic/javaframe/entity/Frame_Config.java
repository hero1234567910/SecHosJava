package com.basic.javaframe.entity;

/**
 * <p>Title: Frame_Config</p>
 * <p>Description: 系统参数实体</p>
 * @author wzl
 */
public class Frame_Config {
    //行标识
    private Integer rowId;
    //记录唯一标识
    private String rowGuid;
    //分类唯一标识
    private String categoryGuid;
    //参数名称
    private String configName;
    //参数值
    private String configValue;
    //参数描述
    private String description;
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

    public String getCategoryGuid() {
        return categoryGuid;
    }

    public void setCategoryGuid(String categoryGuid) {
        this.categoryGuid = categoryGuid;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSortSq() {
        return sortSq;
    }

    public void setSortSq(int sortSq) {
        this.sortSq = sortSq;
    }
}
