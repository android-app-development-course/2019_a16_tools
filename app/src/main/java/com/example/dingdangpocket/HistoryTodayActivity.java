package com.example.dingdangpocket;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import static com.example.dingdangpocket.HttpUtils.GetJSON;

public class HistoryTodayActivity extends AppCompatActivity {

    private static final int JSON_OK = 1000;
    private static final int JSON_NO = 1001;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                //加载网络成功进行UI的更新,处理得到的图片资源
                case JSON_OK:
                    getData((String) msg.obj);
                    break;
                case JSON_NO:
                    Toast.makeText(HistoryTodayActivity.this, "网络出现了问题", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_today);
        init();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home://点击了返回按钮
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void init() {
        Toolbar tb = findViewById(R.id.toolbar_history_today);
        setSupportActionBar(tb);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
        final String appid = "392811492abcabf76053bf618e571e0e";
        final String httpUrl = "https://api.shenjian.io/todayOnhistory/queryEvent";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy,MM/dd,HH:mm:ss");
        Date date1 = new Date(System.currentTimeMillis());
        String date2 = simpleDateFormat.format(date1).split(",")[1];
        String httpArg = "?appid=" + appid + "&date=" + date2;
        GetJSON(httpUrl + httpArg, handler, JSON_OK, JSON_NO);
    }

    void getData(String jsonStr){
        Gson gson = new Gson();
        JsonObject jsonObject= gson.fromJson(jsonStr, JsonObject.class);
        String error_code = jsonObject.get("error_code").toString();
        if(error_code.equals("0")) {
            //获取成功
            JsonArray jsonArray = jsonObject.getAsJsonArray("data");
            ArrayList<HistoryToday> arrayList = new ArrayList<>();
            for(int i = jsonArray.size()-1;i>-1;i--){
                JsonObject jsonObject1 = jsonArray.get(i).getAsJsonObject();
                String date = jsonObject1.get("date").toString().replaceAll("^\"|\"$","");
                String title = jsonObject1.get("title").toString().replaceAll("^\"|\"$","");
                String imgUrl = jsonObject1.get("img").toString().replaceAll("^\"|\"$","");
                arrayList.add(new HistoryToday(date,title,imgUrl));
            }
            ListView lv_history_day = findViewById(R.id.lv_history_today);
            lv_history_day.setAdapter(new LVHistoryTodayAdapter(this,arrayList));
        }
    }
}
