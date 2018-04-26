package com.manle.saitamall.home.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.manle.saitamall.R;
import com.manle.saitamall.app.GoodsInfoActivity;
import com.manle.saitamall.bean.Category;
import com.manle.saitamall.bean.Product;
import com.manle.saitamall.home.activity.GoodsListActivity;
import com.manle.saitamall.home.bean.GoodsBean;
import com.manle.saitamall.home.bean.HomeBean;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnLoadImageListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class HomeRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final String GOODS_BEAN = "goods_bean";
    public static final String PRODUCT = "product_detail";

    /**
     * 上下文
     */
    private Context mContext;
    /**
     * 数据Bean对象
     */
    private HomeBean homeBean;
    /**
     * 五种类型
     */
    /**
     * 横幅广告
     */
    public static final int BANNER = 0;
    /**
     * 频道
     */
    public static final int CHANNEL = 1;

    /**
     * 活动
     */
    //   public static final int ACT = 1;

    /**
     * 秒杀
     */
    public static final int SECKILL = 2;
    /**
     * 推荐
     */
    public static final int RECOMMEND = 3;
    /**
     * 热卖
     */
    public static final int HOT = 4;

    /**
     * 当前类型
     */
    public int currentType = BANNER;
    private final LayoutInflater mLayoutInflater;


    int[] imgResourseBanner = {R.drawable.banner1, R.drawable.banner2, R.drawable.banner3};

    public HomeRecycleAdapter(Context mContext, HomeBean homeBean) {
        this.mContext = mContext;
        this.homeBean = homeBean;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    /**
     * 根据位置得到类型-系统调用
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case BANNER:
                currentType = BANNER;
                break;
            case CHANNEL:
                currentType = CHANNEL;
                break;
         /*   case ACT:
                currentType = ACT;
                break;*/
            case SECKILL:
                currentType = SECKILL;
                break;
            case RECOMMEND:
                currentType = RECOMMEND;
                break;
            case HOT:
                currentType = HOT;
                break;
        }
        return currentType;
    }

    /**
     * 返回总条数，共六种类型
     *
     * @return
     */
    @Override
    public int getItemCount() {
        //================================================================
        return 5;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == BANNER) {
            return new BannerViewHolder(mLayoutInflater.inflate(R.layout.banner_viewpager, null), mContext, homeBean);
        } else if (viewType == CHANNEL) {
            return new ChannelViewHolder(mLayoutInflater.inflate(R.layout.channel_item, null), mContext);
        }/* else if (viewType == ACT) {
            return new ActViewHolder(mLayoutInflater.inflate(R.layout.act_item, null), mContext);
        } */ else if (viewType == SECKILL) {
            return new SeckillViewHolder(mLayoutInflater.inflate(R.layout.seckill_item, null), mContext);
        } else if (viewType == RECOMMEND) {
            return new RecommendViewHolder(mLayoutInflater.inflate(R.layout.recommend_item, null), mContext);
        } else if (viewType == HOT) {
            return new HotViewHolder(mLayoutInflater.inflate(R.layout.hot_item, null), mContext);
        }
        return null;
    }

    /**
     * 绑定数据
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == BANNER) {
            BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
            bannerViewHolder.setData(imgResourseBanner);
        } else if (getItemViewType(position) == CHANNEL) {
            ChannelViewHolder channelViewHolder = (ChannelViewHolder) holder;
            channelViewHolder.setData(homeBean.getCategoryInfo());
        }/* else if (getItemViewType(position) == ACT) {
            ActViewHolder actViewHolder = (ActViewHolder) holder;
            actViewHolder.setData(homeBean.getAct_info());
        } */ else if (getItemViewType(position) == SECKILL) {
            SeckillViewHolder seckillViewHolder = (SeckillViewHolder) holder;
            seckillViewHolder.setData(homeBean.getSeckillInfo());
        } else if (getItemViewType(position) == RECOMMEND) {
            RecommendViewHolder recommendViewHolder = (RecommendViewHolder) holder;
            recommendViewHolder.setData(homeBean.getRecommendInfo());
        } else if (getItemViewType(position) == HOT) {
            HotViewHolder hotViewHolder = (HotViewHolder) holder;
            hotViewHolder.setData(homeBean.getHotInfo());
        }
    }

    class HotViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_more_hot;
        private GridView gv_hot;
        private Context mContext;

        public HotViewHolder(View itemView, Context mContext) {
            super(itemView);
            tv_more_hot = (TextView) itemView.findViewById(R.id.tv_more_hot);
            gv_hot = (GridView) itemView.findViewById(R.id.gv_hot);
            this.mContext = mContext;
        }

        public void setData(final List<Product> data) {
            HotGridViewAdapter adapter = new HotGridViewAdapter(mContext, data);
            gv_hot.setAdapter(adapter);

            //点击事件
            gv_hot.setOnItemClickListener((parent, view, position, id) -> {
                String cover_price = data.get(position).getCoverPrice() + "";
                String name = data.get(position).getProductName();
                String figure = data.get(position).getFigure();
                String product_id = data.get(position).getId() + "";
                String product_detail = data.get(position).getDescription();
                GoodsBean goodsBean = new GoodsBean(name, cover_price, figure, product_id, product_detail);

                Intent intent = new Intent(mContext, GoodsInfoActivity.class);
                intent.putExtra(GOODS_BEAN, goodsBean);
                intent.putExtra(PRODUCT, data.get(position));
                mContext.startActivity(intent);
            });
            tv_more_hot.setOnClickListener(v -> {
                Intent intent = new Intent(mContext, GoodsListActivity.class);
                intent.putExtra("more_products", new Gson().toJson(data, new TypeToken<List<Product>>() {
                }.getType()));
                mContext.startActivity(intent);
            });

        }
    }

    class RecommendViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_more_recommend;
        private GridView gv_recommend;
        private Context mContext;

        public RecommendViewHolder(View itemView, Context mContext) {
            super(itemView);
            tv_more_recommend = (TextView) itemView.findViewById(R.id.tv_more_recommend);
            gv_recommend = (GridView) itemView.findViewById(R.id.gv_recommend);
            this.mContext = mContext;

        }


        public void setData(final List<Product> data) {
            RecommendGridViewAdapter adapter = new RecommendGridViewAdapter(mContext, data);
            gv_recommend.setAdapter(adapter);


            //点击事件
            gv_recommend.setOnItemClickListener((parent, view, position, id) -> {
                String cover_price = data.get(position).getCoverPrice() + "";
                String name = data.get(position).getProductName();
                String figure = data.get(position).getFigure();
                String product_id = data.get(position).getId() + "";
                String product_detail = data.get(position).getDescription();
                GoodsBean goodsBean = new GoodsBean(name, cover_price, figure, product_id, product_detail);

                Intent intent = new Intent(mContext, GoodsInfoActivity.class);
                intent.putExtra(GOODS_BEAN, goodsBean);
                intent.putExtra(PRODUCT,data.get(position));
                mContext.startActivity(intent);
            });
            tv_more_recommend.setOnClickListener(v -> {
                Intent intent = new Intent(mContext, GoodsListActivity.class);
                intent.putExtra("more_products", new Gson().toJson(data, new TypeToken<List<Product>>() {
                }.getType()));
                mContext.startActivity(intent);
            });

        }
    }

    private boolean isFirst = true;
    private TextView tvTime;
    private int dt;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                dt = dt - 1000;
                @SuppressLint("SimpleDateFormat") SimpleDateFormat sd = new SimpleDateFormat("HH:mm:ss");
                tvTime.setText(sd.format(new Date(dt)));

                handler.removeMessages(0);
                handler.sendEmptyMessageDelayed(0, 1000);
                if (dt == 0) {
                    handler.removeMessages(0);
                }
            }

        }
    };

    class SeckillViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMore;
        private RecyclerView recyclerView;
        public Context mContext;

        public SeckillViewHolder(View itemView, Context mContext) {
            super(itemView);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time_seckill);
            tvMore = (TextView) itemView.findViewById(R.id.tv_more_seckill);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.rv_seckill);
            recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    super.getItemOffsets(outRect, view, parent, state);
                    outRect.right = 20;
                }
            });
            this.mContext = mContext;
        }


        public void setData(final List<Product> data) {
            //设置时间
            if (isFirst) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR, 24);
                calendar.set(Calendar.SECOND, 59);
                calendar.set(Calendar.MINUTE, 59);
                calendar.set(Calendar.MILLISECOND, 59);
                dt = (int) (calendar.getTimeInMillis() - System.currentTimeMillis());
                //     dt = (int) (Integer.parseInt(data.getEnd_time()) - (Integer.parseInt(data.getStart_time())));
                isFirst = false;
            }

            tvMore.setOnClickListener(v -> {
                Intent intent = new Intent(mContext, GoodsListActivity.class);
                intent.putExtra("more_products", new Gson().toJson(data, new TypeToken<List<Product>>() {
                }.getType()));
                mContext.startActivity(intent);
            });

            //设置RecyclerView
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            SeckillRecyclerViewAdapter adapter = new SeckillRecyclerViewAdapter(mContext, data);
            recyclerView.setAdapter(adapter);

            //倒计时
            handler.sendEmptyMessageDelayed(0, 1000);

            //点击事件
            adapter.setOnSeckillRecyclerView(position -> {
                Product listBean = data.get(position);
                String name = listBean.getProductName();
                String cover_price = listBean.getCoverPrice() + "";
                String figure = listBean.getFigure();
                String product_id = listBean.getId() + "";
                String product_detail = data.get(position).getDescription();
                GoodsBean goodsBean = new GoodsBean(name, cover_price, figure, product_id, product_detail);
//
                Intent intent = new Intent(mContext, GoodsInfoActivity.class);
                intent.putExtra(GOODS_BEAN, goodsBean);
                intent.putExtra(PRODUCT,data.get(position));
                mContext.startActivity(intent);

                // Toast.makeText(mContext, "position:" + position, Toast.LENGTH_SHORT).show();
            });

        }
    }

   /* class ActViewHolder extends RecyclerView.ViewHolder {
        public ViewPager actViewPager;
        public Context mContext;

        public ActViewHolder(View itemView, Context mContext) {
            super(itemView);
            actViewPager = (ViewPager) itemView.findViewById(R.id.act_viewpager);
            this.mContext = mContext;
        }

        public void setData(final List<Product> data) {
            actViewPager.setPageMargin(20);
            actViewPager.setOffscreenPageLimit(3);
            actViewPager.setPageTransformer(true, new AlphaPageTransformer(new ScaleInTransformer()));
            actViewPager.setAdapter(new ActAdapter(mContext,homeBean.getAct_info()));



            //点击事件
            actViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    Toast.makeText(mContext, "position:" + position, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }
    }*/

    class ChannelViewHolder extends RecyclerView.ViewHolder {
        public GridView gvChannel;
        public Context mContext;

        public ChannelViewHolder(View itemView, Context mContext) {
            super(itemView);
            gvChannel = (GridView) itemView.findViewById(R.id.gv_channel);
            this.mContext = mContext;
        }

        public void setData(final List<Category> channel_info) {
            gvChannel.setAdapter(new ChannelAdapter(mContext, channel_info));

            //点击事件
            gvChannel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (position <= 4) {
                        Intent intent = new Intent(mContext, GoodsListActivity.class);
                        intent.putExtra("position", position);
                        mContext.startActivity(intent);
                    } else {

                    }
                }
            });
        }

    }

    class BannerViewHolder extends RecyclerView.ViewHolder {
        public Banner banner;
        public Context mContext;
        public HomeBean homeBean;

        public BannerViewHolder(View itemView, Context context, HomeBean homeBean) {
            super(itemView);
            banner = (Banner) itemView.findViewById(R.id.banner);
            this.mContext = context;
            this.homeBean = homeBean;
        }

        public void setData(final int[] banner_info) {

            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
            //如果你想用自己项目的图片加载,那么----->自定义图片加载框架
            List<Integer> imageUris = new ArrayList<>();
            for (int i = 0; i < imgResourseBanner.length; i++) {
                imageUris.add(imgResourseBanner[i]);
            }
            banner.setBannerAnimation(Transformer.CubeIn);
            banner.setImages(imageUris, new OnLoadImageListener() {
                @Override
                public void OnLoadImage(ImageView view, Object url) {
                    view.setImageResource((int) url);
                }
            });

         /*   banner.setImages(imageUris, new OnLoadImageListener() {
                @Override
                public void OnLoadImage(ImageView view, Object url) {
                    *//**
             * 这里你可以根据框架灵活设置
             *//*
                    Glide.with(mContext)
                            .load(Constants.BASE_URl_IMAGE + url)
                            .into(view);
                }
            });*/
        /*    //设置点击事件
            banner.setOnBannerClickListener(new OnBannerClickListener() {
                @Override
                public void OnBannerClick(int position) {
                    if(position - 1 < imageUris.size()){
                        int option = imageUris.get(position - 1).getOption();
                        String product_id = "";
                        String name = "";
                        String cover_price = "";
                        if (position - 1 == 0) {
                            product_id = "627";
                            cover_price = "32.00";
                            name = "剑三T恤批发";
                        } else if (position - 1 == 1) {
                            product_id = "21";
                            cover_price = "8.00";
                            name = "同人原创】剑网3 剑侠情缘叁 Q版成男 口袋胸针";
                        } else {
                            product_id = "1341";
                            cover_price = "50.00";
                            name = "【蓝诺】《天下吾双》 剑网3同人本";
                        }
                        String image = banner_info.get(position - 1).getImage();
                        GoodsBean goodsBean = new GoodsBean(name, cover_price, image, product_id);

                        Intent intent = new Intent(mContext, GoodsInfoActivity.class);
                        intent.putExtra("goods_bean", goodsBean);
                        mContext.startActivity(intent);
                    }

                }
            });*/

        }
    }

}
