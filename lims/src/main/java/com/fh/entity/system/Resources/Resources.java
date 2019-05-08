package com.fh.entity.system.Resources;

/**
 * @Author: Wangjian
 * @Description:资源路径表
 * @Date: $time$ $date$
 **/
public class Resources {
    private Integer id; // 自增主键
    private String lims_resource; // 资源名称
    private String lims_path; // 资源路径

    public Resources(Integer id, String lims_resource, String lims_path) {
        this.id = id;
        this.lims_resource = lims_resource;
        this.lims_path = lims_path;
    }
    public Resources(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLims_resource() {
        return lims_resource;
    }

    public void setLims_resource(String lims_resource) {
        this.lims_resource = lims_resource;
    }

    public String getLims_path() {
        return lims_path;
    }

    public void setLims_path(String lims_path) {
        this.lims_path = lims_path;
    }
}
