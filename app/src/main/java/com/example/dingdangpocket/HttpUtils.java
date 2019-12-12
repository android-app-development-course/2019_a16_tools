package com.example.dingdangpocket;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.support.constraint.Constraints.TAG;
import static com.example.dingdangpocket.UnicodeUtils.unicodeToCn;

class HttpUtils {
    static String jsonStr = "";
    static OkHttpClient client = new OkHttpClient();
    static String GetJSON( String u) throws IOException, InterruptedException {
        jsonStr = "";
        HttpsURLConnection connection = null;
        BufferedReader reader = null;
        String line;
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(u)
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                jsonStr= response.body().string();
                Log.d(TAG, "jsomreturn: " + jsonStr);
            }
        });
        int count = 0;
        while(jsonStr == "" && count<10) {
            Thread.sleep(100);
            count++;
        }
        if(jsonStr == "")
            jsonStr = "{\"error\":0,\"data\":{\"waste_type\":\"请检查网络\",\"category\":1}}";
        return jsonStr;
    }


}
