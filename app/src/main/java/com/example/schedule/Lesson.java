package com.example.schedule;

public class Lesson {
    private String name;
    private String date;

    Lesson(String name, String date){
        this.name = name;
        this.date=date;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public String getDate(){
        return this.date;
    }
}
