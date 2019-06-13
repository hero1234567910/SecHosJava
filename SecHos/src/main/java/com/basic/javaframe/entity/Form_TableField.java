package com.basic.javaframe.entity;
/**
 * <p>Title: Frame_TableField</p>
 * <p>Description: 表单字段设置实体</p>
 * @author my
 */
public class Form_TableField {
    //行号
    private int rowId;
    //行唯一标识
    private String rowGuid;
    //字段名称
    private String fieldName;
    //字段类型
    private String fieldType;
    //字段长度
    private String fieldLength;
    //字段精度
    private String decimalLength;
    //字段显示名称
    private String fieldDisplayName;
    //排序号
    private int sortSq;
    //是否作为查询条件
    private char isQueryCondition;
    //是否必填
    private char mustFill;
    //字段展现方式
    private char fieldDisplayType;
    //是否显示在新增页面
    private char showInadd;
    //对应表的行标
    private String allowTo;
    //对应代码项行标
    private String codesGuid;

    public String getCodesGuid() {
        return codesGuid;
    }

    public void setCodesGuid(String codesGuid) {
        this.codesGuid = codesGuid;
    }

    public String getAllowTo() {
        return allowTo;
    }

    public void setAllowTo(String allowTo) {
        this.allowTo = allowTo;
    }

    public int getRowId() {
        return rowId;
    }

    public void setRowId(int rowId) {
        this.rowId = rowId;
    }

    public String getRowGuid() {
        return rowGuid;
    }

    public void setRowGuid(String rowGuid) {
        this.rowGuid = rowGuid;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldLength() {
        return fieldLength;
    }

    public void setFieldLength(String fieldLength) {
        this.fieldLength = fieldLength;
    }

    public String getDecimalLength() {
        return decimalLength;
    }

    public void setDecimalLength(String decimalLength) {
        this.decimalLength = decimalLength;
    }

    public String getFieldDisplayName() {
        return fieldDisplayName;
    }

    public void setFieldDisplayName(String fieldDisplayName) {
        this.fieldDisplayName = fieldDisplayName;
    }

    public int getSortSq() {
        return sortSq;
    }

    public void setSortSq(int sortSq) {
        this.sortSq = sortSq;
    }

	public char getIsQueryCondition() {
		return isQueryCondition;
	}

	public void setIsQueryCondition(char isQueryCondition) {
		this.isQueryCondition = isQueryCondition;
	}

	public char getMustFill() {
		return mustFill;
	}

	public void setMustFill(char mustFill) {
		this.mustFill = mustFill;
	}

	public char getFieldDisplayType() {
		return fieldDisplayType;
	}

	public void setFieldDisplayType(char fieldDisplayType) {
		this.fieldDisplayType = fieldDisplayType;
	}

	public char getShowInadd() {
		return showInadd;
	}

	public void setShowInadd(char showInadd) {
		this.showInadd = showInadd;
	}

}
