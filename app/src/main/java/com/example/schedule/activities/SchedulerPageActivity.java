package com.example.schedule.activities;

import android.os.Bundle;
import android.widget.ImageButton;

import com.example.schedule.BasePage;
import com.example.schedule.R;
import com.example.schedule.requests.GetSubjectsRequest;

public class SchedulerPageActivity extends BasePage {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onPageCreate(this, "Группа", true);
        super.onCreate(savedInstanceState);
        setTitle("Расписание студентов");
        setActualIcon();
    }

    public void refreshList(){

        if(searchListItem != null && currentDate != null){
            GetSubjectsRequest request = new GetSubjectsRequest(subjectAdapter, this, subjects);

            handleExpanded(request);
        }
    }

    public void setActualIcon(){
        ImageButton scheduler = (ImageButton) findViewById(R.id.show_scheduler);
        ImageButton lectures = (ImageButton) findViewById(R.id.show_lecturers);
        ImageButton rooms = (ImageButton) findViewById(R.id.show_rooms);
        ImageButton roomsСapacity = (ImageButton) findViewById(R.id.show_rooms_capacity);
        scheduler.setImageResource(R.drawable.calendar_alt_regular);
        lectures.setImageResource(R.drawable.user_graduate_solid_gray);
        rooms.setImageResource(R.drawable.door_open_solid_gray);
        roomsСapacity.setImageResource(R.drawable.tachometer_alt_solid_gray);
    }
}
