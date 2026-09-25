package com.androidstudio.stopwatch;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class ab extends AppCompatActivity {
    private TextView info, timer, milli, status;
    private Button back;

    private int seconds = 0;
    private boolean runing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.ab);


        back = findViewById(R.id.back);
        info = findViewById(R.id.aboutinfo);
        status = findViewById(R.id.status);
        timer = findViewById(R.id.Timer);
        milli = findViewById(R.id.milli);

        info.setText("MADE BY Jasmeet\nFROM ANDROID STUDIO\n");

        if(savedInstanceState != null){
            seconds = savedInstanceState.getInt("S");
            runing = savedInstanceState.getBoolean("r");
        }else{
            Intent gt = getIntent();
            if (gt != null){
                seconds = gt.getIntExtra("S", 0);
                runing = gt.getBooleanExtra("r", false);
            }

        }

        if(runing){
            status.setText("YOUR TIMER IS RUNNING!");
        }else{
            status.setText("");
        }

        runwatch();



        back.setOnClickListener(v -> {
            finish();
        });


    }
    private void runwatch(){
        Handler h = new Handler(Looper.getMainLooper());
        h.post(new Runnable(){
            @Override
            public void run(){
                int hours = (seconds / 3600000);
                int minutes = (seconds % 3600000) / 60000;
                int sec = (seconds % 60000) / 1000;
                int mili = (seconds % 1000) / 10;

                String time = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, sec);
                String mele = String.format(Locale.getDefault(), "%02d", mili);

                timer.setText(time);
                milli.setText(mele);

                if(runing){
                    seconds += 10;
                }
                h.postDelayed(this, 9);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle os){
        super.onSaveInstanceState(os);
        os.putInt("S", seconds);
        os.putBoolean("r", runing);
    }
}

