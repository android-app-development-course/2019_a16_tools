package com.example.dingdangpocket;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import es.dmoral.toasty.Toasty;


public class SortFragment extends Fragment {
    private final String[] mVals = new String[]
            {"打字板", "花板", "表情制作", "带壳截图", "GIF合成分解", "取色器",
                    "图片压缩", "图片拼接", "图片转链接"};
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sort, container, false);
        init(view);
        return view;
    }
    public void init(View view){
        int id_flowlayout[] = {
                R.id.id_flowlayout_1,
                R.id.id_flowlayout_2,
                R.id.id_flowlayout_3,
                R.id.id_flowlayout_4,
                R.id.id_flowlayout_5,
                R.id.id_flowlayout_6
        };
        //获取布局填充器,一会将tv.xml文件填充到标签内.
        final LayoutInflater mInflater = LayoutInflater.from(getActivity());
        for(int id : id_flowlayout) {
            //初始化布局和适配器,直接粘就行.
            final TagFlowLayout mFlowLayout = view.findViewById(id);
            mFlowLayout.setAdapter(new TagAdapter<String>(mVals) {

                @Override
                public View getView(com.zhy.view.flowlayout.FlowLayout parent, int position, String s) {
//                将flowlayout_item.xml文件填充到标签内.
                    TextView tv = (TextView) mInflater.inflate(R.layout.flowlayout_item,
                            mFlowLayout, false);
//               为标签设置对应的内容
                    tv.setText(s);
                    return tv;
                }

                //             为标签设置预点击内容(就是一开始就处于点击状态的标签)
                @Override
                public boolean setSelected(int position, String s) {
                    return s.equals("Android");
                }
            });
//          为点击标签设置点击事件.
            mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                @Override
                public boolean onTagClick(View view, int position, com.zhy.view.flowlayout.FlowLayout parent) {
                    Toasty.normal(getActivity(), mVals[position], Toast.LENGTH_SHORT).show();
                    //view.setVisibility(View.GONE);
                    return true;
                }
            });

//          展示哪些标签处于选中状态,这个很重要我们设置标签可点击就是为了把用户选中状态的标签中的数据上传.
            mFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
                @Override
                public void onSelected(Set<Integer> selectPosSet) {

                }
            });

            mFlowLayout.setOnLongClickListener(new TagFlowLayout.OnLongClickListener(){
                //长按收藏
                @Override
                public boolean onLongClick(View view) {

                    return false;
                }
            });
        }

    }

}
