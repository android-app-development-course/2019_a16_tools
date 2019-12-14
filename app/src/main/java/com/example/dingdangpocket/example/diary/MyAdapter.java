package com.example.dingdangpocket.example.diary;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dingdangpocket.R;

public class MyAdapter extends BaseAdapter {
    private Context mContext;
    private Cursor mCursor;
    private LinearLayout mLayout;
    public MyAdapter(Context mContext,Cursor mCursor) {
        this.mContext = mContext;
        this.mCursor = mCursor;
    }

    @Override
    public int getCount() {
        return mCursor.getCount();
    }

    @Override
    public Object getItem(int position) {
        return mCursor.getPosition();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        mLayout = (LinearLayout) inflater.inflate(R.layout.diary_listview,null);
        TextView title = (TextView) mLayout.findViewById(R.id.diary_title);
        TextView content = (TextView) mLayout.findViewById(R.id.diary_content);
        TextView time = (TextView) mLayout.findViewById(R.id.diary_time);
        mCursor.moveToPosition(position);
        String dbtitle = mCursor.getString(mCursor.getColumnIndex("title"));
        String dbcontext = mCursor.getString(mCursor.getColumnIndex("content"));
        String dbtime = mCursor.getString(mCursor.getColumnIndex("time"));
        title.setText(dbtitle);
        content.setText(dbcontext);
        time.setText(dbtime);
        return mLayout;
    }
}

