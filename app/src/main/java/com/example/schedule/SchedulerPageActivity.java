package com.example.schedule;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

public class SchedulerPageActivity extends AppCompatActivity {
    TextView currentDateTimeView;
    TextView groupView;
    SearchListItem currentGroup;
    String currentDate;
    Calendar dateAndTime=Calendar.getInstance();
    Gson gson = new Gson();
    ArrayList<Subject> subjects;
    GetSubjectsRequest request;
    SubjectAdapter subjectAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduler_page);
        groupView = (TextView) findViewById(R.id.groups);
        currentDateTimeView=(TextView)findViewById(R.id.selectedDate);
        Bundle arguments = getIntent().getExtras();

        initSubjectsList();
        setInitialDateTime();

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

    // отображаем диалоговое окно для выбора даты
    public void setDate(View v) {
        new DatePickerDialog(this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    // установка начальных даты и времени
    private void setInitialDateTime() {
                // Текущее время
        Date currentDate = new Date(dateAndTime.getTimeInMillis());
        // Форматирование времени как "день.месяц.год"
        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());
        this.currentDate = dateFormat.format(currentDate);
        currentDateTimeView.setText(this.currentDate);
    }

    private void initSubjectsList(){
        subjects = new ArrayList<Subject>();
        subjectAdapter = new SubjectAdapter(this, R.layout.subject_list_item, subjects);
        ListView subjectsView = (ListView) findViewById(R.id.classesList);
        subjectsView.setAdapter(subjectAdapter);


    }

    private void refreshList(){
        if(currentGroup != null && currentDate != null){
            request = new GetSubjectsRequest(subjectAdapter, this, subjects);
            request.execute(currentGroup.id, currentDate);
        }
    }

    public void onGroupClick(View v) {
        Intent intent = new Intent(this, SearchActivity.class );
        intent.putExtra("type", GroupLecturer.GROUP);
        startActivity(intent);
    }

    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime();
            String d = currentDate;
            refreshList();
        }
    };
}
