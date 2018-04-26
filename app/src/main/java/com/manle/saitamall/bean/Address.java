package com.manle.saitamall.bean;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/4/13.
 */

public class Address extends JSONObject implements Serializable{
    private static final long serialVersionUID = -4346710251245007957L;
    private Long id;

    private String consignee;

    private String address;

    private String phone;

    private Long clientUserId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getClientUserId() {
        return clientUserId;
    }

    public void setClientUserId(Long clientUserId) {
        this.clientUserId = clientUserId;
    }

    @Override
    public boolean equals(Object o) {
        Address addressO = (Address)o;
        if (addressO.getPhone().equals(this.getPhone())&&addressO.getAddress()
                .equals(this.getAddress())&&addressO.getConsignee().equals(this.getConsignee())){
            return true;
        }
        return false;
    }
}
