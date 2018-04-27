package com.manle.saitamall.utils;

public class Constants {
    //家里wifi
    //  public static final String BASE = "http://192.168.1.106";
    //公司wifi
    public static final String BASE = "http://192.168.137.1";

    public static final String BASE_MANLE = "http://192.168.137.1:8080";

    public static final String CLIENT_USER = BASE_MANLE + "/api/client-users";

    public static final String USER_FIGURE = CLIENT_USER + "/upload";

    // 请求图片基本URL
    public static final String BASE_SERVER_IMAGE = BASE_MANLE + "/templates/images/";
    public static final String AVATAR_IMAGE = BASE_SERVER_IMAGE + "avatar/";


    public static final String ORDER_MASTER = BASE_MANLE + "/api/order-masters";

    public static final String ORDER_MASTER_USER = ORDER_MASTER + "/user";

    public static final String ORDER_ITEM = BASE_MANLE + "/api/order-items";

    public static final String ORDER_ITEM_MASTER = ORDER_ITEM + "/master";

    //社区
    public static final String ARTICAL = BASE_MANLE+"/api/articals";
    public static final String ARTICAL_FIGURE = ARTICAL+"/upload";
    public static final String ARTICAL_IMAGE = BASE_SERVER_IMAGE+"article/";

    //地址
    public static final String ADDRESS = BASE_MANLE + "/api/addresses";


    public static final String PRODUCT = BASE_MANLE + "/api/products";
    public static final String HOME = PRODUCT + "/home";


    //收藏

    //点赞
    public static final String LIKE_ADD = ARTICAL+"/add-like";
    public static final String LIKE_REMOVE = ARTICAL+"/remove-like";

    //文章评论
    public static final String ARTICAL_COMMENT = BASE_MANLE + "/api/artical-comments";


//分类
    public static final String PRODUCT_CATRGORY = PRODUCT + "/category";



/*
   public static final String WAIT_TO_SEND = "WAIT_TO_SEND";
   public static final String WAIT_TO_PAY = "WAIT_TO_PAY";
   public static final String WAIT_TO_TAKE = "WAIT_TO_TAKE";
   public static final String DONE = "DONE";
   public static final String WAIT_TO_COMMENT = "WAIT_TO_COMMENT";
*/


    // 请求Json数据基本URL
    public static final String BASE_URL_JSON = BASE + "/atguigu/json/";




    public static final String Theme_URL = BASE_URL_JSON + "THEME_URL.json";



    //果冻
    public static final String GUODONG_STORE = BASE_URL_JSON + "GUODONG_STORE.json";
    public static final String COLLECT_URL = BASE_URL_JSON + "COLLECT_URL.json";

    public static Boolean isBackHome = false;

    //客服数据
    //   public static final String CALL_CENTER = "http://www6.53kf.com/webCompany.php?arg=10007377&style=1&kflist=off&kf=info@atguigu.com,video@atguigu.com,public@atguigu.com,3069368606@qq.com,215648937@qq.com,sudan@atguigu.com,sszhang@atguigu.com&zdkf_type=1&language=zh-cn&charset=gbk&referer=http%3A%2F%2Fwww.atguigu.com%2Fcontant.shtml&keyword=&tfrom=1&tpl=crystal_blue&timeStamp=1479001706368&ucust_id=";
    public static final String CALL_CENTER = "https://chat.kuaishangkf.com/#/?script=xx4yrZrj37tm5HrfH67KJNhH";
}


