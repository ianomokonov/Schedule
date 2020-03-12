package com.example.schedule;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.schedule.models.GroupListItem;
import com.example.schedule.models.Subject;
import com.example.schedule.requests.GetSubjectsRequest;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;

public class SchedulerPageActivity extends AppCompatActivity {
    String[] groupFilterValues = {"ПИ3-1", "ПИ3-2", "ПИ3-3"};
    TextView currentDateTime;
    TextView groupView;
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
        Bundle arguments = getIntent().getExtras();
        this.initSubjectsList();
        if(arguments != null && arguments.containsKey("group")){
            GroupListItem group = gson.fromJson(arguments.get("group").toString(), GroupListItem.class);
            groupView.setText(group.label);
            refreshList(Integer.toString(group.id));
        } else {
            refreshList("8892");
        }



        currentDateTime=(TextView)findViewById(R.id.selectedDate);
        setInitialDateTime();




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

        currentDateTime.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
    }

    private void initSubjectsList(){
        subjects = new ArrayList<Subject>();
        subjectAdapter = new SubjectAdapter(this, R.layout.subject_list_item, subjects);
        ListView subjectsView = (ListView) findViewById(R.id.classesList);
        subjectsView.setAdapter(subjectAdapter);


    }

    private void refreshList(String group){
        request = new GetSubjectsRequest(subjectAdapter, this, subjects);
        request.execute(group);
    }

    public void onGroupClick(View v) {
        Intent intent = new Intent(this, SearchGroupActivity.class );
        startActivity(intent);
    }

    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime();
        }
    };
}
