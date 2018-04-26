package com.manle.saitamall.user.bean;

import com.manle.saitamall.home.bean.GoodsBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/4/5.
 */

public class Collector implements Serializable {
    private static final long serialVersionUID = -8007451681326959565L;

    private int code;
    private String msg;
    private List<ResultBean> collects;
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

    public List<ResultBean> getCollects() {
        return collects;
    }

    public void setCollects(List<ResultBean> collects) {
        this.collects = collects;
    }

    public static class ResultBean{
       private String product_id;
       private String origin_price;
       private String channel_id;
       private String brand_id;
       private String p_catalog_id;
       private String supplier_type;
       private String supplier_code;
       private String name;
       private String cover_price;
       private String brief;
       private String figure;
       private String sell_time_start;
       private String sell_time_end;

       public String getProduct_id() {
           return product_id;
       }

       public void setProduct_id(String product_id) {
           this.product_id = product_id;
       }

       public String getOrigin_price() {
           return origin_price;
       }

       public void setOrigin_price(String origin_price) {
           this.origin_price = origin_price;
       }

       public String getChannel_id() {
           return channel_id;
       }

       public void setChannel_id(String channel_id) {
           this.channel_id = channel_id;
       }

       public String getBrand_id() {
           return brand_id;
       }

       public void setBrand_id(String brand_id) {
           this.brand_id = brand_id;
       }

       public String getP_catalog_id() {
           return p_catalog_id;
       }

       public void setP_catalog_id(String p_catalog_id) {
           this.p_catalog_id = p_catalog_id;
       }

       public String getSupplier_type() {
           return supplier_type;
       }

       public void setSupplier_type(String supplier_type) {
           this.supplier_type = supplier_type;
       }

       public String getSupplier_code() {
           return supplier_code;
       }

       public void setSupplier_code(String supplier_code) {
           this.supplier_code = supplier_code;
       }

       public String getName() {
           return name;
       }

       public void setName(String name) {
           this.name = name;
       }

       public String getCover_price() {
           return cover_price;
       }

       public void setCover_price(String cover_price) {
           this.cover_price = cover_price;
       }

       public String getBrief() {
           return brief;
       }

       public void setBrief(String brief) {
           this.brief = brief;
       }

       public String getFigure() {
           return figure;
       }

       public void setFigure(String figure) {
           this.figure = figure;
       }

       public String getSell_time_start() {
           return sell_time_start;
       }

       public void setSell_time_start(String sell_time_start) {
           this.sell_time_start = sell_time_start;
       }

       public String getSell_time_end() {
           return sell_time_end;
       }

       public void setSell_time_end(String sell_time_end) {
           this.sell_time_end = sell_time_end;
       }
   }
}
