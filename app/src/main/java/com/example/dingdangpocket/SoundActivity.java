package com.example.dingdangpocket;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SoundActivity extends AppCompatActivity
        implements View.OnClickListener{

    private Button btn_start;
    private Button btn_stop;
    private TextView tv;
    private TextView tv2;

    private String []color = {"#767676", "#9bd81c", "#93c42d","#4f0093ff","#ffb700","#ff4c00"};
    private String [] description = {"寂静", "安静", "耳边的喃喃细语", "正常交谈", "吵闹", "开始损害听力"};

    static final int SAMPLE_RATE_IN_HZ = 8000;
    static final int BUFFER_SIZE = AudioRecord.getMinBufferSize(SAMPLE_RATE_IN_HZ,
            AudioFormat.CHANNEL_IN_DEFAULT, AudioFormat.ENCODING_PCM_16BIT);
    AudioRecord mAudioRecord;
    boolean isGetVoiceRun;
    Object mLock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound);

        btn_start = findViewById(R.id.button_sound_start);
        btn_stop = findViewById(R.id.button_sound_stop);
        tv = findViewById(R.id.textview_sound_db);
        tv2 = findViewById(R.id.textview_sound_dscp);
        tv2.setTextColor(Color.parseColor(color[0]));

        btn_stop.setOnClickListener(this);
        btn_start.setOnClickListener(this);

        mLock = new Object();


        PackageManager pm = getPackageManager();
        boolean permission = (PackageManager.PERMISSION_GRANTED ==
                pm.checkPermission("android.permission.RECORD_AUDIO", "com.example.dingdangpocket"));
        if (!permission) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        1);
            /*AlertDialog.Builder normalDialog =
                    new AlertDialog.Builder(SoundActivity.this);
            normalDialog.setTitle("提示");
            normalDialog.setMessage("请打开麦克风权限后重新打开应用！");
            normalDialog.setPositiveButton("确定",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //...To-do
                        }
                    });
            normalDialog.setNegativeButton("取消",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //...To-do
                        }
                    });
            // 显示对话框
            normalDialog.show();

            btn_start.setEnabled(false);
            btn_start.setEnabled(false);*/
        }

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.button_sound_start:
                getNoiseLevel();
                break;
            case R.id.button_sound_stop:
                isGetVoiceRun = false;
                tv.setText("0");
                tv2.setTextColor(Color.parseColor(color[0]));
                tv2.setText(description[0]);
                break;
            default:break;
        }
    }

    public void getNoiseLevel() {
        if (isGetVoiceRun) {
            return;
        }
        mAudioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
                SAMPLE_RATE_IN_HZ, AudioFormat.CHANNEL_IN_DEFAULT,
                AudioFormat.ENCODING_PCM_16BIT, BUFFER_SIZE);
        if (mAudioRecord == null) {

            return;
        }
        isGetVoiceRun = true;

        new Thread(new Runnable() {
            @Override
            public void run() {
            mAudioRecord.startRecording();
            short[] buffer = new short[BUFFER_SIZE];
            while (isGetVoiceRun) {
                //r是实际读取的数据长度，一般而言r会小于buffersize
                int r = mAudioRecord.read(buffer, 0, BUFFER_SIZE);
                long v = 0;
                // 将 buffer 内容取出，进行平方和运算
                for (int i = 0; i < buffer.length; i++) {
                    v += buffer[i] * buffer[i];
                }
                // 平方和除以数据总长度，得到音量大小。
                double mean = v / (double) r;
                double volume = 10 * Math.log10(mean);

                int temp = (int)volume;
                int t = 0;
                if(temp <= 15){
                    t = 0;
                }else if(temp <= 20){
                    t = 1;
                }else if(temp <= 40){
                    t = 2;
                }else if(temp <= 60){
                    t = 3;
                }else if(temp <= 70){
                    t = 4;
                }else t = 5;
                tv.setText(String.valueOf(temp));
                tv2.setTextColor(Color.parseColor(color[t]));
                tv2.setText(description[t]);

                // 大概一秒十次
                synchronized (mLock) {
                    try {
                        mLock.wait(400);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            mAudioRecord.stop();
            mAudioRecord.release();
            mAudioRecord = null;
            }
        }).start();
    }


}
