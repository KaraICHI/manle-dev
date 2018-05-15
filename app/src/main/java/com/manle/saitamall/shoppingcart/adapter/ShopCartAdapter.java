package com.manle.saitamall.shoppingcart.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.manle.saitamall.R;
import com.manle.saitamall.bean.OrderItem;
import com.manle.saitamall.home.bean.GoodsBean;
import com.manle.saitamall.shoppingcart.view.NumberAddSubView;
import com.manle.saitamall.shoppingcart.utils.CartProvider;
import com.manle.saitamall.utils.Constants;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 购物车适配器
 */
public class ShopCartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private CartProvider cartProvider;

    private Context mContext;
    private List<GoodsBean> datas;
    private TextView tvShopcartTotal;
    private CheckBox checkboxAll;
    private CheckBox cb_all;
    private List<OrderItem> orderItems;
    public Integer mQuanity;

    public ShopCartAdapter(Context context, final List<GoodsBean> datas, TextView tvShopcartTotal, CartProvider cartProvider, CheckBox checkboxAll, CheckBox cb_all, List<OrderItem> orderItems) {
        this.mContext = context;
        this.datas = datas;
        this.tvShopcartTotal = tvShopcartTotal;
        this.cartProvider = cartProvider;
        this.checkboxAll = checkboxAll;
        this.cb_all = cb_all;
        this.orderItems = orderItems;

        //首次加载数据
        showTotalPrice();
        checkboxAll.setChecked(true);
        for (int i = 0; i < datas.size(); i++) {
            datas.get(i).setIsChildSelected(true);
        }

        showTotalPrice();

        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                GoodsBean goodsBean = datas.get(position);
                goodsBean.setIsChildSelected(!goodsBean.isChildSelected());
                notifyItemChanged(position);
                checkAll();
                showTotalPrice();
            }
        });

        //设置全选点击事件
        checkboxAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = getCheckboxAll().isChecked();
                checkAll_none(checked);
                showTotalPrice();
            }
        });

        cb_all.setOnClickListener(v -> {
            boolean checked = getCb_all().isChecked();
            checkAll_none(checked);
            showTotalPrice();
        });
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(mContext, R.layout.item_shop_cart, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setData(datas.get(position));
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void checkAll_none(boolean checked) {
        if (datas != null && datas.size() > 0) {
            for (int i = 0; i < datas.size(); i++) {
                datas.get(i).setIsChildSelected(checked);
                checkboxAll.setChecked(checked);
                notifyItemChanged(i);
            }
        } else {
            checkboxAll.setChecked(false);
        }
    }

    public void deleteData() {

        if (datas != null && datas.size() > 0) {
            for (Iterator iterator = datas.iterator(); iterator.hasNext(); ) {

                GoodsBean cart = (GoodsBean) iterator.next();

                if (cart.isChildSelected()) {

                    //这行代码放在前面
                    int position = datas.indexOf(cart);
                    //1.删除本地缓存的
                    cartProvider.deleteData(cart);

                    //2.删除当前内存的
                    iterator.remove();

                    //3.刷新数据
                    notifyItemRemoved(position);
                }
            }
        }
    }

    public void checkAll() {

        if (datas != null && datas.size() > 0) {
            for (int i = 0; i < datas.size(); i++) {
                if (!datas.get(i).isChildSelected()) {
                    checkboxAll.setChecked(false);
                    cb_all.setChecked(false);
                    return;
                } else {
                    checkboxAll.setChecked(true);
                    cb_all.setChecked(true);
                }
            }
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private CheckBox cbGov;
        private ImageView ivGov;
        private TextView tvDescGov;
        private TextView tvPriceGov;
        private NumberAddSubView numberAddSubView;

        ViewHolder(View itemView) {
            super(itemView);
            cbGov = (CheckBox) itemView.findViewById(R.id.cb_gov);
            ivGov = (ImageView) itemView.findViewById(R.id.iv_gov);
            tvDescGov = (TextView) itemView.findViewById(R.id.tv_desc_gov);
            tvPriceGov = (TextView) itemView.findViewById(R.id.tv_price_gov);
            numberAddSubView = (NumberAddSubView) itemView.findViewById(R.id.numberAddSubView);

            itemView.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClickListener(v, getLayoutPosition());
                }
            });
        }

        public void setData(final GoodsBean goodsBean) {
            cbGov.setChecked(goodsBean.isChildSelected());

            Glide.with(mContext)
                    .load(Constants.BASE_SERVER_IMAGE+ goodsBean.getFigure())
                    .into(ivGov);

            tvDescGov.setText(goodsBean.getName());
            tvPriceGov.setText("RMB " + goodsBean.getCover_price());

            //设置数字加减回调
            numberAddSubView.setValue(goodsBean.getNumber());

            //-------------------------------------------
            //cartProvider = new CartProvider(mContext);

            numberAddSubView.setOnNumberChangeListener(new NumberAddSubView.OnNumberChangeListener() {
                @Override
                public void addNumber(View view, Integer value) {

                    goodsBean.setNumber(value);
                    cartProvider.updataData(goodsBean);

                    showTotalPrice();
                }

                @Override
                public void subNumner(View view, Integer value) {
                    goodsBean.setNumber(value);
                    cartProvider.updataData(goodsBean);

                    showTotalPrice();
                }
            });
        }
    }

    public void showTotalPrice() {
        tvShopcartTotal.setText(getTotalPrice() + "");
    }

    private double getTotalPrice() {
        double total = 0;
        int totalQuanity = 0;
        List<OrderItem> orderItemList = new ArrayList<>();
        if (datas != null && datas.size() > 0) {
            for (int i = 0; i < datas.size(); i++) {
                GoodsBean goodsBean = datas.get(i);


                if (goodsBean.isChildSelected()){
                    orderItemList.add(new OrderItem(goodsBean.getName(),goodsBean.getCover_price(),goodsBean.getNumber(),goodsBean.getFigure(),goodsBean.getProduct_id()));
                    total += goodsBean.getCover_price().doubleValue() *goodsBean.getNumber();
                    totalQuanity += goodsBean.getNumber();
                }



            }

        }
        orderItems.clear();
        orderItems.addAll(orderItemList);
        mQuanity = totalQuanity;
        return total;
    }

    //回调点击事件的监听
    private OnItemClickListener onItemClickListener;

    interface OnItemClickListener {
        void onItemClickListener(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public CheckBox getCb_all() {
        return cb_all;
    }

    public void setCb_all(CheckBox cb_all) {
        this.cb_all = cb_all;
    }

    public CheckBox getCheckboxAll() {
        return checkboxAll;
    }

    public void setCheckboxAll(CheckBox checkboxAll) {
        this.checkboxAll = checkboxAll;
    }
}
