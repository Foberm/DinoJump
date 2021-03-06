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

public class MyView extends View {
    final GestureDetector gestureDetector;
    Bitmap mBitmap = null;
    Paint mCirclePaint;
    Rect mMeasuredRect, bitRect;
    Bitmap mBitmapA, dino = null;

    @Override
    public void onDraw(final Canvas canv) {

        canv.drawBitmap(mBitmap, bitRect, mMeasuredRect, null);
        //canv.drawBitmap(mBitmapA, bitRect, mMeasuredRect, null);
        canv.drawBitmap(dino,null, Player.bounding, null);
    }
    int backgroundX=0;
    private void init(final Context ct) {
        mBitmapA = BitmapFactory.decodeResource(ct.getResources(), R.drawable.dinojump_background);
        dino = BitmapFactory.decodeResource(ct.getResources(), R.drawable.cute_dino);
        mBitmap = Bitmap.createBitmap(mBitmapA, backgroundX, 0, mBitmapA.getWidth()/2, mBitmapA.getHeight());

        bitRect=new Rect(0,0,mBitmapA.getWidth()/4, mBitmapA.getHeight());

        new CountDownTimer(990, 33) {
            public void onTick(long millisUntilFinished) {
               // backgroundX+=2;
               // mBitmap = null;
              //  mBitmap = Bitmap.createBitmap(mBitmapA, backgroundX, 0, mBitmapA.getWidth()/2, mBitmapA.getHeight());
               bitRect.left+=5;
               bitRect.right+=5;
                invalidate();
            }
            public void onFinish() {this.start();}
        }.start();
    }

    synchronized public void onSwipeTop() {
        if(allowMove) {
            allowMove = false;
            new CountDownTimer(500, 33) {
                public void onTick(long millisUntilFinished) {
                    Player.bounding.top -= 20;
                    Player.bounding.bottom -= 20;
                }

                public void onFinish() {
                    new CountDownTimer(400, 33) {
                        public void onTick(long millisUntilFinished) {
                            Player.bounding.top += 25;
                            Player.bounding.bottom += 25;
                        }

                        public void onFinish() {
                            Player.bounding.top = 800;
                            Player.bounding.bottom = 1000;
                            allowMove = true;
                        }
                    }.start();
                }
            }.start();


        }
    }
    public void onSwipeBottom() {
        if(allowMove) {
            allowMove = false;
            new CountDownTimer(660, 33) {
                public void onTick(long millisUntilFinished) {
                    Player.bounding.top += 5;
                }

                public void onFinish() {
                    new CountDownTimer(660, 33) {
                        public void onTick(long millisUntilFinished) {
                            Player.bounding.top -= 5;
                        }

                        public void onFinish() {
                            Player.bounding.top=800;
                            allowMove = true;
                        }
                    }.start();
                }
            }.start();
        }
    }
boolean allowMove=true;
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

        private static final int SWIPE_THRESHOLD = 30;
        private static final int SWIPE_VELOCITY_THRESHOLD = 1;

        @Override
        public boolean onDown(final MotionEvent e) {
            drin=false;
            new CountDownTimer(200, 1001) {
                public void onTick(long millisUntilFinished) {
                }
                public void onFinish() {
                    if(!drin){

                        onSwipeTop();
                    }
                }
            }.start();
            return true;
        }
        boolean drin=false;
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            boolean result = false;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        drin=true;
                        if (diffX > 0) {
                            onSwipeRight();
                        } else {
                            onSwipeLeft();
                        }
                        result = true;
                    }
                }
                else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    drin=true;
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