package com.manle.saitamall.order.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.manle.saitamall.R;
import com.manle.saitamall.bean.OrderMaster;
import com.manle.saitamall.bean.User;
import com.manle.saitamall.bean.enumeration.OrderStatus;
import com.manle.saitamall.home.fragment.HomeFragment;
import com.manle.saitamall.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2018/4/11.
 */

public class OrderMasterToPayAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private  List<OrderMaster> result;
    private FragmentActivity activity;
    private User user;



    public OrderMasterToPayAdapter(FragmentActivity activity, Context mContext, List<OrderMaster> result, User user) {
        this.mContext = mContext;
        this.result = result;
        this.activity = activity;
        this.user = user;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext,R.layout.item_order_wait_to_pay,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        OrderMasterToPayAdapter.ViewHolder viewHolder = (OrderMasterToPayAdapter.ViewHolder) holder;
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
        Button btnPay;
        OrderMaster data;

        public ViewHolder(View itemView) {
            super(itemView);
            ivProductId = (TextView) itemView.findViewById(R.id.iv_product_id);
            tvOrderNumber = (TextView) itemView.findViewById(R.id.tv_order_number);
            tvOrderQuanity = (TextView) itemView.findViewById(R.id.tv_order_quanity);
            tvOrderPrice = (TextView) itemView.findViewById(R.id.tv_order_price);
            btnPay = (Button) itemView.findViewById(R.id.btn_pay);

            btnPay.setOnClickListener(v -> {
                LinearLayout ll_pay = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.dialog_shopcart_pay, null);
                AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
                dialog.setTitle("请输入密码")
                        .setView(ll_pay)
                        .setPositiveButton("确定", (dialog12, which) -> {
                            EditText editText = (EditText) ll_pay.findViewById(R.id.et_password);
                            if (!user.getPassword().equals(editText.getText().toString())) {
                                editText.setError("密码错误");
                            } else {
                                dialog12.dismiss();
                                data.setOrderStatus(OrderStatus.WAIT_TO_SEND);
                                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                                RequestBody requestBody = RequestBody.create(JSON, new Gson().toJson(data));
                                OkHttpUtils.put().requestBody(requestBody).url(Constants.ORDER_MASTER).build().execute(new MyCallBack());

                            }
                        })
                        .setNegativeButton("取消", (dialog1, which) -> {
                            dialog1.dismiss();
                        })
                        .create();
                dialog.show();
            });
            itemView.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    onItemClickListener.setOnItemClickListener(data);
                }
            });
            itemView.setOnLongClickListener(v -> {
                AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
                dialog.setMessage("是否删除该订单")
                        .setNegativeButton("取消", (dialog14, which) -> dialog14.dismiss())
                        .setPositiveButton("确定", (dialog13, which) -> {
                          /*  MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                            RequestBody requestBody = RequestBody.create(JSON, new Gson().toJson(data));*/
                            OkHttpUtils.delete().url(Constants.ORDER_MASTER+"/"+data.getId()).build().execute(new MyCallBack());

                        }).create().show();
                return true;

            });
        }

        public void setData(OrderMaster data) {
            ivProductId.setText(data.getId()+"");
            tvOrderNumber.setText("订单号："+data.getOrderNumber());
            tvOrderQuanity.setText("x"+data.getTotalQuanity()+"");
            tvOrderPrice.setText("RMB "+data.getTotalPrices()+"");
            this.data = data;
        }

        private class MyCallBack extends StringCallback {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showShortToast(mContext,"联网失败");
            }

            @Override
            public void onResponse(String response, int id) {
                if (response!=null){
                    result.remove(data);
                    notifyDataSetChanged();
                }
            }
        }
    }

    private OrderMasterToPayAdapter.OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void setOnItemClickListener(OrderMaster data);
    }

    public void setOnItemClickListener(OrderMasterToPayAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


}
