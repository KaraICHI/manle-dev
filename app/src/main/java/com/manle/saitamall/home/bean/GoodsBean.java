package com.manle.saitamall.home.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Administrator on 2016/10/9.
 * child商品类
 */
public class GoodsBean implements Serializable {

    private String name;
    private BigDecimal cover_price;
    private String figure;
    private Long product_id;
    private String product_detail;
    private int number = 1;

    /**
     * 是否处于编辑状态
     */
    private boolean isEditing;
    /**
     * 是否被选中
     */
    private boolean isChildSelected;

    public GoodsBean() {
    }

    public GoodsBean(String name, BigDecimal cover_price, String figure, Long product_id, String product_detail) {
        this.name = name;
        this.cover_price = cover_price;
        this.figure = figure;
        this.product_id = product_id;
        this.product_detail = product_detail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCover_price() {
        return cover_price;
    }

    public void setCover_price(BigDecimal cover_price) {
        this.cover_price = cover_price;
    }

    public String getFigure() {
        return figure;
    }

    public void setFigure(String figure) {
        this.figure = figure;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public boolean isEditing() {
        return isEditing;
    }

    public void setIsEditing(boolean isEditing) {
        this.isEditing = isEditing;
    }

    public boolean isChildSelected() {
        return isChildSelected;
    }

    public void setIsChildSelected(boolean isChildSelected) {
        this.isChildSelected = isChildSelected;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }



    public String getProduct_detail() {
        return product_detail;
    }

    public void setProduct_detail(String product_detail) {
        this.product_detail = product_detail;
    }
}
