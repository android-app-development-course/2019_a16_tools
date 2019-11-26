package com.example.dingdangpocket;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 启动页面的设计
 */

public class SplashActivity extends BaseActivity {

    //显示广告页面的时间，5 秒
    long showTime=5;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //延迟5000ms跳转到主页面
        initView();
        initData();
    }

    @Override
    int setLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        setTranslucent(this);
        textView= (TextView) findViewById(R.id.text);
    }

    @Override
    protected void initData() {
        handler.postDelayed(myRunnable, showTime*1000);
        handler.sendEmptyMessage(111);//給Handler对象发送信息
    }

    //创建Handler对象
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==111){

                Log.e("TAG","what"+showTime);
                textView.setText(""+showTime);
                showTime--;//时间减一秒
                if (showTime>0){
                    handler.sendEmptyMessageDelayed(111,1000);//一秒后給自己发送一个信息
                }

            }

        }
    };

    //创建Runnable对象
    Runnable myRunnable=new Runnable() {
        @Override
        public void run() {
            jundToMainActivity();
        }
    };

    //跳转到主页的方法，并关闭自身页面
    public void jundToMainActivity(){
        Intent intent = new Intent(SplashActivity.this,
                MainActivity.class);
        startActivity(intent);
        finish();
    }

    //关闭页面
    public void closeSplash(View view){
        Log.e("TAG","closeSplash");
        handler.removeCallbacks(myRunnable);//移出Runnable对象
        jundToMainActivity();

    }

    //回退键的监听方法，
    // 这里如果直接关闭页面，线程没有关闭的话，5秒后还是会启动主页面，除非移出线程对象
    @Override
    public void onBackPressed() {
        // super.onBackPressed();不让它关闭
        //如果按回退键，关闭程序，代码设计
        finish();//关闭页面
        // handler.removeCallbacks(myRunnable);//取消runnable对象

    }

}