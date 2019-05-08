package com.fh.entity.system;

/**
 * @Description:$
 * @Param:$
 * @return:$
 * @Author:Mr.Wang
 * @Date:$
 */

public class Customer {
    private Integer id;
    private String client_number;
    private String client_name;
    private String client_linkman;
    private String client_phone;
    private String client_emil;
    private String client_address;
    private String client_jointime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClient_number() {
        return client_number;
    }

    public void setClient_number(String client_number) {
        this.client_number = client_number;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getClient_linkman() {
        return client_linkman;
    }

    public void setClient_linkman(String client_linkman) {
        this.client_linkman = client_linkman;
    }

    public String getClient_phone() {
        return client_phone;
    }

    public void setClient_phone(String client_phone) {
        this.client_phone = client_phone;
    }

    public String getClient_emil() {
        return client_emil;
    }

    public void setClient_emil(String client_emil) {
        this.client_emil = client_emil;
    }

    public String getClient_address() {
        return client_address;
    }

    public void setClient_address(String client_address) {
        this.client_address = client_address;
    }

    public String getClient_jointime() {
        return client_jointime;
    }

    public void setClient_jointime(String client_jointime) {
        this.client_jointime = client_jointime;
    }
}
