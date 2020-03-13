package com.example.schedule;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
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
import java.util.concurrent.ExecutionException;

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
    ListView subjectsView;
    TextView noDataView;
    Boolean expanded = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduler_page);
        groupView = (TextView) findViewById(R.id.groups);
        noDataView = (TextView) findViewById(R.id.subject_no_data);
        subjectsView = (ListView) findViewById(R.id.classesList);
        currentDateTimeView=(TextView)findViewById(R.id.selectedDate);
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

    // отображаем диалоговое окно для выбора даты
    public void setDate(View v) {
        new DatePickerDialog(this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH)-1,
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    public  void onExpandClick(View v){
        ImageButton button = (ImageButton) findViewById(R.id.subject_expand);
        if(expanded){
            button.setImageResource(R.drawable.expand_solid);
        } else {
            button.setImageResource(R.drawable.compress_solid);
        }
        expanded = !expanded;

    }

    // установка начальных даты и времени
    private void setInitialDateTime() {
                // Текущее время
        Date currentDate = new Date(dateAndTime.getTimeInMillis());
        // Форматирование времени как "день.месяц.год"
        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());
        this.setDateViewText(dateFormat.format(currentDate));
    }

    private void setDateViewText(String dateString){
        currentDate = dateString;
        dateAndTime.set(Integer.parseInt(dateString.substring(0,4)),Integer.parseInt(dateString.substring(5,7)),Integer.parseInt(dateString.substring(8,10)));
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
            request.execute(currentGroup.id, currentDate);
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
}
