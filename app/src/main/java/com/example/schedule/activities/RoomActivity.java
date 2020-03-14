package com.example.schedule.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.schedule.BasePage;
import com.example.schedule.R;
import com.example.schedule.models.Datable;
import com.example.schedule.models.SearchListItem;
import com.example.schedule.models.SearchType;
import com.example.schedule.requests.GetRoomClassesRequest;
import com.example.schedule.requests.GetSubjectsRequest;

public class RoomActivity extends BasePage implements Datable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onPageCreate(this);
        super.groupView.setText("Аудитория");
        super.onCreate(savedInstanceState);
        setActualIcon();
    }

    @Override
    public void onSearchPanelClick(View v){
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra("type", SearchType.AUDITORIUM);
        intent.putExtra("saveData", super.currentDate);
        intent.putExtra("returnClass", RoomActivity.class);
        startActivity(intent);
    }

    @Override
    protected SearchListItem setDefaultSearchItem(){
        return null;
    }


    @Override
    public void saveFavoriteGroup(boolean save) {
        //TODO сохранение группы в избранное
    }

    @Override
    public void refreshList(){
        if(searchListItem != null && currentDate != null){
            GetRoomClassesRequest request = new GetRoomClassesRequest(subjectAdapter, this, subjects);
            super.handleExpanded(request);
            return;
        }
        super.setNoDataView(0);
    }

    public void setActualIcon(){
        ImageButton scheduler = (ImageButton) findViewById(R.id.show_scheduler);
        ImageButton lectures = (ImageButton) findViewById(R.id.show_lecturers);
        ImageButton rooms = (ImageButton) findViewById(R.id.show_rooms);
        scheduler.setImageResource(R.drawable.calendar_alt_regular_gray);
        lectures.setImageResource(R.drawable.user_graduate_solid_gray);
        rooms.setImageResource(R.drawable.door_open_solid);
    }
}
