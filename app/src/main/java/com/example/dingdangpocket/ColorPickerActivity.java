package com.example.dingdangpocket;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.shixia.colorpickerview.ColorPickerView;
import com.shixia.colorpickerview.OnColorChangeListener;

public class ColorPickerActivity extends AppCompatActivity {

    private TextView tv;
    private ColorPickerView colorPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_picker);

        tv = findViewById(R.id.textview_color);
        colorPicker = findViewById(R.id.cpv_color);
        colorPicker.setOnColorChangeListener(new OnColorChangeListener() {
            @Override
            public void colorChanged(int color) {
                tv.setBackgroundColor(color);
                tv.setText("#" + Integer.toHexString(color));
            }
        });
    }
}
