package com.manle.saitamall.shoppingcart.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.google.gson.Gson;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.manle.saitamall.R;
import com.manle.saitamall.app.LoginActivity;
import com.manle.saitamall.app.MainActivity;
import com.manle.saitamall.bean.Address;
import com.manle.saitamall.bean.OrderItem;
import com.manle.saitamall.bean.OrderMaster;
import com.manle.saitamall.bean.User;
import com.manle.saitamall.bean.enumeration.OrderStatus;
import com.manle.saitamall.shoppingcart.adapter.ShopCartAdapter;
import com.manle.saitamall.home.bean.GoodsBean;
import com.manle.saitamall.shoppingcart.utils.CartProvider;
import com.manle.saitamall.user.bean.Collector;
import com.manle.saitamall.utils.CacheUtils;
import com.manle.saitamall.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.RequestBody;

// 购物车
public class ShoppingCartActivity extends Activity implements View.OnClickListener {
    private ImageButton ibShopcartBack;
    private TextView tvShopcartEdit;
    private RecyclerView recyclerview;
    private CheckBox checkboxAll;
    private TextView tvShopcartTotal;
    private LinearLayout ll_check_all;
    private LinearLayout ll_delete;
    private CheckBox cb_all;
    private Button btn_delete;
    private Button btn_collection;
    private Button btnCheckOut;
    private ShopCartAdapter adapter;
    private LinearLayout ll_empty_shopcart;
    private TextView tv_empty_cart_tobuy;
    private LinearLayout ll_not_login;
    private TextView tv_to_login;
    User user;
    List<GoodsBean> datas;
    private List<OrderItem> orderItems = new ArrayList<>();
    OrderMaster orderMaster;
    CartProvider cartProvider = CartProvider.getInstance();
    /**
     * 编辑状态
     */
    private static final int ACTION_EDIT = 0;
    /**
     * 完成状态
     */
    private static final int ACTION_COMPLETE = 1;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2016-10-11 21:08:02 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        ibShopcartBack = (ImageButton) findViewById(R.id.ib_shopcart_back);
        tvShopcartEdit = (TextView) findViewById(R.id.tv_shopcart_edit);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        checkboxAll = (CheckBox) findViewById(R.id.checkbox_all);
        tvShopcartTotal = (TextView) findViewById(R.id.tv_shopcart_total);
        btnCheckOut = (Button) findViewById(R.id.btn_check_out);
        ll_check_all = (LinearLayout) findViewById(R.id.ll_check_all);
        ll_delete = (LinearLayout) findViewById(R.id.ll_delete);
        cb_all = (CheckBox) findViewById(R.id.cb_all);
        btn_delete = (Button) findViewById(R.id.btn_delete);
        btn_collection = (Button) findViewById(R.id.btn_collection);
        ll_empty_shopcart = (LinearLayout) findViewById(R.id.ll_empty_shopcart);
        tv_empty_cart_tobuy = (TextView) findViewById(R.id.tv_empty_cart_tobuy);
        ll_not_login = (LinearLayout) findViewById(R.id.ll_not_login);
        tv_to_login = (TextView) findViewById(R.id.tv_to_login);

