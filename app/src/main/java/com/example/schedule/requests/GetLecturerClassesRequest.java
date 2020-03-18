package com.example.schedule.requests;

import android.content.Context;
import android.os.AsyncTask;

import com.example.schedule.ApiService;
import com.example.schedule.adapters.SubjectAdapter;
import com.example.schedule.models.Subject;
import com.example.schedule.models.SubjectDTO;
import com.example.schedule.models.SubjectType;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;

public class GetLecturerClassesRequest extends AsyncTask<String, Void, ArrayList<Subject>> {
    Gson gson = new Gson();
    SubjectAdapter adapter;
    Calendar dateAndTime=Calendar.getInstance();
    Context context;
    ArrayList<Subject> subjects;
    public GetLecturerClassesRequest(SubjectAdapter adapter, Context context, ArrayList<Subject> subjects){
        this.adapter = adapter;
        this.context = context;
        this.subjects = subjects;
    }
    protected ArrayList<Subject> doInBackground(String... term){
        ArrayList<Subject> subjects = new ArrayList<Subject>();
        try{
            SubjectDTO[] subjectsDTO;
            if(term.length > 2){
                subjectsDTO = gson.fromJson(ApiService.get("https://ruz.fa.ru/api/schedule/person/"+term[0]+"?start="+term[1]+"&finish="+term[2]+"&lng=1"), SubjectDTO[].class);
            } else {
                subjectsDTO = gson.fromJson(ApiService.get("https://ruz.fa.ru/api/schedule/person/"+term[0]+"?start="+term[1]+"&finish="+term[1]+"&lng=1"), SubjectDTO[].class);
            }
            int dayOfWeek = -1;
            for(SubjectDTO subject:subjectsDTO){
                if(term.length > 2 && dayOfWeek != subject.dayOfWeek){
                    subjects.add(new Subject(subject.date, "", "", "", subject.dayOfWeekString, "", "", SubjectType.seminar));
                }
                subjects.add(new Subject(subject.discipline, subject.date, subject.beginLesson, subject.endLesson, subject.auditorium, subject.building, subject.lecturer, SubjectType.seminar));
                dayOfWeek = subject.dayOfWeek;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return subjects;
    }

    @Override
    protected void onPostExecute(ArrayList<Subject> subjects) {
        if (this.subjects.size() > 0){
            this.subjects.clear();
        }

        this.subjects.addAll(subjects);
        adapter.notifyDataSetChanged();
    }
}
