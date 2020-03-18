package com.example.schedule.models;

public class Favorite {
    public int id;
    public String label;
    public String description;
    public String type;
    public boolean isDefault;

//    public Favorite(int id, String label, String description, String type, boolean isDefault){
//        this.id = id;
//        this.label = label;
//        this.description = description;
//        this.type = type;
//        this.isDefault = isDefault;
//    }

    public int getId(){
        return id;
    }

    public String getLabel(){
        return label;
    }

    public String getDescription(){
        return description;
    }

    public String getType(){
        return type;
    }

    public Boolean getDef(){
        return isDefault;
    }
}
