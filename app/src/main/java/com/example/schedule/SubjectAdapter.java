package com.example.schedule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.schedule.models.Subject;

import java.util.ArrayList;

class SubjectAdapter extends ArrayAdapter<Subject> {
    private LayoutInflater inflater;
    private int layout;
    private ArrayList<Subject> objects;

    SubjectAdapter(Context context, int resource, ArrayList<Subject> objects) {
        super(context, resource, objects);
        this.objects = objects;
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
        final Subject subject = objects.get(position);

        viewHolder.nameView.setText(subject.name);
        viewHolder.timeFromView.setText(subject.timeFrom);
        viewHolder.timeToView.setText(subject.timeTo);
        viewHolder.roomView.setText(subject.room);
        viewHolder.addressView.setText(subject.address);
        viewHolder.lecturerView.setText(subject.lecturer);

        return convertView;
    }
    private class ViewHolder {
        final TextView nameView, timeFromView, timeToView, roomView, addressView, lecturerView ;
        ViewHolder(View view){
            nameView = (TextView) view.findViewById(R.id.subject_list_item_name);
            timeFromView = (TextView) view.findViewById(R.id.subject_list_item_time_from);
            timeToView = (TextView) view.findViewById(R.id.subject_list_item_time_to);
            roomView = (TextView) view.findViewById(R.id.subject_list_item_room);
            addressView = (TextView) view.findViewById(R.id.subject_list_item_address);
            lecturerView = (TextView) view.findViewById(R.id.subject_list_item_lecturer);
        }
    }
}
