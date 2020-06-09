package com.example.schedule;

import android.app.ActivityOptions;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.schedule.activities.LecturersActivity;
import com.example.schedule.activities.MainActivity;
import com.example.schedule.activities.RoomActivity;
import com.example.schedule.activities.RoomsCapacityActivity;
import com.example.schedule.activities.SchedulerPageActivity;
import com.example.schedule.activities.SearchActivity;
import com.example.schedule.adapters.SubjectAdapter;
import com.example.schedule.models.SearchListItem;
import com.example.schedule.models.SearchType;
import com.example.schedule.models.Subject;
import com.example.schedule.models.UserType;
import com.example.schedule.requests.GetSubjectsRequest;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class BasePage extends AppCompatActivity {
    private Context context;
    protected Calendar dateAndTime=Calendar.getInstance();
    protected String currentDate;
    protected TextView currentDateView;
    protected TextView groupView;
    protected SearchListItem searchListItem;
    protected Gson gson = new Gson();
    protected ArrayList<Subject> subjects;
    protected SubjectAdapter subjectAdapter;
    protected ListView subjectsView;
    protected TextView noDataView;
    protected Boolean expanded = false;
    protected Boolean showExpandButton = true;
    protected ScheduleDB scheduleDB;


    protected void onPageCreate(Context context, String searchFilterName, Boolean showExpandButton) {
        this.context = context;
        setContentView(R.layout.activity_scheduler_page);
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("schedule.db", MODE_PRIVATE, null);
        scheduleDB = new ScheduleDB(db);
        this.showExpandButton = showExpandButton;
        groupView = findViewById(R.id.groups);
        noDataView = findViewById(R.id.subject_no_data);
        subjectsView = findViewById(R.id.classesList);
        subjectsView.setNestedScrollingEnabled(true);
        currentDateView = findViewById(R.id.selectedDate);
        groupView.setText(searchFilterName);
        OnSwipeTouchListener swipeListener = new OnSwipeTouchListener(this) {

            public void onSwipeLeft() {
                //обработка свайпа влево
                if(expanded){
                    dateAndTime.add(Calendar.DAY_OF_YEAR, 7);
                } else {
                    dateAndTime.add(Calendar.DAY_OF_YEAR, 1);
                }

                setDateViewText();
                refreshList();
            }

            public void onSwipeRight() {
                //обработка свайпа вправо
                if(expanded){
                    dateAndTime.add(Calendar.DAY_OF_YEAR, -7);
                } else {
                    dateAndTime.add(Calendar.DAY_OF_YEAR, -1);
                }
                setDateViewText();
                refreshList();
            }

        };
        subjectsView.setOnTouchListener(swipeListener);
        noDataView.setOnTouchListener(swipeListener);
        Bundle arguments = getIntent().getExtras();

        initList();
        setInitialDateTime();
        handleArgumets(arguments);

        refreshList();
        
    }
    
    protected void handleArgumets(Bundle arguments){
        if(arguments != null && arguments.containsKey("saveData")){
            setDateViewText(arguments.get("saveData").toString());
        }

        if(arguments != null && arguments.containsKey("searchItem")){
            searchListItem = gson.fromJson(arguments.get("searchItem").toString(), SearchListItem.class);
        } else {
            searchListItem = setDefaultSearchItem();
        }
        if(searchListItem != null){
            groupView.setText(searchListItem.label);
        }
    }

    protected SearchListItem setDefaultSearchItem() {
        SearchListItem item = new SearchListItem();
        item.label = "ПИ3-2";
        item.id = "8892";

        return  item;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.scheduler_menu, menu);
        menu.getItem(0).setEnabled(this.showExpandButton);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.expand:
                if(this.expanded){
                    item.setTitle("Просмотр недели");
                } else {
                    item.setTitle("Просмотр дня");
                }

                onExpandClick();
                return true;
            case R.id.exit:
                SQLiteDatabase db = getBaseContext().openOrCreateDatabase("schedule.db", MODE_PRIVATE, null);
                scheduleDB = new ScheduleDB(db);
                Intent intent = new Intent(context, MainActivity.class);
                scheduleDB.setUser(UserType.NONE);
                UserType user = scheduleDB.getUser();
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void goScheduler(View v){
        ActivityOptions options = ActivityOptions.makeCustomAnimation(context,0, 0);
        Intent intent = new Intent(context, SchedulerPageActivity.class);
        startActivity(intent, options.toBundle());
    }

    public void goLecturers(View v){
        ActivityOptions options = ActivityOptions.makeCustomAnimation(context,0, 0);
        Intent intent = new Intent(context, LecturersActivity.class);
        startActivity(intent, options.toBundle());
    }

    public void goRooms(View v){
        ActivityOptions options = ActivityOptions.makeCustomAnimation(context,0, 0);
        Intent intent = new Intent(context, RoomActivity.class);
        startActivity(intent, options.toBundle());
    }

    public void goRoomsCapacity(View v){
        ActivityOptions options = ActivityOptions.makeCustomAnimation(context,0, 0);
        Intent intent = new Intent(context, RoomsCapacityActivity.class);
        startActivity(intent, options.toBundle());
    }

    public void onSearchPanelClick(View v){
        Intent intent = new Intent(context, SearchActivity.class);
        intent.putExtra("type", SearchType.GROUP);
        intent.putExtra("saveData", currentDate);
        intent.putExtra("returnClass", SchedulerPageActivity.class);
        startActivity(intent);
    }

    public  void onExpandClick(){
        expanded = !expanded;
        refreshList();
    }

    public void setDate(View v) {
        new DatePickerDialog(this, onDateSetListener,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    public void refreshList(){

        if(searchListItem != null && currentDate != null){
            GetSubjectsRequest request = new GetSubjectsRequest(subjectAdapter, this, subjects);
            handleExpanded(request);
            return;
        }
        setNoDataView(0);
    }

    protected void handleExpanded(AsyncTask<String, Void, ArrayList<Subject>> request){
        if(expanded){
            int dayOfWeek = 2; // Monday
            int weekday = dateAndTime.get(Calendar.DAY_OF_WEEK);

            // calculate how much to add
            int days = weekday - Calendar.MONDAY;
            Calendar calendar = (Calendar) dateAndTime.clone();
            calendar.add(Calendar.DAY_OF_YEAR, -days);
            // now is the date you want
            Date startDate = calendar.getTime();
            calendar.add(Calendar.DAY_OF_YEAR, 6);
            Date finishDate = calendar.getTime();
            request.execute(searchListItem.id, dateTOString(startDate), dateTOString(finishDate));
        } else {
            request.execute(searchListItem.id, currentDate);
        }
        try {
            ArrayList<Subject> subjects = request.get();
            setNoDataView(subjects.size());

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void setNoDataView(int size){
        if(size > 0){
            subjectsView.setVisibility(View.VISIBLE);
            noDataView.setVisibility(View.GONE);
        } else {
            subjectsView.setVisibility(View.GONE);
            noDataView.setVisibility(View.VISIBLE);
            noDataView.setText("Нет пар");
        }
    }

    public void initList(){
        subjects = new ArrayList<Subject>();
        subjectAdapter = new SubjectAdapter(this, R.layout.subject_list_item, subjects);
        subjectsView.setAdapter(subjectAdapter);


    }

    // установка начальных даты и времени
    protected void setInitialDateTime() {
        Date currentDate = new Date(dateAndTime.getTimeInMillis());
        this.setDateViewText(dateTOString(currentDate));
    }

    protected String dateTOString(Date date){
        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());
        return  dateFormat.format(date);
    }

    protected void setDateViewText(String dateString){
        currentDate = dateString;
        dateAndTime.set(Integer.parseInt(dateString.substring(0,4)),Integer.parseInt(dateString.substring(5,7))-1,Integer.parseInt(dateString.substring(8,10)));
        currentDateView.setText(currentDate);
    }

    protected void setDateViewText(){
        currentDate = dateTOString(dateAndTime.getTime());
        currentDateView.setText(currentDate);
    }

    DatePickerDialog.OnDateSetListener onDateSetListener =new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(year, monthOfYear, dayOfMonth);
            setInitialDateTime();
            refreshList();
        }
    };
}
