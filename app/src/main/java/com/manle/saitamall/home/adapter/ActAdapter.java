package com.manle.saitamall.home.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.manle.saitamall.R;
import com.manle.saitamall.home.bean.ResultBean;
import com.manle.saitamall.utils.Constants;

import java.util.List;

/**
 * Created by Administrator on 2018/4/2.
 */

public class ActAdapter extends PagerAdapter {
    private List<ResultBean.ActInfoBean> list;
    private Context mContext;
    private LayoutInflater inflater;

    public ActAdapter(Context context, List<ResultBean.ActInfoBean> list) {
        mContext = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return list.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = inflater.inflate(R.layout.item_act, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.theme_img);
        Glide.with(mContext)
                .load(Constants.BASE_SERVER_IMAGE +list.get(position).getIcon_url())
                .into(imageView);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}