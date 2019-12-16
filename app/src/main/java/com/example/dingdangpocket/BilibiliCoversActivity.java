package com.example.dingdangpocket;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

import static com.example.dingdangpocket.HttpUtils.GetImage;
import static com.example.dingdangpocket.HttpUtils.GetJSON;

public class BilibiliCoversActivity extends AppCompatActivity
        implements View.OnClickListener , EasyPermissions.PermissionCallbacks{

    private EditText et_video_id;
    private String video_id;
    private ImageView img_video_cover;
    private Bitmap bitmap_video_cover;
    final String pictures = "pictures";
    final String bilibili = "bilibili";
    private final String bilibili_address = "https://api.bilibili.com/x/web-interface/view?aid=";
    private static final int JSON_OK = 1000;
    private static final int JSON_NO = 1001;
    private static final int IMG_OK = 1002;
    private static final int IMG_NO = 1003;
    private static final int SAVE_COVER = 1004;
    private static final int VIEW_SAVED_COVER = 1005;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                //加载网络成功进行UI的更新,处理得到的图片资源
                case JSON_OK:
                    getData((String) msg.obj);
                    break;
                case IMG_OK:
                    //通过message，拿到字节数组
                    byte[] Picture = (byte[]) msg.obj;
                    //使用BitmapFactory工厂，把字节数组转化为bitmap
                    bitmap_video_cover = BitmapFactory.decodeByteArray(Picture, 0, Picture.length);
                    //通过imageview，设置图片
                    img_video_cover.setImageBitmap(bitmap_video_cover);
                    findViewById(R.id.layout_save_img).setVisibility(View.VISIBLE);
                    Toast.makeText(BilibiliCoversActivity.this,R.string.get_success,Toast.LENGTH_SHORT).show();
                    break;
                case JSON_NO:
                case IMG_NO:
                    Toast.makeText(BilibiliCoversActivity.this, R.string.network_error, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.activity_bilibili_covers_menu, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_view_saved_cover:
                String []perms = {Manifest.permission.READ_EXTERNAL_STORAGE};
                if(EasyPermissions.hasPermissions(this,perms)) {
                    viewSavedCover();
                }else {
                    EasyPermissions.requestPermissions(this,getString(R.string.view_img),VIEW_SAVED_COVER,perms);
                }
                break;
            case android.R.id.home://点击了返回按钮
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bilibili_covers);
        init();
    }

    private void init(){
        Toolbar tb = findViewById(R.id.toolbar_bilibili);
        setSupportActionBar(tb);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
        et_video_id = findViewById(R.id.et_video_id);
        img_video_cover = findViewById(R.id.img_video_cover);
        findViewById(R.id.btn_get).setOnClickListener(this);
        findViewById(R.id.btn_save_img).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_get:
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                video_id = et_video_id.getText().toString();
                video_id = video_id.toLowerCase();
                if(video_id.contains("av")){
                    video_id = video_id.replace("av","");
                }
                String url = bilibili_address + video_id;
                GetJSON(url,handler,JSON_OK,JSON_NO);
                break;
            case R.id.btn_save_img:
                String []perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                if(EasyPermissions.hasPermissions(this,perms)) {
                    saveCover();
                }else {
                    EasyPermissions.requestPermissions(this,getString(R.string.storage_img),SAVE_COVER,perms);
                }
            default:break;
        }
    }
    void getData(String jsonStr){
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(jsonStr,JsonObject.class);
        String code = jsonObject.get("code").toString();
        if(code.equals("0")){
            JsonObject data = jsonObject.get("data").getAsJsonObject();
            String picUrl = data.get("pic").toString().replaceAll("^\"|\"$","");
            GetImage(picUrl,handler,IMG_OK,IMG_NO);
        }else {
            Toast.makeText(this, R.string.incorrect_video_id, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults,this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        switch (requestCode) {
            case SAVE_COVER:
                saveCover();
                break;
            case VIEW_SAVED_COVER:
                viewSavedCover();
                break;
            default:
                break;
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if(EasyPermissions.somePermissionPermanentlyDenied(this,perms)){
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    private void saveCover(){
        String path = Environment.getExternalStoragePublicDirectory("").getAbsolutePath();
        if (null != path) {
            path = path + "/" + getString(R.string.app_name)+ "/"+pictures+ "/" + bilibili;
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            File imgFile = new File(file,video_id+".jpg");
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(imgFile);
                bitmap_video_cover.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);

                try {
                    fileOutputStream.flush();
                } catch (IOException e) {
                    Log.e("fileOutputStream#flush",e.toString());
                }
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    Log.e("fileOutputStream#close",e.toString());
                }
                String msg = getString(R.string.save_as) +"：/"+
                        getString(R.string.app_name) + "/" + pictures +"/"+bilibili+
                        video_id+".jpg";
                Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
                return;
            } catch (FileNotFoundException e) {
                Log.e("new FileOutputStream",e.toString());
            }
        }
        Toast.makeText(this,R.string.save_fail,Toast.LENGTH_SHORT).show();
    }


    private void viewSavedCover(){

        String path = Environment.getExternalStoragePublicDirectory("").getAbsolutePath();
        if (null != path) {

            path = path + "/" + getString(R.string.app_name) +"/" + pictures +"/" + bilibili +"/";
            File file = new File(Uri.parse(path).getPath());
            if (!file.exists()) {
                file.mkdirs();
            }
//            Uri uri;
//            if(Build.VERSION.SDK_INT>=24){
//                uri = FileProvider.getUriForFile(this,
//                        "com.example.dingdangpocket",file);
//            }else {
//                uri = Uri.parse(path);
//            }

//            uri = Uri.parse(path);
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N) {
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri contentUri = FileProvider.getUriForFile(this,
                        "com.example.dingdangpocket.fileProvider",file);
                intent.setDataAndType(contentUri,"image/*");
            }else {
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
                intent.setDataAndType(Uri.fromFile(file),"image/*");
            }
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            if(intent.resolveActivity(getPackageManager())!=null){
                startActivity(intent);
            }else {
                Log.d("resolveActivity","false");
            }

        }
    }
}
