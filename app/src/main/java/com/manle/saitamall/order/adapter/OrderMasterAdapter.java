package com.manle.saitamall.order.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.manle.saitamall.R;
import com.manle.saitamall.bean.OrderMaster;
import com.manle.saitamall.home.adapter.GoodsListAdapter;
import com.manle.saitamall.home.bean.TypeListBean;

import java.util.List;

/**
 * Created by Administrator on 2018/4/11.
 */

public class OrderMasterAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private  List<OrderMaster> result;

    public OrderMasterAdapter(Context mContext, List<OrderMaster> result) {
        this.mContext = mContext;
        this.result = result;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext,R.layout.item_order,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        OrderMasterAdapter.ViewHolder viewHolder = (OrderMasterAdapter.ViewHolder) holder;
        viewHolder.setData(result.get(position));

    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView ivProductId;
        TextView tvOrderNumber;
        TextView tvOrderQuanity;
        TextView tvOrderPrice;
        OrderMaster data;

        public ViewHolder(View itemView) {
            super(itemView);
            ivProductId = (TextView) itemView.findViewById(R.id.iv_product_id);
            tvOrderNumber = (TextView) itemView.findViewById(R.id.tv_order_number);
            tvOrderQuanity = (TextView) itemView.findViewById(R.id.tv_order_quanity);
            tvOrderPrice = (TextView) itemView.findViewById(R.id.tv_order_price);
            itemView.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    onItemClickListener.setOnItemClickListener(data);
                }
            });
        }

        public void setData(OrderMaster data) {
            ivProductId.setText(data.getId()+"");
            tvOrderNumber.setText("订单号："+data.getOrderNumber());
            tvOrderQuanity.setText("x"+data.getTotalQuanity()+"");
            tvOrderPrice.setText("RMB "+data.getTotalPrices()+"");
            this.data = data;
        }
    }

    private OrderMasterAdapter.OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void setOnItemClickListener(OrderMaster data);
    }

    public void setOnItemClickListener(OrderMasterAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
