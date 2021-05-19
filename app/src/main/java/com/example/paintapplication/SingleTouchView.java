package com.example.paintapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.content.ContextCompat;

import java.io.File;

public class SingleTouchView extends View {
    private Paint paint = new Paint();
    private Path path = new Path();
    private int paintColor = 0xFF000000;
    private Paint canvasPaint;
    private Canvas drawCanvas;
    private Bitmap canvasBitmap;
    private float touchX,touchY;
    private int x,y;
    private int rect_w,rect_h;
    private int circle_size=40;

    public SingleTouchView(Context context, AttributeSet attrs) {
        super(context, attrs);

        paint.setAntiAlias(true);
        // 펜크기 조절 함수
        paint.setStrokeWidth(10f);
        //펜 색 지정
        paint.setColor(paintColor);
        paint.setStyle(Paint.Style.STROKE);
        //펜이 끝날때 둥그렇게 끝나도록 함
        paint.setStrokeJoin(Paint.Join.ROUND);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        paint.setStyle(Paint.Style.STROKE);

        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
        canvasPaint = new Paint(Paint.DITHER_FLAG);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setStyle(Paint.Style.STROKE);

        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        canvas.drawPath(path, paint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {//펜을 움직일때마다 발생하는 이벤
        touchX = event.getX();
        touchY = event.getY();
        x = (int)touchX;
        y = (int)touchY;

        rect_w = x +60;
        rect_h = y +60;
        paint.setStyle(Paint.Style.STROKE);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
                drawCanvas.drawPath(path, paint);
                path.reset();
                break;
            default:
                return false;
        }
        invalidate();
        return true;

    }
    public void newDraw() {
        canvasBitmap.eraseColor(Color.TRANSPARENT);
        paint.setStyle(Paint.Style.STROKE);
        drawCanvas.drawBitmap(canvasBitmap, 0, 0, paint);
    }

    public void setColor(String newColor) {
        invalidate();
        //전체 뷰의 무효화, 즉 현재 View에 나타나 있는 모든 그림 및 이미지들을 무효화하여 화면에 나타나지 않게 한다.
        paintColor = Color.parseColor(newColor);
        paint.setColor(paintColor);
    }


    public void small(){ //펜의 크기 small
        rect_w = x+20;
        rect_h = y+20;
        circle_size = 20;
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeWidth(5f);


    }
    public void medium(){ //펜의 크기 medium
        rect_w = x+60;
        rect_h = y+60;
        circle_size = 40;
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeWidth(10f);


    }
    public void large(){//펜의 크기 large
        rect_w = x+100;
        rect_h = y+100;
        circle_size =80;
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeWidth(30f);


    }
    public void backcolor(){
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(paintColor);
        drawCanvas.drawColor(ContextCompat.getColor(getContext(), R.color.black));
    }

    public void draw_rect(){
        paint.setStrokeWidth(10f);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(paintColor);
        drawCanvas.drawRect(new Rect(x,y,rect_w,rect_h),paint);
    }
    public void draw_circle(){
        paint.setStrokeWidth(10f);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(paintColor);
        drawCanvas.drawCircle(x,y,circle_size,paint);
    }

    public void load(){
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        Bitmap bm = BitmapFactory.decodeFile(dir+"/picture.png");
        Log.d("사진 경로",dir+"/picture.png");
        drawCanvas.drawBitmap(bm,0,0,new Paint());
    }

}

