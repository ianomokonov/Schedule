package com.example.schedule;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.schedule.models.GroupListItem;
import com.example.schedule.models.Subject;
import com.example.schedule.models.SubjectDTO;
import com.example.schedule.models.SubjectType;
import com.google.gson.Gson;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;

public class SchedulerPageActivity extends AppCompatActivity {
    String[] groupFilterValues = {"ПИ3-1", "ПИ3-2", "ПИ3-3"};
    TextView currentDateTime;
    Calendar dateAndTime=Calendar.getInstance();
    ArrayList<Subject> subjects = new ArrayList();
    ListView productList;
    Gson gson = new Gson();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduler_page);
        this.initSubjectsList(this);
        // адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, groupFilterValues);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        Spinner spinner = (Spinner) findViewById(R.id.groups);
        spinner.setAdapter(adapter);
        // заголовок
        spinner.setPrompt("Группы");

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

    private void initSubjectsList(Context context){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try{
                    GroupListItem[] groups = gson.fromJson(ApiService.get("https://ruz.fa.ru/api/search?term="+ URLEncoder.encode("ПИ3-2", "UTF-8")+"&type=group"), GroupListItem[].class);
                    SubjectDTO[] subjectsDTO = gson.fromJson(ApiService.get("https://ruz.fa.ru/api/schedule/group/8892?start=2020.03.09&finish=2020.03.15&lng=1"), SubjectDTO[].class);
                    for(SubjectDTO subject:subjectsDTO){
                        subjects.add(new Subject(subject.discipline, subject.date, subject.beginLesson, subject.endLesson, subject.auditorium, subject.building, subject.lecturer, SubjectType.seminar));
                    }
                    productList = (ListView) findViewById(R.id.classesList);
                    SubjectAdapter classAdapter = new SubjectAdapter(null, R.layout.subject_list_item, subjects);
                    productList.setAdapter(classAdapter);
                } catch(Exception e){

                }
            }
        });
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
