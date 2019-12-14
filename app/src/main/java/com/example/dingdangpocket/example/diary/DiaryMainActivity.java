package com.example.dingdangpocket.example.diary;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.dingdangpocket.R;

public class DiaryMainActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private ListView mList;
    private MyAdapter mAdapter;
    private DiaryDb mDiarydb;
    private Cursor cursor;
    private SQLiteDatabase dbreader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_main);

        mToolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        initToolbar();

        mList = (ListView) this.findViewById(R.id.list);
        mDiarydb = new DiaryDb(this);
        dbreader = mDiarydb.getReadableDatabase();
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                cursor.moveToPosition(i);
                Intent intent = new Intent(DiaryMainActivity.this,ReadActivity.class);
                intent.putExtra(DiaryDb.ID,cursor.getInt(cursor.getColumnIndex(DiaryDb.ID)));
                intent.putExtra(DiaryDb.TITLE,cursor.getString(cursor.getColumnIndex(DiaryDb.TITLE)));
                intent.putExtra(DiaryDb.CONTENT,cursor.getString(cursor.getColumnIndex(DiaryDb.CONTENT)));
                intent.putExtra(DiaryDb.TIME,cursor.getString(cursor.getColumnIndex(DiaryDb.TIME)));
                startActivity(intent);
            }
        });

    }

    public void selectDb() {
        cursor = dbreader.query
                (DiaryDb.TABLE_NAME,null,null,null,null,null,DiaryDb.ID + " DESC ");
        mAdapter = new MyAdapter(this,cursor);
        mList.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        selectDb();
    }

    // 初始化工具栏
    private void initToolbar() {
        mToolbar.inflateMenu(R.menu.activity_diary_main_menu);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int menuItemId = item.getItemId();
                if (menuItemId == R.id.action_main_add) {
                    startActivity(new Intent(DiaryMainActivity.this, AddActivity.class));
                }
                return true;
            }
        });
    }

}
