package com.example.schedule.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageButton;

import com.example.schedule.BasePage;
import com.example.schedule.R;
import com.example.schedule.SchedulerPage;
import com.example.schedule.models.Datable;

public class LecturersActivity extends BasePage implements Datable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onPageCreate(this, "Преподаватель");
        super.onCreate(savedInstanceState);
        setTitle("Расписание преподавателей");
        setActualIcon();
    }



    @Override
    public void saveFavoriteGroup(boolean save) {
        //TODO сохранение группы в избранное
    }

    public void setActualIcon(){
        ImageButton scheduler = (ImageButton) findViewById(R.id.show_scheduler);
        ImageButton lectures = (ImageButton) findViewById(R.id.show_lecturers);
        ImageButton rooms = (ImageButton) findViewById(R.id.show_rooms);
        ImageButton roomsСapacity = (ImageButton) findViewById(R.id.show_rooms_capacity);
        scheduler.setImageResource(R.drawable.calendar_alt_regular_gray);
        lectures.setImageResource(R.drawable.user_graduate_solid);
        rooms.setImageResource(R.drawable.door_open_solid_gray);
        roomsСapacity.setImageResource(R.drawable.tachometer_alt_solid_gray);
    }
}
