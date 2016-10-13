package com.guoyu.guoyuoaplatform_android.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by liuhao on 16/6/2.
 */
public abstract class BaseFragment extends Fragment {
    // 这个activity就是MainActivity
    public Activity mActivity;

    // Fragment被创建
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = getActivity();// 获取所在的activity对象
    }

    // 初始化Fragment布局
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = initView(inflater, container, savedInstanceState);
        return view;
    }

    // activity创建结束
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
        initData();

    }

    /**
     * 初始化布局, 子类必须实现
     *
     * @param savedInstanceState
     * @param container
     * @param inflater
     */
    public abstract View initView(LayoutInflater inflater, ViewGroup container,
                                  Bundle savedInstanceState);

    /**
     * 初始化数据, 子类可以不实现
     */
    public void initData() {

    }

    /**
     * 初始化view, 子类可以不实现
     */
    public void initView() {

    }

}
