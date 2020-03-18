package com.example.schedule.activities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.schedule.R;
import com.example.schedule.ScheduleDB;
import com.example.schedule.models.UserType;

public class MainActivity extends AppCompatActivity {

    private ScheduleDB scheduleDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Расписание ФУ");
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("schedule.db", MODE_PRIVATE, null);
        scheduleDB = new ScheduleDB(db);
        UserType user = scheduleDB.getUser();
    }

    public void onStudentClick(View view) {
        Intent intent = new Intent(MainActivity.this, SchedulerPageActivity.class);
        scheduleDB.setUser(UserType.STUDENT);
        startActivity(intent);
    }

    public void onLecturerClick(View view) {
        Intent intent = new Intent(MainActivity.this, LecturersActivity.class);
        scheduleDB.setUser(UserType.LECTURER);
        startActivity(intent);
    }

}
