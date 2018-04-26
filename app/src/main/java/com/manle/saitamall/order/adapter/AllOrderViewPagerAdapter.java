package com.manle.saitamall.order.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.manle.saitamall.order.fragment.OrderDoneFragment;
import com.manle.saitamall.order.fragment.OrderWaitToComment;
import com.manle.saitamall.order.fragment.OrderWaitToPayFragment;
import com.manle.saitamall.order.fragment.OrderWaitToSendFragment;
import com.manle.saitamall.order.fragment.OrderWaitToTakeFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/11.
 */

public class AllOrderViewPagerAdapter extends FragmentPagerAdapter{
    private List<Fragment> fragmentList = new ArrayList<>();
    private String[] titles = new String[]{"待付款", "待发货","待收货","已完成","待评价"};



    public AllOrderViewPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        initFragments();
    }



    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList == null ? 0 : fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    private void initFragments() {
        OrderWaitToPayFragment orderWaitToPayFragment = new OrderWaitToPayFragment();
        OrderWaitToSendFragment orderWaitToSendFragment = new OrderWaitToSendFragment();
        OrderWaitToTakeFragment orderWaitToTakeFragment = new OrderWaitToTakeFragment();
        OrderDoneFragment orderDoneFragment = new OrderDoneFragment();
        OrderWaitToComment orderWaitToComment = new OrderWaitToComment();

        fragmentList.add(orderWaitToPayFragment);
        fragmentList.add(orderWaitToSendFragment);
        fragmentList.add(orderWaitToTakeFragment);
        fragmentList.add(orderDoneFragment);
        fragmentList.add(orderWaitToComment);
    }
}
