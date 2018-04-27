package com.manle.saitamall.home.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.JsonToken;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.google.gson.reflect.TypeToken;
import com.lljjcoder.Constant;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.manle.saitamall.R;
import com.manle.saitamall.app.GoodsInfoActivity;
import com.manle.saitamall.bean.Product;
import com.manle.saitamall.community.bean.ArticalVO;
import com.manle.saitamall.home.adapter.ExpandableListViewAdapter;
import com.manle.saitamall.home.adapter.GoodsListAdapter;
import com.manle.saitamall.home.bean.GoodsBean;
import com.manle.saitamall.home.bean.TypeListBean;
import com.manle.saitamall.home.uitls.SpaceItemDecoration;
import com.manle.saitamall.utils.Constants;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Request;

import static com.zhy.http.okhttp.log.LoggerInterceptor.TAG;

// 商品列表页面
public class GoodsListActivity extends Activity implements View.OnClickListener {

    @Bind(R.id.ib_goods_list_back)
    ImageButton ibGoodsListBack;
    @Bind(R.id.tv_goods_list_search)
    EditText tvGoodsListSearch;
    @Bind(R.id.ib_goods_list_home)
    ImageButton ibGoodsListHome;
    @Bind(R.id.tv_goods_list_sort)
    TextView tvGoodsListSort;
    @Bind(R.id.ll_goods_list_price)
    LinearLayout llGoodsListPrice;
    @Bind(R.id.tv_goods_list_price)
    TextView tvGoodsListPrice;
    @Bind(R.id.iv_goods_list_arrow)
    ImageView ivGoodsListArrow;
    @Bind(R.id.tv_goods_list_select)
    TextView tvGoodsListSelect;
    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    @Bind(R.id.expandableListView)
    ExpandableListView listView;


    @Bind(R.id.ll_select_root)
    LinearLayout ll_select_root;
    @Bind(R.id.ll_price_root)
    LinearLayout ll_price_root;
    @Bind(R.id.ll_theme_root)
    LinearLayout ll_theme_root;
    @Bind(R.id.ll_type_root)
    LinearLayout ll_type_root;
    @Bind(R.id.ib_drawer_layout_back)
    Button ib_drawer_layout_back;
    @Bind(R.id.btn_drawer_layout_cancel)
    Button btn_drawer_layout_cancel;
    @Bind(R.id.btn_drawer_type_confirm)
    Button btn_drawer_type_confirm;
    @Bind(R.id.btn_drawer_type_cancel)
    Button btn_drawer_type_cancel;
    @Bind(R.id.btn_drawer_theme_confirm)
    Button btn_drawer_theme_confirm;
    @Bind(R.id.btn_drawer_theme_cancel)
    Button btn_drawer_theme_cancel;
    @Bind(R.id.rl_select_price)
    RelativeLayout rl_select_price;
    @Bind(R.id.rl_select_recommend_theme)
    RelativeLayout rl_select_recommend_theme;
    @Bind(R.id.rl_select_type)
    RelativeLayout rl_select_type;
    @Bind(R.id.rl_price_0_5)
    RelativeLayout rl_price_0_5;
    @Bind(R.id.rl_price_5_10)
    RelativeLayout rl_price_5_10;
    @Bind(R.id.rl_price_10_20)
    RelativeLayout rl_price_10_20;
    @Bind(R.id.rl_price_20)
    RelativeLayout rl_price_20;
    @Bind(R.id.rl_denify_price)
    RelativeLayout rl_denify_price;
    @Bind(R.id.et_price_start)
    EditText et_price_start;
    @Bind(R.id.et_price_end)
    EditText et_price_end;
    @Bind(R.id.tv_drawer_price)
    TextView tv_drawer_price;
    @Bind(R.id.rl_theme_note)
    RelativeLayout rl_theme_note;
    @Bind(R.id.dl_left)
    DrawerLayout dl_left;

    private Long categoryId;
    private int click_count = 0;

