package com.example.schedule.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageButton;

import com.example.schedule.BasePage;
import com.example.schedule.R;
import com.example.schedule.SchedulerPage;
import com.example.schedule.models.Datable;
import com.example.schedule.requests.GetSubjectsRequest;

public class SchedulerPageActivity extends BasePage implements Datable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onPageCreate(this);
        super.onCreate(savedInstanceState);
        setActualIcon();
    }

    public void refreshList(){

        if(searchListItem != null && currentDate != null){
            GetSubjectsRequest request = new GetSubjectsRequest(subjectAdapter, this, subjects);

            handleExpanded(request);
        }
    }


    @Override
    public void saveFavoriteGroup(boolean save) {
        //TODO сохранение группы в избранное
    }

    public void setActualIcon(){
        ImageButton scheduler = (ImageButton) findViewById(R.id.show_scheduler);
        ImageButton lectures = (ImageButton) findViewById(R.id.show_lecturers);
        ImageButton rooms = (ImageButton) findViewById(R.id.show_rooms);
        scheduler.setImageResource(R.drawable.calendar_alt_regular);
        lectures.setImageResource(R.drawable.user_graduate_solid_gray);
        rooms.setImageResource(R.drawable.door_open_solid_gray);
    }
}