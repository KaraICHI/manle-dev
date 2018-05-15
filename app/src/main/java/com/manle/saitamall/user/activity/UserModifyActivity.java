package com.manle.saitamall.user.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.manle.saitamall.R;
import com.manle.saitamall.base.CameraBaseActivity;
import com.manle.saitamall.bean.User;
import com.manle.saitamall.bean.enumeration.OrderStatus;
import com.manle.saitamall.shoppingcart.activity.ShoppingCartActivity;
import com.manle.saitamall.utils.CacheUtils;
import com.manle.saitamall.utils.Constants;
import com.manle.saitamall.utils.GlideCircleTransform;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2018/4/16.
 */

public class UserModifyActivity extends CameraBaseActivity implements View.OnClickListener {
    @Bind(R.id.iv_user_avatar)
    ImageView ivUserAvatar;
    /* @Bind(R.id.tv_username)
     TextView tvUsername;*/
    @Bind(R.id.et_username)
    EditText et_username;
    @Bind(R.id.et_password)
    EditText et_password;
    @Bind(R.id.ib_edit_done)
    ImageButton ibEditDone;
    @Bind(R.id.ib_community_back)
    ImageButton ibBack;
    @Bind(R.id.ll_add_menu)
    LinearLayout llAddMenu;
    @Bind(R.id.btn_take_photo)
    Button btnTakePhoto;
    @Bind(R.id.btn_choose_album)
    Button btnChooseAlbum;
    @Bind(R.id.btn_cancel)
    Button btnCancel;
    ProgressDialog progressDialog;

    boolean isVerified;

    Context mContext = UserModifyActivity.this;

