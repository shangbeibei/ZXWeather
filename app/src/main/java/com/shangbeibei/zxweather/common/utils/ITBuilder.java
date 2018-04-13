package com.shangbeibei.zxweather.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * PACKAGE_NAME: com.tomato.z.zxweather.common.utils
 * FUNCTIONAL_DESCRIPTION:
 * CREATE_BY: 尽际
 * CREATE_TIME: 16/1/16 下午4:13
 * MODIFICATORY_DESCRIPTION:
 * MODIFY_BY:
 * MODIFICATORY_TIME:
 */
public class ITBuilder {
    public static String getTimeFormat1(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        String curTime = simpleDateFormat.format(curDate);
        return curTime;
    }

    public static String getTimeFormat2(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date curDate = new Date(System.currentTimeMillis());
        String curTime = simpleDateFormat.format(curDate);
        return curTime;
    }

    public static String getTimeFormat3(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmm");
        Date curDate = new Date(System.currentTimeMillis());
        String curTime = simpleDateFormat.format(curDate);
        return curTime;
    }

    //根据日期取得星期几
    public static String getWeek(Calendar calendar){
        String mWay = String.valueOf(calendar.get(Calendar.DAY_OF_WEEK));
        String week = "";
        switch (Integer.parseInt(mWay)) {
            case 1:
                week ="日";
                break;
            case 2:
                week ="一";
                break;
            case 3:
                week ="二";
                break;
            case 4:
                week ="三";
                break;
            case 5:
                week ="四";
                break;
            case 6:
                week ="五";
                break;
            case 7:
                week ="六";
                break;
        }

        return "周" + week;
    }

    /*
     *
     * 功能描述: 6位随机数
     * 创建作者: Z
     * 创建时间:
     * 修改描述:
     * 修改作者:
     * 修改时间:
     */
    public static int getSixNo(){
        int[] array = {0,1,2,3,4,5,6,7,8,9};
        Random rand = new Random();
        for (int i = 10; i > 1; i--) {
            int index = rand.nextInt(i);
            int tmp = array[index];
            array[index] = array[i - 1];
            array[i - 1] = tmp;
        }
        int result = 0;
        for(int i = 0; i < 6; i++)
            result = result * 10 + array[i];
        System.out.println(result);
        return result;
    }
}
