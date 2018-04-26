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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
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

import okhttp3.Call;
import okhttp3.Request;

import static com.zhy.http.okhttp.log.LoggerInterceptor.TAG;

// 商品列表页面
public class GoodsListActivity extends Activity implements View.OnClickListener {

    private ImageButton ibGoodsListBack;
    private EditText tvGoodsListSearch;
    private ImageButton ibGoodsListHome;
    private TextView tvGoodsListSort;
    private LinearLayout llGoodsListPrice;
    private TextView tvGoodsListPrice;
    private ImageView ivGoodsListArrow;
    private TextView tvGoodsListSelect;
    private RecyclerView recyclerview;
    private ExpandableListView listView;

    /*    private static final int DEFAULE_STATE = 1;
        private static final int ASC_STATE = 2;
        private static final int DESC_STATE = 3;*/
    private int childP;
    private int groupP;

    private int click_count = 0;
    private ArrayList<String> group;
    private ArrayList<List<String>> child;
    private ExpandableListViewAdapter adapter;

    private LinearLayout ll_select_root;
    private LinearLayout ll_price_root;
    private LinearLayout ll_theme_root;
    private LinearLayout ll_type_root;

    private Button ib_drawer_layout_back;
    private Button btn_drawer_layout_confirm;
    private Button btn_drawer_layout_cancel;

    private Button btn_drawer_type_confirm;
    private Button btn_drawer_type_cancel;

    private Button btn_drawer_theme_confirm;
    private Button btn_drawer_theme_cancel;

    private RelativeLayout rl_select_price;
    private RelativeLayout rl_select_recommend_theme;
    private RelativeLayout rl_select_type;
    private RelativeLayout rl_price_30_50;
    private RelativeLayout rl_theme_note;
    private int position;
    private String[] urls = new String[]{
            Constants.GUODONG_STORE,
            Constants.GUODONG_STORE,
            Constants.GUODONG_STORE,
            Constants.GUODONG_STORE,
            Constants.GUODONG_STORE,
            Constants.GUODONG_STORE,
            Constants.GUODONG_STORE,
            Constants.GUODONG_STORE,
            Constants.GUODONG_STORE,
            Constants.GUODONG_STORE,
            Constants.GUODONG_STORE,

    };
    private List<Product> page_data;
    private GoodsListAdapter adapter1;
    private DrawerLayout dl_left;

    private void findViews() {
        LinearLayout llGoodsListHead = (LinearLayout) findViewById(R.id.ll_goods_list_head);
        ibGoodsListBack = (ImageButton) findViewById(R.id.ib_goods_list_back);
        tvGoodsListSearch = (EditText) findViewById(R.id.tv_goods_list_search);
        ibGoodsListHome = (ImageButton) findViewById(R.id.ib_goods_list_home);
        tvGoodsListSort = (TextView) findViewById(R.id.tv_goods_list_sort);
        llGoodsListPrice = (LinearLayout) findViewById(R.id.ll_goods_list_price);
        tvGoodsListPrice = (TextView) findViewById(R.id.tv_goods_list_price);
        ivGoodsListArrow = (ImageView) findViewById(R.id.iv_goods_list_arrow);
        tvGoodsListSelect = (TextView) findViewById(R.id.tv_goods_list_select);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);

        ll_select_root = (LinearLayout) findViewById(R.id.ll_select_root);
        ll_price_root = (LinearLayout) findViewById(R.id.ll_price_root);
        ll_theme_root = (LinearLayout) findViewById(R.id.ll_theme_root);
        ll_type_root = (LinearLayout) findViewById(R.id.ll_type_root);

        ib_drawer_layout_back = (Button) findViewById(R.id.ib_drawer_layout_back);
        btn_drawer_layout_confirm = (Button) findViewById(R.id.btn_drawer_layout_confirm);
        btn_drawer_layout_cancel = (Button) findViewById(R.id.btn_drawer_layout_cancel);
        btn_drawer_type_confirm = (Button) findViewById(R.id.btn_drawer_type_confirm);
        btn_drawer_type_cancel = (Button) findViewById(R.id.btn_drawer_type_cancel);
        btn_drawer_theme_confirm = (Button) findViewById(R.id.btn_drawer_theme_confirm);
        btn_drawer_theme_cancel = (Button) findViewById(R.id.btn_drawer_theme_cancel);

        rl_select_price = (RelativeLayout) findViewById(R.id.rl_select_price);
        rl_select_recommend_theme = (RelativeLayout) findViewById(R.id.rl_select_recommend_theme);
        rl_select_type = (RelativeLayout) findViewById(R.id.rl_select_type);
        rl_price_30_50 = (RelativeLayout) findViewById(R.id.rl_price_30_50);
        rl_theme_note = (RelativeLayout) findViewById(R.id.rl_theme_note);
        dl_left = (DrawerLayout) findViewById(R.id.dl_left);

