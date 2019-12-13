package com.example.dingdangpocket;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.dingdangpocket.HttpUtils.*;

public class BilibiliCoversActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_video_id;
    private ImageView img_video_cover;
    private final String bilibili_address = "https://api.bilibili.com/x/web-interface/view?aid=";
    private static final int JSON_OK = 1000;
    private static final int JSON_NO = 1001;
    private static final int IMG_OK = 1002;
    private static final int IMG_NO = 1003;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                //加载网络成功进行UI的更新,处理得到的图片资源
                case JSON_OK:
                    String html = (String) msg.obj;
                    String ImgUrl = getImgUrl(html);
                    Log.d("ImgUrl",ImgUrl);
                    GetImage(ImgUrl,handler,IMG_OK,IMG_NO);
                    break;
                case IMG_OK:
                    //通过message，拿到字节数组
                    byte[] Picture = (byte[]) msg.obj;
                    //使用BitmapFactory工厂，把字节数组转化为bitmap
                    Bitmap bitmap = BitmapFactory.decodeByteArray(Picture, 0, Picture.length);
                    //通过imageview，设置图片
                    img_video_cover.setImageBitmap(bitmap);
                    break;
                case JSON_NO:
                case IMG_NO:
                    Toast.makeText(BilibiliCoversActivity.this, "网络出现了问题", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bilibili_covers);
        init();
    }

    private void init(){
        et_video_id = findViewById(R.id.et_video_id);
        img_video_cover = findViewById(R.id.img_video_cover);
        findViewById(R.id.btn_get).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_get:
                String video_id = et_video_id.getText().toString();
                video_id = video_id.toLowerCase();
                if(video_id.contains("av")){
                    video_id = video_id.replace("av","");
                }
                String url = bilibili_address + video_id;
                GetJSON(url,handler,JSON_OK,JSON_NO);
                break;
            default:break;
        }
    }
    String getImgUrl(String html){
//"pic":"http://i0.hdslb.com/bfs/archive/d52994a1876d07a975dc6683b78a898d9b581208.png"
        String regex = "\"pic\":\"([a-zA-z]+://[^\\s]*.(png|jpg))\"";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(html);
        String s = "";
        if (m.find()) {
            s = m.group(1);
            String[] ary1 = s.split("\"");
            s = ary1[ary1.length-1];
        }
        return s;
    }
}
