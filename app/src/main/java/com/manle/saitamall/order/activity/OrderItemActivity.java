package com.manle.saitamall.order.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.manle.saitamall.R;
import com.manle.saitamall.bean.OrderItem;
import com.manle.saitamall.bean.OrderMaster;
import com.manle.saitamall.order.adapter.OrderItemAdapter;
import com.manle.saitamall.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.Bind;
import okhttp3.Call;
import okhttp3.Request;

import static com.zhy.http.okhttp.log.LoggerInterceptor.TAG;

public class OrderItemActivity extends Activity {
    @Bind(R.id.ib_order_back)
    ImageButton ib_order_back;
    @Bind(R.id.recyclerview)
    RecyclerView recyclerView;

    ProgressDialog progressDialog;

    List<OrderItem> orderItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_item);
        ib_order_back = (ImageButton) findViewById(R.id.ib_order_back);
        ib_order_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        initData();
    }

    private void initData() {
        OkHttpUtils.get().url(Constants.ORDER_ITEM_MASTER).addParams("id",getIntent()
                .getStringExtra("masterId")).build().execute(new StringCallback() {
            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                progressDialog = new ProgressDialog(OrderItemActivity.this,ProgressDialog.STYLE_SPINNER);
                progressDialog.setMessage("加载中...");
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                progressDialog.dismiss();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                Log.e(TAG, "onError: "+e.getMessage() );
                Toast.makeText(OrderItemActivity.this,"联网失败",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {
                processData(response);
                OrderItemAdapter adapter = new OrderItemAdapter(OrderItemActivity.this,orderItemList);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(OrderItemActivity.this));
            }
        });
    }

    private void processData(String response) {
        Gson gson = new Gson();
        orderItemList = gson.fromJson(response, new TypeToken<List<OrderItem>>(){}.getType());
    }
}
