package com.example.dingdangpocket;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class RandomNumberActivity extends AppCompatActivity
        implements View.OnClickListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_random_number);
        findViewById(R.id.random_generate).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.random_generate:
                EditText et1 = findViewById(R.id.edittext_random_min);
                EditText et2 = findViewById(R.id.edittext_random_max);
                TextView tv = findViewById(R.id.textview_random_number);

                String str1 = et1.getText().toString();
                String str2 = et2.getText().toString();

                if(str1.isEmpty() || str2.isEmpty())
                    return;

                int min = Integer.valueOf(str1);
                int max = Integer.valueOf(str2);
                if(min > max)
                {
                    tv.setText("0");
                    return;
                }
                else if(min == max) {
                    tv.setText(Integer.toString(min));
                    return;
                }
                int result = (new Random()).nextInt(max - min) + min;
                tv.setText(Integer.toString(result));
                break;

                default:break;
        }
    }
}
