package com.example.schedule.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.schedule.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        onClick(null);
    }

    public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this, SchedulerPageActivity.class);
        startActivity(intent);
    }


}
