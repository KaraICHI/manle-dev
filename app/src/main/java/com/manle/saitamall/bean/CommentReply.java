package com.manle.saitamall.bean;


import org.joda.time.LocalDate;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the CommentReply entity.
 */
public class CommentReply extends JSONObject implements Serializable {

    private static final long serialVersionUID = -4159073556024819196L;
    private Long id;

    private String content;

    private LocalDate creatDate;

    private Long orderCommentId;

    private Long clientUserId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getOrderCommentId() {
        return orderCommentId;
    }

    public void setOrderCommentId(Long orderCommentId) {
        this.orderCommentId = orderCommentId;
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

        CommentReply commentReply = (CommentReply) o;
        if(commentReply.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commentReply.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommentReply{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            ", creatDate='" + getCreatDate() + "'" +
            "}";
    }
}
