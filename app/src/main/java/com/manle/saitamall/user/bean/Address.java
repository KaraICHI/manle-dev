package com.manle.saitamall.user.bean;


import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/4/5.
 */

public class Address implements Serializable {
    private static final long serialVersionUID = 4145227146633259265L;

    private int code;
    private String msg;
    private List<Address.ResultBean> result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Address.ResultBean> getResult() {
        return result;
    }

    public void setResult(List<Address.ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        private String consignee;
        private String address;
        private String phone;

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



    }
}
