package com.guoyu.guoyuoaplatform_android.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guoyu.guoyuoaplatform_android.R;

/**
 * Created by siasun on 16/9/23.
 */

public class ContentView extends LinearLayout {

    private TextView mTextView;
    private ImageView mImageView;

    private String titleText;
    private int titleTextColor;
    private float titleTextSize;

    public ContentView(Context context) {
        super(context);
    }

    public ContentView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //加载视图的布局
        LayoutInflater.from(context).inflate(R.layout.contentview,this,true);

        //加载自定义的属性
        TypedArray a=context.obtainStyledAttributes(attrs,R.styleable.ContentView);
        titleText=a.getString(R.styleable.ContentView_conentview_text);
        titleTextColor=a.getColor(R.styleable.ContentView_contentview_color, Color.WHITE);
        titleTextSize=a.getDimension(R.styleable.ContentView_contentview_textSize,20f);

        //回收资源，这一句必须调用
        a.recycle();
    }

    /**
     * 此方法会在所有的控件都从xml文件中加载完成后调用
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        //获取子控件
        mTextView= (TextView) findViewById(R.id.contentview_tv);
        mImageView= (ImageView) findViewById(R.id.contentview_iv);

        //将从资源文件中加载的属性设置给子控件
        if (!TextUtils.isEmpty(titleText))
            setPageTitleText(titleText);
        setPageTitleTextColor(titleTextColor);
        setPageTitleTextSize(titleTextSize);
    }

    /**
     * 设置标题文字
     * @param text
     */
    public void setPageTitleText(String text) {
        mTextView.setText(text);
    }

    /**
     * 设置标题文字颜色
     * @param color
     */
    public void setPageTitleTextColor(int color) {
        mTextView.setTextColor(color);
    }

    /**
     * 设置标题文字大小
     * @param size
     */
    public void setPageTitleTextSize(float size) {
        mTextView.setTextSize(size);
    }

    /**
     * 设置按钮点击事件监听器
     * @param listener
     */
//    public void setOnContentViewClickListener(OnClickListener listener) {
//        mImageView.setOnClickListener(listener);
//    }
}
