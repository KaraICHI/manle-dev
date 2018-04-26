package com.manle.saitamall.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.manle.saitamall.R;
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

public class RegisterActivity extends Activity implements View.OnClickListener{
    @Bind(R.id.et_register_name)
    EditText etRegisterName;
    @Bind(R.id.et_register_pwd)
    EditText etRegisterPwd;
    @Bind(R.id.et_register_pwd_re)
    EditText etRegisterPwdRe;
    @Bind(R.id.et_register_phone)
    EditText etRegisterPhone;
    @Bind(R.id.btn_register)
    Button btnRegister;
    @Bind(R.id.tv_alert_error)
    TextView tvAlertError;
    @Bind(R.id.ib_register_back)
    ImageButton ibRegisterBack;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        btnRegister.setOnClickListener(this);
        ibRegisterBack.setOnClickListener(this);
        etRegisterPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    isRepeat();
                }
            }
        });
    }

    private void isRepeat() {
        if (!etRegisterPwdRe.getText().toString().equals(etRegisterPwd.getText().toString())){
            tvAlertError.setVisibility(View.VISIBLE);
            tvAlertError.setText("与输入密码不一致");
        }else {
            tvAlertError.setVisibility(View.INVISIBLE);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_register:
                register();
                break;
            case R.id.ib_register_back:
                finish();
                break;
        }

    }

    private void register() {
       User user = new User(etRegisterName.getText().toString(),etRegisterPhone.getText().toString(),etRegisterPwd.getText().toString(),null,0f);
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        String userJson = new Gson().toJson(user);
       OkHttpUtils.postString().tag(this).url(Constants.CLIENT_USER).mediaType(JSON).content(userJson).build().execute(new StringCallback() {
           @Override
           public void onBefore(Request request, int id) {
               super.onBefore(request, id);
               progressDialog = new ProgressDialog(RegisterActivity.this, ProgressDialog.STYLE_SPINNER);
               progressDialog.setMessage("注册中...");
               progressDialog.show();
           }

           @Override
           public void onAfter(int id) {
               super.onAfter(id);
               progressDialog.dismiss();
           }

           @Override
           public void onError(Call call, Exception e, int id) {
               Log.d(TAG, "onError: =============="+e.getMessage());
               ToastUtils.showShortToast(RegisterActivity.this,e.getMessage());
           }

           @Override
           public void onResponse(String response, int id) {
               Log.d(TAG, "onResponse:===============register "+response);
               if (response != null) {
                   Gson gson = new Gson();
                   User user = gson.fromJson(response, User.class);
                   if (user!=null){
                       ToastUtils.showShortToast(RegisterActivity.this,"注册成功");
                       finish();
                   }else {
                       ToastUtils.showShortToast(RegisterActivity.this,"注册失败");
                   }

               } else {
                   ToastUtils.showShortToast(RegisterActivity.this,"注册失败");
               }

           }
       });

    }

}
