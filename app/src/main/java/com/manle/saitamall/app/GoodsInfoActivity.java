package com.manle.saitamall.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.manle.saitamall.R;
import com.manle.saitamall.bean.Product;
import com.manle.saitamall.bean.User;
import com.manle.saitamall.home.bean.GoodsBean;
import com.manle.saitamall.home.uitls.VirtualkeyboardHeight;
import com.manle.saitamall.shoppingcart.activity.ShoppingCartActivity;
import com.manle.saitamall.shoppingcart.utils.CartProvider;
import com.manle.saitamall.shoppingcart.view.NumberAddSubView;
import com.manle.saitamall.utils.CacheUtils;
import com.manle.saitamall.utils.Constants;
import com.bumptech.glide.Glide;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.opendanmaku.DanmakuView.TAG;

/**
 * 商品信息列表页面
 */
public class GoodsInfoActivity extends Activity implements View.OnClickListener {
    private ImageButton ibGoodInfoBack;
    private ImageButton ibGoodInfoMore;
    private ImageView ivGoodInfoImage;
    private TextView tvGoodInfoName;
    private TextView tvGoodInfoDesc;
    private TextView tvGoodInfoPrice;
    private TextView tvGoodInfoStore;
    private TextView tvGoodInfoStyle;
    private WebView wbGoodInfoMore;
    private TextView tvGoodInfoCallcenter;
    private CheckBox rbGoodInfoCollection;
    private TextView tvGoodInfoCart;
    private Button btnGoodInfoAddcart;
    private TextView tvMoreShare;
    private TextView tvMoreSearch;
    private TextView tvMoreHome;
    private LinearLayout ll_root;
    private Button btn_more;

    private CartProvider cartProvider;
    // private Boolean isFirst = true;

    private GoodsBean goods_bean;
    private Product product;
    private User user;
    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2016-10-09 01:34:23 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        ibGoodInfoBack = (ImageButton) findViewById(R.id.ib_good_info_back);
        ibGoodInfoMore = (ImageButton) findViewById(R.id.ib_good_info_more);
        ivGoodInfoImage = (ImageView) findViewById(R.id.iv_good_info_image);
        tvGoodInfoName = (TextView) findViewById(R.id.tv_good_info_name);
      //  tvGoodInfoDesc = (TextView) findViewById(R.id.tv_good_info_desc);
        tvGoodInfoPrice = (TextView) findViewById(R.id.tv_good_info_price);
        tvGoodInfoStore = (TextView) findViewById(R.id.tv_good_info_store);
        tvGoodInfoStyle = (TextView) findViewById(R.id.tv_good_info_style);
        wbGoodInfoMore = (WebView) findViewById(R.id.wb_good_info_more);

        tvGoodInfoCallcenter = (TextView) findViewById(R.id.tv_good_info_callcenter);
        rbGoodInfoCollection = (CheckBox) findViewById(R.id.tv_good_info_collection);
        tvGoodInfoCart = (TextView) findViewById(R.id.tv_good_info_cart);
        btnGoodInfoAddcart = (Button) findViewById(R.id.btn_good_info_addcart);

        ll_root = (LinearLayout) findViewById(R.id.ll_root);
        tvMoreShare = (TextView) findViewById(R.id.tv_more_share);
        tvMoreSearch = (TextView) findViewById(R.id.tv_more_search);
        tvMoreHome = (TextView) findViewById(R.id.tv_more_home);

        btn_more = (Button) findViewById(R.id.btn_more);
        changeImageSize();

        ibGoodInfoBack.setOnClickListener(this);
        ibGoodInfoMore.setOnClickListener(this);
        btnGoodInfoAddcart.setOnClickListener(this);

        tvMoreShare.setOnClickListener(this);
        tvMoreSearch.setOnClickListener(this);
        tvMoreHome.setOnClickListener(this);

        btn_more.setOnClickListener(this);

