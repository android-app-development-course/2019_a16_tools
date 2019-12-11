package com.example.dingdangpocket;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.dingdangpocket.HttpUtils.GetJSON;

public class RubbishClassificationActivity extends AppCompatActivity implements View.OnTouchListener {

    private EditText et_rubbishName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rubbish_classification);
        init();
    }

    public void init(){
        et_rubbishName = findViewById(R.id.et_rubbish_query);
        et_rubbishName.setOnTouchListener(this);
    }
    public boolean touch_et(View view, MotionEvent motionEvent){
        EditText editText = (EditText) view;
        // getCompoundDrawables()得到一个长度为4的数组，分别表示左上右下四张图片
        Drawable drawable = editText.getCompoundDrawables()[2];
        //如果右边没有图片，不再处理
        if (drawable == null)
            return false;
        //如果不是按下事件，不再处理
        if (motionEvent.getAction() != MotionEvent.ACTION_UP)
            return false;
        int x1 = editText.getWidth() - editText.getPaddingRight();
        int x2 = x1 - drawable.getIntrinsicWidth();
        return motionEvent.getX() > x2 && motionEvent.getX()< x1;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (view.getId())
        {
            case R.id.et_rubbish_query:
                if(touch_et(view, motionEvent)){
                    //点击了垃圾分类查询右边的搜索图标
                    String rubbishName = et_rubbishName.getText().toString();
                    String rubbishClassification = "";
//                    rubbishClassification = rubbishQuery(rubbishName);
                    Toast.makeText(this, rubbishName+" , "+rubbishClassification, Toast.LENGTH_LONG).show();
                }
                break;
            default:break;
        }
        view.performClick();
        return false;
    }

    public String rubbishQuery(String rubbishName){
        //例子：https://quark.sm.cn/api/rest?method=sc.operation_sorting_category&app_chain_name=waste_classify&q=湿纸巾
        //返回JSON：{"error":0,"data":{"waste_type":"干垃圾或其他垃圾","category":1}}
        String url = "https://quark.sm.cn/api/rest?method=sc.operation_sorting_category&app_chain_name=waste_classify&q=";
//        String api1 = url + name;
//        String api2 = "https://quark.sm.cn/api/quark_sug?q=罐头是什么垃圾";
        String result = GetJSON(url+rubbishName);
        String rubbishClassification = "";
        try {
            JSONArray obj = new JSONArray(result);
            for(int i=0;i<obj.length();i++){
                JSONObject jsonobj = (JSONObject)obj.get(i);
                if (jsonobj.has("waste_type")){
                    rubbishClassification = jsonobj.getString("waste_type");
                    break;
                }
            }
        } catch (JSONException e) {
            Log.i("JSONException",e.toString());
        }
        return rubbishClassification;
    }
}
