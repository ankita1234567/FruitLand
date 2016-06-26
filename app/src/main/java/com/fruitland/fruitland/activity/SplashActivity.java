package com.fruitland.fruitland.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.fruitland.fruitland.R;

/**
 * Created by Admin on 3/25/2016.
 */
public class SplashActivity extends Activity {

    private Context context = null;
    private int time = 2000;
    private int tm = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        context = this.getApplicationContext();
        new CountDownTimer(time, tm) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {

                Intent intent = new Intent(context, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
                finish();

            }
        }.start();

    }
}
