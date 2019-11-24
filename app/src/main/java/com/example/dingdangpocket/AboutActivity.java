package com.example.dingdangpocket;

import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;


import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

/* About页面 */


public class AboutActivity extends AppCompatActivity{

    private RelativeLayout relativeLayout;

    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        initViews();

        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setImage(R.drawable.lingdang2)//图片
                .setDescription("有限的道具，无限的童真")//介绍
                .addItem(new Element().setTitle("Version 1.0"))
                .addGroup("与我们联系")
                .addEmail("zhaoweihaochn@foxmail.com")//邮箱
                .addWebsite("http://zhaoweihao.me")//网站
                .addGitHub("zhaoweihaoChina")//github
                .create();

        relativeLayout.addView(aboutPage);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            default:

        }
        return true;
    }

    private void initViews(){
        relativeLayout= (RelativeLayout) findViewById(R.id.about_relativeLayout);
        toolbar= (Toolbar) findViewById(R.id.about_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


}