        tvGoodInfoCallcenter.setOnClickListener(this);
        tvGoodInfoCart.setOnClickListener(this);
        btnGoodInfoAddcart.setOnClickListener(this);
        tvGoodInfoCallcenter.setOnClickListener(this);

        rbGoodInfoCollection.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isLogin()){
                if (isChecked){
                    user.getProducts().add(product);
                    updateUser();
                    ToastUtils.showShortToast(GoodsInfoActivity.this,"已收藏");

                }else {
                    ToastUtils.showShortToast(GoodsInfoActivity.this,"已取消");
                    updateUser();
                    user.getProducts().remove(product);
                }

            }
        });
    }


    @Override
    public void onClick(View v) {
        if (v == ibGoodInfoBack) {
            finish();
        } else if (v == ibGoodInfoMore) {
            if (ll_root.getVisibility() == View.VISIBLE) {
                ll_root.setVisibility(View.GONE);
            } else {
                ll_root.setVisibility(View.VISIBLE);
            }
        } else if (v == btn_more) {
            ll_root.setVisibility(View.GONE);
        } else if (v == tvMoreShare) {
            ToastUtils.showShortToast(GoodsInfoActivity.this,"分享");

       //   showShare();
        } else if (v == tvMoreSearch) {
            ToastUtils.showShortToast(GoodsInfoActivity.this,"搜索");
        } else if (v == tvMoreHome) {
            Constants.isBackHome = true;
            finish();
        } else if (v == tvGoodInfoCallcenter) {
            ToastUtils.showShortToast(GoodsInfoActivity.this,"客服");
            Intent intent = new Intent(this, CallCenterActivity.class);
            startActivity(intent);
        }  else if (v == tvGoodInfoCart) {
            Intent intent = new Intent(this, ShoppingCartActivity.class);
            startActivity(intent);

        } else if (v == btnGoodInfoAddcart) {
            //添加购物车
//            cartProvider.addData(goods_bean);
            if (isLogin()){
                showPopwindow();
            }
//            Toast.makeText(GoodsInfoActivity.this, "添加购物车成功", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateUser() {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(JSON,new Gson().toJson(user));
        OkHttpUtils.put().requestBody(requestBody).url(Constants.CLIENT_USER).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.e(TAG, "onError: "+e.getMessage() );
            }

            @Override
            public void onResponse(String response, int id) {
                user = new Gson().fromJson(response,User.class);
                CacheUtils.putString(GoodsInfoActivity.this,"user",new Gson().toJson(user,User.class));
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_info);
        findViews();
        user = new Gson().fromJson(CacheUtils.getString(GoodsInfoActivity.this,"user"),User.class);
        cartProvider = CartProvider.getInstance();
        //取出intent
        Intent intent = getIntent();
        goods_bean = (GoodsBean) intent.getSerializableExtra("goods_bean");
        product = (Product) intent.getSerializableExtra("product_detail");
      //  setWebView(goods_bean);
        if (goods_bean != null) {
            //本地获取存储的数据
            setDataFormView(goods_bean);
        }

    }

    private void setWebView(GoodsBean product) {

        if (product != null) {
            wbGoodInfoMore.loadUrl(goods_bean.getProduct_detail());
            Log.e(TAG, "setWebView: "+goods_bean.getProduct_detail() );
            //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
            wbGoodInfoMore.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                    if (url.startsWith("http:") || url.startsWith("https:")) {
                        return false;
                    }
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                    } catch (Exception e) { }
                    return true;
                }

            });
            //启用支持javascript
            WebSettings settings = wbGoodInfoMore.getSettings();
            settings.setJavaScriptEnabled(true);
            settings.setUseWideViewPort(true);

            //优先使用缓存
            wbGoodInfoMore.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
    }


    public void setDataFormView(GoodsBean goodsBean) {
        String name = goodsBean.getName();
        String cover_price = goodsBean.getCover_price().toString();
        String figure = goodsBean.getFigure();
        String product_id = goodsBean.getProduct_id()+"";

        Glide.with(this).load(Constants.BASE_SERVER_IMAGE + figure).into(ivGoodInfoImage);

        if (name != null) {
            tvGoodInfoName.setText(name);
        }
        if (cover_price != null) {
            tvGoodInfoPrice.setText(cover_price);
        }
      //  setWebView(product_id);
    }


    /**
     * 显示popupWindow
     */
    private void showPopwindow() {

        // 1 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.popupwindow_add_product, null);

        // 2下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()
        final PopupWindow window = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);

        // 3 参数设置
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xFFFFFFFF);
        window.setBackgroundDrawable(dw);

        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.mypopwindow_anim_style);


        // 4 控件处理
        ImageView iv_goodinfo_photo = (ImageView) view.findViewById(R.id.iv_goodinfo_photo);
        TextView tv_goodinfo_name = (TextView) view.findViewById(R.id.tv_goodinfo_name);
        TextView tv_goodinfo_price = (TextView) view.findViewById(R.id.tv_goodinfo_price);
        NumberAddSubView nas_goodinfo_num = (NumberAddSubView) view.findViewById(R.id.nas_goodinfo_num);
        Button bt_goodinfo_cancel = (Button) view.findViewById(R.id.bt_goodinfo_cancel);
        Button bt_goodinfo_confim = (Button) view.findViewById(R.id.bt_goodinfo_confim);

        // 加载图片
        Glide.with(GoodsInfoActivity.this).load(Constants.BASE_SERVER_IMAGE + goods_bean.getFigure()).into(iv_goodinfo_photo);

        // 名称
        tv_goodinfo_name.setText(goods_bean.getName());
        // 显示价格
        tv_goodinfo_price.setText(goods_bean.getCover_price().toString());

        // 设置最大值和当前值
        nas_goodinfo_num.setMaxValue(8);
        nas_goodinfo_num.setValue(1);

        nas_goodinfo_num.setOnNumberChangeListener(new NumberAddSubView.OnNumberChangeListener() {
            @Override
            public void addNumber(View view, Integer value) {
                goods_bean.setNumber(value);
            }

            @Override
            public void subNumner(View view, Integer value) {
                goods_bean.setNumber(value);
            }
        });

        bt_goodinfo_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
            }
        });

        bt_goodinfo_confim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
                //添加购物车
                cartProvider.addData(goods_bean);
                Log.e("TAG", "66:" + goods_bean.toString());
                Toast.makeText(GoodsInfoActivity.this, "添加购物车成功", Toast.LENGTH_SHORT).show();
            }
        });

        window.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                window.dismiss();
            }
        });

        // 5 在底部显示
        window.showAtLocation(GoodsInfoActivity.this.findViewById(R.id.ll_goods_root),
                Gravity.BOTTOM, 0, VirtualkeyboardHeight.getBottomStatusHeight(GoodsInfoActivity.this));

    }


    private void changeImageSize() {
        //定义底部标签图片大小
        Drawable drawableHome = getResources().getDrawable(R.drawable.main_user_1);
        drawableHome.setBounds(0, 0, 59, 59);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        tvGoodInfoCallcenter.setCompoundDrawables(null, drawableHome, null, null);//只放上面
        Drawable drawableType = getResources().getDrawable(R.drawable.favorite_selector);
        drawableType.setBounds(0, 0, 59, 59);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        rbGoodInfoCollection.setCompoundDrawables(null, drawableType, null, null);//只放上面
        Drawable drawableCommunity = getResources().getDrawable(R.drawable.main_cart_1);
        drawableCommunity.setBounds(0, 0, 59, 59);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        tvGoodInfoCart.setCompoundDrawables(null, drawableCommunity, null, null);//只放上面
        Drawable drawableSearch = getResources().getDrawable(R.drawable.icon_search_white);
        drawableSearch.setBounds(0, 0, 89, 89);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        tvMoreSearch.setCompoundDrawables(null, drawableSearch, null, null);//只放上面


    }
    private boolean isLogin() {
        if (user==null){
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
            return false;
        }
        return true;
    }
}
