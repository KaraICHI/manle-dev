package com.manle.saitamall.user.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.google.gson.Gson;
import com.manle.saitamall.R;
import com.manle.saitamall.app.GoodsInfoActivity;
import com.manle.saitamall.bean.Product;
import com.manle.saitamall.bean.User;
import com.manle.saitamall.home.bean.GoodsBean;
import com.manle.saitamall.home.uitls.SpaceItemDecoration;
import com.manle.saitamall.user.adapter.CollectorAdapter;
import com.manle.saitamall.user.bean.Collector;
import com.manle.saitamall.utils.CacheUtils;
import com.manle.saitamall.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.opendanmaku.DanmakuView.TAG;

/**
 * Created by Administrator on 2018/4/5.
 */

public class CollectorMangerActivity extends Activity implements View.OnClickListener {
    private List<Product> result;
    private List<GoodsBean> datas;
    private RecyclerView collects;
    private ImageButton ibCollectBack;
    private TextView tvCollectEdit;
    private LinearLayout ll_delete;
    private CheckBox cb_all;
    private Button btn_delete;
    CollectorAdapter adapter;
    Context mContext = CollectorMangerActivity.this;
    User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collector_manager);
        initView();
        initData();
    }

    private void initView() {
        collects = (RecyclerView) findViewById(R.id.recyclerview);
        ibCollectBack = (ImageButton) findViewById(R.id.ib_collect_back);
        tvCollectEdit = (TextView) findViewById(R.id.tv_collect_edit);
        ll_delete = (LinearLayout) findViewById(R.id.ll_delete);
        cb_all = (CheckBox) findViewById(R.id.cb_all);
        btn_delete = (Button) findViewById(R.id.btn_delete);
        ibCollectBack.setOnClickListener(this);
        tvCollectEdit.setOnClickListener(this);
        btn_delete.setOnClickListener(this);

    }

    private void initData() {
        user = new Gson().fromJson(CacheUtils.getString(mContext,"user"),User.class);
        result = user.getProducts();
        datas = new ArrayList<>();
        for (Product temp:result) {
            if (temp!=null){
                GoodsBean goodsBean = new GoodsBean(temp.getProductName(),temp.getCoverPrice(),temp.getFigure(),temp.getId(), temp.getDescription());
                datas.add(goodsBean);
            }


        }
        adapter = new CollectorAdapter(CollectorMangerActivity.this, datas,cb_all,tvCollectEdit);
        collects.setLayoutManager(new GridLayoutManager(CollectorMangerActivity.this,2));
        collects.setAdapter(adapter);
        collects.addItemDecoration(new SpaceItemDecoration(10));

        adapter.setOnItemClickListener(data -> {
            String name = data.getName();
            BigDecimal cover_price = data.getCover_price();
            String figure = data.getFigure();
            long product_id = data.getProduct_id();
            String product_detail = data.getProduct_detail();
            GoodsBean goodsBean = new GoodsBean(name, cover_price, figure, product_id, product_detail);
            Intent intent = new Intent(CollectorMangerActivity.this, GoodsInfoActivity.class);
            intent.putExtra("goods_bean", goodsBean);
            startActivity(intent);
        });
        //getDataFromNet();
    }

  /*  private void getDataFromNet() {
        OkHttpUtils
                .get()
                .url(Constants.COLLECT_URL)
                .id(100)
                .build()
                .execute(new CollectorMangerActivity.MyStringCallback());
    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_collect_back:
                finish();
                break;
            case R.id.tv_collect_edit:
                if (tvCollectEdit.getText().toString().equals("编辑")){
                    tvCollectEdit.setText("完成");
                    ll_delete.setVisibility(View.VISIBLE);
                }else {
                    tvCollectEdit.setText("编辑");
                    ll_delete.setVisibility(View.GONE);
                }
                break;
            case R.id.btn_delete:
                datas = Stream.of(datas).filter(d->!d.isChildSelected()).collect(Collectors.toList());
                if (datas.size()==0){
                    user.setProducts(null);
                }else {
                    List<Long> productId = Stream.of(datas).map(d->d.getProduct_id()).collect(Collectors.toList());
                    List<Product> products = Stream.of(result).filter(r->productId.contains(r.getId())).collect(Collectors.toList());
                    user.setProducts(products);
                }
                updateUser();
                adapter = new CollectorAdapter(CollectorMangerActivity.this, datas,cb_all,tvCollectEdit);
                collects.setAdapter(adapter);

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
                CacheUtils.putString(CollectorMangerActivity.this,"user",new Gson().toJson(user,User.class));
            }
        });
    }
/*
    public class MyStringCallback extends StringCallback {

        @Override
        public void onBefore(Request request, int id) {
        }

        @Override
        public void onAfter(int id) {

        }

        @Override
        public void onError(Call call, Exception e, int id) {
            Log.e("TAG", "联网失败" + e.getMessage());
        }

        @Override
        public void onResponse(String response, int id) {

            switch (id) {
                case 100:

                    if (response != null) {
                        processData(response);
                        CollectorAdapter adapter = new CollectorAdapter(CollectorMangerActivity.this, datas,cb_all,tvCollectEdit);
                        collects.setLayoutManager(new GridLayoutManager(CollectorMangerActivity.this,2));
                        collects.setAdapter(adapter);
                        collects.addItemDecoration(new SpaceItemDecoration(10));

                        adapter.setOnItemClickListener(data -> {
                            String name = data.getName();
                            String cover_price = data.getCover_price();
                            String figure = data.getFigure();
                            String product_id = data.getProduct_id();
                            String product_detail = data.getProduct_detail();
                            GoodsBean goodsBean = new GoodsBean(name, cover_price, figure, product_id, product_detail);
                            Intent intent = new Intent(CollectorMangerActivity.this, GoodsInfoActivity.class);
                            intent.putExtra("goods_bean", goodsBean);
                            startActivity(intent);
                        });

                    }
                    break;

                case 101:
                    Toast.makeText(CollectorMangerActivity.this, "https", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }*/

  /*  private void processData(String json) {
        Gson gson = new Gson();

        result = gson.fromJson(json,new TypeToken<List<Product>>() {}.getType());
        datas = new ArrayList<>();
        for (Product temp:result) {
            GoodsBean goodsBean = new GoodsBean(temp.getProductName(),temp.getCoverPrice().toString(),temp.getFigure(),temp.getId()+"", temp.getDescription());
            datas.add(goodsBean);

        }

    }
*/
}
