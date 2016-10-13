package com.guoyu.guoyuoaplatform_android.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.guoyu.guoyuoaplatform_android.R;

/**
 * Created by siasun on 16/9/23.
 */

public class NavigationView extends RelativeLayout implements View.OnClickListener {
    private ImageView leftView;
    private TextView titleView;
    private ImageView rightView;

    private String title;
    private int leftViewResId;
    private int rightViewResId;

    public NavigationView(Context context) {
        super(context);
    }

    public NavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);

        View view = LayoutInflater.from(context).inflate(R.layout.navigationview, this, true);
        leftView = (ImageView) view.findViewById(R.id.iv_nav_left);
        leftView.setOnClickListener(this);

        titleView = (TextView) view.findViewById(R.id.tv_nav_title);

        rightView = (ImageView) view.findViewById(R.id.iv_nav_right);
        rightView.setOnClickListener(this);

        //加载自定义的属性
        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.NavigationView);
        title = a.getString(R.styleable.NavigationView_navigationview_title);
        leftViewResId = a.getResourceId(R.styleable.NavigationView_navigationview_left_src,0);
        rightViewResId = a.getResourceId(R.styleable.NavigationView_navigationview_right_src,0);

        a.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        titleView.setText(title);
        leftView.setImageResource(leftViewResId);
        rightView.setImageResource(rightViewResId);
    }

    public ImageView getLeftView() {
        return leftView;
    }

    public TextView getTitleView() {
        return titleView;
    }

    public ImageView getRightView() {
        return rightView;
    }

    public void setTitle(String text) {
        this.titleView.setText(text);
    }

    /**
     * 导航栏点击回调接口
     * 如若需要标题可点击,可再添加
     * @author Asia
     *
     */
    public static interface ClickCallback{
        /**
         * 点击返回按钮回调
         */
        void onLeftClick();

        void onRightClick();
    }

    private ClickCallback callback;
    /**
     * 设置按钮点击回调接口
     * @param callback
     */
    public void setClickCallback(ClickCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_nav_left) {
            callback.onLeftClick();
            return;
        }
        if (id == R.id.iv_nav_right) {
            callback.onRightClick();
            return;
        }
    }
}
