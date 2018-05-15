package com.manle.saitamall.bean;


import org.json.JSONObject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the OrderItem entity.
 */
public class OrderItem  implements Serializable {

    private static final long serialVersionUID = -120092599051089506L;
    private Long id;

    private String productName;

    private BigDecimal productPrice;

    private Integer productQuantity;

    private String productIcon;

    private Long orderMasterId;

    private Long productId;

    public OrderItem(String productName, BigDecimal productPrice, Integer productQuantity, String productIcon, Long productId) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.productIcon = productIcon;
        this.productId = productId;
    }

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

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Integer productQuantity) {
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
