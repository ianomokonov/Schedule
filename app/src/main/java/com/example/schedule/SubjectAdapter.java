package com.example.schedule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

class SubjectAdapter extends ArrayAdapter<Subject> {
    private LayoutInflater inflater;
    private int layout;
    private ArrayList<Subject> lessons;

    SubjectAdapter(Context context, int resource, ArrayList<Subject> lessons) {
        super(context, resource, lessons);
        this.lessons = lessons;
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
        final Subject lesson = lessons.get(position);

        viewHolder.nameView.setText(lesson.name);
        viewHolder.dateView.setText(lesson.date);
        viewHolder.nameView1.setText(lesson.name);

        return convertView;
    }
    private class ViewHolder {
        final TextView nameView, dateView, nameView1, dateView1;
        ViewHolder(View view){
            nameView = (TextView) view.findViewById(R.id.class_list_item_name);
            dateView = (TextView) view.findViewById(R.id.class_list_item_date);
            nameView1 = (TextView) view.findViewById(R.id.class_list_item_name1);
            dateView1 = (TextView) view.findViewById(R.id.class_list_item_date1);
        }
    }
}
