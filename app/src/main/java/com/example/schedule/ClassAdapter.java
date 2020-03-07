package com.example.schedule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

class ClassAdapter extends ArrayAdapter<Lesson> {
    private LayoutInflater inflater;
    private int layout;
    private ArrayList<Lesson> lessons;

    ClassAdapter(Context context, int resource, ArrayList<Lesson> lessons) {
        super(context, resource, lessons);
        this.lessons     = lessons;
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
        final Lesson lesson = lessons.get(position);

        viewHolder.nameView.setText(lesson.getName());
        viewHolder.dateView.setText(lesson.getDate());
        viewHolder.nameView1.setText(lesson.getName());

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
