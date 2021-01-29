package com.example.shopisthan_bolte;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    ImageView logoo;
    Handler handler;
    int progress= 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //color



        progressBar =(ProgressBar)findViewById(R.id.progressBar);
        setProgressValue(progress);
        progressBar.getProgressDrawable().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);

        //new activty
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this,Login_Activity.class);
                startActivity(intent);
                finish();
            }
        },3000);

    }

    private  void setProgressValue(final int progress){
        progressBar.setProgress(progress);


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);

                } catch (Exception exception) {
                    exception.printStackTrace();
                }setProgressValue(progress + 30);
            }
        });
        thread.start();
    }
}