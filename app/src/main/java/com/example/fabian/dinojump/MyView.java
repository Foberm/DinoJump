package com.example.fabian.dinojump;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.Timer;

public class MyView extends View {
    final GestureDetector gestureDetector;
    Bitmap mBitmap = null;
    Paint mCirclePaint;
    Rect mMeasuredRect;

    @Override
    public void onDraw(final Canvas canv) {
        Log.d("a", "b");
        canv.drawBitmap(mBitmap, null, mMeasuredRect, null);
        canv.drawRect(Player.bounding,mCirclePaint);
    }

    private void init(final Context ct) {
        mBitmap = BitmapFactory.decodeResource(ct.getResources(), R.drawable.proof);

        mCirclePaint = new Paint();
        mCirclePaint.setColor(Color.BLUE);
        mCirclePaint.setStyle(Paint.Style.FILL);
    }

    synchronized public void onSwipeTop() {
        Log.d("swipe", "top");

        new CountDownTimer(990, 33) {
            public void onTick(long millisUntilFinished) {
                Player.bounding.top-=5;
                Player.bounding.bottom-=5;
                invalidate();
            }
            public void onFinish() {        new CountDownTimer(660, 33) {
                public void onTick(long millisUntilFinished) {
                    Player.bounding.top+=7;
                    Player.bounding.bottom+=7;
                    invalidate();
                }
                public void onFinish() {}
            }.start();}
        }.start();



    }

    public void onSwipeBottom() {
        Log.d("swipe", "bottom");
    }


    /*-------------------DON'T EDIT----------------------------------------*/

    public MyView(final Context ct) {
        super(ct);
        gestureDetector = new GestureDetector(ct, new GestureListener());
        init(ct);
    }
    public MyView(final Context ct, final AttributeSet attrs) {
        super(ct, attrs);
        gestureDetector = new GestureDetector(ct, new GestureListener());

        init(ct);
    }
    public MyView(final Context ct, final AttributeSet attrs, final int defStyle) {
        super(ct, attrs, defStyle);
        gestureDetector = new GestureDetector(ct, new GestureListener());
        init(ct);
    }


    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mMeasuredRect = new Rect(0, 0, getMeasuredWidth(), getMeasuredHeight());
    }


       @Override
    public boolean onTouchEvent(final MotionEvent event) {
           return gestureDetector.onTouchEvent(event);
    }


    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight();
                        } else {
                            onSwipeLeft();
                        }
                        result = true;
                    }
                }
                else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        onSwipeBottom();
                    } else {
                        onSwipeTop();
                    }
                    result = true;
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }
    }

    public void onSwipeRight() {
    }

    public void onSwipeLeft() {
    }

}