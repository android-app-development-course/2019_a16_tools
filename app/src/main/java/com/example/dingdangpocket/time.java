package com.example.dingdangpocket;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.datepicker.CustomDatePicker;
import com.datepicker.DateFormatUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class time extends AppCompatActivity implements View.OnClickListener{

    private TextView mTvSelectedDate,live_time,live_days,live_hours;
    private CustomDatePicker mDatePicker;
    ClockView clockView;
    int width,height;
    String currenttime,birthday,time_sec;
    SimpleDateFormat sdf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);
        clockView=(ClockView)findViewById(R.id.clock);
        findViewById(R.id.ll_date).setOnClickListener(this);
        live_time=(TextView)findViewById(R.id.live_time);
        live_days=(TextView)findViewById(R.id.live_day);
        live_hours=(TextView)findViewById(R.id.live_hour);
        mTvSelectedDate = findViewById(R.id.tv_selected_date);
        DisplayMetrics dm = getResources().getDisplayMetrics();

        initDatePicker();

        ViewGroup.MarginLayoutParams margin = new ViewGroup.MarginLayoutParams(clockView.getLayoutParams());
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(margin);
        layoutParams.width = dm.widthPixels*2/3 ;
        layoutParams.height = dm.widthPixels*2/3 ;
        //设置正中位置
        layoutParams.leftMargin=dm.widthPixels/2-dm.widthPixels/3;
        clockView.setLayoutParams(layoutParams);
    }

    public void onClick(View v)
    {
        mDatePicker.show(mTvSelectedDate.getText().toString());
    }
    private void initDatePicker(){


        long beginTimestamp = DateFormatUtils.str2Long("1980-01-01", false);
        long endTimestamp = System.currentTimeMillis();

        mTvSelectedDate.setText(DateFormatUtils.long2Str(endTimestamp, false));
        currenttime=DateFormatUtils.long2Str(endTimestamp,false);
        time_sec=DateFormatUtils.long2Str(endTimestamp,true);

        // 通过时间戳初始化日期，毫秒级别
        mDatePicker = new CustomDatePicker(this, new CustomDatePicker.Callback() {
            @Override
            public void onTimeSelected(long timestamp) throws ParseException {
                mTvSelectedDate.setText(DateFormatUtils.long2Str(timestamp, false));
                birthday=mTvSelectedDate.getText().toString();
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                Date date1=sdf.parse(currenttime);
                Date date2=sdf.parse(birthday);
                double time_day=daysBetween(date2,date1);

                String time_hour=time_sec.substring(11,13);
                String time_min=time_sec.substring(14,15);
                double hour=time_day*24+Integer.parseInt(time_hour)+Integer.parseInt(time_min)/60.0;
                time_day=hour/24.0;
                double year=time_day/365;
                String years=String.valueOf(year);
                if(years.length()>11)
                {
                    years=years.substring(0,11);
                }
                String days=String.valueOf(time_day);
                if(days.length()>11)
                {
                    days=days.substring(0,11);
                }

                String hours=String.valueOf(hour);
                if(hours.length()>11)
                {
                    hours=hours.substring(0,11);
                }
                live_time.setText(years+"    年");
                live_days.setText("累计   "+days+"    天");
                live_hours.setText("累计   "+hours+"    小时");
            }
        }, beginTimestamp, endTimestamp);
        // 不允许点击屏幕或物理返回键关闭
        mDatePicker.setCancelable(false);
        // 不显示时和分
        mDatePicker.setCanShowPreciseTime(false);
        // 不允许循环滚动
        mDatePicker.setScrollLoop(false);
        // 不允许滚动动画
        mDatePicker.setCanShowAnim(false);

    }

    /**
     * 计算两个日期之间相差的天数
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     * calendar 对日期进行时间操作
     * getTimeInMillis() 获取日期的毫秒显示形式
     */
    public static int daysBetween(Date smdate,Date bdate) throws ParseException
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);
        return Integer.parseInt(String.valueOf(between_days));
    }
    /**
     * 字符串日期格式的计算
     * @param smdate
     * @param bdate
     * @return
     * @throws ParseException
     */
    public static int daysBetween(String smdate,String bdate) throws ParseException{
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(smdate));
        long time1 = cal.getTimeInMillis();
        cal.setTime(sdf.parse(bdate));
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);
        return Integer.parseInt(String.valueOf(between_days));
    }
    /**
     * 字符串日期格式和date日期格式的计算
     * @param smdate
     * @param bdate
     * @return
     * @throws ParseException
     */
    public static int daysBetween(String smdate,Date bdate) throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(smdate));
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);
        return Integer.parseInt(String.valueOf(between_days));
    }

}
