package com.basic.javaframe.entity;

import java.util.Date;

/**
 * <p>Title: Frame_Role</p>
 * <p>Description: 角色管理实体</p>
 * @author wzl
 */
public class Frame_Role {
    //行标识
    private Integer rowId;
    //记录唯一标识
    private String rowGuid;
    //删除标识
    private int delFlag;
    //创建时间
    private Date createTime;
    //角色名称
    private String roleName;
    //角色类型
    private String roleType;
    //首页地址
    private String mainIndex;
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

    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public String getMainIndex() {
        return mainIndex;
    }

    public void setMainIndex(String mainIndex) {
       this.mainIndex = mainIndex;
    }

    public int getSortSq() {
        return sortSq;
    }

    public void setSortSq(int sortSq){ this.sortSq = sortSq;}



}
