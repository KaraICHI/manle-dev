package com.manle.saitamall.user.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.manle.saitamall.R;
import com.manle.saitamall.home.bean.GoodsBean;
import com.manle.saitamall.user.activity.CollectorMangerActivity;
import com.manle.saitamall.utils.Constants;

import java.util.List;

/**
 * Created by Administrator on 2018/4/5.
 */

public class CollectorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<GoodsBean> datas;
    private CheckBox cb_all;
    private TextView tvCollectEdit;

    public CollectorAdapter(CollectorMangerActivity mContext, final List<GoodsBean> datas, CheckBox cb_all, TextView tvCollectEdit) {
        this.mContext = mContext;
        this.datas = datas;
        this.cb_all = cb_all;
        this.tvCollectEdit = tvCollectEdit;



        cb_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = getCb_all().isChecked();
                checkAll_none(checked);
            }
        });
        tvCollectEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if ("编辑".equals(s.toString())){
                    for (int i = 0; i < datas.size(); i++) {
                        datas.get(i).setIsEditing(true);
                    }
                    notifyDataSetChanged();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CollectorAdapter.ViewHolder(View.inflate(mContext, R.layout.item_collector_list_adapter, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CollectorAdapter.ViewHolder viewHolder = (CollectorAdapter.ViewHolder) holder;
        viewHolder.setData(datas.get(position));

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public CheckBox getCb_all() {
        return cb_all;
    }

    public void setCb_all(CheckBox cb_all) {
        this.cb_all = cb_all;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private CheckBox cb_collect;
        private ImageView iv_hot;
        private TextView tv_name;
        private TextView tv_price;
        private GoodsBean data;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_hot = (ImageView) itemView.findViewById(R.id.iv_hot);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            cb_collect = (CheckBox) itemView.findViewById(R.id.cb_collect);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.setOnItemClickListener(data);
                    }
                }
            });

        }

        public void setData(final GoodsBean data) {
            Glide.with(mContext).load(Constants.BASE_SERVER_IMAGE + data.getFigure()).into(iv_hot);
            tv_name.setText(data.getName());
            tv_price.setText(data.getCover_price().toString());
            if (data.isEditing()){
                cb_collect.setVisibility(View.VISIBLE);
            }else {
                cb_collect.setVisibility(View.INVISIBLE);
            }

            cb_collect.setChecked(data.isChildSelected());
            this.data = data;
            cb_collect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        data.setIsChildSelected(true);
                    }
                }
            });


        }
    }


    private CollectorAdapter.OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void setOnItemClickListener(GoodsBean data);
    }

    public void setOnItemClickListener(CollectorAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void checkAll_none(boolean checked) {
        if (datas != null && datas.size() > 0) {
            for (int i = 0; i < datas.size(); i++) {
                datas.get(i).setIsChildSelected(checked);
                notifyItemChanged(i);
            }
        }
    }
}