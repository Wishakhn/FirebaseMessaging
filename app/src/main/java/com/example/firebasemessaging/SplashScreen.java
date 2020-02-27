package com.example.firebasemessaging;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.firebasemessaging.Activities.InboxActivity;
import com.example.firebasemessaging.Activities.LoginActivity;
import com.example.firebasemessaging.Activities.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SplashScreen extends AppCompatActivity {
Button loginuser;
Button reguser;
FirebaseUser user;
TextView title;
Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hide_statusbar();
        setContentView(R.layout.activity_splash_screen);
        initViews();
        setListner();

    }

    private void setListner() {
        if (user != null){
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent autologin = new Intent(SplashScreen.this, InboxActivity.class);
                    startActivity(autologin);
                    finish();
                }
            },2000);

        }
        else {
            title.setVisibility(View.GONE);
            loginuser.setVisibility(View.VISIBLE);
            reguser.setVisibility(View.VISIBLE);
        }
        loginuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gologin = new Intent(SplashScreen.this, LoginActivity.class);
                startActivity(gologin);
            }
        });
        reguser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goreg = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(goreg);
            }
        });
    }

    private void initViews() {
        handler = new Handler();
        user = FirebaseAuth.getInstance().getCurrentUser();
        title = findViewById(R.id.title);
        loginuser = findViewById(R.id.loginuser);
        reguser = findViewById(R.id.reguser);
    }

    void hide_statusbar() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

}