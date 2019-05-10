package com.fh.entity.system;

import java.util.Date;

/**
 * @Author: Wangjian
 * @Description:统计信息
 * @Date: $time$ $date$
 **/
public class Statistical {
    /**
     *  自增主键
     */
    private Integer id;
    /**
     * 孔坐标
     */
    private String hole_number;
    /**
     *  孔板id关联孔板表
     */
    private Integer hole_poreid;
    /**
     * 旧孔类型
     */
    private Integer oldhole_type;
    /**
     * 新孔类型
     */
    private Integer newhole_type;
    /**
     *  旧样品id关联样品表
     */
    private Integer old_sampleid;
    /**
     * 新样品id关联样品表
     */
    private Integer new_sampleid;
    /**
     * 样本类型
     */
    private Integer oldspecial_sample;
    /**
     * 样本类型
     */
    private Integer newspecial_sample;
    /**
     * 修改步骤
     */
    private Integer update_type;
    /**
     *  修改者
     */
    private String update_people;
    /**
     * 修改时间
     */
    private Date update_time;

    public Statistical(){

    }

    public Statistical(Integer id, String hole_number, Integer hole_poreid, Integer oldhole_type, Integer newhole_type, Integer old_sampleid, Integer new_sampleid, Integer oldspecial_sample, Integer newspecial_sample, Integer update_type, String update_people, Date update_time) {
        this.id = id;
        this.hole_number = hole_number;
        this.hole_poreid = hole_poreid;
        this.oldhole_type = oldhole_type;
        this.newhole_type = newhole_type;
        this.old_sampleid = old_sampleid;
        this.new_sampleid = new_sampleid;
        this.oldspecial_sample = oldspecial_sample;
        this.newspecial_sample = newspecial_sample;
        this.update_type = update_type;
        this.update_people = update_people;
        this.update_time = update_time;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHole_number() {
        return hole_number;
    }

    public void setHole_number(String hole_number) {
        this.hole_number = hole_number;
    }

    public Integer getHole_poreid() {
        return hole_poreid;
    }

    public void setHole_poreid(Integer hole_poreid) {
        this.hole_poreid = hole_poreid;
    }

    public Integer getOldhole_type() {
        return oldhole_type;
    }

    public void setOldhole_type(Integer oldhole_type) {
        this.oldhole_type = oldhole_type;
    }

    public Integer getNewhole_type() {
        return newhole_type;
    }

    public void setNewhole_type(Integer newhole_type) {
        this.newhole_type = newhole_type;
    }

    public Integer getOld_sampleid() {
        return old_sampleid;
    }

    public void setOld_sampleid(Integer old_sampleid) {
        this.old_sampleid = old_sampleid;
    }

    public Integer getNew_sampleid() {
        return new_sampleid;
    }

    public void setNew_sampleid(Integer new_sampleid) {
        this.new_sampleid = new_sampleid;
    }

    public Integer getOldspecial_sample() {
        return oldspecial_sample;
    }

    public void setOldspecial_sample(Integer oldspecial_sample) {
        this.oldspecial_sample = oldspecial_sample;
    }

    public Integer getNewspecial_sample() {
        return newspecial_sample;
    }

    public void setNewspecial_sample(Integer newspecial_sample) {
        this.newspecial_sample = newspecial_sample;
    }

    public Integer getUpdate_type() {
        return update_type;
    }

    public void setUpdate_type(Integer update_type) {
        this.update_type = update_type;
    }

    public String getUpdate_people() {
        return update_people;
    }

    public void setUpdate_people(String update_people) {
        this.update_people = update_people;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }
}
