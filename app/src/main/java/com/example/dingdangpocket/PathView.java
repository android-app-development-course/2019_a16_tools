package com.example.dingdangpocket;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class PathView extends View {

    private Path mPath = null;
    private Paint mPaint = null;

    private static int HOUR = 0;
    private static int MINUTE = 0;
    private static int SECOND = 0;
    final Calendar mCalendar=Calendar.getInstance();

    private String[] times = new String[3];

    public PathView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPath = new Path();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.parseColor("#f90876"));
        mPaint.setStrokeWidth(2);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setTextSize(30.0f);


        // 启动每间隔一秒刷新一次界面
        Timer  timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                postInvalidate();
            }
        },0,1000);

    }

    public PathView(Context context) {
        this(context,null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //获取到当前view的宽和高
        int width = getWidth();
        int height = getHeight();

        mPaint.setColor(Color.parseColor("#f90876"));
        mPaint.setStrokeWidth(2);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setTextSize(30.0f);
        // 画表盘
        canvas.drawCircle(width/2,height/2,300,mPaint);
        // 表心，一个点
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawPoint(width/2,height/2,mPaint);
        // 保存画布
        canvas.save();
        // 首先旋转30度，保证第一个绘制在30度的位置，和数字保持一致
        canvas.rotate(30,width/2,height/2);
        // 绘制时针刻度
        for (int i=1; i< 13; i++) {
            canvas.drawLine(width/2,height/2-300,width/2,height/2-300+40,mPaint);
            canvas.drawText(""+i,width/2 -10,height/2-300+50,mPaint);
            // canvas默认旋转中心为左上角，可以调用这个方法，后面两个参数是旋转中心的坐标
            canvas.rotate(30,width/2,height/2);
        }


        // 绘制分针刻度
        for (int j=1; j< 13; j++) {
            for (int k = 1; k < 4; k++) {
                mPaint.setColor(Color.BLUE);
                canvas.rotate(360/48f,width/2,height/2);
                canvas.drawLine(width/2,height/2-300,width/2,height/2-300+20,mPaint);
            }
            canvas.rotate(360/48f,width/2,height/2);
        }
        canvas.restore();

        Calendar mCalendar=Calendar.getInstance();
        HOUR = mCalendar.get(Calendar.HOUR);
        MINUTE = mCalendar.get(Calendar.MINUTE);
        SECOND = mCalendar.get(Calendar.SECOND);

        canvas.save();
        mPaint.setStrokeWidth(8);
        mPaint.setColor(Color.GREEN);
        canvas.rotate(360/12f*HOUR + (MINUTE / 60f * 30),width/2,height/2);
        canvas.drawLine(width/2,height/2,width/2,height/2-300+150,mPaint);
        canvas.restore();

        canvas.save();
        mPaint.setStrokeWidth(8);
        mPaint.setColor(Color.RED);
        canvas.rotate(MINUTE / 60f * 360 + SECOND / 60 * 6,width/2,height/2);
        canvas.drawLine(width/2,height/2,width/2,height/2-300+100,mPaint);
        canvas.restore();

        canvas.save();
        mPaint.setStrokeWidth(4);
        mPaint.setColor(Color.YELLOW);
        canvas.rotate(SECOND * 6,width/2,height/2);
        canvas.drawLine(width/2,height/2,width/2,height/2-300+80,mPaint);
        canvas.restore();
    }

}
