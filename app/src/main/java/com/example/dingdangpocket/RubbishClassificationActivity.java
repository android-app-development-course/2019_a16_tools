package com.example.dingdangpocket;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.dingdangpocket.HttpUtils.GetJSON;

public class RubbishClassificationActivity extends AppCompatActivity implements View.OnTouchListener {

    private EditText et_rubbishName;
    final String api_waste_classify = "https://quark.sm.cn/api/rest?method=sc.operation_sorting_category&app_chain_name=waste_classify&q=";
    private static final int OK = 1;
    private static final int NO = 0;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                //加载网络成功进行UI的更新,处理得到的图片资源
                case OK:
                    String  jsonStr= (String)msg.obj;
                    String []results = jsonStr.split("\"");
                    Toast.makeText(RubbishClassificationActivity.this,results[results.length-4], Toast.LENGTH_LONG).show();
                    break;
                //当加载网络失败执行的逻辑代码
                case NO:
                    Toast.makeText(RubbishClassificationActivity.this, "网络出现了问题", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
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
                    GetJSON(api_waste_classify+rubbishName,
                            handler,OK,NO);
//                    rubbishClassification = rubbishQuery(rubbishName);
//                    Toast.makeText(this, rubbishName+" , "+rubbishClassification, Toast.LENGTH_LONG).show();
                }
                break;
            default:break;
        }
        view.performClick();
        return false;
    }

}
