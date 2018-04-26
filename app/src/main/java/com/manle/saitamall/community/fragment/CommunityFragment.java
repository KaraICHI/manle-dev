package com.manle.saitamall.community.fragment;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;

import com.google.gson.Gson;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.manle.saitamall.R;
import com.manle.saitamall.base.BaseFragment;
import com.manle.saitamall.bean.User;
import com.manle.saitamall.community.activity.EditArticleActivity;
import com.manle.saitamall.community.adapter.CommunityViewPagerAdapter;
import com.manle.saitamall.utils.CacheUtils;

// 社区
public class CommunityFragment extends BaseFragment {
    private TabLayout tablayout;
    private ViewPager viewPager;
    private ImageButton ib_edit;

    User user;

    @Override
    public View initView() {

        View view = View.inflate(mContext, R.layout.fragment_community, null);

        tablayout = (TabLayout) view.findViewById(R.id.tablayout);
        viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        ib_edit = (ImageButton) view.findViewById(R.id.ib_edit);
        ib_edit.setOnClickListener(v -> {
            user = new Gson().fromJson(CacheUtils.getString(mContext,"user"),User.class);
            if (user!=null){
                Intent intent = new Intent(getContext(), EditArticleActivity.class);
                intent.putExtra("user",user);
                startActivity(intent);
            }else {
                ToastUtils.showShortToast(mContext,"请登录账号");
            }

        });


        CommunityViewPagerAdapter adapter = new CommunityViewPagerAdapter(getFragmentManager());
        viewPager.setAdapter(adapter);
        tablayout.setVisibility(View.VISIBLE);
        tablayout.setupWithViewPager(viewPager);

        return view;
    }

    @Override
    public void initData() {
        super.initData();
    }
}
