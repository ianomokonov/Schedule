package com.example.schedule.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorLong;
import androidx.annotation.ColorRes;

import com.example.schedule.R;
import com.example.schedule.models.RoomSubject;
import com.example.schedule.models.Subject;

import java.util.ArrayList;

public class RoomOpacityAdapter extends ArrayAdapter<RoomSubject> {
    private LayoutInflater inflater;
    private int layout;
    private ArrayList<RoomSubject> rooms;

    public RoomOpacityAdapter(Context context, int resource, ArrayList<RoomSubject> rooms) {
        super(context, resource, rooms);
        this.rooms = rooms;
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
        final RoomSubject room = rooms.get(position);
        viewHolder.numberView.setText(room.number);
        for(int i = 0; i<room.subjects.length; i++){
            if(room.subjects[i] == null){
                viewHolder.views[i].setBackgroundColor(Color.rgb(204, 255, 255));
            } else {
                viewHolder.views[i].setBackgroundColor(Color.rgb(255, 255, 255));
            }
        }

        return convertView;
    }
    private class ViewHolder {
        final TextView numberView;
        final TextView[] views = new TextView[8];
        ViewHolder(View view){
            numberView = (TextView) view.findViewById(R.id.room_name);
            views[0] = view.findViewById(R.id.first_class);
            views[1] = (TextView) view.findViewById(R.id.second_class);
            views[2] = (TextView) view.findViewById(R.id.third_class);
            views[3] = (TextView) view.findViewById(R.id.forth_class);
            views[4] = (TextView) view.findViewById(R.id.fifth_class);
            views[5] = (TextView) view.findViewById(R.id.sixth_class);
            views[6] = (TextView) view.findViewById(R.id.seventh_class);
            views[7] = (TextView) view.findViewById(R.id.eighth_class);
        }
    }
}
