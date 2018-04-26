package com.manle.saitamall.app;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.manle.saitamall.R;
import com.manle.saitamall.base.BaseFragment;
import com.manle.saitamall.bean.User;
import com.manle.saitamall.community.fragment.CommunityFragment;
import com.manle.saitamall.home.fragment.HomeFragment;
import com.manle.saitamall.shoppingcart.fragment.ShoppingCartFragment;
import com.manle.saitamall.type.fragment.ThemeFragment;
import com.manle.saitamall.user.fragment.UserFragment;
import com.manle.saitamall.utils.CacheUtils;
import com.manle.saitamall.utils.Constants;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

// 主页面
public class MainActivity extends FragmentActivity {
    private static final String TAG = "MainActivity";

    @Bind(R.id.frameLayout)
    FrameLayout frameLayout;
    @Bind(R.id.rb_home)
    public RadioButton rbHome;
    @Bind(R.id.rb_type)
    RadioButton rbType;
    @Bind(R.id.rb_community)
    RadioButton rbCommunity;
    @Bind(R.id.rb_cart)
    RadioButton rbCart;
    @Bind(R.id.rb_user)
    RadioButton rbUser;
    @Bind(R.id.rg_main)
    RadioGroup rgMain;
    private ArrayList<BaseFragment> fragments;
    private int position;
    private ThemeFragment themeFragment;
    private BaseFragment mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去除title
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //去掉Activity上面的状态栏
   //    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        changeImageSize();
        initFragment();
        initListener();
    }

    private void initFragment() {
        fragments = new ArrayList<>();
        themeFragment = new ThemeFragment();
        fragments.add(new HomeFragment());
        fragments.add(themeFragment);
        fragments.add(new CommunityFragment());
        fragments.add(new ShoppingCartFragment());
        fragments.add(new UserFragment());
    }
    private void changeImageSize() {
        //定义底部标签图片大小
        Drawable drawableHome = getResources().getDrawable(R.drawable.home_button_selector);
        drawableHome.setBounds(0, 0, 69, 69);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        rbHome.setCompoundDrawables(null, drawableHome, null, null);//只放上面
        Drawable drawableType = getResources().getDrawable(R.drawable.type_button_selector);
        drawableType.setBounds(0, 0, 69, 69);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        rbType.setCompoundDrawables(null, drawableType, null, null);//只放上面
        Drawable drawableCommunity = getResources().getDrawable(R.drawable.community_button_selector);
        drawableCommunity.setBounds(0, 0, 69, 69);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        rbCommunity.setCompoundDrawables(null, drawableCommunity, null, null);//只放上面
        Drawable drawableCart = getResources().getDrawable(R.drawable.cart_button_selector);
        drawableCart.setBounds(0, 0, 69, 69);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        rbCart.setCompoundDrawables(null, drawableCart, null, null);//只放上面
        Drawable drawableMe = getResources().getDrawable(R.drawable.user_button_selector);
        drawableMe.setBounds(0, 0, 69, 69);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        rbUser.setCompoundDrawables(null, drawableMe, null, null);//只放上面
    }

    private void initListener() {

        rgMain.setOnCheckedChangeListener((group, checkedId) -> {

            switch (checkedId) {
                case R.id.rb_home:
                    position = 0;
                    break;

                case R.id.rb_type:
                    position = 1;
                    break;

                case R.id.rb_community:
                    position = 2;
                    break;

                case R.id.rb_cart:
                    position = 3;
                    break;

                case R.id.rb_user:
                    position = 4;
                    break;
            }

            BaseFragment baseFragment = getFragment(position);

                switchFragment(mContext, baseFragment);

        });

        rgMain.check(R.id.rb_home);
    }


    /**
     * @param position
     * @return
     */
    private BaseFragment getFragment(int position) {

        if (fragments != null && fragments.size() > 0) {
            BaseFragment baseFragment = fragments.get(position);
            return baseFragment;
        }

        return null;
    }

    private void switchFragment(Fragment fromFragment, BaseFragment nextFragment) {

        if (mContext != nextFragment) {

            mContext = nextFragment;

            if (nextFragment != null) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                //判断nextFragment是否添加
                if (!nextFragment.isAdded()) {
                    //隐藏当前Fragment
                    if (fromFragment != null) {
                        transaction.hide(fromFragment);
                    }
                    transaction.add(R.id.frameLayout, nextFragment).commit();
                } else {
                    //隐藏当前Fragment
                    if (fromFragment != null) {
                        transaction.hide(fromFragment);
                    }
                    transaction.show(nextFragment).commit();
                }
            }
        }
    }





}
