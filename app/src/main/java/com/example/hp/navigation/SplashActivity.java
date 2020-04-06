package com.example.hp.navigation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import java.util.Objects;

/**
 * Created by Parshva on 08-Aug-17.
 */

public class SplashActivity extends AppCompatActivity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Window window = getWindow();
        window.setStatusBarColor(Color.GRAY);
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
               /* SharedPreferences preferences=getSharedPreferences("slider",MODE_PRIVATE);
                Boolean temp=preferences.getBoolean("flag",true);
                if(temp==true)
                {
                    Intent newIntent = new Intent(SplashActivity.this, Introduction.class);
                    startActivity(newIntent);
                }
                else {*/
                    SharedPreferences prefs = getSharedPreferences("Login", MODE_PRIVATE);
                    if (prefs.getString("id", "").equals("")) {

                        Intent newIntent = new Intent(SplashActivity.this, Login.class);
                        startActivity(newIntent);
                    } else {

                        Log.v("#####", "^^^^^^^^^");
                        Intent i = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(i);
                    }
               // }
                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

}
