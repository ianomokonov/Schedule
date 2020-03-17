package com.example.schedule.models;

public interface IDataBase {
    void setUser(String type);
    boolean getUser();
    void setFavorite(Favorite favoriteItem);
    int updateFavorite(int id, String type, boolean isDefault);
    void removeFavorite(int id, String type);
}
