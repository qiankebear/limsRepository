package com.fh.entity.system;

/**
 * @Author: Wangjian
 * @Description:孔板表
 * @Date: $time$ $date$
 **/
public class PorePlate {
    private Integer id; // 自增主键
    private String pore_plate_name; // 孔板名称，孔板默认编号 项目缩写-年月日-序号
    private Integer plate_project_id; // 所属项目编号
    private String fabric_swatch_people; // 布板者
    private Integer sample_sum; // 样本总数
    private Integer pore_plate_type; // 孔板类型(1.普通板 2.质检板)
    private Integer pore_plate_quality; // 复核质检(1.通过 2.不通过)
    private Integer pore_plate_entirety; // 是否整版重扩(1.是 2.不是)
    private String current_procedure; // 步骤(0.准备 1.布板 2.打孔 3.扩增 4.分析 )

    private HoleType holeType;
    private Project project;
    private Sample sample;

    public PorePlate(){

    }

    public PorePlate(Integer id, String pore_plate_name, Integer plate_project_id, String fabric_swatch_people, Integer sample_sum, Integer pore_plate_type, Integer pore_plate_quality, Integer pore_plate_entirety, String current_procedure, HoleType holeType, Project project, Sample sample) {
        this.id = id;
        this.pore_plate_name = pore_plate_name;
        this.plate_project_id = plate_project_id;
        this.fabric_swatch_people = fabric_swatch_people;
        this.sample_sum = sample_sum;
        this.pore_plate_type = pore_plate_type;
        this.pore_plate_quality = pore_plate_quality;
        this.pore_plate_entirety = pore_plate_entirety;
        this.current_procedure = current_procedure;
        this.holeType = holeType;
        this.project = project;
        this.sample = sample;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPore_plate_name() {
        return pore_plate_name;
    }

    public void setPore_plate_name(String pore_plate_name) {
        this.pore_plate_name = pore_plate_name;
    }

    public Integer getPlate_project_id() {
        return plate_project_id;
    }

    public void setPlate_project_id(Integer plate_project_id) {
        this.plate_project_id = plate_project_id;
    }

    public String getFabric_swatch_people() {
        return fabric_swatch_people;
    }

    public void setFabric_swatch_people(String fabric_swatch_people) {
        this.fabric_swatch_people = fabric_swatch_people;
    }

    public Integer getSample_sum() {
        return sample_sum;
    }

    public void setSample_sum(Integer sample_sum) {
        this.sample_sum = sample_sum;
    }

    public Integer getPore_plate_type() {
        return pore_plate_type;
    }

    public void setPore_plate_type(Integer pore_plate_type) {
        this.pore_plate_type = pore_plate_type;
    }

    public Integer getPore_plate_quality() {
        return pore_plate_quality;
    }

    public void setPore_plate_quality(Integer pore_plate_quality) {
        this.pore_plate_quality = pore_plate_quality;
    }

    public Integer getPore_plate_entirety() {
        return pore_plate_entirety;
    }

    public void setPore_plate_entirety(Integer pore_plate_entirety) {
        this.pore_plate_entirety = pore_plate_entirety;
    }

    public String getCurrent_procedure() {
        return current_procedure;
    }

    public void setCurrent_procedure(String current_procedure) {
        this.current_procedure = current_procedure;
    }

    public HoleType getHoleType() {
        return holeType;
    }

    public void setHoleType(HoleType holeType) {
        this.holeType = holeType;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Sample getSample() {
        return sample;
    }

    public void setSample(Sample sample) {
        this.sample = sample;
    }
}
