package com.example.schedule.models;

public class Subject {
    public String name;
    public String date;
    public String timeFrom;
    public String timeTo;
    public String room;
    public String address;
    public String lecturer;
    public String type;


    public Subject(String name, String date, String timeFrom, String timeTo, String room, String address, String lecturer, String type){
        this.name = name;
        this.date=date;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.room = room;
        this.address = address;
        this.lecturer = lecturer;
        this.type = type;
    }
}
