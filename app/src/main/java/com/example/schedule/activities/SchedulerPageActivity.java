package com.example.schedule.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageButton;

import com.example.schedule.BasePage;
import com.example.schedule.R;
import com.example.schedule.SchedulerPage;
import com.example.schedule.models.Datable;
import com.example.schedule.models.Favorite;
import com.example.schedule.models.SearchType;
import com.example.schedule.requests.GetSubjectsRequest;

public class SchedulerPageActivity extends BasePage implements Datable {

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


    @Override
    public void saveFavoriteGroup(boolean save) {
        Favorite favorite = new Favorite();
        favorite.label = super.searchListItem.label;
        favorite.id = super.searchListItem.id;
        favorite.description = super.searchListItem.description;
        favorite.isDefault = save;
        favorite.type = SearchType.GROUP;
        super.scheduleDB.setFavorite(favorite);
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
