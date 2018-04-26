package com.manle.saitamall.order.bean;

import com.manle.saitamall.user.bean.Address;

import java.util.List;

/**
 * Created by Administrator on 2018/4/6.
 */

public class OrderMasterBean {

    private int code;
    private String msg;

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

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    private List<OrderMasterBean.ResultBean> result;

    public class ResultBean {
        private String user_id;
        private String total_price;
        private String total_quanity;
        private String order_number;
        private Address address;
        private int orderStatus;

        public Address getAddress() {
            return address;
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public int getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(int orderStatus) {
            this.orderStatus = orderStatus;
        }

        public int getPayStatus() {
            return payStatus;
        }

        public void setPayStatus(int payStatus) {
            this.payStatus = payStatus;
        }

        private int payStatus;
        private String creat_date;

        public List<OrderMasterBean.ResultBean.OrderBean> orderBeans;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getTotal_price() {
            return total_price;
        }

        public void setTotal_price(String total_price) {
            this.total_price = total_price;
        }

        public String getTotal_quanity() {
            return total_quanity;
        }

        public void setTotal_quanity(String total_quanity) {
            this.total_quanity = total_quanity;
        }

        public String getOrder_number() {
            return order_number;
        }

        public void setOrder_number(String order_number) {
            this.order_number = order_number;
        }

        public String getCreat_date() {
            return creat_date;
        }

        public void setCreat_date(String creat_date) {
            this.creat_date = creat_date;
        }

        public List<OrderBean> getOrderBeans() {
            return orderBeans;
        }

        public void setOrderBeans(List<OrderBean> orderBeans) {
            this.orderBeans = orderBeans;
        }

        public class OrderBean {
            private String product_id;
            private String figure;
            private String product_name;
            private String product_price;
            private String product_quanity;



            public String getProduct_id() {
                return product_id;
            }

            public void setProduct_id(String product_id) {
                this.product_id = product_id;
            }

            public String getProduct_name() {
                return product_name;
            }

            public void setProduct_name(String product_name) {
                this.product_name = product_name;
            }

            public String getProduct_price() {
                return product_price;
            }

            public void setProduct_price(String product_price) {
                this.product_price = product_price;
            }

            public String getProduct_quanity() {
                return product_quanity;
            }

            public void setProduct_quanity(String product_quanity) {
                this.product_quanity = product_quanity;
            }

            public String getFigure() {
                return figure;
            }

            public void setFigure(String figure) {
                this.figure = figure;
            }
        }
    }
}
