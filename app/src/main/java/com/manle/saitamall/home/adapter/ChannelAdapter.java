package com.manle.saitamall.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.manle.saitamall.R;
import com.manle.saitamall.bean.Category;
import com.manle.saitamall.home.bean.ResultBean;
import com.manle.saitamall.utils.Constants;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/9/28.
 */
public class ChannelAdapter extends BaseAdapter {
    private Context mContext;
    private List<Category> channel_info;
    int[] imgResourseCategory={R.drawable.category_mailisu,R.drawable.category_shupian,R.drawable.category_meat,R.drawable.category_juice,R.drawable.category_jianguo};


    public ChannelAdapter(Context mContext, List<Category> channel_info) {
        this.mContext = mContext;
        this.channel_info = channel_info;
    }


    @Override
    public int getCount() {
        return channel_info == null ? 0 : channel_info.size();
    }

    @Override
    public Object getItem(int position) {
        return channel_info.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holer;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_channel, null);
            holer = new ViewHolder(convertView);
            convertView.setTag(holer);
        } else {
            holer = (ViewHolder) convertView.getTag();
        }

        Category channelInfoBean = channel_info.get(position);
        holer.tvChannel.setText(channelInfoBean.getCategoryName());
        holer.ivChannel.setImageResource(imgResourseCategory[position]);
     /*   Glide.with(mContext)
                .load(Constants.BASE_URl_IMAGE +channelInfoBean.getImage())
                .into(holer.ivChannel);*/
        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.iv_channel)
        ImageView ivChannel;
        @Bind(R.id.tv_channel)
        TextView tvChannel;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
