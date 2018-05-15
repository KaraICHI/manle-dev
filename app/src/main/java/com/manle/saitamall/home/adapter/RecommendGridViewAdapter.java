package com.manle.saitamall.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.manle.saitamall.R;
import com.manle.saitamall.bean.Product;
import com.manle.saitamall.home.bean.ResultBean;
import com.manle.saitamall.utils.Constants;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/9/29.
 */
public class RecommendGridViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<Product> data;

    public RecommendGridViewAdapter(Context mContext, List<Product> data) {
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_recommend_grid_view, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Product recommendInfoBean = data.get(position);
        Glide.with(mContext)
                .load(Constants.BASE_SERVER_IMAGE +recommendInfoBean.getFigure())
                .into(holder.ivRecommend);
        holder.tvName.setText(recommendInfoBean.getProductName());
        holder.tvPrice.setText( recommendInfoBean.getCoverPrice().toString());
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.iv_recommend)
        ImageView ivRecommend;
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_price)
        TextView tvPrice;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);

        }
    }
}
