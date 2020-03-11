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
    ListView listView;
    Context context;
    public GetSubjectsRequest(ListView listView, Context context){
        this.listView = listView;
        this.listView.setTextFilterEnabled(true);
        this.context = context;
    }
    protected ArrayList<Subject> doInBackground(String... term){
        ArrayList<Subject> subjects = new ArrayList<Subject>();
        try{
//            GroupListItem[] groups = gson.fromJson(ApiService.get("https://ruz.fa.ru/api/search?term="+ URLEncoder.encode("ПИ3-2", "UTF-8")+"&type=group"), GroupListItem[].class);
            SubjectDTO[] subjectsDTO = gson.fromJson(ApiService.get("https://ruz.fa.ru/api/schedule/group/8892?start=2020.03.09&finish=2020.03.15&lng=1"), SubjectDTO[].class);
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
        SubjectAdapter classAdapter = new SubjectAdapter(context, R.layout.subject_list_item, subjects);
        listView.setAdapter(classAdapter);
    }
}
