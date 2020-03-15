package com.example.schedule.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.schedule.BasePage;
import com.example.schedule.R;
import com.example.schedule.SchedulerPage;
import com.example.schedule.adapters.RoomOpacityAdapter;
import com.example.schedule.adapters.SubjectAdapter;
import com.example.schedule.models.Datable;
import com.example.schedule.models.RoomSubject;
import com.example.schedule.models.SearchListItem;
import com.example.schedule.models.SearchType;
import com.example.schedule.models.Subject;
import com.example.schedule.requests.GetRoomClassesRequest;
import com.example.schedule.requests.GetRoomsCapacityRequest;
import com.example.schedule.requests.GetSubjectsRequest;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class RoomsCapacityActivity extends BasePage implements Datable {
    ArrayList<RoomSubject> rooms = new ArrayList<>();
    RoomOpacityAdapter roomAdapter;
    LinearLayout header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onPageCreate(this, "Здание");
        super.onCreate(savedInstanceState);
        setTitle("Загруженность аудиторий");
        header = findViewById(R.id.room_opacity_header);
        header.setVisibility(View.VISIBLE);
        noDataView.setText("Укажите здание");
        setActualIcon();
    }

    @Override
    public void onSearchPanelClick(View v){
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra("type", SearchType.BUILDING);
        intent.putExtra("saveData", super.currentDate);
        intent.putExtra("returnClass", RoomsCapacityActivity.class);
        startActivity(intent);
    }

    @Override
    protected SearchListItem setDefaultSearchItem(){
        return null;
    }

    @Override
    public void refreshList(){
        if(searchListItem != null && currentDate != null){
            GetRoomsCapacityRequest request = new GetRoomsCapacityRequest(roomAdapter, this, rooms);
            handleExpanded(request);
            return;
        }
        super.setNoDataView(0);
    }

    @Override
    public void initList(){
        rooms = new ArrayList<RoomSubject>();
        roomAdapter = new RoomOpacityAdapter(this, R.layout.room_opacity_list_item, rooms);
        subjectsView.setAdapter(roomAdapter);
    }

    protected void handleExpanded(GetRoomsCapacityRequest request){
        request.execute(searchListItem.id, currentDate);
        try {
            ArrayList<RoomSubject> rooms = request.get();
            setNoDataView(rooms.size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
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
        ImageButton roomsСapacity = (ImageButton) findViewById(R.id.show_rooms_capacity);
        scheduler.setImageResource(R.drawable.calendar_alt_regular_gray);
        lectures.setImageResource(R.drawable.user_graduate_solid_gray);
        rooms.setImageResource(R.drawable.door_open_solid_gray);
        roomsСapacity.setImageResource(R.drawable.tachometer_alt_solid);
    }
}
