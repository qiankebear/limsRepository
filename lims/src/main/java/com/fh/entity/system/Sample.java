package com.fh.entity.system;

import java.util.Date;

/**
 * @Author: Wangjian
 * @Description:样本表
 * @Date: $time$ $date$
 *
 **/
public class Sample {
    private Integer id;
    private String sample_number;
    private Date sample_generate_time;
    private Integer sample_course;
    private String sample_remark;
    private Integer special_sample;
    private String sample_serial;

    public Sample(){

    }

    public Sample(Integer id, String sample_number, Date sample_generate_time, Integer sample_course, String sample_remark, Integer special_sample, String sample_serial) {
        this.id = id;
        this.sample_number = sample_number;
        this.sample_generate_time = sample_generate_time;
        this.sample_course = sample_course;
        this.sample_remark = sample_remark;
        this.special_sample = special_sample;
        this.sample_serial = sample_serial;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSample_number() {
        return sample_number;
    }

    public void setSample_number(String sample_number) {
        this.sample_number = sample_number;
    }

    public Date getSample_generate_time() {
        return sample_generate_time;
    }

    public void setSample_generate_time(Date sample_generate_time) {
        this.sample_generate_time = sample_generate_time;
    }

    public Integer getSample_course() {
        return sample_course;
    }

    public void setSample_course(Integer sample_course) {
        this.sample_course = sample_course;
    }

    public String getSample_remark() {
        return sample_remark;
    }

    public void setSample_remark(String sample_remark) {
        this.sample_remark = sample_remark;
    }

    public Integer getSpecial_sample() {
        return special_sample;
    }

    public void setSpecial_sample(Integer special_sample) {
        this.special_sample = special_sample;
    }

    public String getSample_serial() {
        return sample_serial;
    }

    public void setSample_serial(String sample_serial) {
        this.sample_serial = sample_serial;
    }
}
