package com.fh.entity.system;

/**
 * @Author: Wangjian
 * @Description:孔类型表
 * @Date: $time$ $date$
 *
 **/
public class HoleType {
    /**
     * 自增主键
     */
    private Integer id;
    /**
     * 孔类型
     */
    private Integer hole_type;
    /**
     * 孔编号
     */
    private Integer hole_number;
    /**
     * 孔板id关联孔板表
     */
    private Integer hole_poreid;
    /**
     * 样品id关联样品表
     */
    private Integer hole_sampleid;

    public HoleType(){

    }

    public HoleType(Integer id, Integer hole_type, Integer hole_number, Integer hole_poreid, Integer hole_sampleid) {
        this.id = id;
        this.hole_type = hole_type;
        this.hole_number = hole_number;
        this.hole_poreid = hole_poreid;
        this.hole_sampleid = hole_sampleid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHole_type() {
        return hole_type;
    }

    public void setHole_type(Integer hole_type) {
        this.hole_type = hole_type;
    }

    public Integer getHole_number() {
        return hole_number;
    }

    public void setHole_number(Integer hole_number) {
        this.hole_number = hole_number;
    }

    public Integer getHole_poreid() {
        return hole_poreid;
    }

    public void setHole_poreid(Integer hole_poreid) {
        this.hole_poreid = hole_poreid;
    }

    public Integer getHole_sampleid() {
        return hole_sampleid;
    }

    public void setHole_sampleid(Integer hole_sampleid) {
        this.hole_sampleid = hole_sampleid;
    }
}
