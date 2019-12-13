package com.example.dingdangpocket;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

import static com.example.dingdangpocket.HttpUtils.GetJSON;

public class BilibiliCoversActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_video_id;
    private ImageView img_video_cover;
    private final String bilibili_address = "https://m.bilibili.com/video/";

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
                if(!video_id.contains("av")){
                    video_id = "av" + video_id;
                }
                String url = bilibili_address + video_id + ".html";
                try {
                    String html = GetJSON(url);
                    Toast.makeText(this, html, Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            default:break;
        }
    }
}
