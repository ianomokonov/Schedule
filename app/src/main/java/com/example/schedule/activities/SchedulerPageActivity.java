package com.example.schedule.activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.schedule.OnSwipeTouchListener;
import com.example.schedule.R;
import com.example.schedule.SaveFavoriteGroupDialog;
import com.example.schedule.adapters.SubjectAdapter;
import com.example.schedule.models.Datable;
import com.example.schedule.models.GroupLecturer;
import com.example.schedule.models.SearchListItem;
import com.example.schedule.models.Subject;
import com.example.schedule.requests.GetSubjectsRequest;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class SchedulerPageActivity extends AppCompatActivity implements Datable {
    TextView currentDateTimeView;
    TextView groupView;
    SearchListItem currentGroup;
    String currentDate;
    Calendar dateAndTime=Calendar.getInstance();
    Gson gson = new Gson();
    ArrayList<Subject> subjects;
    GetSubjectsRequest request;
    SubjectAdapter subjectAdapter;
    ListView subjectsView;
    TextView noDataView;
    Boolean expanded = false;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduler_page);
        groupView = (TextView) findViewById(R.id.groups);
        noDataView = (TextView) findViewById(R.id.subject_no_data);
        subjectsView = (ListView) findViewById(R.id.classesList);
        subjectsView.setNestedScrollingEnabled(true);
        currentDateTimeView=(TextView)findViewById(R.id.selectedDate);
        groupView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                SaveFavoriteGroupDialog dialog = new SaveFavoriteGroupDialog();
                dialog.show(getSupportFragmentManager(), "custom");
                return  true;
            }
        });
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

        initSubjectsList();
        setInitialDateTime();

        if(arguments != null && arguments.containsKey("saveData")){
            setDateViewText(arguments.get("saveData").toString());
        }

        if(arguments != null && arguments.containsKey("group")){
            SearchListItem group = gson.fromJson(arguments.get("group").toString(), SearchListItem.class);
            currentGroup = group;
        } else {
            currentGroup = new SearchListItem();
            currentGroup.id = "8892";
            currentGroup.label = "ПИ3-2";
        }
        refreshList();
        groupView.setText(currentGroup.label);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.scheduler_menu, menu);
        return true;
    }

    // отображаем диалоговое окно для выбора даты
    public void setDate(View v) {
        new DatePickerDialog(this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    public  void onExpandClick(View v){
        ImageButton button = (ImageButton) findViewById(R.id.subject_expand);
        if(expanded){
            button.setImageResource(R.drawable.expand_solid_white);
        } else {
            button.setImageResource(R.drawable.compress_solid_white);
        }
        expanded = !expanded;
        refreshList();

    }

    // установка начальных даты и времени
    private void setInitialDateTime() {
        Date currentDate = new Date(dateAndTime.getTimeInMillis());
        this.setDateViewText(dateTOString(currentDate));
    }

    private String dateTOString(Date date){
        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());
        return  dateFormat.format(date);
    }

    private void setDateViewText(String dateString){
        currentDate = dateString;
        dateAndTime.set(Integer.parseInt(dateString.substring(0,4)),Integer.parseInt(dateString.substring(5,7))-1,Integer.parseInt(dateString.substring(8,10)));
        currentDateTimeView.setText(currentDate);
    }

    private void setDateViewText(){
        currentDate = dateTOString(dateAndTime.getTime());
        currentDateTimeView.setText(currentDate);
    }

    private void initSubjectsList(){
        subjects = new ArrayList<Subject>();
        subjectAdapter = new SubjectAdapter(this, R.layout.subject_list_item, subjects);
        subjectsView.setAdapter(subjectAdapter);


    }

    private void refreshList(){
        if(currentGroup != null && currentDate != null){
            request = new GetSubjectsRequest(subjectAdapter, this, subjects);
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
                request.execute(currentGroup.id, dateTOString(startDate), dateTOString(finishDate));
            } else {
                request.execute(currentGroup.id, currentDate);
            }
            try {
                ArrayList<Subject> subjects = request.get();
                if(subjects.size() > 0){
                    subjectsView.setVisibility(View.VISIBLE);
                    noDataView.setVisibility(View.GONE);
                } else {
                    subjectsView.setVisibility(View.GONE);
                    noDataView.setVisibility(View.VISIBLE);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    public void onGroupClick(View v) {
        Intent intent = new Intent(this, SearchActivity.class );
        intent.putExtra("type", GroupLecturer.GROUP);
        intent.putExtra("saveData", currentDate);
        startActivity(intent);
    }

    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(year, monthOfYear, dayOfMonth);
            setInitialDateTime();
            refreshList();
        }
    };

    @Override
    public void saveFavoriteGroup(boolean save) {
        //TODO сохранение группы в избранное
        int i = 0;
    }
}
