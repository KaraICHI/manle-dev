package com.manle.saitamall.home.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.manle.saitamall.R;
import com.manle.saitamall.bean.Product;
import com.manle.saitamall.home.bean.ResultBean;
import com.manle.saitamall.utils.Constants;
import com.bumptech.glide.Glide;

import java.util.List;


/**
 * Created by Administrator on 2016/9/29.
 */
public class SeckillRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;

    private final List<Product> list;

    public SeckillRecyclerViewAdapter(Context mContext, List<Product> data) {
        this.mContext = mContext;
        list = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_seckill, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.setData(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivFigure;
        private TextView tvCoverPrice;
        private TextView tvOriginPrice;
        private LinearLayout ll_root;

        public MyViewHolder(View itemView) {
            super(itemView);
            ivFigure = (ImageView) itemView.findViewById(R.id.iv_figure);
            tvCoverPrice = (TextView) itemView.findViewById(R.id.tv_cover_price);
            tvOriginPrice = (TextView) itemView.findViewById(R.id.tv_origin_price);
            ll_root = (LinearLayout) itemView.findViewById(R.id.ll_root);
        }

        public void setData(final int position) {
            Product listBean = list.get(position);
            tvCoverPrice.setText(listBean.getCoverPrice().toString());
            tvOriginPrice.setText("直降" + listBean.getOriginPrice());
            Glide.with(mContext)
                    .load(Constants.BASE_SERVER_IMAGE +listBean.getFigure())
                    .into(ivFigure);
            ll_root.setOnClickListener(v -> {
                if (onSeckillRecyclerView != null) {
                    onSeckillRecyclerView.onClick(position);
                }
            });
        }
    }

    public interface OnSeckillRecyclerView {
        void onClick(int position);
    }

    public void setOnSeckillRecyclerView(OnSeckillRecyclerView onSeckillRecyclerView) {
        this.onSeckillRecyclerView = onSeckillRecyclerView;
    }

    private OnSeckillRecyclerView onSeckillRecyclerView;
}
