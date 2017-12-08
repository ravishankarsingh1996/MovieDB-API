package com.app.pagination;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Ravi on 07-12-2017.
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        int SPLASH_TIME = 1500;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                    Intent intentLaunchLoginActivity = new Intent(
                            SplashActivity.this, MainActivity.class);
                    startActivity(intentLaunchLoginActivity);
                    finish();
            }
        }, SPLASH_TIME);
    }
}

