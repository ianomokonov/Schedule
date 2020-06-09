package com.example.schedule.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.schedule.R;
import com.example.schedule.models.Subject;

import java.util.ArrayList;

public class SubjectAdapter extends ArrayAdapter<Subject> {
    private LayoutInflater inflater;
    private int layout;
    private ArrayList<Subject> objects;

    public SubjectAdapter(Context context, int resource, ArrayList<Subject> objects) {
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
        viewHolder.typeView.setText(subject.type);

        if(subject.name == "" && subject.lecturer == "" && subject.address == ""){
            viewHolder.mainData.setVisibility(View.GONE);
            viewHolder.timeData.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
            viewHolder.header.setBackgroundColor(Color.rgb(204, 255, 255));
        } else {
            viewHolder.timeData.setLayoutParams(new LinearLayout.LayoutParams(150, ViewGroup.LayoutParams.MATCH_PARENT));
            viewHolder.mainData.setVisibility(View.VISIBLE);
            viewHolder.header.setBackgroundColor(Color.WHITE);

            switch (subject.type){
                case "Лекция": {
                    viewHolder.color.setBackgroundColor(Color.rgb(179, 225, 133));
                    break;
                }
                case "Семинар": {
                    viewHolder.color.setBackgroundColor(Color.rgb(100,	149,	237));
                    break;
                }
                case "Предэкзаменационная консультация": {
                    viewHolder.color.setBackgroundColor(Color.rgb(221,	128,	204));
                    break;
                }
                case "Экзамен": {
                    viewHolder.color.setBackgroundColor(Color.rgb(239,	48,	56));
                    break;
                }

                default: {
                    viewHolder.color.setBackgroundColor(Color.rgb(220,	220,	220));
                    break;
                }
            }
        }

        return convertView;
    }
    private class ViewHolder {
        final TextView nameView, timeFromView, timeToView, roomView, addressView, lecturerView, typeView ;
        final LinearLayout header, mainData, timeData, color;
        ViewHolder(View view){
            nameView = (TextView) view.findViewById(R.id.subject_list_item_name);
            timeFromView = (TextView) view.findViewById(R.id.subject_list_item_time_from);
            timeToView = (TextView) view.findViewById(R.id.subject_list_item_time_to);
            roomView = (TextView) view.findViewById(R.id.subject_list_item_room);
            addressView = (TextView) view.findViewById(R.id.subject_list_item_address);
            lecturerView = (TextView) view.findViewById(R.id.subject_list_item_lecturer);
            typeView = (TextView) view.findViewById(R.id.subject_list_item_type);
            mainData = (LinearLayout) view.findViewById(R.id.main_data);
            timeData = (LinearLayout) view.findViewById(R.id.time_data);
            header = (LinearLayout) view.findViewById(R.id.header);
            color = (LinearLayout) view.findViewById(R.id.color_type);
        }
    }
}