    User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_user);
        ButterKnife.bind(this);
        user = new Gson().fromJson(CacheUtils.getString(mContext, "user"), User.class);
        Glide.with(mContext).load(Constants.AVATAR_IMAGE + user.getFigure()).centerCrop().transform(new GlideCircleTransform(mContext)).into(ivUserAvatar);
        et_username.setText(user.getUserName());
        et_password.setText(user.getPassword());
        ivUserAvatar.setOnClickListener(this);
        ibEditDone.setOnClickListener(this);
        ibBack.setOnClickListener(this);
        btnTakePhoto.setOnClickListener(this);
        btnChooseAlbum.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        et_username.setOnClickListener(this);
        et_password.setOnClickListener(this);
        aspectX = 90;
        aspectY = 90;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_user_avatar:
                llAddMenu.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_take_photo:
                startCamera();
                llAddMenu.setVisibility(View.GONE);
                break;
            case R.id.btn_choose_album:
                choiceFromAlbum();
                llAddMenu.setVisibility(View.GONE);
                break;
            case R.id.btn_cancel:
                llAddMenu.setVisibility(View.GONE);
                break;

            case R.id.ib_edit_done:
                saveUser();
                Intent intent = new Intent();
                intent.putExtra("user", user);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.ib_community_back:
                finish();
                break;
            case R.id.et_username:
                et_username.setFocusable(true);
                et_username.setCursorVisible(true);
                et_username.setFocusableInTouchMode(true);
                et_username.requestFocus();
                break;
            case R.id.et_password:
                if (!isVerified){
                    LinearLayout ll_pay = (LinearLayout) getLayoutInflater().inflate(R.layout.dialog_shopcart_pay, null);
                    AlertDialog.Builder dialog = new AlertDialog.Builder(UserModifyActivity.this);
                    dialog.setTitle("请输入密码")
                            .setView(ll_pay)
                            .setPositiveButton("确定", (dialog12, which) -> {
                                EditText editText = (EditText) ll_pay.findViewById(R.id.et_password);
                                if (!user.getPassword().equals(editText.getText().toString())) {
                                    editText.setError("密码错误");
                                } else {
                                    isVerified = true;
                                    dialog12.dismiss();
                                    et_password.setFocusable(true);
                                    et_password.setCursorVisible(true);
                                    et_password.setFocusableInTouchMode(true);
                                    et_password.requestFocus();
                                }
                            })
                            .setNegativeButton("取消", (dialog1, which) -> {
                                dialog1.dismiss();
                            })
                            .create();
                    dialog.show();
                }


                break;

        }


    }

    private void saveUser() {
        user.setUserName(et_username.getText().toString());
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(JSON, new Gson().toJson(user));
        OkHttpUtils.put().url(Constants.CLIENT_USER).requestBody(requestBody).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.e(TAG, "onError: " + e.getMessage());
            }

            @Override
            public void onResponse(String response, int id) {
                processData(response);
                if (response != null) {
                    CacheUtils.putString(mContext, "user", response);
                }

            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: resultcode" + resultCode);
        if (resultCode == RESULT_OK) {
            // 通过返回码判断是哪个应用返回的数据
            switch (requestCode) {
                case TAKE_PHOTO_REQUEST_CODE:
                    cropPhoto(photoUri);
                    break;
                // 相册选择
                case CHOICE_FROM_ALBUM_REQUEST_CODE:
                    cropPhoto(data.getData());
                    break;
                // 裁剪图片
                case CROP_PHOTO_REQUEST_CODE:
                    File file = new File(photoOutputUri.getPath());
                    Log.e(TAG, "onActivityResult: " + file.exists() + photoOutputUri.getPath());
                    if (file.exists()) {
                        Log.e(TAG, "onActivityResult: " + file.exists());
                        uploadImg(file);
                    } else {
                        Toast.makeText(mContext, "找不到照片", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }


    }

    private void uploadImg(File file) {
        Long userId = CacheUtils.getLong(mContext, "userId");
        OkHttpUtils.post().addFile("file", fileName, file).addParams("id", userId + "")
                .url(Constants.USER_FIGURE).tag(this).build().execute(new StringCallback() {

            @Override
            public void onBefore(Request request, int id) {
                progressDialog = new ProgressDialog(mContext, ProgressDialog.STYLE_SPINNER);
                progressDialog.setMessage("头像上传...");
                progressDialog.show();
            }

            @Override
            public void onAfter(int id) {
                progressDialog.dismiss();
                Log.d(TAG, "onAfter:============== " + file.getPath());
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                Log.e("TAG", "联网失败" + e.getMessage());

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    processData(response);
                    if (user.getFigure().equals(fileName)) {
                        Bitmap bitmap = createCircleImage(BitmapFactory.decodeFile(photoOutputUri.getPath()));
                        ivUserAvatar.setImageDrawable(new BitmapDrawable(getResources(), bitmap));
                        //CacheUtils.putString(mContext,"user",response);
                        ToastUtils.showShortToast(mContext, "上传头像成功");
                    } else {
                        Toast.makeText(mContext, "上传失败，请重试！", Toast.LENGTH_SHORT);
                    }

                } catch (Exception e) {
                    ToastUtils.showShortToast(mContext, "上传异常，请重试！");
                    Log.e(TAG, "onResponse: " + e.getMessage());
                }


            }

            @Override
            public void inProgress(float progress, long total, int id) {
                Log.e(TAG, "inProgress:" + progress);//inProgress:0.99884826
                progressDialog.setProgress((int) progress);
                //ProgressWheelDialogUtil.setDialogMsg((int)(progress * 100)+"%");//更改上传进度提示框的进度值
            }
        });
    }

    private void processData(String response) {
        if (response != null && !"".equals(response)) {
            Gson gson = new Gson();
            User user1 = gson.fromJson(response, User.class);
            user.setFigure(user1.getFigure());
            Log.e(TAG, "processData: ========" + user);
        } else {
            Toast.makeText(mContext, "修改失败，请重试！", Toast.LENGTH_SHORT);
        }
    }

    public static Bitmap createCircleImage(Bitmap source) {
        int length = source.getWidth() < source.getHeight() ? source.getWidth() : source.getHeight();
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(length, length, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(target);
        canvas.drawCircle(length / 2, length / 2, length / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(source, 0, 0, paint);
        return target;
    }
}


