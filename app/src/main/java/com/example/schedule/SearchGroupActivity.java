package com.example.schedule;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.IntDef;
import androidx.appcompat.app.AppCompatActivity;

import com.example.schedule.requests.GetGroupsRequest;

public class SearchGroupActivity extends AppCompatActivity {
    ListView groupListView;
    Context context;
    GetGroupsRequest request;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.search_group_page);
        groupListView = (ListView) findViewById(R.id.listView);
        EditText editText = (EditText) findViewById(R.id.txtSearch);
        this.initGroupList();

        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                request = new GetGroupsRequest(groupListView, context);
                request.execute(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

        });
    }

    private  void initGroupList(){
        request = new GetGroupsRequest(groupListView, this);
        request.execute("");
    }
}

