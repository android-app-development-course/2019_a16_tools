package com.example.dingdangpocket;
import android.os.Handler;
import android.os.Message;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

class HttpUtils {
    private static OkHttpClient client = new OkHttpClient();
    private static Call newCall(String url){
        Request request = new Request.Builder()
                .url(url)
                .get()//默认就是GET请求，可以不写
                .build();
        return client.newCall(request);
    }
    static void GetJSON(String url,final Handler handler,
                          final int OK, final int NO) {
        Call call = newCall(url);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, IOException e) {
                Message message = handler.obtainMessage();
                message.what = NO;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String jsonStr= Objects.requireNonNull(response.body()).string();
                Message message = handler.obtainMessage();
                message.obj = jsonStr;
                message.what = OK;
                handler.sendMessage(message);
            }
        });
    }

    static void GetImage(String url,final Handler handler,
                           final int OK, final int NO) {
        Call call = newCall(url);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Message message = handler.obtainMessage();
                message.what = NO;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                //得到从网上获取资源，转换成我们想要的类型
                byte[] Picture_bt = Objects.requireNonNull(response.body()).bytes();
                //通过handler更新UI
                Message message = handler.obtainMessage();
                message.obj = Picture_bt;
                message.what = OK;
                handler.sendMessage(message);
             }
        });
    }
}
