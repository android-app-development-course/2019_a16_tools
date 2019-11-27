package com.example.dingdangpocket;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListViewAdapter extends BaseAdapter {
    private ArrayList<Tool> tools;
    private LayoutInflater inflater;

    ListViewAdapter(Context context, ArrayList<Tool> tools) {
        this.tools = tools;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return tools.size();
    }

    @Override
    public Object getItem(int position) {
        return tools.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        final Tool tool = this.tools.get(position);
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.listview_item, null);
            viewHolder.tv_tool_name = convertView.findViewById(R.id.tv_tool_name);
            viewHolder.tv_tool_detail = convertView.findViewById(R.id.tv_tool_detail);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_tool_name.setText(tool.getName());
        viewHolder.tv_tool_name.setCompoundDrawables(tool.getDrawable(),null,null,null);
        viewHolder.tv_tool_detail.setText(tool.getDetail());
        return convertView;
    }

    class ViewHolder {
        TextView tv_tool_name;
        TextView tv_tool_detail;
    }

}