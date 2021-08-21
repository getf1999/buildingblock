package com.wglvzyx.mybatisredis.entity;

import java.util.Date;

public class SysRole {
    private Long id;

    private String roleName;

    private String roleCode;

    private String roleDesc;

    private Date addTime;

    private Date editTime;

    private String addBy;

    private String editBy;

    public SysRole(Long id, String roleName, String roleCode, String roleDesc, Date addTime, Date editTime, String addBy, String editBy) {
        this.id = id;
        this.roleName = roleName;
        this.roleCode = roleCode;
        this.roleDesc = roleDesc;
        this.addTime = addTime;
        this.editTime = editTime;
        this.addBy = addBy;
        this.editBy = editBy;
    }

    public SysRole() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode == null ? null : roleCode.trim();
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc == null ? null : roleDesc.trim();
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public String getAddBy() {
        return addBy;
    }

    public void setAddBy(String addBy) {
        this.addBy = addBy == null ? null : addBy.trim();
    }

    public String getEditBy() {
        return editBy;
    }

    public void setEditBy(String editBy) {
        this.editBy = editBy == null ? null : editBy.trim();
    }
}