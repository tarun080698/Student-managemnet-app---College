package com.example.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Start extends AppCompatActivity {
    private ImageView iV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        iV = findViewById(R.id.iv);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.mytrans);
        iV.startAnimation(animation);

        final Intent i = new Intent(this, AfterStart.class);
        Thread thread = new Thread() {
            public void run() {
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startActivity(i);
                    finish();
                }
            }
        };
        thread.start();
    }
}
