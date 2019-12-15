package com.example.dingdangpocket;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class LVHistoryTodayAdapter extends BaseAdapter {
    private ArrayList<HistoryToday> items;
    private LayoutInflater inflater;
    LVHistoryTodayAdapter(Context context,ArrayList<HistoryToday> items) {
        this.inflater = LayoutInflater.from(context);
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        final HistoryToday item = this.items.get(position);
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.listview_item_history_today, null);
            viewHolder.tv_date = convertView.findViewById(R.id.tv_date);
            viewHolder.tv_title = convertView.findViewById(R.id.tv_title);
            viewHolder.img_history_today = convertView.findViewById(R.id.img_history_today);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.clear();
        }
        viewHolder.tv_date.setText(item.getYear());
        viewHolder.tv_title.setText(item.getTitle());
        Glide.with(inflater.getContext())
                .load(item.getImageUrl())
                .into(viewHolder.img_history_today);
        return convertView;
    }

    class ViewHolder {
        TextView tv_date;
        TextView tv_title;
        ImageView img_history_today;
        void clear(){
            tv_date.setText(null);
            tv_title.setText(null);
            img_history_today.setImageDrawable(null);
        }
    }

}

class HistoryToday{
    private String date;
    private String title;
    private String imageUrl;
    public HistoryToday(){
        date = "";
        title = "";
        imageUrl = "";
    }
    HistoryToday(String date, String title, String imageUrl){
        this.date = date;
        this.title = title;
        this.imageUrl = imageUrl;
    }
    void setYear(String date){ this.date = date;}
    void setTitle(String title){ this.title = title;}
    void setImageUrl(String imageUrl){ this.imageUrl = imageUrl;}
    String getYear(){return this.date;}
    String getTitle(){return this.title;}
    String getImageUrl(){return this.imageUrl;}

}