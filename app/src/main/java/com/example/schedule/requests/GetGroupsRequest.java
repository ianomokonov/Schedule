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
    ListView listView;
    Context context;
    public GetGroupsRequest(ListView listView, Context context){
        this.listView = listView;
        this.context = context;
    }

    protected ArrayList<GroupListItem> doInBackground(String... terms){
        ArrayList<GroupListItem> groups = new ArrayList<>();
        try{
            GroupListItem[] groupsList = gson.fromJson(ApiService.get("https://ruz.fa.ru/api/search?term="+ URLEncoder.encode("П", "UTF-8")+"&type=group"), GroupListItem[].class);
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
        SearchGroupAdapter classAdapter = new SearchGroupAdapter(context, R.layout.list_item, groups);
        listView.setAdapter(classAdapter);
        // устанавливаем размеры
//        listView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.FILL_PARENT));
    }
}
