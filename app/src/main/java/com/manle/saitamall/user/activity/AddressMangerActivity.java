package com.manle.saitamall.user.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.lljjcoder.style.citypickerview.CityPickerView;
import com.manle.saitamall.R;
import com.manle.saitamall.bean.Address;
import com.manle.saitamall.bean.User;
import com.manle.saitamall.utils.CacheUtils;
import com.manle.saitamall.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Request;

import static android.content.ContentValues.TAG;


/**
 * Created by Administrator on 2018/4/5.
 */

public class AddressMangerActivity extends Activity implements View.OnClickListener {
    @Bind(R.id.btn_choose)
    RelativeLayout btnChoose;
    @Bind(R.id.et_address_consignee)
    EditText etAddressConsignee;
    @Bind(R.id.et_address_phone)
    EditText etAddressPhone;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.et_address_detail)
    EditText etAddressDetail;
    @Bind(R.id.ib_address_back)
    ImageButton ibAddressBack;
    @Bind(R.id.tv_address_edit)
    TextView tvAddressEdit;
    CityPickerView mPicker = new CityPickerView();
    Address address;
    User user;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_manager);
        ButterKnife.bind(this);

        user = new Gson().fromJson(CacheUtils.getString(AddressMangerActivity.this, "user"), User.class);
        tvAddressEdit.setOnClickListener(this);
        btnChoose.setOnClickListener(this);
        ibAddressBack.setOnClickListener(this);
        tvAddressEdit.setText("保存");
        address = new Gson().fromJson(CacheUtils.getString(AddressMangerActivity.this, "address"), Address.class);

        mPicker.init(this);
        if (address!=null&&address.getClientUserId()==user.getId() || getAddress() != null) {
            initData();
        }


    }

    private void initData() {
        etAddressPhone.setText(address.getPhone());
        etAddressConsignee.setText(address.getConsignee());
        String addressDetail = address.getAddress().split("-")[1];
        String addressCity = address.getAddress().split("-")[0];
        etAddressDetail.setText(addressDetail);
        tvAddress.setText(addressCity);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_choose:
                if (tvAddressEdit.getText().toString().equals("保存")) {
                    showCityPicker();
                } else {

                }
                break;
            case R.id.tv_address_edit:
                if (tvAddressEdit.getText().toString().equals("保存")) {
                    Address address1 = new Address();
                    if (address == null) {
                        address = new Address();
                    }
                    String consignee = etAddressConsignee.getText().toString();
                    String addressCity = tvAddress.getText().toString();
                    String addressDetail = etAddressDetail.getText().toString();
                    String phone = etAddressPhone.getText().toString();
                    if (consignee == null || addressDetail == null || addressCity == null || phone == null) {
                        ToastUtils.showShortToast(AddressMangerActivity.this, "信息不完整");
                    } else {
                        address1.setConsignee(consignee);
                        address1.setAddress(addressCity + "-" + addressDetail);
                        address1.setPhone(phone);
                        address1.setClientUserId(user.getId());
                        if (!address1.equals(address)) {
                            saveAddress(address1);
                        }

                    }

                } else {
                    tvAddressEdit.setText("保存");
                    etAddressDetail.setEnabled(true);
                    etAddressConsignee.setEnabled(true);
                    etAddressPhone.setEnabled(true);
                }
                break;
            case R.id.ib_address_back:
                finish();
                break;

        }
    }

    private void saveAddress(Address address1) {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        String addressJson = new Gson().toJson(address1);
        OkHttpUtils.postString().mediaType(JSON).content(addressJson).url(Constants.ADDRESS).build().execute(new StringCallback() {
            @Override
            public void onBefore(Request request, int id) {
                progressDialog = new ProgressDialog(AddressMangerActivity.this, ProgressDialog.STYLE_SPINNER);
                progressDialog.setMessage("保存...");
                progressDialog.show();
            }

            @Override
            public void onAfter(int id) {
                progressDialog.dismiss();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showShortToast(AddressMangerActivity.this, e.getMessage());
            }

            @Override
            public void onResponse(String response, int id) {
                if (response != null) {
                    processData(response);
                    tvAddressEdit.setText("编辑");
                    etAddressDetail.setEnabled(false);
                    etAddressConsignee.setEnabled(false);
                    etAddressPhone.setEnabled(false);
                }
            }
        });
    }

    private Address processData(String response) {
        CacheUtils.putString(AddressMangerActivity.this, "address", response);
        address = new Gson().fromJson(response, Address.class);
        ToastUtils.showShortToast(AddressMangerActivity.this,"保存成功");
        return address;
    }

    private void showCityPicker() {
        CityConfig cityConfig = new CityConfig.Builder().confirTextColor("#ED3F3F").build();
        mPicker.setConfig(cityConfig);

        mPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {


                //省份
                if (province != null) {

                }

                //城市
                if (city != null) {

                }

                //地区
                if (district != null) {

                }
                ToastUtils.showShortToast(AddressMangerActivity.this, province.getName() + " " + city.getName()
                        + " " + district.getName());
                tvAddress.setText(province.getName() + " " + city.getName()
                        + " " + district.getName());

            }

            @Override
            public void onCancel() {
                ToastUtils.showLongToast(AddressMangerActivity.this, "已取消");
            }
        });
        //显示
        mPicker.showCityPicker();
    }

    public Address getAddress() {
        OkHttpUtils.get().url(Constants.ADDRESS + "/user/" + user.getId()).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showShortToast(AddressMangerActivity.this,e.getMessage());
            }

            @Override
            public void onResponse(String response, int id) {
                if (response != null) {
                   address =  processData(response);
                }
            }
        });
        return address;
    }
}
