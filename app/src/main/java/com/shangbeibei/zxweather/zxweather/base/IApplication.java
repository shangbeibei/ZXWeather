package com.shangbeibei.zxweather.zxweather.base;

import android.app.Application;
import android.content.Context;

/**CREATE_BY: 商贝贝
 * FUNCTIONAL_DSECRIPTION: the whole appliction of the ZXWeather
 * Created by Administrator on 2016/1/18.
 */
public class IApplication extends Application{
     public static Context applicationContext;
    private static IApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext=this;
        instance=this;
    }
    public static IApplication getInstance(){
        return instance;
    }
}
