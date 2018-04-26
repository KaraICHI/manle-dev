package com.manle.saitamall.order.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.manle.saitamall.R;
import com.manle.saitamall.base.BaseFragment;
import com.manle.saitamall.bean.OrderMaster;
import com.manle.saitamall.bean.User;
import com.manle.saitamall.bean.enumeration.OrderStatus;
import com.manle.saitamall.home.uitls.SpaceItemDecoration;
import com.manle.saitamall.order.activity.OrderItemActivity;
import com.manle.saitamall.order.adapter.OrderMasterAdapter;
import com.manle.saitamall.utils.CacheUtils;
import com.manle.saitamall.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;
import okhttp3.Request;

import static com.zhy.http.okhttp.log.LoggerInterceptor.TAG;

/**
 * Created by Administrator on 2018/4/6.
 */

public class OrderWaitToTakeFragment extends BaseFragment{
    RecyclerView recyclerView;
    private List<OrderMaster> result;

    private User user;
    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_order, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        return view;

    }

    @Override
    public void initData() {
        super.initData();
        user = new Gson().fromJson(CacheUtils.getString(mContext,"user"),User.class);
        getDataFromNet();
    }

    private void getDataFromNet() {
        OkHttpUtils
                .get()
                .url(Constants.ORDER_MASTER_USER)
                .addParams("id",user.getId()+"")
                .addParams("status", OrderStatus.WAIT_TO_TAKE+"")
                .id(100)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onBefore(Request request, int id) {
                        super.onBefore(request, id);
                    }

                    @Override
                    public void onAfter(int id) {
                        super.onAfter(id);
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        processData(CacheUtils.getString(mContext,"orderMasterJson"));
                    }

                    @Override
                    public void onResponse(String response, int id) {

                        switch (id) {
                            case 100:

                                if (response != null) {
                                    processData(response);
                                }
                                break;

                            case 101:
                                Toast.makeText(mContext, "https", Toast.LENGTH_SHORT).show();
                                break;
                        }

                    }
                });
    }

    private void processData(String response) {
        Gson gson =new Gson();
        try{
            result = gson.fromJson(response,  new TypeToken<List<OrderMaster>>(){}.getType());
        }catch (Exception e){
            Log.e(TAG, "processData: "+e.getMessage() );
        }

        if (result.size()>0) Log.e(TAG, "processData: " +result.get(0).getOrderNumber());
        CacheUtils.putString(mContext,"orderMasterJson",response);
        OrderMasterAdapter adapter = new OrderMasterAdapter(mContext, result);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.addItemDecoration(new SpaceItemDecoration(3));
        adapter.setOnItemClickListener(data -> {
            String masterId = data.getId()+"";
            Intent intent = new Intent(mContext, OrderItemActivity.class);
            intent.putExtra("masterId",masterId);
            startActivity(intent);
        });

    }

}
