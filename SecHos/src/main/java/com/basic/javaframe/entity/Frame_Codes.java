package com.basic.javaframe.entity;

/**
 * <p>Title: Frame_Codes</p>
 * <p>Description: 代码项实体</p>
 * @author wzl
 */
public class Frame_Codes {
    //行号
    private Integer rowId;

    //行唯一标识
    private String rowGuid;

    //代码项目名
    private String codeName;

    //代码项级别
    private String codeMask;

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

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getCodeMask() {
        return codeMask;
    }

    public void setCodeMask(String codeMask) {
        this.codeMask = codeMask;
    }
}
