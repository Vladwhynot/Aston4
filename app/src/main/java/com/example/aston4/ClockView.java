package com.example.aston4;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.Calendar;

public class ClockView extends View {

    private int height, width = 0;
    private int padding = 0;
    private int fontSize = 0;
    private int numeralSpacing = 0;
    private int handTruncation,hourHandTruncation = 0;
    private int radius = 0;
    private Paint paint;
    private boolean isInit;
    private RectF rect =new RectF();



    public ClockView(Context context) {
        super(context);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initClock(){
        height = getHeight();
        width = getWidth();
        padding = numeralSpacing + 50;
        fontSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 13,
                getResources().getDisplayMetrics());
        int min = Math.min(height,width);
        radius = min / 2 - padding;
        handTruncation = min / 20;
        hourHandTruncation = min / 7;
        paint = new Paint();
        isInit =true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!isInit){
            initClock();
        }

        canvas.drawColor(Color.WHITE);
        drawCircle(canvas);
        drawLine(canvas);
        drawHands(canvas);
        postInvalidateDelayed(500);
        invalidate();
    }


    private void drawHand(Canvas canvas, double loc, int isHour){
       double angle = Math.PI * (loc / 30) - (Math.PI / 2);
       int handRadius;

        if (isHour == 3){
            handRadius = (radius - handTruncation - hourHandTruncation) / 2;
            canvas.drawLine((float) (width / 2 - Math.cos(angle) * 25), (float) (height / 2 - Math.sin(angle) * 25),
                    (float) (width / 2 + Math.cos(angle) * handRadius),
                    (float) (height / 2 +  Math.sin(angle) * handRadius),
                    paint);

        }
        else if (isHour == 2){
            handRadius = radius - hourHandTruncation;
            canvas.drawLine((float) (width / 2 - Math.cos(angle) * 35), (float) (height / 2 - Math.sin(angle) * 35),
                    (float) (width / 2 + Math.cos(angle) * handRadius),
                    (float) (height / 2 +  Math.sin(angle) * handRadius),
                    paint);
        }
        else{
            handRadius = radius - handTruncation;
            canvas.drawLine((float) (width / 2 - Math.cos(angle) * 50), (float) (height / 2 - Math.sin(angle) * 50),
                    (float) (width / 2 + Math.cos(angle) * handRadius),
                    (float) (height / 2 +  Math.sin(angle) * handRadius),
                    paint);
        }
    }


    private void drawHands(Canvas canvas){
        Calendar c = Calendar.getInstance();
        float hour = c.get(Calendar.HOUR_OF_DAY);
        hour = hour > 12 ? hour - 12 : hour;

        paint.setColor(getResources().getColor(android.R.color.black));
        paint.setStrokeWidth(20);
        drawHand(canvas, (hour + c.get(Calendar.MINUTE) / 60) * 5f,1);

        paint.setColor(getResources().getColor(android.R.color.holo_red_dark));
        paint.setStrokeWidth(10);
        drawHand(canvas,c.get(Calendar.MINUTE), 2);

        paint.setColor(getResources().getColor(android.R.color.holo_blue_bright));
        paint.setStrokeWidth(5);
        drawHand(canvas,c.get(Calendar.SECOND), 3);
    }


    public void drawLine(Canvas canvas){
        paint.setTextSize(fontSize);
        paint.setColor(getResources().getColor(android.R.color.black));
        for (int number = 0; number < 12; number++ ){
            double angle = Math.PI / 6 * (number -3);
            int x = (int) (width / 2 + Math.cos(angle) * radius - rect.width() / 2);
            int y = (int) (height / 2 + Math.sin(angle) * radius + rect.height() / 2);
            int x2 = (int) ((width / 2 + Math.cos(angle) * radius - rect.width() / 2) + Math.cos(angle) * 40);
            int y2 = (int) ((height / 2 + Math.sin(angle) * radius + rect.height() / 2) + Math.sin(angle) * 40);
            canvas.drawLine(x,y,x2,y2,paint);
        }

    }

    private void drawCircle(Canvas canvas){
        paint.reset();
        paint.setColor(getResources().getColor(android.R.color.black));
        paint.setStrokeWidth(20);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        canvas.drawCircle(width / 2,height / 2 , radius + padding - 10, paint);
    }


}
