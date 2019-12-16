package com.example.dingdangpocket;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dingdangpocket.example.diary.DiaryMainActivity;
import com.example.dingdangpocket.example.painting.PaintingActivity;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.Set;

public class SortFragment extends Fragment {

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
            mFlowLayout.setAdapter(new TagAdapter<String>(MainActivity.mVals) {

                @Override
                public View getView(com.zhy.view.flowlayout.FlowLayout parent, int position, String s) {
//                将tv.xml文件填充到标签内.
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
//                    Toast.makeText(getContext(), "点击,"+MainActivity.mVals[position], Toast.LENGTH_SHORT).show();
                    switch (MainActivity.mVals[position]) {
                        case "生命时钟":
                            startActivity(new Intent(getActivity(),time.class));
                            break;
                        case "打字板":
                            Toast.makeText(getContext(), "点击,"+MainActivity.mVals[position], Toast.LENGTH_SHORT).show();
                            break;
                        case "随机数生成器":
                            startActivity(new Intent(getActivity(), RandomNumberActivity.class));
                            break;
                        case "垃圾分类查询":
                            startActivity(new Intent(getActivity(), RubbishClassificationActivity.class));
                            break;
                        case "分贝检测":
                            startActivity(new Intent(getActivity(), SoundActivity.class));
                            break;
                        case "取色器":
                            startActivity(new Intent(getActivity(), ColorPickerActivity.class));
                            break;
                        case "画板":
                            startActivity(new Intent(getActivity(), PaintingActivity.class));
                            break;
                        case "便签":
                            startActivity(new Intent(getActivity(), DiaryMainActivity.class));
                            break;
                        case "Bilibili视频封面提取":
                            startActivity(new Intent(getActivity(), BilibiliCoversActivity.class));
                            break;
                        case "历史上的今天":
                            startActivity(new Intent(getActivity(), HistoryTodayActivity.class));
                            break;
                        default:
                            Toast.makeText(getContext(), "该功能正在开发中，敬请期待！", Toast.LENGTH_SHORT).show();
                            break;
                    }
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
            //为长按标签设置长按事件.
            mFlowLayout.setOnTagLongClickListener(new TagFlowLayout.OnTagLongClickListener() {
                @Override
                public boolean onTagLongClick(View view, final int position, FlowLayout parent) {
                    AlertDialog.Builder builder =
                            new AlertDialog.Builder(getContext()); // 注意：这里只能使用当前Activity的this，

                    builder.setCancelable(false); // 设置点击Dialog其他区域不隐藏对话框，默认是true

                    builder.setTitle("提示");
                    builder.setMessage("你确定将此工具添加到收藏中吗？");

                    // PositiveButton 右边的按钮
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (MainActivity.add_collection(MainActivity.mVals[position]))
                            {
                                Toast.makeText(getContext(), R.string.add_collection_succeeded, Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getContext(), R.string.has_been_collected, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    // 左边的按钮
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getContext(), "取消", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.show();
                    return false;
                }
            });
        }

    }

}

