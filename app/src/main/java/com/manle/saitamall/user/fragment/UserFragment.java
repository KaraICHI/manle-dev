package com.manle.saitamall.user.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hankkin.gradationscroll.GradationScrollView;
import com.manle.saitamall.R;
import com.manle.saitamall.app.LoginActivity;
import com.manle.saitamall.base.BaseFragment;
import com.manle.saitamall.bean.User;
import com.manle.saitamall.order.activity.AllOrderActivity;
import com.manle.saitamall.user.activity.AddressMangerActivity;
import com.manle.saitamall.user.activity.CollectorMangerActivity;
import com.manle.saitamall.user.activity.UserModifyActivity;
import com.manle.saitamall.utils.CacheUtils;
import com.manle.saitamall.utils.Constants;
import com.manle.saitamall.utils.GlideCircleTransform;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

// 个人中心页面
public class UserFragment extends BaseFragment implements View.OnClickListener {

    @Bind(R.id.ib_user_avator)
    ImageView ibUserAvator;
    @Bind(R.id.rl_person_header)
    RelativeLayout rlPersonHeader;
    @Bind(R.id.sv_person)
    GradationScrollView svPerson;
    @Bind(R.id.tv_usercenter)
    TextView tvUsercenter;
    @Bind(R.id.tv_my_collector)
    TextView tvMyCollector;
    @Bind(R.id.tv_login_out)
    TextView tvLoginOut;
    @Bind(R.id.tv_username)
    TextView tvUsername;
    @Bind(R.id.tv_point)
    TextView tvPoint;
    @Bind(R.id.ll_not_login)
    LinearLayout llNotLogin;
    @Bind(R.id.tv_to_login)
    TextView tvToLogin;
    @Bind(R.id.tv_modify_user)
    TextView tvModifyUser;
    @Bind(R.id.tv_to_all_order)
    TextView tvToAllOrder;
    @Bind(R.id.tv_to_address)
    TextView tvToAddress;


    User user;


    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_user, null);
        ButterKnife.bind(this, view);
        tvMyCollector.setOnClickListener(this);
        tvLoginOut.setOnClickListener(this);
        tvToLogin.setClickable(true);
        tvToLogin.setOnClickListener(this);
        ibUserAvator.setOnClickListener(this);
        tvModifyUser.setOnClickListener(this);
        tvToAllOrder.setOnClickListener(this);
        tvToAddress.setOnClickListener(this);
        user = new Gson().fromJson(CacheUtils.getString(mContext, "user"), User.class);
        if (user == null) {
            llNotLogin.setVisibility(View.VISIBLE);
        } else {
            tvUsername.setText(user.getUserName());
            initAvatar(user.getFigure());
            Float point = user.getPoint();
            if (point < 100) {
                tvPoint.setText("LV1");
            } else if (point < 1000) {
                tvPoint.setText("LV2");
            }
        }

        return view;
    }

    @Override
    public void initData() {
        super.initData();


        ViewTreeObserver vto = rlPersonHeader.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            private int height;

            @Override
            public void onGlobalLayout() {
                // 移除监听
             /*   tvUsercenter.getViewTreeObserver().removeGlobalOnLayoutListener(this);*/

                // 获取顶部图片的高度
                height = rlPersonHeader.getHeight();

                // 监听滑动，改变透明度
                svPerson.setScrollViewListener((scrollView, x, y, oldx, oldy) -> {

                    if (y <= 0) {   //设置标题的背景颜色
                        tvUsercenter.setBackgroundColor(Color.argb((int) 0, 255, 0, 0));
                    } else if (y > 0 && y <= height) { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
                        float scale = (float) y / height;
                        float alpha = (255 * scale);
                        tvUsercenter.setTextColor(Color.argb((int) alpha, 255, 255, 255));
                        tvUsercenter.setBackgroundColor(Color.argb((int) alpha, 255, 0, 0));
                    } else {    //滑动到banner下面设置普通颜色
                        tvUsercenter.setBackgroundColor(Color.argb((int) 255, 255, 0, 0));
                    }
                });
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_my_collector:
                Intent intent = new Intent(mContext, CollectorMangerActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_login_out:
                Log.d(TAG, "onClick: =============loginout");
                CacheUtils.putString(mContext, "user", null);
                llNotLogin.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_to_login:
                Intent intent1 = new Intent(mContext, LoginActivity.class);
                startActivityForResult(intent1, 1);
                break;
            case R.id.tv_modify_user:
                Intent intent2 = new Intent(mContext, UserModifyActivity.class);
                startActivityForResult(intent2, 2);
                break;
            case R.id.tv_to_all_order:
                Intent intent3 = new Intent(mContext, AllOrderActivity.class);
                startActivity(intent3);
                break;
            case R.id.tv_to_address:
                Intent intent4 = new Intent(mContext, AddressMangerActivity.class);
                startActivity(intent4);
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: resultcode" + resultCode);
        if (resultCode == 0) {
            if (requestCode == 1&&data!=null) {
                llNotLogin.setVisibility(View.GONE);
                User user = (User) data.getSerializableExtra("user");
                Log.d(TAG, "onActivityResult: " + user);
                tvUsername.setText(user.getUserName());
                initAvatar(user.getFigure());
                if (user.getPoint() < 100) {
                    tvPoint.setText("LV1");
                } else if (user.getPoint() < 1000) {
                    tvPoint.setText("LV2");
                }
            }

        } else if (resultCode == RESULT_OK && requestCode == 2) {
            Log.e(TAG, "onActivityResult: =======================");
            User user = (User) data.getSerializableExtra("user");
            Log.d(TAG, "onActivityResult: " + user);
            initAvatar(user.getFigure());
            tvUsername.setText(user.getUserName());
        }


    }

    private void initAvatar(String figure) {
        Glide.with(mContext).load(Constants.AVATAR_IMAGE + figure).centerCrop().transform(new GlideCircleTransform(mContext)).into(ibUserAvator);
    }


}
