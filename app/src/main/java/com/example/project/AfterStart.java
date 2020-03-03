package com.example.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class AfterStart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_start);

        findViewById(R.id.b1).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Start LoginActivity.class
                Toast.makeText(view.getContext(), "Opening student page...",
                        Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AfterStart.this, LoginS.class));
            }
        });
        findViewById(R.id.b2).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Start LoginActivity.class
                Toast.makeText(view.getContext(), "Opening teacher page...",
                        Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AfterStart.this, MainActivity.class));
            }
        });

        findViewById(R.id.b3).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Start LoginActivity.class
//                Toast.makeText(view.getContext(), "",
//                        Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AfterStart.this, MainActivity.class));
            }
        });

    }
}
