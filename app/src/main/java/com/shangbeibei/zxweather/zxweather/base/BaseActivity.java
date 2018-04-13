package com.shangbeibei.zxweather.zxweather.base;

import android.app.Activity;
import android.content.Context;

import android.os.Bundle;

import android.view.View;
import android.view.ViewGroup;


import com.shangbeibei.zxweather.common.db.DataBaseManager;

import com.shangbeibei.zxweather.common.utils.RecycleUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/1/18.
 */
public abstract class BaseActivity extends Activity {
    public static Context context;
    public static List<Activity> activityList =new ArrayList<>();
    public View rootView;
    public DataBaseManager<?> dbManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;
        activityList.add(this);
        dbManager = DataBaseManager.getInstance(getApplicationContext());
    }

    /**
     * 初始化 视图View
     */
    public abstract  void initView();
    /**
     *初始化数据  注：数据处理相关内容
     */
    public abstract  void initData();
    /**
     *初始化监听器
     */
    public abstract  void initListener();

    /**
     * 先移除activiytList 集合  然后在退出的时候在销毁
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityList.remove(this);
        if (rootView != null) {
            RecycleUtil.recycleView((ViewGroup)rootView);
        }
    }

    public static void exit(){
        for (Activity activity : activityList) {
            activity.finish();
        }
        activityList.clear();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.gc();
        System.exit(0);
    }

}
