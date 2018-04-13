package com.shangbeibei.zxweather.zxweather.init.logic;

import android.util.Log;

import com.shangbeibei.zxweather.common.db.DBHelper;
import com.shangbeibei.zxweather.common.db.DataBaseManager;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/1/18.
 */
public class WelcomeLogic {
    /**
     * 功能描述 ：向（天气）省份表中插入数据
     * @param dbManager
     * @param list
     * @return
     */
    public static boolean insertWeatherProvincesTableData(DataBaseManager<?> dbManager, List<HashMap<String,Object>> list){
       long insertSum =0 ;
        try {
            String[] weatherProvinceContent ={"name","province_id"};
            insertSum =dbManager.insertBatchData2(DBHelper.WEATHER_PROVINCES_TABLE,list,weatherProvinceContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (insertSum > 0) {
            return  true;
        }else {
            return false;
        }
    }

    /**
     * 功能描述 ：向（天气）省份表中插入数据
     * @param dbManager
     * @param list
     * @return
     */
    public static boolean insertWeatherCitiesTableData(DataBaseManager<?> dbManager, List<HashMap<String,Object>> list){
        long insertSum =0 ;
        try {
            String[] weatherCitiesContent = {"province_id","name","city_num"};
            insertSum = dbManager.insertBatchData2(DBHelper.WEATHER_CITYS_TABLE,list,weatherCitiesContent);
//            String[] weatherCitiesContent ={"province_id","name","city_num"};
//            insertSum =dbManager.insertBatchData2(DBHelper.WEATHER_PROVINCES_TABLE,list,weatherCitiesContent);
            Log.e("--","城市数据"+insertSum+"条");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (insertSum > 0) {
            return  true;
        }else {
            return false;
        }
    }
}
