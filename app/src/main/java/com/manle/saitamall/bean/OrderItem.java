package com.manle.saitamall.bean;


import org.json.JSONObject;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the OrderItem entity.
 */
public class OrderItem  extends JSONObject implements Serializable {

    private static final long serialVersionUID = -120092599051089506L;
    private Long id;

    private String productName;

    private Long productPrice;

    private Long productQuantity;

    private String productIcon;

    private Long orderMasterId;

    private Long productId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Long productPrice) {
        this.productPrice = productPrice;
    }

    public Long getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Long productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductIcon() {
        return productIcon;
    }

    public void setProductIcon(String productIcon) {
        this.productIcon = productIcon;
    }

    public Long getOrderMasterId() {
        return orderMasterId;
    }

    public void setOrderMasterId(Long orderMasterId) {
        this.orderMasterId = orderMasterId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrderItem orderItem = (OrderItem) o;
        if(orderItem.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), orderItem.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrderItem{" +
            "id=" + getId() +
            ", productName='" + getProductName() + "'" +
            ", productPrice=" + getProductPrice() +
            ", productQuantity=" + getProductQuantity() +
            ", productIcon='" + getProductIcon() + "'" +
            "}";
    }
}
