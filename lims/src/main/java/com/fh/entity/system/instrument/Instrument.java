package com.fh.entity.system.instrument;

/**
 * @Author: Wangjian
 * @Description:仪器管理
 * @Date: $time$ $date$
 **/
public class Instrument {
    /**
     * 自增主键
     */
    private Integer ID;
    /**
     * 仪器编号
     */
    private String INSTRUMENT_NUMBER;
    /**
     * 仪器型号
     */
    private String INSTRUMENT_TYPE;
    /**
     *  仪器状态
     */
    private Integer INSTRUMENT_STATUS;
    /**
     *  仪器类别
     */
    private Integer INSTRUMENT_CLASSIFY;
    /**
     * 其它信息
     */
    private String INSTRUMENT_OTHER;
    /**
     * 保存的程序
     */
    private String SAVE_PROCEDURE;

    public Instrument(){

    }

    public Instrument(Integer ID, String INSTRUMENT_NUMBER, String INSTRUMENT_TYPE, Integer INSTRUMENT_STATUS, Integer INSTRUMENT_CLASSIFY, String INSTRUMENT_OTHER, String SAVE_PROCEDURE) {
        this.ID = ID;
        this.INSTRUMENT_NUMBER = INSTRUMENT_NUMBER;
        this.INSTRUMENT_TYPE = INSTRUMENT_TYPE;
        this.INSTRUMENT_STATUS = INSTRUMENT_STATUS;
        this.INSTRUMENT_CLASSIFY = INSTRUMENT_CLASSIFY;
        this.INSTRUMENT_OTHER = INSTRUMENT_OTHER;
        this.SAVE_PROCEDURE = SAVE_PROCEDURE;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getINSTRUMENT_NUMBER() {
        return INSTRUMENT_NUMBER;
    }

    public void setINSTRUMENT_NUMBER(String INSTRUMENT_NUMBER) {
        this.INSTRUMENT_NUMBER = INSTRUMENT_NUMBER;
    }

    public String getINSTRUMENT_TYPE() {
        return INSTRUMENT_TYPE;
    }

    public void setINSTRUMENT_TYPE(String INSTRUMENT_TYPE) {
        this.INSTRUMENT_TYPE = INSTRUMENT_TYPE;
    }

    public Integer getINSTRUMENT_STATUS() {
        return INSTRUMENT_STATUS;
    }

    public void setINSTRUMENT_STATUS(Integer INSTRUMENT_STATUS) {
        this.INSTRUMENT_STATUS = INSTRUMENT_STATUS;
    }

    public Integer getINSTRUMENT_CLASSIFY() {
        return INSTRUMENT_CLASSIFY;
    }

    public void setINSTRUMENT_CLASSIFY(Integer INSTRUMENT_CLASSIFY) {
        this.INSTRUMENT_CLASSIFY = INSTRUMENT_CLASSIFY;
    }

    public String getINSTRUMENT_OTHER() {
        return INSTRUMENT_OTHER;
    }

    public void setINSTRUMENT_OTHER(String INSTRUMENT_OTHER) {
        this.INSTRUMENT_OTHER = INSTRUMENT_OTHER;
    }

    public String getSAVE_PROCEDURE() {
        return SAVE_PROCEDURE;
    }

    public void setSAVE_PROCEDURE(String SAVE_PROCEDURE) {
        this.SAVE_PROCEDURE = SAVE_PROCEDURE;
    }
}
