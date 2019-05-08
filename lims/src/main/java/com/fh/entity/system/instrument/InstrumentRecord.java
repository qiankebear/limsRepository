package com.fh.entity.system.instrument;

import com.fh.entity.system.User;

import java.util.Date;

/**
 * @Author: Wangjian
 * @Description:仪器使用记录表
 * @Date: $time$ $date$
 **/
public class InstrumentRecord {
    private Integer ID; // 主键id
    private Integer INSTRUMENT_ID; // 仪器id
    private String INSTRUMENT_USER; // 仪器使用者
    private String INSTRUMENT_EXCEPTION_INFO; // 仪器异常信息
    private String INSTRUMENT_SOLUTION; // 解决方法
    private Date INSTRUMENT_TIME; // 仪器使用时间
    private Instrument instrument;
    private User user;

    public InstrumentRecord() {

    }

    public InstrumentRecord(Integer ID, Integer INSTRUMENT_ID, String INSTRUMENT_USER, String INSTRUMENT_EXCEPTION_INFO, String INSTRUMENT_SOLUTION, Date INSTRUMENT_TIME, Instrument instrument, User user) {
        this.ID = ID;
        this.INSTRUMENT_ID = INSTRUMENT_ID;
        this.INSTRUMENT_USER = INSTRUMENT_USER;
        this.INSTRUMENT_EXCEPTION_INFO = INSTRUMENT_EXCEPTION_INFO;
        this.INSTRUMENT_SOLUTION = INSTRUMENT_SOLUTION;
        this.INSTRUMENT_TIME = INSTRUMENT_TIME;
        this.instrument = instrument;
        this.user = user;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getINSTRUMENT_ID() {
        return INSTRUMENT_ID;
    }

    public void setINSTRUMENT_ID(Integer INSTRUMENT_ID) {
        this.INSTRUMENT_ID = INSTRUMENT_ID;
    }

    public String getINSTRUMENT_USER() {
        return INSTRUMENT_USER;
    }

    public void setINSTRUMENT_USER(String INSTRUMENT_USER) {
        this.INSTRUMENT_USER = INSTRUMENT_USER;
    }

    public String getINSTRUMENT_EXCEPTION_INFO() {
        return INSTRUMENT_EXCEPTION_INFO;
    }

    public void setINSTRUMENT_EXCEPTION_INFO(String INSTRUMENT_EXCEPTION_INFO) {
        this.INSTRUMENT_EXCEPTION_INFO = INSTRUMENT_EXCEPTION_INFO;
    }

    public String getINSTRUMENT_SOLUTION() {
        return INSTRUMENT_SOLUTION;
    }

    public void setINSTRUMENT_SOLUTION(String INSTRUMENT_SOLUTION) {
        this.INSTRUMENT_SOLUTION = INSTRUMENT_SOLUTION;
    }

    public Date getINSTRUMENT_TIME() {
        return INSTRUMENT_TIME;
    }

    public void setINSTRUMENT_TIME(Date INSTRUMENT_TIME) {
        this.INSTRUMENT_TIME = INSTRUMENT_TIME;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}