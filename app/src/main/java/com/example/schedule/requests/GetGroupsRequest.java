package com.example.schedule.requests;

import android.content.Context;
import android.os.AsyncTask;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.schedule.ApiService;
import com.example.schedule.R;
import com.example.schedule.SearchGroupAdapter;
import com.example.schedule.models.GroupListItem;
import com.google.gson.Gson;

import java.net.URLEncoder;
import java.util.ArrayList;

public class GetGroupsRequest extends AsyncTask<String, Void, ArrayList<GroupListItem>> {
    Gson gson = new Gson();
    Context context;
    SearchGroupAdapter searchGroupAdapter;
    ArrayList<GroupListItem> groups;
    public GetGroupsRequest(SearchGroupAdapter searchGroupAdapter, Context context, ArrayList<GroupListItem> groups){
        this.searchGroupAdapter = searchGroupAdapter;
        this.context = context;
        this.groups = groups;
    }

    protected ArrayList<GroupListItem> doInBackground(String... terms){
        ArrayList<GroupListItem> groups = new ArrayList<>();
        try{
            GroupListItem[] groupsList = gson.fromJson(ApiService.get("https://ruz.fa.ru/api/search?term="+ URLEncoder.encode(terms[0], "UTF-8")+"&type=group"), GroupListItem[].class);
            for(GroupListItem group:groupsList){
                groups.add(group);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return groups;
    }

    @Override
    protected void onPostExecute(ArrayList<GroupListItem> groups) {
        if (this.groups.size() > 0){
            this.groups.clear();
        }

        this.groups.addAll(groups);
        searchGroupAdapter.notifyDataSetChanged();
//        устанавливаем размеры
//        listView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.FILL_PARENT));
    }
}
