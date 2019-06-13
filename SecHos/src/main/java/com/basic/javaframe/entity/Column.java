package com.basic.javaframe.entity;

import java.util.List;
import java.util.Map;

/**
 * Column实体
  * @ClassName: Column 
  * @Description: Column实体
  * @author zwh
  * @date 2019年1月10日 下午2:51:41 
  *
 */
public class Column {
	/**列名**/
    private String columnName;
    /**列名类型**/
    private String dataType;
    private String maxLength;
    /**列名备注**/
    private String comments;
    
    /**属性名称(第一个字母大写)，如：user_name => UserName**/
    private String attrName;
    /**属性名称(第一个字母小写)，如：user_name => userName**/
    private String attrname;
    /**属性类型**/
    private String attrType;
    /**auto_increment**/
    private String extra;
    /**是否显示**/
    private String isShow;
    /**是否必填**/
    private String isRequired;
    /**是否是查询条件**/
    private String isSelected;
    /**显示方式**/
    private String showType;
    
    private List<Frame_CodeValue> codeValues;
	public List<Frame_CodeValue> getCodeValues() {
		return codeValues;
	}
	public void setCodeValues(List<Frame_CodeValue> codeValues) {
		this.codeValues = codeValues;
	}
	public String getIsShow() {
		return isShow;
	}
	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}
	public String getIsRequired() {
		return isRequired;
	}
	public void setIsRequired(String isRequired) {
		this.isRequired = isRequired;
	}
	public String getIsSelected() {
		return isSelected;
	}
	public void setIsSelected(String isSelected) {
		this.isSelected = isSelected;
	}
	public String getShowType() {
		return showType;
	}
	public void setShowType(String showType) {
		this.showType = showType;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getAttrname() {
		return attrname;
	}
	public void setAttrname(String attrname) {
		this.attrname = attrname;
	}
	public String getAttrName() {
		return attrName;
	}
	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}
	public String getAttrType() {
		return attrType;
	}
	public void setAttrType(String attrType) {
		this.attrType = attrType;
	}
	public String getExtra() {
		return extra;
	}
	public void setExtra(String extra) {
		this.extra = extra;
	}

	public String getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(String maxLength) {
		this.maxLength = maxLength;
	}
	@Override
	public String toString() {
		return "Column [columnName=" + columnName + ", dataType=" + dataType + ", maxLength=" + maxLength
				+ ", comments=" + comments + ", attrName=" + attrName + ", attrname=" + attrname + ", attrType="
				+ attrType + ", extra=" + extra + ", isShow=" + isShow + ", isRequired=" + isRequired + ", isSelected="
				+ isSelected + ", showType=" + showType + "]";
	}
	
	
}
