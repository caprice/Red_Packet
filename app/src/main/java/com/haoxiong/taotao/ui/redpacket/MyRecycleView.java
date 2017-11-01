package com.haoxiong.taotao.ui.redpacket;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * 类描述：
 * 作者： YinJin
 * 创建时间：2017/11/1.19:57
 */

public class MyRecycleView extends RecyclerView {
    public MyRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        int newHeightSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthSpec, newHeightSpec);
    }
}
