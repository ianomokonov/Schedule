package com.example.schedule;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;

public class SchedulerPageActivity extends AppCompatActivity {
    String[] groupFilterValues = {"ПИ3-1", "ПИ3-2", "ПИ3-3"};
    TextView currentDateTime;
    Calendar dateAndTime=Calendar.getInstance();
    ArrayList<Subject> lessons = new ArrayList();
    ListView productList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduler_page);

        // адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, groupFilterValues);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        Spinner spinner = (Spinner) findViewById(R.id.groups);
        spinner.setAdapter(adapter);
        // заголовок
        spinner.setPrompt("Группы");

        currentDateTime=(TextView)findViewById(R.id.selectedDate);
        setInitialDateTime();

        lessons.add(new Subject("Картофель", "23.12.1998"));
        lessons.add(new Subject("Картофель", "23.12.1998"));
        lessons.add(new Subject("Картофель", "23.12.1998"));
        lessons.add(new Subject("Картофель", "23.12.1998"));
        lessons.add(new Subject("Картофель", "23.12.1998"));
        lessons.add(new Subject("Картофель", "23.12.1998"));
        lessons.add(new Subject("Картофель", "23.12.1998"));
        lessons.add(new Subject("Картофель", "23.12.1998"));
        lessons.add(new Subject("Картофель", "23.12.1998"));
        productList = (ListView) findViewById(R.id.classesList);
        SubjectAdapter classAdapter = new SubjectAdapter(this, R.layout.class_list_item, lessons);
        productList.setAdapter(classAdapter);
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
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
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
