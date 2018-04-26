package com.manle.saitamall.bean;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * A DTO for the ArticalCommentReply entity.
 */
public class ArticalCommentReply  extends JSONObject implements Serializable {

    private static final long serialVersionUID = 4210097711578904343L;
    private Long id;

    private String content;

    private Date creatDate;

    private Long articalCommentId;

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

    public Date getCreatDate() {
        return creatDate;
    }

    public void setCreatDate(Date creatDate) {
        this.creatDate = creatDate;
    }

    public Long getArticalCommentId() {
        return articalCommentId;
    }

    public void setArticalCommentId(Long articalCommentId) {
        this.articalCommentId = articalCommentId;
    }

    public Long getClientUserId() {
        return clientUserId;
    }

    public void setClientUserId(Long clientUserId) {
        this.clientUserId = clientUserId;
    }
}
