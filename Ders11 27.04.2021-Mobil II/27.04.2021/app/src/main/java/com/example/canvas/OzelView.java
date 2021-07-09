package com.example.canvas;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class OzelView extends View {

    Paint mBoyama;
    Path mYol;

    Paint mPaint, otherPaint, outerPaint, mTextPaint;
    RectF mRectF;

    float arcLeft, arcTop, arcRight, arcBottom;
    Path mPath; //yol tanımlama, çizim sırasında çizimin başlayacağı noktaya hareket etmemizi sağlar.
    //sonrasında belirlenen noktalar arasında çizim çizebiliriz. Bu sadece yapabileceklerimizin bir kısmı.

    int mPadding;
    public OzelView(Context context) {
        super(context);
        //yapiciOnTanimlama(context);
        yapiciIkinciOnTanimlama(context);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //onCizim(canvas);
        onCizimIkinci(canvas);
    }

    public void onCizim(Canvas canvas){
        canvas.drawRoundRect(mRectF,10,10,otherPaint);
        canvas.clipRect(mRectF, Region.Op.DIFFERENCE);
        canvas.drawPaint(outerPaint);

        canvas.drawLine(250,250,400,400,mPaint);
        canvas.drawRect(mPadding,mPadding,getWidth()-mPadding,getHeight()-mPadding,mPaint);

        otherPaint.setColor(Color.GREEN);
        otherPaint.setStyle(Paint.Style.FILL);

        canvas.drawRect(getLeft()+(getRight()-getLeft())/3, getTop()+(getBottom()-getTop())/3,
                getRight()-(getRight()-getLeft())/3,
                getBottom()-(getBottom()-getTop())/3,otherPaint);

        canvas.drawPath(mPath,mPaint);
        otherPaint.setColor(Color.BLACK);
        canvas.drawCircle(getWidth()/2,getHeight()/2,arcLeft,otherPaint);
        canvas.drawText("Mobil Cizim",(float)(getWidth()*0.3),(float)(getHeight()*0.8),mTextPaint);
    }

    public void onCizimIkinci(Canvas canvas){
        canvas.drawPath(mYol,mBoyama);
    }

    public void yapiciOnTanimlama(Context context){
        mPaint=new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(5);

        mTextPaint=new Paint(Paint.LINEAR_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.BLACK);


        int genislik=getResources().getDisplayMetrics().widthPixels;
        int yukseklik=getResources().getDisplayMetrics().heightPixels;

        genislik=context.getResources().getDisplayMetrics().widthPixels;
        yukseklik=context.getResources().getDisplayMetrics().heightPixels;

        float yogunluk=context.getResources().getDisplayMetrics().density;

        float px=pxFromDp(context,24);
        Log.i("pxFromDp:",String.valueOf(px));
        mTextPaint.setTextSize(pxFromDp(context,24));

        otherPaint=new Paint();
        outerPaint=new Paint();
        outerPaint.setStyle(Paint.Style.FILL);
        outerPaint.setColor(Color.YELLOW);
        mPadding=100;


        DisplayMetrics displayMetrics=new DisplayMetrics();
        ((Activity)getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int screenWidth=displayMetrics.widthPixels;
        int screenHeight=displayMetrics.heightPixels;

        arcLeft=pxFromDp(context,20);
        arcTop=pxFromDp(context,20);
        arcRight=pxFromDp(context,20);
        arcBottom=pxFromDp(context,20);

        Point p1=new Point((int)pxFromDp(context,80)+(screenWidth/2),(int)pxFromDp(context,40));
        Point p2=new Point((int)pxFromDp(context,40)+(screenWidth/2),(int)pxFromDp(context,80));
        Point p3=new Point((int)pxFromDp(context,120)+(screenWidth/2),(int)pxFromDp(context,80));

        mPath=new Path();
        mPath.moveTo(p1.x,p1.y);
        mPath.lineTo(p2.x,p2.y);
        mPath.lineTo(p3.x,p3.y);
        mPath.close();

        mRectF=new RectF(screenWidth/4,screenHeight/3,screenWidth/6,screenHeight/2);
    }
    public void yapiciIkinciOnTanimlama(Context context){
        mBoyama=new Paint();
        mYol=new Path();

        mBoyama.setAntiAlias(true);
        mBoyama.setStrokeWidth(3f); //çizim kalınlıgı
        mBoyama.setColor(Color.BLACK); //çizim rengi
        mBoyama.setStyle(Paint.Style.STROKE); //çizim sitili
        mBoyama.setStrokeJoin(Paint.Join.ROUND); //çizim birleştirme
    }

    public static float pxFromDp(final Context context, final float dp){
        return dp*context.getResources().getDisplayMetrics().density;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //return super.onTouchEvent(event);

        float x=event.getX();
        float y=event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mYol.moveTo(x,y);
                break;
            case MotionEvent.ACTION_MOVE:
                mYol.lineTo(x,y);
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                return false;
        }

        invalidate();
        return true;
    }
}
