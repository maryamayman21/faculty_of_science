package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import com.example.myapplication.MainActivity;

public class Splash_act extends AppCompatActivity {

    private Handler handler = new Handler();

    private  Runnable runnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);



        SharedPreferences preferences = getSharedPreferences("userdetails", Context.MODE_PRIVATE);


        runnable=new Runnable() {
            @Override
            public void run() {

                if (preferences.contains("isUserLogin")) {
                    Intent intent22=new Intent(Splash_act.this, MainActivity.class);
                    startActivity(intent22);
                    finish();
                } else {
                    Intent intent = new Intent(Splash_act.this, login.class);
                    startActivity(intent);
                }




            }
        };
        handler.postDelayed(runnable,3500);





    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }
}