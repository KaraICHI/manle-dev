package com.manle.saitamall.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Administrator on 2018/4/10.
 */

public class CameraUtil {

    protected View startCameraButton = null;
    protected View choiceFromAlbumButton = null;
    protected ImageView pictureImageView = null;
    protected View setPhotoNull = null;
    protected Dialog dialog = null;
    protected static int count=0;
    protected int aspectX=365;
    protected int aspectY=202;
    public Context mContext;
    public Activity activity;




    protected static final int TAKE_PHOTO_PERMISSION_REQUEST_CODE = 0; // 拍照的权限处理返回码
    protected static final int WRITE_SDCARD_PERMISSION_REQUEST_CODE = 1; // 读储存卡内容的权限处理返回码

    protected static final int TAKE_PHOTO_REQUEST_CODE = 3; // 拍照返回的 requestCode
    protected static final int CHOICE_FROM_ALBUM_REQUEST_CODE = 4; // 相册选取返回的 requestCode
    protected static final int CROP_PHOTO_REQUEST_CODE = 5; // 裁剪图片返回的 requestCode

    protected Uri photoUri = null;
    protected Uri photoOutputUri = null; // 图片最终的输出文件的 Uri

    protected void initCameraControls(View startCameraButton, View choiceFromAlbumButton, ImageView pictureImageView) {
        this.startCameraButton = startCameraButton;
        this.choiceFromAlbumButton = choiceFromAlbumButton;
        this.pictureImageView = pictureImageView;
        if (startCameraButton != null) {
            startCameraButton.setOnClickListener(clickListener);
        }
        if (choiceFromAlbumButton != null) {
            choiceFromAlbumButton.setOnClickListener(clickListener);
        }


    }



    protected void initCameraControls(Activity activity,View startCameraButton, View choiceFromAlbumButton, ImageView pictureImageView, Dialog dialog, View setPhotoNull) {
        this.startCameraButton = startCameraButton;
        this.choiceFromAlbumButton = choiceFromAlbumButton;
        this.pictureImageView = pictureImageView;
        this.setPhotoNull = setPhotoNull;
        startCameraButton.setOnClickListener(clickListener);
        choiceFromAlbumButton.setOnClickListener(clickListener);
        setPhotoNull.setOnClickListener(clickListener);
        this.dialog = dialog;
    }


    protected View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // 调用相机拍照
            if (v == startCameraButton) {
                // 同上面的权限申请逻辑
                if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    /*
                     * 下面是对调用相机拍照权限进行申请
                     */
                    ActivityCompat.requestPermissions(activity,
                            new String[]{Manifest.permission.CAMERA,}, TAKE_PHOTO_PERMISSION_REQUEST_CODE);
                } else {
                    startCamera();
                    if (dialog != null)
                        dialog.dismiss();
                }
                // 从相册获取
            } else if (v == choiceFromAlbumButton) {
                choiceFromAlbum();
                if (dialog != null)
                    dialog.dismiss();
            } else if (v == setPhotoNull) {

                pictureImageView.setVisibility(View.INVISIBLE);
                dialog.dismiss();
            }
        }
    };

    /**
     * 拍照
     */
    @TargetApi(Build.VERSION_CODES.FROYO)
    protected void startCamera() {

        File file = new File(mContext.getExternalCacheDir(), "image.jpg");
        try {
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT >= 24) {
            photoUri = FileProvider.getUriForFile(mContext, "com.zhi_dian.provider", file);
        } else {
            photoUri = Uri.fromFile(file); // Android 7.0 以前使用原来的方法来获取文件的 Uri
        }
        // 打开系统相机的 Action，等同于："android.media.action.IMAGE_CAPTURE"
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 设置拍照所得照片的输出目录
        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        activity.startActivityForResult(takePhotoIntent, TAKE_PHOTO_REQUEST_CODE);
    }

    /**
     * 从相册选取
     */
    protected void choiceFromAlbum() {
        // 打开系统图库的 Action，等同于: "android.intent.action.GET_CONTENT"
        Intent choiceFromAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
        // 设置数据类型为图片类型
        choiceFromAlbumIntent.setType("image/*");
        activity.startActivityForResult(choiceFromAlbumIntent, CHOICE_FROM_ALBUM_REQUEST_CODE);
    }

    /**
     * 裁剪图片
     */
    protected void cropPhoto(Uri inputUri) {
        count=(int)(1+Math.random()*1000);
        // 调用系统裁剪图片的 Action
        Intent cropPhotoIntent = new Intent("com.android.camera.action.CROP");
        // 设置数据Uri 和类型
        cropPhotoIntent.setDataAndType(inputUri, "image/*");
        cropPhotoIntent.putExtra("aspectX", aspectX);
        cropPhotoIntent.putExtra("aspectY",aspectY);
        // 授权应用读取 Uri，这一步要有，不然裁剪程序会崩溃
        cropPhotoIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        // 设置图片的最终输出目录
        cropPhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                photoOutputUri = Uri.parse("file:////sdcard/image_output"+count+".jpg"));
        activity.startActivityForResult(cropPhotoIntent, CROP_PHOTO_REQUEST_CODE);
    }

    protected String getPhotoOutputUri(){
        return "file:////sdcard/image_output"+count+".jpg";

    }


    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            // 调用相机拍照：
            case TAKE_PHOTO_PERMISSION_REQUEST_CODE:
                // 如果用户授予权限，那么打开相机拍照
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startCamera();
                } else {
                    Toast.makeText(mContext, "拍照权限被拒绝", Toast.LENGTH_SHORT).show();
                }
                break;
            // 打开相册选取：
            case WRITE_SDCARD_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(mContext, "读写内存卡内容权限被拒绝", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * 通过这个 activity 启动的其他 Activity 返回的结果在这个方法进行处理
     * 我们在这里对拍照、相册选择图片、裁剪图片的返回结果进行处理
     *
     * @param requestCode 返回码，用于确定是哪个 Activity 返回的数据
     * @param resultCode  返回结果，一般如果操作成功返回的是 RESULT_OK
     * @param data        返回对应 activity 返回的数据
     */

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
                    File file = new File(photoOutputUri.getPath());
                    if (file.exists()) {
                        Bitmap bitmap = BitmapFactory.decodeFile(photoOutputUri.getPath());
                        if (pictureImageView != null) {
                            pictureImageView.setImageBitmap(bitmap);
                        }

//                      file.delete(); // 选取完后删除照片
                    } else {
                        Toast.makeText(mContext, "找不到照片", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }

    }

}
