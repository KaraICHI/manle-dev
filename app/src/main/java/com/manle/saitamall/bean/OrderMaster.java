package com.manle.saitamall.bean;


import com.manle.saitamall.bean.enumeration.OrderStatus;

import org.json.JSONObject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the OrderMaster entity.
 */
public class OrderMaster  extends JSONObject implements Serializable{

    private static final long serialVersionUID = -4172103825583550040L;
    private Long id;

    private String orderNumber;

    private BigDecimal totalPrices;

    private Integer totalQuanity;

    private OrderStatus orderStatus;

    private Long addressId;

    private Long clientUserId;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public BigDecimal getTotalPrices() {
        return totalPrices;
    }

    public void setTotalPrices(BigDecimal totalPrices) {
        this.totalPrices = totalPrices;
    }

    public Integer getTotalQuanity() {
        return totalQuanity;
    }

    public void setTotalQuanity(Integer totalQuanity) {
        this.totalQuanity = totalQuanity;
    }


    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Long getClientUserId() {
        return clientUserId;
    }

    public void setClientUserId(Long clientUserId) {
        this.clientUserId = clientUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrderMaster orderMaster = (OrderMaster) o;
        if(orderMaster.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), orderMaster.getId());
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
