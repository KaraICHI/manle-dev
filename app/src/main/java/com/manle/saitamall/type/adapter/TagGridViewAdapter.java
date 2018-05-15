package com.manle.saitamall.type.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.manle.saitamall.R;
import com.manle.saitamall.bean.Theme;
import com.manle.saitamall.type.bean.ThemeBean;


import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/2.
 */
public class TagGridViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<Theme> result;

    private int[] colors = {Color.parseColor("#E83C2E"), Color.parseColor("#deE83F0F"), Color.parseColor("#cfE83C2E"),
            Color.parseColor("#88E83C2E"), Color.parseColor("#e3E83C2E"), Color.parseColor("#f0a420"),
            Color.parseColor("#edE83C2E"), Color.parseColor("#f0a420"), Color.parseColor("#4ba5e2")
    };

    public TagGridViewAdapter(Context mContext, List<Theme> result) {
        this.mContext = mContext;
        this.result = result;

    }

    @Override
    public int getCount() {
        return result.size();
    }

    @Override
    public Object getItem(int position) {
        return result.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_tab_gridview, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Theme resultBean = result.get(position);
        holder.tvTag.setText(resultBean.getThemeName());
        holder.tvTag.setBackgroundColor(colors[position % colors.length]);

        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.tv_tag)
        TextView tvTag;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
