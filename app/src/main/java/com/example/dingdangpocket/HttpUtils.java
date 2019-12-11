package com.example.dingdangpocket;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import static com.example.dingdangpocket.UnicodeUtils.unicodeToCn;

class HttpUtils {
    static String GetJSON(String u){
        HttpsURLConnection connection = null;
        BufferedReader reader = null;
        String line;
        try {
            URL url2 = new URL(u);
            connection = (HttpsURLConnection) url2.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            if (connection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                StringBuilder result = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    result.append(line).append(System.getProperty("line.separator"));
                }
                return unicodeToCn(result.toString());
            }
        } catch (IOException e) {
            Log.i("NullPointerException",e.toString());
        } finally {
            try {
                reader.close();
            } catch (NullPointerException e2) {
                Log.i("NullPointerException",e2.toString());
            } catch (IOException e) {
                Log.i("IOException",e.toString());
            }
            try {
                connection.disconnect();
            }catch (NullPointerException e){
                Log.i("NullPointerException",e.toString());
            }

        }
        return "";
    }
}
