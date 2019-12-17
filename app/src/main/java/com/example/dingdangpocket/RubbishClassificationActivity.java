package com.example.dingdangpocket;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.Objects;

import static com.example.dingdangpocket.HttpUtils.GetJSON;

public class RubbishClassificationActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_rubbishName;
    private String rubbishName;
    final String api_waste_classify = "https://quark.sm.cn/api/rest?method=sc.operation_sorting_category&app_chain_name=waste_classify&q=";
    private static final int OK = 1;
    private static final int NO = 0;
    private TextView tv_rubbish_name;
    private TextView tv_rubbish_classify;
    private ImageView img_rubbish_classify;
    private ImageButton im_search_rubbish;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                //加载网络成功进行UI的更新,处理得到的图片资源
                case OK:
                    getData((String)msg.obj);
                    break;
                //当加载网络失败执行的逻辑代码
                case NO:
                    Toast.makeText(RubbishClassificationActivity.this, "网络出现了问题", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    private void getData(String jsonStr) {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(jsonStr, JsonObject.class);
//        JsonObject data = jsonObject.get("data").getAsJsonObject();
        JsonObject data = jsonObject.getAsJsonObject("data");
        String waste_type = data.get("waste_type").toString();
        if(!waste_type.equals("\"\"")) {
            int category = Integer.parseInt(data.get("category").toString());
            String type[] = {"干垃圾", "湿垃圾", "有害垃圾", "可回收物"};
            int img_id[] = {R.drawable.ic_ganlaji, R.drawable.ic_shilaji,
                    R.drawable.ic_youhailaji, R.drawable.ic_kehuishouwu};
            tv_rubbish_name.setText(rubbishName);
            tv_rubbish_classify.setText(type[category - 1]);
            img_rubbish_classify.setImageDrawable(
                    ContextCompat.getDrawable(this, img_id[category - 1]));
            findViewById(R.id.layout_rubbish_classify).setVisibility(View.VISIBLE);
            Toast.makeText(this,R.string.query_success,Toast.LENGTH_SHORT).show();
        }else{
            findViewById(R.id.layout_rubbish_classify).setVisibility(View.INVISIBLE);
            Toast.makeText(this,R.string.query_error,Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rubbish_classification);
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

    public void init(){
        et_rubbishName = findViewById(R.id.et_rubbish_query);
        tv_rubbish_classify = findViewById(R.id.tv_rubbish_classify);
        tv_rubbish_name = findViewById(R.id.tv_rubbish_name);
        img_rubbish_classify = findViewById(R.id.img_rubbish_classify);
        im_search_rubbish=(ImageButton) findViewById(R.id.img_rubbish_search);
        im_search_rubbish.setOnClickListener(this);
        findViewById(R.id.layout_rubbish_classify).setVisibility(View.INVISIBLE);
        Toolbar tb = findViewById(R.id.toolbar_rubbish_classify);
        setSupportActionBar(tb);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
    }

    /*public boolean touch_et(View view, MotionEvent motionEvent){
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
                    rubbishName = et_rubbishName.getText().toString();
                    if (rubbishName.equals(""))
                    {
                        Toast.makeText(this,"请输入垃圾名称",Toast.LENGTH_SHORT).show();
                        return false;
                    }
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
    }*/

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            View v = getCurrentFocus();

            //如果不是落在EditText区域，则需要关闭输入法
            if (HideKeyboard(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    // 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘
    private boolean HideKeyboard(View view, MotionEvent event) {
        if (view != null && (view instanceof EditText)) {

            int[] location = { 0, 0 };
            view.getLocationInWindow(location);

            //获取现在拥有焦点的控件view的位置，即EditText
            int left = location[0], top = location[1], bottom = top + view.getHeight(), right = left + view.getWidth();
            //判断我们手指点击的区域是否落在EditText上面，如果不是，则返回true，否则返回false
            boolean isInEt = (event.getX() > left && event.getX() < right && event.getY() > top
                    && event.getY() < bottom);
            return !isInEt;
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        switch ((view).getId()) {
            case R.id.img_rubbish_search: {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                rubbishName = et_rubbishName.getText().toString();
                if (rubbishName.equals("")) {
                    Toast.makeText(this, "请输入垃圾名称", Toast.LENGTH_SHORT).show();
                    break;
                }
                GetJSON(api_waste_classify + rubbishName,
                        handler, OK, NO);
                break;
            }
        }
    }
}
