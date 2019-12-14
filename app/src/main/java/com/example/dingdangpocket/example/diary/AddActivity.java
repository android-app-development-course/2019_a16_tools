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

public class AddActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private EditText mEt_title;
    private EditText mEt_content;
    private DiaryDb mDb;
    private SQLiteDatabase mSqldb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_add);

        mToolbar = (Toolbar) findViewById(R.id.activity_add_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//添加默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用

        mEt_title = (EditText) this.findViewById(R.id.add_title);
        mEt_content = (EditText) this.findViewById(R.id.add_content);
        mDb = new DiaryDb(this);
        mSqldb = mDb.getWritableDatabase();
    }

    public void DbAdd() {
        ContentValues cv = new ContentValues();
        cv.put(DiaryDb.TITLE, mEt_title.getText().toString());
        cv.put(DiaryDb.CONTENT, mEt_content.getText().toString());
        cv.put(DiaryDb.TIME,getTime());
        mSqldb.insert(DiaryDb.TABLE_NAME,null,cv);
    }

    public String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日 HH:mm");
        Date date = new Date();
        String str = sdf.format(date);
        return str;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_diary_add_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemId = item.getItemId();
        if(menuItemId==android.R.id.home){
            finish();
        }else if(menuItemId == R.id.action_add_save) {
            DbAdd();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
