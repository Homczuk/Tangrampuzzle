package com.example.tangrampuzzle;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;


public class MainActivity extends AppCompatActivity {

    private ViewGroup mainLayout;

    private ImageView image;
    private ImageView image2, image3, image4, image5, image6, image7;
    private View background;
    private int level_id;
    private int xDelta;
    private int yDelta;
    private long timerStart;
    private long timerStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button Select = findViewById(R.id.button);
        Select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLevelSelectClicked();
            }
        });
        mainLayout = (RelativeLayout) findViewById(R.id.main);
        image = findViewById(R.id.image);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);
        image4 = findViewById(R.id.image4);
        image5 = findViewById(R.id.image5);
        image6 = findViewById(R.id.image6);
        image7 = findViewById(R.id.image7);
        background = findViewById(R.id.view);

        image.setOnTouchListener(onTouchListener());
        image2.setOnTouchListener(onTouchListener());
        image3.setOnTouchListener(onTouchListener());
        image4.setOnTouchListener(onTouchListener());
        image5.setOnTouchListener(onTouchListener());
        image6.setOnTouchListener(onTouchListener());
        image7.setOnTouchListener(onTouchListener());

    }


    private View.OnTouchListener onTouchListener() {
        return new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                final int x = (int) event.getRawX();
                final int y = (int) event.getRawY();

                switch (event.getAction() & MotionEvent.ACTION_MASK) {

                    case MotionEvent.ACTION_DOWN:
                        timerStart = SystemClock.uptimeMillis();
                        RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams)
                                view.getLayoutParams();
                        xDelta = x - lParams.leftMargin;
                        yDelta = y - lParams.topMargin;
                        break;

                    case MotionEvent.ACTION_UP:
                        view.performClick();
                        timerStop = SystemClock.uptimeMillis();
                        if ((timerStop - timerStart) <= 150) {
                            float rot = view.getRotation();
                            rot += 45.0;
                            if (rot > 360.0) {
                                rot -= 360.0;
                            }
                            view.setRotation(rot);
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                                view.getLayoutParams();
                        layoutParams.leftMargin = x - xDelta;
                        layoutParams.topMargin = y - yDelta;
                        layoutParams.rightMargin = 0;
                        layoutParams.bottomMargin = 0;
                        view.setLayoutParams(layoutParams);
                        break;
                }
                mainLayout.invalidate();
                return true;
            }
        };
    }

    public void onLevelSelectClicked() {
        Intent intentLevel = new Intent (getApplicationContext(), LevelSelect.class);
        startActivityForResult(intentLevel, 1);
    }
    @Override
    protected void onActivityResult (int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1: {
                if (data == null) break;
                int id = data.getIntExtra("LEVEL_ID",0);
                TypedArray level = getResources().obtainTypedArray(R.array.Level);
                background.setBackground(ContextCompat.getDrawable(this, level.getResourceId(id,0)));
                level.recycle();
                break;
            }
        }
    }
}
