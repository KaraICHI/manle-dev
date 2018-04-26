package com.manle.saitamall.home.bean;

import com.manle.saitamall.bean.Category;
import com.manle.saitamall.bean.Product;

import java.util.List;

/**
 * Created by Administrator on 2018/4/19.
 */

public class HomeBean {
    private List<Category> categoryInfo;
    private List<Product> seckillInfo;
    private List<Product> recommendInfo;
    private List<Product> hotInfo;

    public List<Category> getCategoryInfo() {
        return categoryInfo;
    }

    public void setCategoryInfo(List<Category> categoryInfo) {
        this.categoryInfo = categoryInfo;
    }

    public List<Product> getSeckillInfo() {
        return seckillInfo;
    }

    public void setSeckillInfo(List<Product> seckillInfo) {
        this.seckillInfo = seckillInfo;
    }

    public List<Product> getRecommendInfo() {
        return recommendInfo;
    }

    public void setRecommendInfo(List<Product> recommendInfo) {
        this.recommendInfo = recommendInfo;
    }

    public List<Product> getHotInfo() {
        return hotInfo;
    }

    public void setHotInfo(List<Product> hotInfo) {
        this.hotInfo = hotInfo;
    }
}
