package com.manle.saitamall.community.fragment;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.manle.saitamall.R;
import com.manle.saitamall.base.BaseFragment;
import com.manle.saitamall.bean.User;
import com.manle.saitamall.community.adapter.NewPostListViewAdapter;
import com.manle.saitamall.community.bean.ArticalVO;
import com.manle.saitamall.utils.CacheUtils;
import com.manle.saitamall.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zyao89.view.zloading.ZLoadingDialog;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;

/**
 * 新帖
 */
public class NewPostFragment extends BaseFragment {
    private ListView lv_new_post;
    private List<ArticalVO> result;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    NewPostListViewAdapter adapter;


    public NewPostFragment() {
        super();
    }

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_new_post, null);
        lv_new_post = (ListView) view.findViewById(R.id.lv_new_post);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);

        initPullRefresh();
        return view;
    }

    @Override
    public void initData() {
        getDataFromNet();
    }

    public void getDataFromNet() {
        OkHttpUtils
                .get()
                .url(Constants.ARTICAL + "/new")
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
        public void onError(Call call, Exception e, int id) {
            Log.e("TAG", "联网失败" + e.getMessage());
        }

        @Override
        public void onResponse(String response, int id) {

            switch (id) {
                case 100:
                    if (response != null) {
                        processData(response);
                        adapter = new NewPostListViewAdapter(mContext, result);
                        lv_new_post.setAdapter(adapter);
                    }
                    break;

                case 101:
                    Toast.makeText(mContext, "https", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    private void processData(String json) {
        Gson gson = new Gson();
        result = gson.fromJson(json, new TypeToken<List<ArticalVO>>() {
        }.getType());
        if (result != null) {
            CacheUtils.putString(mContext, "newPost", json);
        }
    }

    private void initPullRefresh() {
        mSwipeRefreshLayout.setOnRefreshListener(() -> new Handler().postDelayed(() -> {
            getDataFromNet();
            //刷新完成
            mSwipeRefreshLayout.setRefreshing(false);
            Toast.makeText(mContext, "更新完成", Toast.LENGTH_SHORT).show();
        }, 3000));
    }

}
