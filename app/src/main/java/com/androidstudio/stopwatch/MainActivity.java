package com.androidstudio.stopwatch;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private int seconds = 0;
    private boolean runing = false;
    private boolean wasruning = false;
    private TextView timer, milli, sttat;
    private Button start, stop, reset, about;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timer = findViewById(R.id.Timer);
        milli = findViewById(R.id.milli);
        sttat = findViewById(R.id.status);
        start = findViewById(R.id.Start);
        stop = findViewById(R.id.Stop);
        reset = findViewById(R.id.Reset);
        about = findViewById(R.id.about);
        hider(View.VISIBLE, View.INVISIBLE, View.INVISIBLE);
        runwatch();

        start.setOnClickListener(v -> {
            if(!runing){
                runing = true;
            }
            hider(View.INVISIBLE, View.VISIBLE, View.VISIBLE);
            sttat.setText("Running");

        });
        stop.setOnClickListener(v -> {
            runing = false;
            hider(View.VISIBLE, View.INVISIBLE, View.VISIBLE);
            sttat.setText("STOPPED!");
            
        });
        reset.setOnClickListener(v -> {
            runing = false;
            seconds = 0;
            hider(View.VISIBLE, View.INVISIBLE, View.INVISIBLE);
            sttat.setText("StopWatch");
        });
        about.setOnClickListener(v -> {
            Intent n = new Intent(MainActivity.this, ab.class);
            n.putExtra("S", seconds);
            n.putExtra("r", runing);
            startActivity(n);
        });

        if (savedInstanceState != null){
            seconds = savedInstanceState.getInt("S");
            runing = savedInstanceState.getBoolean("r");
            wasruning = savedInstanceState.getBoolean("wr");
            if (runing) {
                hider(View.INVISIBLE, View.VISIBLE, View.VISIBLE);
                sttat.setText("RUNNING");
            } else if (seconds > 0) {
                hider(View.VISIBLE, View.INVISIBLE, View.VISIBLE);
                sttat.setText("STOPPED!");
            } else {
                hider(View.VISIBLE, View.INVISIBLE, View.INVISIBLE);
                sttat.setText("StopWatch");
            }

        }
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
                h.postDelayed(this,9);
            }
        });


    }

    @Override
    protected void onSaveInstanceState(Bundle os){
        super.onSaveInstanceState(os);
        os.putInt("S", seconds);
        os.putBoolean("r", runing);
        os.putBoolean("wr", wasruning);
    }

    public void hider(int st, int stp, int rst){
        start.setVisibility(st);
        stop.setVisibility(stp);
        reset.setVisibility(rst);
    }
}