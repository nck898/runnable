package com.example.runnable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.Vibrator;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    TextView timer, status, runCyclesText ;
    Button start, pause, reset;
    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L ;
    Handler handler;
    int Seconds, Minutes, MilliSeconds, runCycles = 0;
    boolean running = true;
    Vibrator v;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timer = (TextView)findViewById(R.id.tvTimer);
        start = (Button)findViewById(R.id.btStart);
        pause = (Button)findViewById(R.id.btPause);
        reset = (Button)findViewById(R.id.btReset);
        status = (TextView)findViewById(R.id.status);
        runCyclesText = (TextView)findViewById(R.id.runCycles);


        status.setText("Dyanmic Entry!!!");
        handler = new Handler() ;
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StartTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);

                reset.setEnabled(false);
                status.setText("running!");
                runCycles = 1;
                runCyclesText.setText("Run Cycles: " + runCycles);

            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimeBuff += MillisecondTime;

                handler.removeCallbacks(runnable);

                reset.setEnabled(true);

            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MillisecondTime = SystemClock.uptimeMillis() - StartTime;

                UpdateTime = TimeBuff + MillisecondTime;

                Seconds = (int) (UpdateTime / 1000);

                Minutes = Seconds / 60;

                Seconds = Seconds % 60;

                MilliSeconds = (int) (UpdateTime % 1000);

                status.setText("Time ran was " + Minutes + ":"  + String.format("%02d", Seconds) + ":" + String.format("%03d", MilliSeconds));

                MillisecondTime = 0L ;
                StartTime = 0L ;
                TimeBuff = 0L ;
                UpdateTime = 0L ;
                Seconds = 0 ;
                Minutes = 0 ;
                MilliSeconds = 0 ;

                timer.setText("00:00:00");
                runCycles = 0;
                runCyclesText.setText("Run Cycles Reset");

            }
        });

    }


    public Runnable runnable = new Runnable() {

        public void run() {

            MillisecondTime = SystemClock.uptimeMillis() - StartTime;

            UpdateTime = TimeBuff + MillisecondTime;

            Seconds = (int) (UpdateTime / 1000);

            Minutes = Seconds / 60;

            Seconds = Seconds % 60;

            MilliSeconds = (int) (UpdateTime % 1000);

            if(!running) { //if you're supposed to be running
                if ((Seconds-3)%4 == 0) {
                    v.vibrate(100);
                    running = true;
                    status.setText("walkin!");


                }
            } else {
                if (Seconds%4 == 0) {

                    v.vibrate(100);

                    running = false;
                    status.setText("running");
                    //Log.d("Run", "Run Working, seconds:" + Seconds);
                    runCycles += 1;
                    runCyclesText.setText("Run Cycles: " + runCycles);
                }
            }

            timer.setText("" + Minutes + ":"  + String.format("%02d", Seconds) + ":" + String.format("%03d", MilliSeconds));

            handler.postDelayed(this, 0);
        }

    };
}
