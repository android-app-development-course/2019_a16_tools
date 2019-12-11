package com.example.dingdangpocket;
import java.util.Random;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
                int min = Integer.valueOf(et1.getText().toString());
                int max = Integer.valueOf(et2.getText().toString());
                if(min >= max) return;
                int result = (new Random()).nextInt(max - min) + min;
                TextView tv = findViewById(R.id.textview_random_number);
                tv.setText(Integer.toString(result));
                break;

                default:break;
        }
    }
}
