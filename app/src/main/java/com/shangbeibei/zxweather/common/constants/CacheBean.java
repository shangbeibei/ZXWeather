package com.shangbeibei.zxweather.common.constants;

import android.content.Context;

import com.shangbeibei.zxweather.common.utils.PreferencesUtils;


/**
 * PACKAGE_NAME: com.tomato.z.zxweather.common.constants
 * FUNCTIONAL_DESCRIPTION:
 * CREATE_BY: 尽际
 * CREATE_TIME: 16/1/15 上午9:35
 * MODIFICATORY_DESCRIPTION:
 * MODIFY_BY:
 * MODIFICATORY_TIME:
 */
public class CacheBean {
    public static CacheBean cacheBean;
    private static Context context;//当前上下文对象

    private String preLocationLatitude;
    private String preLocationLongitude;

    public static CacheBean getInstance(Context context) {
        CacheBean.context = context;
        if (cacheBean == null) {
            cacheBean = new CacheBean();

            cacheBean.setPreLocationLatitude(PreferencesUtils.getString(context,"Latitude"));
            cacheBean.setPreLocationLongitude(PreferencesUtils.getString(context,"Longitude"));
            return cacheBean;
        }
        return cacheBean;
    }


    public String getPreLocationLatitude() {
        return preLocationLatitude;
    }

    public void setPreLocationLatitude(String preLocationLatitude) {
        this.preLocationLatitude = preLocationLatitude;
    }

    public String getPreLocationLongitude() {
        return preLocationLongitude;
    }

    public void setPreLocationLongitude(String preLocationLongitude) {
        this.preLocationLongitude = preLocationLongitude;
    }
}