    /*    private static final int DEFAULE_STATE = 1;
        private static final int ASC_STATE = 2;
        private static final int DESC_STATE = 3;*/
    private int childP;
    private int groupP;


    private ArrayList<String> group;
    private ArrayList<List<String>> child;
    private ExpandableListViewAdapter adapter;

    private List<Product> page_data;
    private GoodsListAdapter adapter1;

    private void findViews() {

        ibGoodsListBack.setOnClickListener(this);
        ibGoodsListHome.setOnClickListener(this);

        llGoodsListPrice.setOnClickListener(this);
        tvGoodsListSort.setOnClickListener(this);
        tvGoodsListSelect.setOnClickListener(this);
        ib_drawer_layout_back.setOnClickListener(this);

        rl_select_price.setOnClickListener(this);
        rl_select_recommend_theme.setOnClickListener(this);
        rl_select_type.setOnClickListener(this);

        btn_drawer_layout_cancel.setOnClickListener(this);
        btn_drawer_type_confirm.setOnClickListener(this);
        btn_drawer_type_cancel.setOnClickListener(this);
        btn_drawer_theme_confirm.setOnClickListener(this);
        btn_drawer_theme_cancel.setOnClickListener(this);

        rl_theme_note.setOnClickListener(this);
        tvGoodsListSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter1.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }


    @Override
    public void onClick(View v) {

        if (v == ibGoodsListBack) {
            finish();
        } else if (v == ibGoodsListHome) {
//            Intent intent = new Intent(this, MainActivity.class);
//            startActivity(intent);
            Constants.isBackHome = true;
            finish();
        } else if (v == tvGoodsListSearch) {
            Toast.makeText(GoodsListActivity.this, "搜索", Toast.LENGTH_SHORT).show();
        } else if (v == llGoodsListPrice) {
            //价格点击事件
            click_count++;
            //综合排序变为默认
            tvGoodsListSort.setTextColor(Color.parseColor("#333538"));
            //价格红
            tvGoodsListPrice.setTextColor(Color.parseColor("#ed4141"));
            if (click_count % 2 == 1) {
                // 箭头向下红
                ivGoodsListArrow.setBackgroundResource(R.drawable.new_price_sort_desc);
                Collections.sort(page_data, (lhs, rhs) -> rhs.getCoverPrice().compareTo(lhs.getCoverPrice()));
                adapter1.notifyDataSetChanged();
            } else {
                // 箭头向上红
                ivGoodsListArrow.setBackgroundResource(R.drawable.new_price_sort_asc);
                Collections.sort(page_data, (lhs, rhs) -> lhs.getCoverPrice().compareTo(rhs.getCoverPrice()));
                adapter1.notifyDataSetChanged();
            }
        } else if (v == tvGoodsListSort) {
            //综合排序点击事件
            click_count = 0;
            ivGoodsListArrow.setBackgroundResource(R.drawable.new_price_sort_normal);
            tvGoodsListPrice.setTextColor(Color.parseColor("#333538"));
            tvGoodsListSort.setTextColor(Color.parseColor("#ed4141"));
        } else if (v == tvGoodsListSelect) {
            //筛选的点击事件
            tvGoodsListSelect.setTextColor(Color.parseColor("#ed4141"));
            dl_left.openDrawer(Gravity.RIGHT);

        } else if (v == rl_select_price) {
            //价格筛选的页面
            ll_price_root.setVisibility(View.VISIBLE);
            ib_drawer_layout_back.setVisibility(View.GONE);

            showPriceLayout();
        } else if (v == rl_select_recommend_theme) {
            ll_theme_root.setVisibility(View.VISIBLE);
            ib_drawer_layout_back.setVisibility(View.GONE);

            showThemeLayout();
        } else if (v == rl_select_type) {
            ll_type_root.setVisibility(View.VISIBLE);
            ib_drawer_layout_back.setVisibility(View.GONE);

            showTypeLayout();
        } else if (v == ib_drawer_layout_back) {
            dl_left.closeDrawers();
        } else if (v == btn_drawer_layout_cancel) {
            Toast.makeText(GoodsListActivity.this, "取消", Toast.LENGTH_SHORT).show();


            showSelectorLayout();
        } /*else if (v == btn_drawer_layout_confirm) {
            Toast.makeText(GoodsListActivity.this, "确认", Toast.LENGTH_SHORT).show();
        }*/ else if (v == rl_price_0_5) {
            tv_drawer_price.setText("0-5");
            showSelectorLayout();
        } else if (v == rl_price_5_10) {
            tv_drawer_price.setText("5-10");
            showSelectorLayout();
        } else if (v == rl_price_10_20) {
            tv_drawer_price.setText("10-20");
            showSelectorLayout();
        } else if (v == rl_price_20) {
            tv_drawer_price.setText("20+");
            showSelectorLayout();
        }else if (v == rl_denify_price) {
            tv_drawer_price.setText(et_price_start.getText().toString()+"-"+et_price_end.getText().toString());
            showSelectorLayout();
        }  else if (v == rl_theme_note) {
            Toast.makeText(GoodsListActivity.this, "123123123", Toast.LENGTH_SHORT).show();
        } else if (v == btn_drawer_type_confirm) {
            Toast.makeText(GoodsListActivity.this, "确认", Toast.LENGTH_SHORT).show();
        } else if (v == btn_drawer_type_cancel) {
            Toast.makeText(GoodsListActivity.this, "取消", Toast.LENGTH_SHORT).show();

            showSelectorLayout();
        } else if (v == btn_drawer_theme_confirm) {
            Toast.makeText(GoodsListActivity.this, "确认", Toast.LENGTH_SHORT).show();
        } else if (v == btn_drawer_theme_cancel) {

            showSelectorLayout();
        }


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_list);
        ButterKnife.bind(this);
        findViews();


