package com.basic.javaframe.entity;

/**
 * <p>Title: Frame_ModuleRight</p>
 * <p>Description: 模块权限管理实体</p>
 * @author wzl
 */
public class Frame_ModuleRight {
    //行标识
    private Integer rowId;
    //模块唯一标识
    private String moduleGuid;
    //授权对象
    private String allowTo;
    //授权对象类型
    private String allowType;
	
	public Integer getRowId() {
		return rowId;
	}
	public void setRowId(Integer rowId) {
		this.rowId = rowId;
	}
	public String getModuleGuid() {
		return moduleGuid;
	}
	public void setModuleGuid(String moduleGuid) {
		this.moduleGuid = moduleGuid;
	}
	public String getAllowTo() {
		return allowTo;
	}
	public void setAllowTo(String allowTo) {
		this.allowTo = allowTo;
	}
	public String getAllowType() {
		return allowType;
	}
	public void setAllowType(String allowType) {
		this.allowType = allowType;
	}

    
}
