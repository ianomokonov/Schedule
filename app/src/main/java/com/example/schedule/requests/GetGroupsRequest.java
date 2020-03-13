package com.example.schedule.requests;

import android.content.Context;
import android.os.AsyncTask;

import com.example.schedule.ApiService;
import com.example.schedule.SearchAdapter;
import com.example.schedule.models.SearchListItem;
import com.google.gson.Gson;

import java.net.URLEncoder;
import java.util.ArrayList;

public class GetGroupsRequest extends AsyncTask<String, Void, ArrayList<SearchListItem>> {
    Gson gson = new Gson();
    Context context;
    SearchAdapter searchAdapter;
    ArrayList<SearchListItem> groups;
    String type;
    public GetGroupsRequest(SearchAdapter searchAdapter, Context context, ArrayList<SearchListItem> groups, String type){
        this.searchAdapter = searchAdapter;
        this.context = context;
        this.groups = groups;
        this.type = type;
    }

    protected ArrayList<SearchListItem> doInBackground(String... terms){
        ArrayList<SearchListItem> groups = new ArrayList<>();
        try{
            SearchListItem[] groupsList = gson.fromJson(ApiService.get("https://ruz.fa.ru/api/search?term="+ URLEncoder.encode(terms[0], "UTF-8")+"&type="+type), SearchListItem[].class);
            for(SearchListItem group:groupsList){
                groups.add(group);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return groups;
    }

    @Override
    protected void onPostExecute(ArrayList<SearchListItem> groups) {
        if (this.groups.size() > 0){
            this.groups.clear();
        }

        this.groups.addAll(groups);
        searchAdapter.notifyDataSetChanged();
//        устанавливаем размеры
//        listView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.FILL_PARENT));
    }
}
