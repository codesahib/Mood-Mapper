package com.example.moodmapper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        TextView appName = findViewById(R.id.welcomeAppName);
        View rectangleUp = findViewById(R.id.welcomeRectangleUp);
        View rectangleDown = findViewById(R.id.welcomeRectangleDown);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                SharedPreferences preferences = getSharedPreferences("PREFERENCE",MODE_PRIVATE);
//              preferences.edit().remove("UserName").commit();
                String UserName = preferences.getString("UserName","");

                Intent intent;
                if(UserName != ""){ // Username exists so start main activity directly
                    intent = new Intent(WelcomeActivity.this,
                            MainActivity.class);

                }
                else{
                    intent = new Intent(WelcomeActivity.this,
                            IntroActivity.class);
                }
                // Start Animations
                appName.startAnimation(AnimationUtils.loadAnimation(
                        getApplicationContext(),
                        R.anim.fade_out));
                rectangleUp.startAnimation(AnimationUtils.loadAnimation(
                        getApplicationContext(),
                        R.anim.slide_up));
                rectangleDown.startAnimation(AnimationUtils.loadAnimation(
                        getApplicationContext(),
                        R.anim.slide_down));
                startActivity(intent);
                finish();
            }
        }, 2000);
    }
}