        ibGoodsListBack.setOnClickListener(this);
        ibGoodsListHome.setOnClickListener(this);

        llGoodsListPrice.setOnClickListener(this);
        tvGoodsListSort.setOnClickListener(this);
        tvGoodsListSelect.setOnClickListener(this);
        ib_drawer_layout_back.setOnClickListener(this);

        rl_select_price.setOnClickListener(this);
        rl_select_recommend_theme.setOnClickListener(this);
        rl_select_type.setOnClickListener(this);

        btn_drawer_layout_confirm.setOnClickListener(this);
        btn_drawer_layout_cancel.setOnClickListener(this);
        btn_drawer_type_confirm.setOnClickListener(this);
        btn_drawer_type_cancel.setOnClickListener(this);
        btn_drawer_theme_confirm.setOnClickListener(this);
        btn_drawer_theme_cancel.setOnClickListener(this);

        rl_price_30_50.setOnClickListener(this);
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

        listView = (ExpandableListView) findViewById(R.id.expandableListView);

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
                Collections.sort(page_data, (lhs, rhs) ->  lhs.getCoverPrice().compareTo(rhs.getCoverPrice()));
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

            ll_select_root.setVisibility(View.VISIBLE);
            ib_drawer_layout_back.setVisibility(View.VISIBLE);
            showSelectorLayout();
        } else if (v == btn_drawer_layout_confirm) {
            Toast.makeText(GoodsListActivity.this, "确认", Toast.LENGTH_SHORT).show();
        } else if (v == rl_price_30_50) {
            Toast.makeText(GoodsListActivity.this, "123123123", Toast.LENGTH_SHORT).show();
        } else if (v == rl_theme_note) {
            Toast.makeText(GoodsListActivity.this, "123123123", Toast.LENGTH_SHORT).show();
        } else if (v == btn_drawer_type_confirm) {
            Toast.makeText(GoodsListActivity.this, "确认", Toast.LENGTH_SHORT).show();
        } else if (v == btn_drawer_type_cancel) {
            Toast.makeText(GoodsListActivity.this, "取消", Toast.LENGTH_SHORT).show();
            ll_select_root.setVisibility(View.VISIBLE);
            ib_drawer_layout_back.setVisibility(View.VISIBLE);
            showSelectorLayout();
        } else if (v == btn_drawer_theme_confirm) {
            Toast.makeText(GoodsListActivity.this, "确认", Toast.LENGTH_SHORT).show();
        } else if (v == btn_drawer_theme_cancel) {
            ll_select_root.setVisibility(View.VISIBLE);
            ib_drawer_layout_back.setVisibility(View.VISIBLE);
            showSelectorLayout();
        }


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_list);
        findViews();

        Intent intent = getIntent();
        position = intent.getIntExtra("position", -1);
        if (position!=-1){
            getDataFromNet();
        }
        String more_products = intent.getStringExtra("more_products");
        if (more_products!=null){
            processData(more_products);
        }
        ll_select_root.setVisibility(View.VISIBLE);
        ib_drawer_layout_back.setVisibility(View.VISIBLE);
        showSelectorLayout();

        initListener();
    }

    private void initListener() {
       /* listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(GoodsListActivity.this, "childPosition" + childPosition, Toast.LENGTH_SHORT).show();
                childP = childPosition;
                adapter.notifyDataSetChanged();
                return false;
            }
        });

        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Toast.makeText(GoodsListActivity.this, "groupPosition" + groupPosition, Toast.LENGTH_SHORT).show();
                groupP = groupPosition;
                adapter.notifyDataSetChanged();
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(GoodsListActivity.this, "position---" + position, Toast.LENGTH_SHORT).show();
            }
        });*/
    }


    //筛选页面
    private void showSelectorLayout() {
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
        addInfo("品牌", new String[]{"德芙", "瑞士莲", "费列罗", "多利是"});
        addInfo("产地", new String[]{"日本", "韩国", "美国", "中国", "意大利"});


        // 这里是控制如果列表没有孩子菜单不展开的效果
        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent,
                                        View v, int groupPosition, long id) {
                if (child.get(groupPosition).isEmpty()) {// isEmpty没有
                    return true;
                } else {
                    return false;
                }
            }
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
        if("theme".equals(getIntent().getStringExtra("type"))){
            Log.e(TAG, "getDataFromNet: type--------------theme"+position);
        }
        OkHttpUtils
                .get()
                .url(urls[position])
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
        page_data = gson.fromJson(response,new TypeToken<List<Product>>() {
        }.getType());
        if (page_data!=null){
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
                String product_id = data.getId()+"";
                String product_detail = data.getDescription();
                GoodsBean goodsBean = new GoodsBean(name, cover_price, figure, product_id, product_detail);
                Intent intent = new Intent(GoodsListActivity.this, GoodsInfoActivity.class);
                intent.putExtra("goods_bean", goodsBean);
                startActivity(intent);
            });
        }
    }

}
