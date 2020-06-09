package com.example.schedule.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.schedule.BasePage;
import com.example.schedule.R;
import com.example.schedule.models.SearchListItem;
import com.example.schedule.models.SearchType;
import com.example.schedule.requests.GetLecturerClassesRequest;

public class LecturersActivity extends BasePage {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onPageCreate(this, "Преподаватель", true);
        super.onCreate(savedInstanceState);
        setTitle("Расписание преподавателей");
        setActualIcon();
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

    @Override
    public void onSearchPanelClick(View v){
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra("type", SearchType.LECTURER);
        intent.putExtra("saveData", super.currentDate);
        intent.putExtra("returnClass", LecturersActivity.class);
        startActivity(intent);
    }

    @Override
    protected SearchListItem setDefaultSearchItem(){
        return null;
    }

    @Override
    public void refreshList(){
        if(searchListItem != null && currentDate != null){
            GetLecturerClassesRequest request = new GetLecturerClassesRequest(subjectAdapter, this, subjects);
            super.handleExpanded(request);
            return;
        }
        super.setNoDataView(0);
    }
}