        ibShopcartBack.setOnClickListener(this);
        btnCheckOut.setOnClickListener(this);
        tvShopcartEdit.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
        tv_empty_cart_tobuy.setClickable(true);
        tv_empty_cart_tobuy.setOnClickListener(this);
        tv_to_login.setClickable(true);
        tv_to_login.setOnClickListener(this);
    }

    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2016-10-11 21:08:02 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    @Override
    public void onClick(View v) {

        if (v == ibShopcartBack) {
            finish();
        } else if (v == btnCheckOut) {
            Toast.makeText(ShoppingCartActivity.this, "结算", Toast.LENGTH_SHORT).show();
            creatOrder();
        } else if (v == tvShopcartEdit) {
            //设置编辑的点击事件
            int tag = (int) tvShopcartEdit.getTag();
            if (tag == ACTION_EDIT) {
                //变成完成状态
                showDelete();
            } else {
                //变成编辑状态
                hideDelete();
            }
        } else if (v == btn_delete) {
            adapter.deleteData();
            adapter.showTotalPrice();
            //显示空空如也
            checkData();
            adapter.checkAll();
        } else if (v == tv_empty_cart_tobuy) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if (v==tv_to_login){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent,1);
        }
    }

    private void creatOrder() {
        Address address = new Gson().fromJson(CacheUtils.getString(ShoppingCartActivity.this, "address"), Address.class);
        if (address!=null){
            orderMaster = new OrderMaster();
            orderMaster.setAddressId(address.getId());
            orderMaster.setClientUserId(address.getClientUserId());
            orderMaster.setOrderStatus(OrderStatus.WAIT_TO_PAY);
            orderMaster.setOrderNumber(String.valueOf(System.currentTimeMillis()));
            orderMaster.setTotalPrices(new BigDecimal(tvShopcartTotal.getText().toString()));
            orderMaster.setTotalQuanity(adapter.mQuanity);
            saveOrderMaster(false);
        }else {
            ToastUtils.showShortToast(ShoppingCartActivity.this,"请添加地址信息");
        }


    }

    private void saveOrderMaster(boolean isUpdate){
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(JSON,new Gson().toJson(orderMaster));
        OkHttpUtils.put().requestBody(requestBody).url(Constants.ORDER_MASTER).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showShortToast(ShoppingCartActivity.this,"联网失败");
            }

            @Override
            public void onResponse(String response, int id) {
                if (response!=null){
                    orderMaster = new Gson().fromJson(response,OrderMaster.class);
                    if (orderMaster!=null&&!isUpdate){
                        creatOrderItem();
                    }
                }
            }
        });
    }

    private void creatOrderItem() {
        for(OrderItem orderItem:orderItems){
            orderItem.setOrderMasterId(orderMaster.getId());
        }
        OkHttpUtils.postString().url(Constants.ORDER_ITEM+"/list")
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content(new Gson().toJson(orderItems)).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showShortToast(ShoppingCartActivity.this,"联网失败");
            }

            @Override
            public void onResponse(String response, int id) {
                LinearLayout ll_pay = (LinearLayout) getLayoutInflater().inflate(R.layout.dialog_shopcart_pay,null);
                AlertDialog dialog = new AlertDialog.Builder(ShoppingCartActivity.this)
                        .setTitle("支付")
                        .setView(ll_pay)
                        .setPositiveButton("确定", (dialog12, which) -> {
                            EditText editText = (EditText) ll_pay.findViewById(R.id.et_password);
                            if (!user.getPassword().equals(editText.getText().toString())){
                                editText.setError("密码错误");
                            }else {
                                ToastUtils.showShortToast(ShoppingCartActivity.this,"支付成功");
                                orderMaster.setOrderStatus(OrderStatus.WAIT_TO_SEND);
                                saveOrderMaster(true);

                            }
                            datas.clear();
                            cartProvider.deleteAll();
                            checkData();
                        })
                        .setNegativeButton("取消", (dialog1, which) -> {
                            dialog1.dismiss();
                            datas.clear();
                            cartProvider.deleteAll();
                            checkData();
                        })
                        .create();
                dialog.show();

            }
        });
    }

    private void hideDelete() {
        tvShopcartEdit.setText("编辑");
        tvShopcartEdit.setTag(ACTION_EDIT);

        adapter.checkAll_none(true);
        ll_delete.setVisibility(View.GONE);
        ll_check_all.setVisibility(View.VISIBLE);

        adapter.showTotalPrice();
    }

    private void showDelete() {
        tvShopcartEdit.setText("完成");
        tvShopcartEdit.setTag(ACTION_COMPLETE);

        adapter.checkAll_none(false);
        cb_all.setChecked(false);
        checkboxAll.setChecked(false);

        ll_delete.setVisibility(View.VISIBLE);
        ll_check_all.setVisibility(View.GONE);

        adapter.showTotalPrice();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        findViews();
        String userJson= CacheUtils.getString(ShoppingCartActivity.this,"user");
        user = new Gson().fromJson(userJson,User.class);
        if (user==null){
            tvShopcartEdit.setVisibility(View.GONE);
            ll_not_login.setVisibility(View.VISIBLE);
            ll_check_all.setVisibility(View.GONE);
            ll_delete.setVisibility(View.GONE);
        }else {
            showData();
            tvShopcartEdit.setTag(ACTION_EDIT);
            tvShopcartEdit.setText("编辑");
        }


    }

    //-----------------------------------------
    private void checkData() {
        if (adapter != null && adapter.getItemCount() > 0) {
            tvShopcartEdit.setVisibility(View.VISIBLE);
            ll_empty_shopcart.setVisibility(View.GONE);
            ll_check_all.setVisibility(View.GONE);
        } else {
            ll_empty_shopcart.setVisibility(View.VISIBLE);
            tvShopcartEdit.setVisibility(View.GONE);
            ll_check_all.setVisibility(View.GONE);
            ll_delete.setVisibility(View.GONE);
        }
    }

    private void showData() {

        datas = cartProvider.getDataFromLocal();
        if (datas != null && datas.size() > 0) {
            tvShopcartEdit.setVisibility(View.VISIBLE);
            ll_empty_shopcart.setVisibility(View.GONE);
            adapter = new ShopCartAdapter(this, datas, tvShopcartTotal, cartProvider, checkboxAll, cb_all, orderItems);
            recyclerview.setLayoutManager(new LinearLayoutManager(this));
            recyclerview.setAdapter(adapter);
        } else {
            //显示空的
            tvShopcartEdit.setVisibility(View.GONE);
            ll_empty_shopcart.setVisibility(View.VISIBLE);
            ll_check_all.setVisibility(View.GONE);
            ll_delete.setVisibility(View.GONE);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1&&resultCode==0){
            onCreate(null);
        }
    }
}
