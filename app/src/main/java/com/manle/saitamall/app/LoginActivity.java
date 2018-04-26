package com.manle.saitamall.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.manle.saitamall.R;
import com.manle.saitamall.bean.User;
import com.manle.saitamall.utils.CacheUtils;
import com.manle.saitamall.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.joda.time.LocalDate;

import okhttp3.Call;
import okhttp3.Request;

import static android.content.ContentValues.TAG;

// 登录页面
public class LoginActivity extends Activity implements View.OnClickListener {
    private ImageButton ibLoginBack;
    private EditText etLoginPhone;
    private EditText etLoginPwd;
    private ImageButton ibLoginVisible;
    private Button btnLogin;
    private ImageButton ib_weibo;
    private ImageButton ib_qq;
    private ImageButton ib_wechat;
    private TextView tv_login_register;
    private ProgressDialog progressDialog;

    private int count;

    // 初始化view
    private void findViews() {
        ibLoginBack = (ImageButton) findViewById(R.id.ib_login_back);
        etLoginPwd = (EditText) findViewById(R.id.et_login_pwd);
        etLoginPhone = (EditText) findViewById(R.id.et_login_phone);
        ibLoginVisible = (ImageButton) findViewById(R.id.ib_login_visible);
        btnLogin = (Button) findViewById(R.id.btn_login);
        ib_weibo = (ImageButton) findViewById(R.id.ib_weibo);
        ib_qq = (ImageButton) findViewById(R.id.ib_qq);
        ib_wechat = (ImageButton) findViewById(R.id.ib_wechat);
        tv_login_register = (TextView) findViewById(R.id.tv_login_register);

        ibLoginBack.setOnClickListener(this);
        ibLoginVisible.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        ib_weibo.setOnClickListener(this);
        ib_qq.setOnClickListener(this);
        ib_wechat.setOnClickListener(this);
        tv_login_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v == ibLoginBack) {
            finish();
        } else if (v == ibLoginVisible) {

            count++;
            if (count % 2 == 0) {
                ibLoginVisible.setBackgroundResource(R.drawable.new_password_drawable_invisible);
                etLoginPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            } else {
                ibLoginVisible.setBackgroundResource(R.drawable.new_password_drawable_visible);
                etLoginPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            }
        } else if (v == tv_login_register) {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        } else if (v == btnLogin) {

            validateUser();
        }
    }

    private void validateUser() {
        String phone = etLoginPhone.getText().toString();
        String password = etLoginPwd.getText().toString();
        OkHttpUtils.get().addParams("phone", phone)
                .addParams("password", password)
                .url(Constants.CLIENT_USER + "/login").build().execute(new StringCallback() {
            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                progressDialog = new ProgressDialog(LoginActivity.this, ProgressDialog.STYLE_SPINNER);
                progressDialog.setMessage("登录中...");
                progressDialog.show();
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                progressDialog.dismiss();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                Log.e("TAG", "联网失败" + e.getMessage());
            }

            @Override
            public void onResponse(String response, int id) {
                if (response != null) {
                    Gson gson = new Gson();
                    User user = gson.fromJson(response, User.class);
                    if (user!=null){
                        CacheUtils.putString(LoginActivity.this,"user",response);
                        Toast.makeText(LoginActivity.this, "登陆成功" + user.getUserName(), Toast.LENGTH_SHORT).show();
                        Intent intent = getIntent();
                        intent.putExtra("user",user);
                        setResult(0,intent);
                        finish();
                    }else {
                        Toast.makeText(LoginActivity.this, "用户名或密码输入错误", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(LoginActivity.this, "用户名或密码输入错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


}
