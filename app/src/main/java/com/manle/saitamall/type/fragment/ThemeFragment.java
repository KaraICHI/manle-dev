package com.manle.saitamall.type.fragment;


import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.manle.saitamall.R;
import com.manle.saitamall.base.BaseFragment;
import com.manle.saitamall.bean.Theme;
import com.manle.saitamall.community.bean.ArticalVO;
import com.manle.saitamall.home.activity.GoodsListActivity;
import com.manle.saitamall.type.adapter.TagGridViewAdapter;
import com.manle.saitamall.type.bean.ThemeBean;
import com.manle.saitamall.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

// 分类页面
public class ThemeFragment extends BaseFragment {

    private ListView gv_tag;
    private TagGridViewAdapter adapter;
    private List<Theme> result;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_tag, null);
        gv_tag = (ListView) view.findViewById(R.id.gv_tag);
        gv_tag.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), GoodsListActivity.class);
                intent.putExtra("themeId",result.get(position).getId());
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void initData() {
        getDataFromNet();
    }

    public void getDataFromNet() {
        OkHttpUtils
                .get()
                .url(Constants.THEME)
                .id(100)
                .build()
                .execute(new ThemeFragment.MyStringCallback());
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
                        adapter = new TagGridViewAdapter(mContext, result);
                        gv_tag.setAdapter(adapter);
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
         result = gson.fromJson(json,new TypeToken<List<Theme>>() {
         }.getType());

    }
}
