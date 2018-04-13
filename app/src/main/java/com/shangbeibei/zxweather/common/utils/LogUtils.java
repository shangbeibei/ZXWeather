package com.shangbeibei.zxweather.common.utils;

import android.util.Log;



/**
 * 
 * 功能描述:日志打印工具类,当打生产版本时可将debugVer改为false
 * 创建作者:Z
 * 创建时间：
 * 修改描述：
 * 修改作者：
 * 修改时间：
 */
public class LogUtils {
	
	public static final int TYPE_LOG = 0;
	public static final int TYPE_CONSOLE = 1;
	public static final int TYPE_CRASH_EXCEPTION = 2;
	
	/**
	 * 日志打印
	 * 
	 * @param logType 选择LogUtils.TYPE_LOG或者LogUtils.
	 *            TYPE_CONSOLE采用Log.i("info", msg)或者System.out.println(msg)输出的形式
	 * @param msg 日志信息
	 */
	public static void print(int logType, String msg) {

//		if ((false == Constants.APPLICATION_BASIC_LOG) || (null == msg)) {
//			return;
//		}

		switch (logType) {
			case TYPE_LOG:
				Log.i("ZXWeather", msg);
				break;
			case TYPE_CONSOLE:
				System.out.println(msg);
				break;
			case TYPE_CRASH_EXCEPTION:
				Log.e("ZXWeather", msg);
				break;
		}
	}
}
