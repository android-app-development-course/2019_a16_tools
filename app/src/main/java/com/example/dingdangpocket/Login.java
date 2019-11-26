package com.example.dingdangpocket;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Login extends BaseActivity implements View.OnClickListener {
    Button login,change,back;
    EditText account,password;
    String ac="",pw="",lang;
    int number,languageType,selection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayout());
        //实例化对象
        initView();
        initData();
    }

    @Override
    int setLayout() {
        return R.layout.login;
    }

    @Override
    protected void initView() {
        setTranslucent(this);
    }

    @Override
    protected void initData() {
        login=(Button)findViewById(R.id.login);
        back=(Button)findViewById(R.id.back);
        final EditText account=(EditText) findViewById(R.id.account);
        final EditText password=(EditText) findViewById(R.id.password);
        change=(Button)findViewById(R.id.change);
        login.setOnClickListener(this);
        back.setOnClickListener(this);

        Intent intent1=getIntent();
        languageType=getIntent().getIntExtra("languageTYpe",0);
        account.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ac=account.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                pw=password.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        change.setOnClickListener(this);
        number=5;
    }

    public void onClick(View v){
        switch (((Button)v).getId()){
            case R.id.login:{
                //没有输入账号
                if(ac.equals("")){
                    Toast.makeText(this,getResources().getString(R.string.ac_hint),Toast.LENGTH_SHORT).show();
                }
                //匹配成功
                else if(pw.equals("")){
                    Toast.makeText(this,getResources().getString(R.string.pw_hint),Toast.LENGTH_SHORT).show();
                }
                else if(ac.equals("10086") &&pw.equals("10086")&&number>0){
                    Toast.makeText(this,getResources().getString(R.string.success),Toast.LENGTH_SHORT).show();
                    finish();

                }
                //匹配失败
                else if(number>0){
                    Toast.makeText(this,getResources().getString(R.string.fail)+number,Toast.LENGTH_SHORT).show();
                    number--;
                }
                break;
            }
            case R.id.change: {
            }
            case R.id.back: {
                finish();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
