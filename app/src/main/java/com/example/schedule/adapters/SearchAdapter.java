package com.example.schedule.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.schedule.R;
import com.example.schedule.models.SearchListItem;

import java.util.ArrayList;

public class SearchAdapter extends ArrayAdapter<SearchListItem> {
    private LayoutInflater inflater;
    private int layout;
    private ArrayList<SearchListItem> items;
//    private OnGroupClickListener onGroupClickListener;


    public SearchAdapter(Context context, int resource, ArrayList<SearchListItem> groups) {
        super(context, resource, groups);
        this.items = groups;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
//        this.onGroupClickListener = onGroupClickListener;
    }
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;
        if(convertView==null){
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final SearchListItem group = items.get(position);

        viewHolder.nameView.setText(group.label);
        viewHolder.descriptionView.setText(group.description);

        return convertView;
    }
    private class ViewHolder {
        final TextView nameView, descriptionView ;
        ViewHolder(View view){
            nameView = (TextView) view.findViewById(R.id.name);
            descriptionView = (TextView) view.findViewById(R.id.description);
        }
    }
}
