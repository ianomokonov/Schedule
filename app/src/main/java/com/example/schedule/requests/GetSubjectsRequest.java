package com.example.schedule.requests;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;

import com.example.schedule.ApiService;
import com.example.schedule.R;
import com.example.schedule.SubjectAdapter;
import com.example.schedule.models.Subject;
import com.example.schedule.models.SubjectDTO;
import com.example.schedule.models.SubjectType;
import com.google.gson.Gson;

import java.util.ArrayList;

public class GetSubjectsRequest extends AsyncTask<String, Void, ArrayList<Subject>> {
    Gson gson = new Gson();
    SubjectAdapter adapter;
    Context context;
    ArrayList<Subject> subjects;
    public GetSubjectsRequest(SubjectAdapter adapter, Context context, ArrayList<Subject> subjects){
        this.adapter = adapter;
        this.context = context;
        this.subjects = subjects;
    }
    protected ArrayList<Subject> doInBackground(String... term){
        ArrayList<Subject> subjects = new ArrayList<Subject>();
        try{
            SubjectDTO[] subjectsDTO = gson.fromJson(ApiService.get("https://ruz.fa.ru/api/schedule/group/"+term[0]+"?start=2020.03.09&finish=2020.03.15&lng=1"), SubjectDTO[].class);
            for(SubjectDTO subject:subjectsDTO){
                subjects.add(new Subject(subject.discipline, subject.date, subject.beginLesson, subject.endLesson, subject.auditorium, subject.building, subject.lecturer, SubjectType.seminar));
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
