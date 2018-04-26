package com.manle.saitamall.bean;


import com.alibaba.fastjson.annotation.JSONField;

import org.joda.time.LocalDate;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Artical entity.
 */
public class Artical {

    private Long id;

    private String title;

    private String content;

    private String figure;

    private Long favorite;

    private String creatDate;

    private Long clientUserId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFigure() {
        return figure;
    }

    public void setFigure(String figure) {
        this.figure = figure;
    }

    public String getCreatDate() {
        return creatDate;
    }

    public void setCreatDate(String creatDate) {
        this.creatDate = creatDate;
    }

    public Long getClientUserId() {
        return clientUserId;
    }

    public void setClientUserId(Long clientUserId) {
        this.clientUserId = clientUserId;
    }

    public Long getFavorite() {
        return favorite;
    }

    public void setFavorite(Long favorite) {
        this.favorite = favorite;
    }
}
