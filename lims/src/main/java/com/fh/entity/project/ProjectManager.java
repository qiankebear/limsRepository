package com.fh.entity.project;

/**
 * 项目实体类
 */
public class ProjectManager {
    /**
     * 主键
     */
    private long id;
    /**
     * 项目编号
     */
    private String project_number;
    /**
     * 项目名称
     */
    private String project_name;
    /**
     * 项目名称缩写
     */
    private String project_number_abbreviation;
    /**
     * 项目状态
     */
    private int project_status;
    /**
     * 复核孔个数
     */
    private int recheck_hole_amount;
    /**
     * 项目开始时间
     */
    private String project_starttime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProject_number() {
        return project_number;
    }

    public void setProject_number(String project_number) {
        this.project_number = project_number;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getProject_number_abbreviation() {
        return project_number_abbreviation;
    }

    public void setProject_number_abbreviation(String project_number_abbreviation) {
        this.project_number_abbreviation = project_number_abbreviation;
    }

    public int getProject_status() {
        return project_status;
    }

    public void setProject_status(int project_status) {
        this.project_status = project_status;
    }

    public int getRecheck_hole_amount() {
        return recheck_hole_amount;
    }

    public void setRecheck_hole_amount(int recheck_hole_amount) {
        this.recheck_hole_amount = recheck_hole_amount;
    }

    public String getProject_starttime() {
        return project_starttime;
    }

    public void setProject_starttime(String project_starttime) {
        this.project_starttime = project_starttime;
    }
}
