package com.parse.starter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by se7en_000 on 2016-01-16.
 */
public class CustomAdapter extends BaseAdapter {
    private Context mContext;
    private List<RoomForAdapter> roomList;

    //constructor


    public CustomAdapter(Context mContext, List<RoomForAdapter> roomList) {
        this.mContext = mContext;
        this.roomList = roomList;
    }

    @Override
    public int getCount() {
        return roomList.size();
    }

    @Override
    public Object getItem(int position) {
        return roomList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(mContext, R.layout.list_two, null);
        TextView date = (TextView)v.findViewById(R.id.textView11);
        TextView time = (TextView)v.findViewById(R.id.textView12);
        TextView title = (TextView)v.findViewById(R.id.textView13);
        TextView category = (TextView)v.findViewById(R.id.textView14);
        TextView availability = (TextView)v.findViewById(R.id.textView15);
        //Set text for TextView
        date.setText(roomList.get(position).getDate());
        time.setText(roomList.get(position).getTime());
        title.setText(roomList.get(position).getTitle());
        category.setText(roomList.get(position).getCategory());
        availability.setText(roomList.get(position).getAvailability());
        //Save id to tag
        v.setTag(roomList.get(position).getId());
        return v;
    }
}