package com.example.schedule.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.schedule.R;
import com.example.schedule.adapters.SearchAdapter;
import com.example.schedule.models.GroupLecturer;
import com.example.schedule.models.SearchListItem;
import com.example.schedule.requests.GetGroupsRequest;
import com.google.gson.Gson;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    ListView listView;
    Context context;
    GetGroupsRequest request;
    ArrayList<SearchListItem> items;
    SearchAdapter searchAdapter;
    String type;
    Gson gson = new Gson();
    Bundle arguments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.search_page);
        listView = (ListView) findViewById(R.id.listView);

        EditText editText = (EditText) findViewById(R.id.txtSearch);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                initRequest(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

        });
        arguments = getIntent().getExtras();

        if(arguments != null && arguments.containsKey("type")){
            if(arguments.get("type") == GroupLecturer.LECTURER){
                type = "person";
            } else {
                type = "group";
            }
        }
        this.initList();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                SearchListItem choosedItem = items.get(position);
                Intent intent = new Intent(SearchActivity.this, SchedulerPageActivity.class);
                intent.putExtra("group", gson.toJson(choosedItem));
                if(arguments != null && arguments.containsKey("saveData")){
                    intent.putExtra("saveData", arguments.get("saveData").toString());
                }
                startActivity(intent);

            }

        });
        this.initRequest("");



    }



    private  void initList(){
        this.items = new ArrayList<SearchListItem>();
        searchAdapter = new SearchAdapter(context, R.layout.list_item, this.items);
        listView.setAdapter(searchAdapter);
    }

    private void  initRequest(String term){
        request = new GetGroupsRequest(searchAdapter, this, items, type);
        request.execute(term);
    }
}

