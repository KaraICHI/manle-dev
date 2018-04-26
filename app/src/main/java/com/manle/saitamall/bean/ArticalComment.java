package com.manle.saitamall.bean;


import org.json.JSONObject;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ArticalComment entity.
 */
public class ArticalComment  {

    private Long id;

    private String content;

    private Long articalId;

    private Long clientUserId;

    public ArticalComment(String content, Long articalId, Long clientUserId) {
        this.content = content;
        this.articalId = articalId;
        this.clientUserId = clientUserId;
    }

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

    public Long getArticalId() {
        return articalId;
    }

    public void setArticalId(Long articalId) {
        this.articalId = articalId;
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

        ArticalComment articalComment = (ArticalComment) o;
        if(articalComment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), articalComment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ArticalComment{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            "}";
    }
}
