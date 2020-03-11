package com.example.schedule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.schedule.models.GroupListItem;
import com.example.schedule.models.Subject;

import java.util.ArrayList;

public class SearchGroupAdapter extends ArrayAdapter<GroupListItem> {
    private LayoutInflater inflater;
    private int layout;
    private ArrayList<GroupListItem> groups;

    public SearchGroupAdapter(Context context, int resource, ArrayList<GroupListItem> groups) {
        super(context, resource, groups);
        this.groups = groups;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
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
        final GroupListItem group = groups.get(position);

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