        Intent intent = getIntent();
        categoryId = intent.getLongExtra("categoryId", -1l);
        if (categoryId != -1) {
            getDataFromNet();
        }
        String more_products = intent.getStringExtra("more_products");
        if (more_products != null) {
            processData(more_products);
        }

        showSelectorLayout();

        initListener();
    }

    private void initListener() {
        listView.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {
            Toast.makeText(GoodsListActivity.this, "childPosition" + childPosition, Toast.LENGTH_SHORT).show();
            childP = childPosition;
            adapter.notifyDataSetChanged();
            return false;
        });

        listView.setOnGroupClickListener((parent, v, groupPosition, id) -> {
            Toast.makeText(GoodsListActivity.this, "groupPosition" + groupPosition, Toast.LENGTH_SHORT).show();
            groupP = groupPosition;
            adapter.notifyDataSetChanged();
            return false;
        });

        listView.setOnItemClickListener((parent, view, position, id) -> Toast.makeText(GoodsListActivity.this, "position---" + position, Toast.LENGTH_SHORT).show());
    }


    //筛选页面
    private void showSelectorLayout() {
        ll_select_root.setVisibility(View.VISIBLE);
        ib_drawer_layout_back.setVisibility(View.VISIBLE);
        ll_price_root.setVisibility(View.GONE);
        ll_theme_root.setVisibility(View.GONE);
        ll_type_root.setVisibility(View.GONE);
    }

    //价格页面
    private void showPriceLayout() {
        ll_select_root.setVisibility(View.GONE);
        ll_theme_root.setVisibility(View.GONE);
        ll_type_root.setVisibility(View.GONE);
    }

    //主题页面
    private void showThemeLayout() {
        ll_select_root.setVisibility(View.GONE);
        ll_price_root.setVisibility(View.GONE);
        ll_type_root.setVisibility(View.GONE);
    }

    //类别页面
    private void showTypeLayout() {
        ll_select_root.setVisibility(View.GONE);
        ll_price_root.setVisibility(View.GONE);
        ll_theme_root.setVisibility(View.GONE);

        //初始化ExpandableListView
        initExpandableListView();
        adapter = new ExpandableListViewAdapter(this, group, child);
        listView.setAdapter(adapter);
    }

    private void initExpandableListView() {
        group = new ArrayList<>();
        child = new ArrayList<>();
        //去掉默认箭头
        listView.setGroupIndicator(null);
        addInfo("全部", new String[]{});
        addInfo("品牌", new String[]{"巧妈妈", "乐事", "娃哈哈", "卡文哈夫"});
        addInfo("产地", new String[]{"日本", "韩国", "德国", "中国", "意大利"});


        listView.setOnGroupClickListener((parent, v, groupPosition, id) -> {
            if (child.get(groupPosition).isEmpty()) {// isEmpty没有
                return true;
            } else {
                return false;
            }
        });
        listView.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {
            if (groupPosition == 1) {
                page_data = Stream.of(page_data).filter(s -> s.getBrand().equals(child.get(1).get(childPosition))).collect(Collectors.toList());
                adapter1 = new GoodsListAdapter(GoodsListActivity.this, page_data);
                recyclerview.setAdapter(adapter1);
            } else if (groupPosition == 2) {
                page_data = Stream.of(page_data).filter(s -> s.getSupply().equals(child.get(2).get(childPosition))).collect(Collectors.toList());
                adapter1 = new GoodsListAdapter(GoodsListActivity.this, page_data);
                recyclerview.setAdapter(adapter1);
            }
            return true;
        });
    }


    /**
     * 添加数据信息
     *
     * @param g
     * @param c
     */
    private void addInfo(String g, String[] c) {
        group.add(g);
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < c.length; i++) {
            list.add(c[i]);
        }
        child.add(list);
    }


    public void getDataFromNet() {
        OkHttpUtils
                .get()
                .url(Constants.PRODUCT_CATRGORY)
                .addParams("categoryId", categoryId + "")
                .id(100)
                .build()
                .execute(new MyStringCallback());
    }

    public class MyStringCallback extends StringCallback {


        @Override
        public void onBefore(Request request, int id) {
        }

        @Override
        public void onAfter(int id) {
        }

        @Override
        public void onError(Call call, Exception e, int id) {
            Log.e("TAG", "联网失败" + e.getMessage());
        }

        @Override
        public void onResponse(String response, int id) {

            switch (id) {
                case 100:
//                    Toast.makeText(GoodsListActivity.this, "http", Toast.LENGTH_SHORT).show();
                    if (response != null) {
                        processData(response);

                    }
                    break;
                case 101:
                    Toast.makeText(GoodsListActivity.this, "https", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

    }

    private void processData(String response) {
        Gson gson = new Gson();
        page_data = gson.fromJson(response, new TypeToken<List<Product>>() {
        }.getType());
        if (page_data != null) {
            GridLayoutManager manager = new GridLayoutManager(GoodsListActivity.this, 2);
            recyclerview.setLayoutManager(manager);
            adapter1 = new GoodsListAdapter(GoodsListActivity.this, page_data);
//                        recyclerview.addItemDecoration(new DividerItemDecoration(GoodsListActivity.this, manager.getOrientation()));
            recyclerview.addItemDecoration(new SpaceItemDecoration(10));
            recyclerview.setAdapter(adapter1);

            adapter1.setOnItemClickListener(data -> {
                String name = data.getProductName();
                String cover_price = data.getCoverPrice().toString();
                String figure = data.getFigure();
                String product_id = data.getId() + "";
                String product_detail = data.getDescription();
                GoodsBean goodsBean = new GoodsBean(name, cover_price, figure, product_id, product_detail);
                Intent intent = new Intent(GoodsListActivity.this, GoodsInfoActivity.class);
                intent.putExtra("goods_bean", goodsBean);
                startActivity(intent);
            });
        }
    }

}
