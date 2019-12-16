package com.example.dingdangpocket;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.wyt.searchbox.SearchFragment;
import com.wyt.searchbox.custom.IOnSearchClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bnView;
    ViewPager viewPager;
    SearchFragment searchFragment;
    public static final String[] mVals = new String[]
            {"打字板",  "表情制作", "带壳截图", "GIF合成分解",
                    "图片压缩", "图片拼接", "图片转链接", "随机数生成器","垃圾分类查询","分贝检测"
                    ,"取色器","画板","便签","历史上的今天","Bilibili视频封面提取","生命时钟"
            };
    public static ArrayList<String> collections =
            new ArrayList(Arrays.asList("打字板", "画板"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);//恢复原有的样式
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // ---------------0--- 底部导航栏, viewpager嵌fragment, 可左右滑动，可直接点----------------

        bnView = findViewById(R.id.bottom_nav_view);
        viewPager = findViewById(R.id.view_pager);

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new CollectionFragment());
        fragments.add(new SortFragment());
        fragments.add(new MoreFragment());

        FragmentAdapter adapter = new FragmentAdapter(fragments, getSupportFragmentManager());
        viewPager.setAdapter(adapter);


        //BottomNavigationView 点击事件监听
        bnView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int menuId = menuItem.getItemId();
                // 跳转指定页面：Fragment
                switch (menuId) {
                    case R.id.tab_one:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.tab_two:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.tab_three:
                        viewPager.setCurrentItem(2);
                        break;
                }
                return false;
            }
        });

        // ViewPager 滑动事件监听
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                //将滑动到的页面对应的 menu 设置为选中状态
                bnView.getMenu().getItem(i).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        //-----------------------------end--------------------------------------------

        //--------------
        searchFragment = SearchFragment.newInstance();
        searchFragment.setOnSearchClickListener(new IOnSearchClickListener() {
            @Override
            public void OnSearchClick(String keyword) {
                //这里处理逻辑
                Toast.makeText(MainActivity.this, keyword, Toast.LENGTH_SHORT).show();
            }
        });


    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    // 顶部栏的搜索功能
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            searchFragment.show(getSupportFragmentManager(),SearchFragment.TAG);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // 左侧菜单的按钮点击事件
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_selfcenter) {
            //单击个人中心进入登录界面
            startActivity(new Intent(this,Userpage.class));

        } else if (id == R.id.nav_alltools) {

        } else if (id == R.id.nav_thememanager) {

        } else if (id == R.id.nav_setting) {

        } else if (id == R.id.nav_feedback) {

        } else if (id == R.id.nav_about) {
            startActivity(new Intent(MainActivity.this, AboutActivity.class));
        }else if(id==R.id.logout){
            startActivity(new Intent(this,Login.class));
        }
        else if(id==R.id.exit){
            finish();
        }

        // 注释掉之后，关闭activity之后左侧菜单还显示
        /*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);*/

        return true;
    }

    public static boolean add_collection(String tool_name){
        if (collections.contains(tool_name))
        {
            return false;//已收藏，返回false.
        }else{
            collections.add(tool_name);
            return true;//收藏成功，返回true.
        }
    }
}
