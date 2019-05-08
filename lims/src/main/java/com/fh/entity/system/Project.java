package com.fh.entity.system;

import java.util.Date;

/**
 * @Author: Wangjian
 * @Description:项目表
 * @Date: $time$ $date$
 **/
public class Project {
    private Integer id; // 自增主键
    private String project_number; // 项目编号
    private String project_name; // 项目名称
    private Integer project_number_abbreviation; // 项目名缩写
    private Integer project_status; // 项目状态
    private Integer recheck_hole_amount; // 复核孔个数
    private Date project_starttime; // 项目开始时间

    public Project(Integer id, String project_number, String project_name, Integer project_number_abbreviation, Integer project_status, Integer recheck_hole_amount, Date project_starttime) {
        this.id = id;
        this.project_number = project_number;
        this.project_name = project_name;
        this.project_number_abbreviation = project_number_abbreviation;
        this.project_status = project_status;
        this.recheck_hole_amount = recheck_hole_amount;
        this.project_starttime = project_starttime;
    }
    public  Project(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getProject_number_abbreviation() {
        return project_number_abbreviation;
    }

    public void setProject_number_abbreviation(Integer project_number_abbreviation) {
        this.project_number_abbreviation = project_number_abbreviation;
    }

    public Integer getProject_status() {
        return project_status;
    }

    public void setProject_status(Integer project_status) {
        this.project_status = project_status;
    }

    public Integer getRecheck_hole_amount() {
        return recheck_hole_amount;
    }

    public void setRecheck_hole_amount(Integer recheck_hole_amount) {
        this.recheck_hole_amount = recheck_hole_amount;
    }

    public Date getProject_starttime() {
        return project_starttime;
    }

    public void setProject_starttime(Date project_starttime) {
        this.project_starttime = project_starttime;
    }
}
