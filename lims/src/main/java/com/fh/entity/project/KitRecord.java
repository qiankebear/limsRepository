package com.fh.entity.project;

/**
 * 出入库记录实体
 */
public class KitRecord {
    /**
     * id
     */
    private long id;
    /**
     * 名称(关联试剂盒与其余试剂和耗材表)
     */
    private long kit_repertory_id;
    /**
     * 操作时间
     */
    private String peration_time;
    /**
     * 操作者(关联user表)
     */
    private String operation_name;
    /**
     * 调整类型，0-增加 1-减少
     */
    private int change_type;
    /**
     * 调整数量
     */
    private float change_count;
    /**
     * 当前库存数量
     */
    private float current_count;
    /**
     * 调整后数量
     */
    private float complete_count;
    /**
     * 项目编号(关联项目表)
     */
    private long repertory_project_id;

    public String getDecrease_reason() {
        return decrease_reason;
    }

    public void setDecrease_reason(String decrease_reason) {
        this.decrease_reason = decrease_reason;
    }

    /**
     * 出库原因
     */
    private String decrease_reason;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getKit_repertory_id() {
        return kit_repertory_id;
    }

    public void setKit_repertory_id(long kit_repertory_id) {
        this.kit_repertory_id = kit_repertory_id;
    }

    public String getPeration_time() {
        return peration_time;
    }

    public void setPeration_time(String peration_time) {
        this.peration_time = peration_time;
    }

    public String getOperation_name() {
        return operation_name;
    }

    public void setOperation_name(String operation_name) {
        this.operation_name = operation_name;
    }

    public int getChange_type() {
        return change_type;
    }

    public void setChange_type(int change_type) {
        this.change_type = change_type;
    }

    public float getChange_count() {
        return change_count;
    }

    public void setChange_count(float change_count) {
        this.change_count = change_count;
    }

    public float getCurrent_count() {
        return current_count;
    }

    public void setCurrent_count(float current_count) {
        this.current_count = current_count;
    }

    public float getComplete_count() {
        return complete_count;
    }

    public void setComplete_count(float complete_count) {
        this.complete_count = complete_count;
    }

    public long getRepertory_project_id() {
        return repertory_project_id;
    }

    public void setRepertory_project_id(long repertory_project_id) {
        this.repertory_project_id = repertory_project_id;
    }
}
