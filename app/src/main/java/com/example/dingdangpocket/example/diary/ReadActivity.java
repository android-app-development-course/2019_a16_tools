package com.example.dingdangpocket.example.diary;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.dingdangpocket.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ReadActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private EditText mTitle;
    private EditText mContent;
    private DiaryDb mDb;
    private SQLiteDatabase mSql;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_read);

        mToolbar = (Toolbar) findViewById(R.id.activity_read_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//添加默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用

        mTitle = (EditText) this.findViewById(R.id.read_title);
        mContent = (EditText) this.findViewById(R.id.read_content);
        mDb = new DiaryDb(this);
        mSql = mDb.getWritableDatabase();
        mTitle.setText(getIntent().getStringExtra(DiaryDb.TITLE));
        mContent.setText(getIntent().getStringExtra(DiaryDb.CONTENT));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_diary_read_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemId = item.getItemId();
        if(menuItemId==android.R.id.home){
            finish();
        }else if(menuItemId == R.id.action_read_delete){
            del();
            finish();
        }else if(menuItemId == R.id.action_read_save){
            del();
            add();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void del(){
        int id = getIntent().getIntExtra(DiaryDb.ID,0);
        mSql.delete(DiaryDb.TABLE_NAME," _id = " + id,null);
    }

    public void add(){
        ContentValues cv = new ContentValues();
        cv.put(DiaryDb.TITLE, mTitle.getText().toString());
        cv.put(DiaryDb.CONTENT, mContent.getText().toString());
        cv.put(DiaryDb.TIME,getTime());
        mSql.insert(DiaryDb.TABLE_NAME,null,cv);
    }

    public String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日 HH:mm");
        Date date = new Date();
        String str = sdf.format(date);
        return str;
    }
}

