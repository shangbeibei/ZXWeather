package com.shangbeibei.zxweather.zxweather.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shangbeibei.zxweather.common.utils.RecycleUtil;

/**
 * Created by Administrator on 2016/1/18.
 */
public abstract class BaseFragment extends Fragment {
    public View rootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    /**
     * 初始化视图，
     * 只做控件的获取不做数据绑定等处理
     */
    public abstract void  initView();

    /**
     * 初始化数据
     * 数据处理相关的
     */
    public abstract void initData();

    /**
     * 初始化监听器
     */
    public abstract void initListener();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (rootView != null) {
            RecycleUtil.recycleView((ViewGroup) rootView);
        }
    }
}
