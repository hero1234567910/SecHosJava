package com.basic.javaframe.entity;
/**
 * <p>Title: Frame_TableInfo</p>
 * <p>Description: 表单实体</p>
 * @author my
 */
public class Form_TableInfo {
    //行标识
    private int rowId;
    //记录唯一标识
    private String rowGuid;
    //数据表表单名称
    private String tableName;
    //数据表物理名称
    private String physicalName;
    //项目名称
    private String ProjectName;
    //序号
    private int sortSq;
    //控制器名称
    private String controllerName;

    private String originName;
    
    public String getOriginName() {
		return originName;
	}

	public void setOriginName(String originName) {
		this.originName = originName;
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

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getPhysicalName() {
        return physicalName;
    }

    public void setPhysicalName(String physicalName) {
        this.physicalName = physicalName;
    }

    public String getProjectName() {
        return ProjectName;
    }

    public void setProjectName(String projectName) {
        ProjectName = projectName;
    }

    public String getControllerName() {
        return controllerName;
    }

    public void setControllerName(String controllerName) {
        this.controllerName = controllerName;
    }

    public int getSortSq() {
        return sortSq;
    }

    public void setSortSq(int sortSq) {
        this.sortSq = sortSq;
    }


}
