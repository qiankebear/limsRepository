package com.fh.entity.project;

/**
 * 项目用户关联表
 */
public class ProjectUser {
    /**
     * 主键
     */
    private long id;
    /**
     * 关联项目
     */
    private long projectId;
    /**
     * 关联用户
     */
    private String userId;
    /**
     * 身份(1.项目负责人 2.实验员 3.访客)
     */
    private int memberKind;
    /**
     * 项目修改权限 (1.可修改 2.不可修改)
     */
    private int projectPermission;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getMemberKind() {
        return memberKind;
    }

    public void setMemberKind(int memberKind) {
        this.memberKind = memberKind;
    }

    public int getProjectPermission() {
        return projectPermission;
    }

    public void setProjectPermission(int projectPermission) {
        this.projectPermission = projectPermission;
    }
}
