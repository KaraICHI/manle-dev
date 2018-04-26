package com.manle.saitamall.home.fragment;


import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.manle.saitamall.R;
import com.manle.saitamall.base.BaseFragment;
import com.manle.saitamall.bean.Category;
import com.manle.saitamall.bean.Product;
import com.manle.saitamall.home.adapter.HomeRecycleAdapter;
import com.manle.saitamall.home.bean.HomeBean;
import com.manle.saitamall.user.activity.MessageCenterActivity;
import com.manle.saitamall.utils.CacheUtils;
import com.manle.saitamall.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Request;

// 首页
public class HomeFragment extends BaseFragment {

    private HomeBean homeBean;
    private RecyclerView rvHome;
    private ImageView ib_top;
    private HomeRecycleAdapter adapter;
    private TextView tv_search_home;
    private ImageButton tv_message_home;


    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_home, null);
        rvHome = (RecyclerView) view.findViewById(R.id.rv_home);
        ib_top = (ImageView) view.findViewById(R.id.ib_top);
        tv_search_home = (TextView) view.findViewById(R.id.tv_search_home);
        tv_message_home = (ImageButton) view.findViewById(R.id.tv_message_home);

        return view;
    }

    @Override
    public void initData() {
        //请求网络
        getDataFromNet();
    }

    public void getDataFromNet() {
        OkHttpUtils
                .get()
                .url(Constants.HOME)
                .id(100)
                .build()
                .execute(new MyStringCallback());
    }

    public class MyStringCallback extends StringCallback {

        @Override
        public void onBefore(Request request, int id) {
        }

        @Override
        public void onAfter(int id) {
        }

        @Override
        public void onError(okhttp3.Call call, Exception e, int id) {
            Toast.makeText(mContext,e.getMessage(),Toast.LENGTH_SHORT).show();
            String home = CacheUtils.getString(mContext,"homeInfo");
            processData(home);
        }


        @Override
        public void onResponse(String response, int id) {


            if (response != null) {
                processData(response);
                CacheUtils.putString(mContext,"homeInfo",response);

            }


        }
    }

    private void initListener() {

        //置顶的监听
        ib_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rvHome.scrollToPosition(0);
            }
        });

        //搜素的监听
        tv_search_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "搜索", Toast.LENGTH_SHORT).show();
            }
        });

        //消息的监听
        tv_message_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MessageCenterActivity.class);
                mContext.startActivity(intent);
            }
        });
    }

    private void processData(String json) {

        if (!TextUtils.isEmpty(json)) {

            homeBean = new Gson().fromJson(json, HomeBean.class);
            adapter = new HomeRecycleAdapter(mContext, homeBean);
            rvHome.setAdapter(adapter);

            GridLayoutManager manager = new GridLayoutManager(getActivity(), 1);

            //设置滑动到哪个位置了的监听
            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (position <= 3) {
                        ib_top.setVisibility(View.GONE);
                    } else {
                        ib_top.setVisibility(View.VISIBLE);
                    }
                    return 1;
                }
            });
            //设置网格布局
            rvHome.setLayoutManager(manager);

            initListener();

        }
    }
}
