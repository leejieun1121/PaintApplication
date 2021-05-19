package com.example.paintapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    private SingleTouchView drawView;
    private ImageButton currPaint;
    private ImageButton new_btn,draw_btn,erase_btn,save_btn,load_btn;
    private ImageButton small,medium,large,back;
    private ImageButton circle,rect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawView = (SingleTouchView) findViewById(R.id.drawing);
        LinearLayout paintLayout = (LinearLayout) findViewById(R.id.paint_colors);
        currPaint = (ImageButton) paintLayout.getChildAt(0);


        //xml의 id와 변수를 매칭해
        new_btn = findViewById(R.id.new_btn);
        erase_btn = findViewById(R.id.erase_btn);
        save_btn = findViewById(R.id.save_btn);
        draw_btn = findViewById(R.id.draw_btn);
        load_btn = findViewById(R.id.load_btn);
        small = findViewById(R.id.small);
        medium = findViewById(R.id.medium);
        large = findViewById(R.id.large);

        circle = findViewById(R.id.circle);
        rect = findViewById(R.id.rect);

        back = findViewById(R.id.back);
        new_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawView.newDraw();
            }
        });

        erase_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawView.setColor("#FFFFFFFF");

            }
        });
//그리기 함수
        draw_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getTag()=="FF000000"){
                    drawView.setColor("#FF000000");
                    currPaint = (ImageButton) v;
                }else if(v.getTag() == "#FFFF0000"){
                    drawView.setColor("#FF000000");
                    currPaint = (ImageButton) v;
                }else if(v.getTag()=="#FFFF6600")
                {
                    drawView.setColor("#FFFF6600");
                    currPaint = (ImageButton) v;
                }else if(v.getTag()=="#FFFFCC00"){
                    drawView.setColor("#FFFFCC00");
                    currPaint = (ImageButton) v;
                }
                else if(v.getTag()=="#FF009900"){
                    drawView.setColor("#FF009900");
                    currPaint = (ImageButton) v;
                }else if(v.getTag()=="#FFFFFFFF"){
                    drawView.setColor("#FFFFFFFF");
                    currPaint = (ImageButton) v;
                }
                else{
                    drawView.setColor("#FF000000");
                    currPaint = (ImageButton) v;
                }
            }
        });
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
        //저장한 그림 로딩해오는 함수호
        load_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawView.load();
            }
        });

        small.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawView.small();
            }
        });
        medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawView.medium();

            }
        });
        large.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawView.large();

            }
        });
//그림판 배경 검정색으로 만들어주는 함
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawView.backcolor();

            }
        });

        circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawView.draw_circle();
            }
        });
        rect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawView.draw_rect();
            }
        });
    }

    //지정해놓은 tag에 따라 색을 지정해줌
    public void clicked(View view) {
        if (view != currPaint) {
            String color = view.getTag().toString();
            drawView.setColor(color);
            currPaint = (ImageButton) view;
        }
    }

    //그림 저장하는 함
    public void save() {
        drawView.setDrawingCacheEnabled(true);    // 캐쉬허용
        Bitmap screenshot = Bitmap.createBitmap(drawView.getDrawingCache());
        drawView.setDrawingCacheEnabled(false);
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        if (!dir.exists())

            dir.mkdirs();

        FileOutputStream fos;

        try {

            fos = new FileOutputStream(new File(dir, "picture.png"));

            screenshot.compress(Bitmap.CompressFormat.PNG, 100, fos);

            fos.close();

            Toast.makeText(this, "저장 성공", Toast.LENGTH_SHORT).show();


        } catch (Exception e) {

            Log.e("photo", "그림저장오류", e);

            Toast.makeText(this, "저장 실패", Toast.LENGTH_SHORT).show();

        }
    }

}

