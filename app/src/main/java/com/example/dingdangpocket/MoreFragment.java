package com.example.dingdangpocket;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class MoreFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more, container, false);
//        init(view);
        return view;
    }

//    public void init(View view){
//        HashMap<String,String> hm1 = new HashMap<>();
//        hm1.put("表情制作","你还愁表情包不够用？表情制作工具可以帮你成为斗图王者");
//        hm1.put("图片拼接","支持拼接影视台词的图片拼接工具");
//        hm1.put("GIF合成分解","支持多个图片合成GIF,以及GIF分解成多个图片");
//        hm1.put("图片压缩","无损压缩图片");
//        HashMap<String,Drawable> hm2 = new HashMap<>();
//        hm2.put("表情制作", ContextCompat.getDrawable(view.getContext(),R.drawable.ic_emoji));
//        hm2.put("图片拼接",ContextCompat.getDrawable(view.getContext(),R.drawable.ic_split_joint));
//        hm2.put("GIF合成分解",ContextCompat.getDrawable(view.getContext(),R.drawable.ic_gif));
//        hm2.put("图片压缩",ContextCompat.getDrawable(view.getContext(),R.drawable.ic_compress));
//
//        Iterator iterator = hm1.keySet().iterator();
//        ArrayList<Tool> tools = new ArrayList<>();
//        while(iterator.hasNext()){
//            String name = (String) iterator.next();
//            String detail = hm1.get(name);
//            Drawable drawable = hm2.get(name);
//            tools.add(new Tool(name,detail,drawable));
//        }
//        NestedListView listView = view.findViewById(R.id.lv_recommend_tools);
//        ListViewAdapter listViewAdapter = new ListViewAdapter(getContext(),tools);
//        listView.setAdapter(listViewAdapter);
//    }
}


class Tool {
    private String name;
    private String detail;
    private Drawable drawable;
    public Tool(){
        name = "";
        detail = "";
    }
    public Tool(String Name, String Detail, Drawable drawableleft){
        name = Name;
        detail = Detail;
        this.drawable = drawableleft;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public String getName() {
        return name;
    }
    public String getDetail() {
        return detail;
    }

    public Drawable getDrawable() {
        return drawable;
    }
}
