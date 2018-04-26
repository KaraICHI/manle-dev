package com.manle.saitamall.community.bean;

import com.manle.saitamall.bean.ArticalComment;
import com.manle.saitamall.bean.User;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/4/23.
 */

public class ArticalVO {
    private Long id;

    private String title;

    private String content;

    private String figure;

    private Date creatDate;

    private Long favorite;

    private User clientUser;

    public boolean isLike;

    private List<ArticalComment> articalCommentList;

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

    public Date getCreatDate() {
        return creatDate;
    }

    public void setCreatDate(Date creatDate) {
        this.creatDate = creatDate;
    }

    public Long getFavorite() {
        return favorite;
    }

    public void setFavorite(Long favorite) {
        this.favorite = favorite;
    }

    public User getUser() {
        return clientUser;
    }

    public void setUser(User clientUser) {
        this.clientUser = clientUser;
    }

    public List<ArticalComment> getArticalCommentList() {
        return articalCommentList;
    }

    public void setArticalCommentList(List<ArticalComment> articalCommentList) {
        this.articalCommentList = articalCommentList;
    }
}
