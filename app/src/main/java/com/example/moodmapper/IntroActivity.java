package com.example.moodmapper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class IntroActivity extends AppCompatActivity {
    Button introButton;
    EditText introName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        SharedPreferences preferences = getSharedPreferences("PREFERENCE",MODE_PRIVATE);
//        preferences.edit().remove("UserName").commit();
        String UserName = preferences.getString("UserName","");
        Log.d("intro",UserName);

        if(UserName != ""){ // Username exists so start main activity directly
            Intent intent = new Intent(IntroActivity.this,
                    MainActivity.class);
            startActivity(intent);
        }

        introName = findViewById(R.id.introName);
        introButton = findViewById(R.id.introButton);
        introButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("UserName",introName.getText().toString());
                editor.apply();

                Intent intent = new Intent(IntroActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });
    }
}