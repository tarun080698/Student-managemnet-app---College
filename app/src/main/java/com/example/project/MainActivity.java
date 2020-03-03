package com.example.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Capture button clicks
        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Start LoginActivity.class
                Toast.makeText(view.getContext(), "Opening Login page...",
                        Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        // Capture button clicks
        findViewById(R.id.guest).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Start Guest.class
                Toast.makeText(view.getContext(), "loading up stories,please wait...",
                        Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, HomeActivityG.class));
            }
        });
    }
}
