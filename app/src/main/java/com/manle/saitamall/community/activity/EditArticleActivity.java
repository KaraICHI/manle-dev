package com.manle.saitamall.community.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.manle.saitamall.R;
import com.manle.saitamall.base.CameraBaseActivity;
import com.manle.saitamall.bean.Artical;
import com.manle.saitamall.bean.User;
import com.manle.saitamall.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Request;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2018/4/3.
 */

public class EditArticleActivity extends CameraBaseActivity implements View.OnClickListener{
    @Bind(R.id.iv_article_image)
    ImageView ivShareImg;
    @Bind(R.id.ib_article_image_add)
     ImageButton imgAdd;
    @Bind(R.id.et_edit_title)
    EditText etEditTitle;
    @Bind(R.id.et_edit_content)
     EditText etShareContent;
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
    Context mContext = EditArticleActivity.this;
    Artical artical;


    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activit_edit_article);
        ButterKnife.bind(this);
        ibBack.setOnClickListener(this);
        ibEditDone.setOnClickListener(this);
        imgAdd.setOnClickListener(this);
        btnTakePhoto.setOnClickListener(this);
        btnChooseAlbum.setOnClickListener(this);

        aspectX = 400;
        aspectY = 250;
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_edit_done :
                save();
                break;
            case R.id.ib_community_back:
                finish();
                break;
            case R.id.ib_article_image_add:
                llAddMenu.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_take_photo:
                startCamera();
                break;
            case R.id.btn_choose_album:
                choiceFromAlbum();
                break;
        }
    }

    private void save(){
        String title = etEditTitle.getText().toString();
        String content = etShareContent.getText().toString();
        if (artical==null){
            Toast.makeText(mContext,"请加配图",Toast.LENGTH_SHORT).show();
        }else if (title.isEmpty()){
            Toast.makeText(mContext,"标题不可为空",Toast.LENGTH_SHORT).show();
        }else if (content.isEmpty()){
            Toast.makeText(mContext,"内容不可为空",Toast.LENGTH_SHORT).show();
        }else if (title.length()>20){
            Toast.makeText(mContext,"标题长度不能超过20",Toast.LENGTH_SHORT).show();
        }else {
            User user = (User) getIntent().getSerializableExtra("user");
            artical.setClientUserId(user.getId());
            artical.setTitle(title);
            artical.setContent(content);
            artical.setFavorite(0l);
            Date createDate = new Date(System.currentTimeMillis());
            SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            fm.setTimeZone(TimeZone.getTimeZone("UTC"));
            artical.setCreatDate(fm.format(createDate));
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            String articalJson = new Gson().toJson(artical);
            OkHttpUtils.postString().mediaType(JSON).content(articalJson).url(Constants.ARTICAL).build().execute(new StringCallback() {

                @Override
                public void onError(Call call, Exception e, int id) {
                    Log.e(TAG, "onError: "+e.getMessage() );
                    Toast.makeText(mContext,"发布失败",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(String response, int id) {
                    if (response!=null){
                        processData(response);
                        Toast.makeText(mContext,"发布成功",Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            // 通过返回码判断是哪个应用返回的数据
            switch (requestCode) {
                // 拍照
                case TAKE_PHOTO_REQUEST_CODE:
                    cropPhoto(photoUri);
                    break;
                // 相册选择
                case CHOICE_FROM_ALBUM_REQUEST_CODE:
                    cropPhoto(data.getData());
                    break;
                // 裁剪图片
                case CROP_PHOTO_REQUEST_CODE:
                    llAddMenu.setVisibility(View.INVISIBLE);
                    File file = new File(photoOutputUri.getPath());
                    if (file.exists()) {

                        if (ivShareImg != null) {
                           uploadImg(file);
                        }
//                      file.delete(); // 选取完后删除照片
                    } else {
                        Toast.makeText(this, "找不到照片", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }

    }

    private void uploadImg(File file) {

        OkHttpUtils.post().addFile("file", fileName, file)
                .url(Constants.ARTICAL_FIGURE).tag(this).build().execute(new StringCallback() {

            @Override
            public void onBefore(Request request, int id) {
                progressDialog = new ProgressDialog(mContext, ProgressDialog.STYLE_SPINNER);
                progressDialog.setMessage("图片上传...");
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
                Log.e(TAG, "onResponse：response=" + response + "===" + id);
                try {
                    processData(response);
                    if (artical != null) {
                        Bitmap bitmap = BitmapFactory.decodeFile(photoOutputUri.getPath());
                        ivShareImg.setImageBitmap(bitmap);
                        Toast.makeText(mContext, "成功", Toast.LENGTH_SHORT);
                    } else {
                        Toast.makeText(mContext, "上传失败，请重试！", Toast.LENGTH_SHORT);
                    }

                } catch (Exception e) {
                    Toast.makeText(mContext, "上传异常，请重试！", Toast.LENGTH_SHORT);
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
            artical = gson.fromJson(response, Artical.class);
            Log.e(TAG, "processData: ========" + artical);
        } else {
            Toast.makeText(mContext, "修改失败，请重试！", Toast.LENGTH_SHORT);
        }
    }



}
