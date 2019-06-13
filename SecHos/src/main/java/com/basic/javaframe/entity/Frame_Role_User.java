package com.basic.javaframe.entity;

public class Frame_Role_User {
    //行标识
    private Integer rowId;
    //用户唯一标识
    private String userGuid;
    //角色唯一标识
    private String roleGuid;

    public Integer getRowId() {
        return rowId;
    }

    public void setRowId(Integer row_ID) {
        this.rowId = rowId;
    }

    public String getUserGuid() {
        return userGuid;
    }

    public void setUserGuid(String userGuid) {
        this.userGuid = userGuid;
    }

    public String getRoleGuid() {
        return roleGuid;
    }

    public void setRoleGuid(String roleGuid) {
        this.roleGuid = roleGuid;
    }
}
