package com.example.schedule.requests;

import android.content.Context;
import android.os.AsyncTask;

import com.example.schedule.ApiService;
import com.example.schedule.adapters.RoomOpacityAdapter;
import com.example.schedule.adapters.SubjectAdapter;
import com.example.schedule.models.RoomSubject;
import com.example.schedule.models.Subject;
import com.example.schedule.models.SubjectDTO;
import com.example.schedule.models.SubjectType;
import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GetRoomsCapacityRequest extends AsyncTask<String, Void, ArrayList<RoomSubject>> {
    Gson gson = new Gson();
    RoomOpacityAdapter adapter;
    Calendar dateAndTime=Calendar.getInstance();
    Context context;
    ArrayList<RoomSubject> rooms;
    public GetRoomsCapacityRequest(RoomOpacityAdapter adapter, Context context, ArrayList<RoomSubject> rooms){
        this.adapter = adapter;
        this.context = context;
        this.rooms = rooms;
    }
    protected ArrayList<RoomSubject> doInBackground(String... term){
        ArrayList<RoomSubject> rooms = new ArrayList<RoomSubject>();
        try{
            RoomSubject[] roomSubjects = gson.fromJson(ApiService.get("https://ruz.fa.ru/api/dictionary/auditoriums?buildingOid="+term[0]), RoomSubject[].class);;
            SubjectDTO[] subjects = gson.fromJson(ApiService.get("https://ruz.fa.ru/api/schedule/building/"+term[0]+"?start="+term[1]+"&finish="+term[1]+"&lng=1"), SubjectDTO[].class);
            List<SubjectDTO> sortedSubjects = Arrays.asList(subjects);
            Collections.sort(sortedSubjects, new SortByRoom());
            int dayOfWeek = -1;
            for(RoomSubject room:roomSubjects){
                room.subjects = new SubjectDTO[8];
                for(int i = 0; i< 8; i++){
                    room.subjects[i] = null;
                }
                rooms.add(searchRoomSubjects(room, sortedSubjects));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rooms;
    }

    private RoomSubject searchRoomSubjects(RoomSubject room, List<SubjectDTO> sortedSubjects){
        boolean reached = false;
        for(SubjectDTO subjectDTO:sortedSubjects){
            if (subjectDTO.auditoriumOid == room.auditoriumOid) {
                reached = true;
                room.subjects[getClassIntex(subjectDTO.beginLesson)] = subjectDTO;
                int i = 0;
            } else {
                if (reached) {
                    break;
                }
            }
        }
        return room;
    }

    private int getClassIntex(String startTime){
        switch (startTime){
            case "08:30":{
                return 0;
            }
            case "10:10":{
                return 1;
            }
            case "11:50":{
                return 2;
            }
            case "14:00":{
                return 3;
            }
            case "15:40":{
                return 4;
            }
            case "17:20":{
                return 5;
            }
            case "18:55":{
                return 6;
            }
            case "20:30":{
                return 7;
            }
        }
        return 0;
    }

    private class SortByRoom implements Comparator<SubjectDTO> {
        public int compare(SubjectDTO a, SubjectDTO b) {
            return a.auditoriumOid > b.auditoriumOid ? 1 : -1;
        }
    }

    @Override
    protected void onPostExecute(ArrayList<RoomSubject> rooms) {
        if (this.rooms.size() > 0){
            this.rooms.clear();
        }

        this.rooms.addAll(rooms);
        adapter.notifyDataSetChanged();
    }
}

