package com.example.administrator.tearcloth;

import android.app.Activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewSwitcher;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.content.Context;
import android.media.MediaPlayer;
import com.example.administrator.tearcloth.NatureActivity;
import java.io.InputStream;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class NatureClothActivity extends Activity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private  int imageid;
    public int screenwidth;
    public int screenheigth;
    private Rect fullRect;

    int[] ClothRemoveId = new int[]{
            R.drawable.after00, R.drawable.after01, R.drawable.after02,
            R.drawable.after3, R.drawable.after4, R.drawable.after11,
            R.drawable.after12, R.drawable.after32, R.drawable.after40,
            R.drawable.after52,R.drawable.after62
    };

   public  NatureClothActivity(){}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_nature);
        Intent readintent = getIntent();
        imageid = readintent.getIntExtra("imageid", 0);
        DisplayMetrics dismeter = new DisplayMetrics();
        dismeter = getResources().getDisplayMetrics();
        screenwidth = dismeter.widthPixels;
        screenheigth = dismeter.heightPixels;
        fullRect = new Rect(0,0,screenwidth,screenheigth);
        setContentView(new removeView(this));
    }

    private  class removeView extends View{
        private Bitmap mBitmap,pBitmap,tBitmap;
        private Canvas mCanvas;
        private Paint mPaint;
        private Path mPath;
        private float mX, mY;

        private removeView(Context context){
            super(context);
            setBackgroundResource(ClothRemoveId[imageid]);
            initPaint();
            initBitmap();
        }
        private  void initPaint(){
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
            mPaint.setAlpha(100);
            mPaint.setDither(true);
            mPaint.setFilterBitmap(true);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeJoin(Paint.Join.ROUND);
            mPaint.setStrokeCap(Paint.Cap.ROUND);
            mPaint.setStrokeWidth(35);
            mPath = new Path();
        }
        private void initBitmap(){
            BitmapFactory.Options opt = new BitmapFactory.Options();
            opt.inPreferredConfig = Config.ARGB_8888;   //it can optimize 1/2 memory
            opt.inPurgeable = true;
            opt.inInputShareable = true;
            opt.inSampleSize = 2;    //if picture very large,should set it! by zxw
            InputStream is = getResources().openRawResource(NatureActivity.imageIds[imageid]);
            mBitmap = BitmapFactory.decodeStream(is, null, opt).copy(Config.ARGB_8888,true);
            tBitmap = Bitmap.createScaledBitmap(mBitmap,screenwidth,screenheigth,true);
            mCanvas = new Canvas();
            pBitmap = Bitmap.createBitmap(screenwidth,screenheigth,Config.ARGB_8888).copy(Config.ARGB_8888,true);
            mCanvas.setBitmap(pBitmap);
            mCanvas.drawBitmap(tBitmap,fullRect,fullRect,null);

        }
        public void onDraw(Canvas cas){
            super.onDraw(cas);
            mCanvas.drawPath(mPath, mPaint);
            cas.drawBitmap(pBitmap, fullRect, fullRect, null);
        }
        public  boolean onTouchEvent(MotionEvent event){
            float x = event.getX();
            float y = event.getY();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mPath.reset();
                    mPath.moveTo(x, y);
                    mX = x;
                    mY = y;
                    //invalidate();
                    break;
                case MotionEvent.ACTION_MOVE:
                    mPath.quadTo(mX,mY,x,y);
                    mX = x;
                    mY = y;
                    invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    mPath.lineTo(mX,mY);
                    invalidate();
                    break;
            }
            return true;

        }
    }
}


