package com.manle.saitamall.order.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.manle.saitamall.R;
import com.manle.saitamall.bean.OrderItem;
import com.manle.saitamall.utils.Constants;

import java.util.List;

/**
 * Created by Administrator on 2018/4/18.
 */

public class OrderItemAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<OrderItem> result;


    public OrderItemAdapter(Context mContext, List<OrderItem> result) {
        this.mContext = mContext;
        this.result = result;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext,R.layout.item_order_item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        OrderItemAdapter.ViewHolder viewHolder = (OrderItemAdapter.ViewHolder) holder;
        viewHolder.setData(result.get(position));
    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView ivOrderItemFigure;
        private TextView tv_product_name;
        private TextView tv_product_quanity;
        private TextView tv_order_price;


        public ViewHolder(View itemView) {
            super(itemView);
            ivOrderItemFigure = (ImageView) itemView.findViewById(R.id.iv_order_item_figure);
            tv_product_name = (TextView) itemView.findViewById(R.id.tv_product_name);
            tv_product_quanity = (TextView) itemView.findViewById(R.id.tv_product_quanity);
            tv_order_price = (TextView) itemView.findViewById(R.id.tv_order_price);

        }
        public void setData(OrderItem data){
            Glide.with(mContext).load(Constants.BASE_SERVER_IMAGE+data.getProductIcon()).into(ivOrderItemFigure);
            tv_product_name.setText("商品名："+data.getProductName());
            tv_product_quanity.setText("x"+data.getProductQuantity()+"");
            tv_order_price.setText("RMB "+data.getProductPrice()+"");
        }
    }
}
