package com.manle.saitamall.bean;


import org.joda.time.LocalDate;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the OrderComment entity.
 */
public class OrderComment  extends JSONObject implements Serializable {

    private static final long serialVersionUID = -2788167314225745149L;
    private Long id;

    private Float level;

    private String content;

    private LocalDate creatDate;

    private Long orderItemId;

    private Long clientUserId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getLevel() {
        return level;
    }

    public void setLevel(Float level) {
        this.level = level;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getCreatDate() {
        return creatDate;
    }

    public void setCreatDate(LocalDate creatDate) {
        this.creatDate = creatDate;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
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

        OrderComment orderComment = (OrderComment) o;
        if(orderComment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), orderComment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrderComment{" +
            "id=" + getId() +
            ", level=" + getLevel() +
            ", content='" + getContent() + "'" +
            ", creatDate='" + getCreatDate() + "'" +
            "}";
    }
}
