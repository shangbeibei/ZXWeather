package com.shangbeibei.zxweather.zxweather.main.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.shangbeibei.zxweather.R;
import com.shangbeibei.zxweather.common.constants.NetConstants;
import com.shangbeibei.zxweather.common.db.DataBaseManager;
import com.shangbeibei.zxweather.common.ioverride.MyStringRequest;
import com.shangbeibei.zxweather.common.utils.PreferencesUtils;
import com.shangbeibei.zxweather.zxweather.base.BaseFragment;
import com.shangbeibei.zxweather.zxweather.weather.activity.AreaIDDialogActivity;
import com.shangbeibei.zxweather.zxweather.weather.bean.WeatherIDCityBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Administrator on 2016/1/18.
 */
public class WeatherFragment extends BaseFragment implements View.OnClickListener {
    DataBaseManager<?> dbManager = DataBaseManager.getInstance(getContext());
    private String cityName;
    private String cityNum;
    private int index;
    private TextView date;
    private TextView week;
    private TextView temperature;
    private TextView address;
    private TextView work;
    private TextView shidu;
    private ImageView weather;

    private TextView week_first;
    private ImageView weather_first;
    private TextView Temp_first;
    private TextView week_two;
    private ImageView weather_two;
    private TextView Temp_two;
    private TextView week_three;
    private ImageView weather_three;
    private TextView Temp_three;
    private TextView week_four;
    private ImageView weather_four;
    private TextView Temp_four;
    private TextView week_five;
    private ImageView weather_five;
    private TextView Temp_five;
    private ImageView loaction;

    private LocationClient mLocationClient;
    private LocationClientOption option;
    private MyLocationListener myLocationListener;

    public WeatherFragment() {

    }

    public WeatherFragment(String cityName, int index) {
        this.cityName = cityName;
        this.index = index;
    }

    @Override
    public void onResume() {
        super.onResume();
        initCurrentData();
    }

    private void initCurrentData() {
        if (index != 0) {
            cityName = PreferencesUtils.getString(getContext(), "cityName" + index);
            address.setText(cityName);
            initData();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_weather, null);
        initView();
        initDateData();
        // initCacheData();
        initData();
        initListener();
        return rootView;
    }


    @Override
    public void initView() {
        date = ((TextView) rootView.findViewById(R.id.data_TextView));
        week = ((TextView) rootView.findViewById(R.id.week_TextView));
        temperature = ((TextView) rootView.findViewById(R.id.temperature_TextView));
        address = ((TextView) rootView.findViewById(R.id.address_TextView));
        work = ((TextView) rootView.findViewById(R.id.backlog_TextView));
        shidu = ((TextView) rootView.findViewById(R.id.shidu_TextView));
        weather = ((ImageView) rootView.findViewById(R.id.weather));


        week_first = ((TextView) rootView.findViewById(R.id.Tuesday_TextView_01));
        weather_first = ((ImageView) rootView.findViewById(R.id.Tuesday_ImageView));
        Temp_first = ((TextView) rootView.findViewById(R.id.Tuesday_TextView_02));

        week_two = ((TextView) rootView.findViewById(R.id.Wednesday_TextView_01));
        weather_two = ((ImageView) rootView.findViewById(R.id.Wednesday_ImageView));
        Temp_two = ((TextView) rootView.findViewById(R.id.Wednesday_TextView_02));

        week_three = ((TextView) rootView.findViewById(R.id.Thursday_TextView_01));
        weather_three = ((ImageView) rootView.findViewById(R.id.Thursday_ImageView));
        Temp_three = ((TextView) rootView.findViewById(R.id.Thursday_TextView_02));

        week_four = ((TextView) rootView.findViewById(R.id.Friday_TextView_01));
        weather_four = ((ImageView) rootView.findViewById(R.id.Friday_ImageView));
        Temp_four = ((TextView) rootView.findViewById(R.id.Friday_TextView_02));

        week_five = ((TextView) rootView.findViewById(R.id.Saturday_TextView_01));
        weather_five = ((ImageView) rootView.findViewById(R.id.Saturday_ImageView));
        Temp_five = ((TextView) rootView.findViewById(R.id.Saturday_TextView_02));
        loaction = (ImageView) rootView.findViewById(R.id.location_ImageView);
    }

    @Override
    public void initData() {

        if (index == 0) {

            mLocationClient = new LocationClient(getContext());
            myLocationListener = new MyLocationListener();
            mLocationClient.registerLocationListener(myLocationListener);
            option = new LocationClientOption();
            option.setOpenGps(true);
            option.setIsNeedAddress(true);
            option.setCoorType("bd09ll");
            option.setScanSpan(20 * 1000 * 60);
            mLocationClient.setLocOption(option);
            mLocationClient.start();
            loaction.setVisibility(View.INVISIBLE);

        } else {

            address.setText(cityName);
            cityNum = nameToId(cityName);
            RequestQueue queue = Volley.newRequestQueue(getContext());
            MyStringRequest stringRequest = new MyStringRequest(Request.Method.GET, NetConstants.WEATHER_URL + cityNum,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Log.e("--result:", response.toString());
                            parseJson(response);
                            //PreferencesUtils.putString(getContext(), "cacheData", response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            //Toast.makeText(getContext(), "网络异常，无法更新数据", Toast.LENGTH_LONG).show();
                        }
                    });
            queue.add(stringRequest);


        }


    }

    private void initCacheData() {

        String cacheJson = PreferencesUtils.getString(getContext(), "cacheData");
        if (cacheJson != null) {

            parseJson(cacheJson);

        }
    }

    private void initDateData() {

        TextView[] texts = {week, week_first, week_two, week_three, week_four, week_five};
        Date dates = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        String dateString = formatter.format(dates);
        date.setText(dateString);
        Calendar calendar = new GregorianCalendar();
        for (int i = 0; i < 6; i++) {

            texts[i].setText(getWeekOfDate(dates));
            calendar.setTime(dates);
            calendar.add(calendar.DATE, 1);
            dates = calendar.getTime();

        }


    }


    @Override
    public void initListener() {

        loaction.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        switch (v.getId()) {

            case R.id.location_ImageView:
                intent.setClass(getContext(), AreaIDDialogActivity.class);
                bundle.putInt("index", index);
                intent.putExtra("index", bundle);
                getActivity().startActivity(intent);

                break;

        }
    }

    private void parseJson(String response) {


        String shiduString = null;
        String cleanCarString = null;
        List<String> weathers = new ArrayList<>();
        List<String> weathers2 = new ArrayList<>();
        TextView temps[] = {temperature, Temp_first, Temp_two, Temp_three, Temp_four, Temp_five};
        ImageView images[] = {weather, weather_first, weather_two, weather_three, weather_four, weather_five};
        try {
            JSONObject jsonObject1 = new JSONObject(response).getJSONObject("forecast");
            JSONObject jsonObject2 = new JSONObject(response).getJSONObject("realtime");
            JSONArray jsonArray = new JSONObject(response).getJSONArray("index");
            JSONObject jsonObject3 = (JSONObject) jsonArray.get(3);
            shiduString = jsonObject2.getString("SD");
            cleanCarString = jsonObject3.getString("index");
            String temp1 = jsonObject1.getString("temp1").replace("℃", "");
            String temp2 = jsonObject1.getString("temp2").replace("℃", "");
            String temp3 = jsonObject1.getString("temp3").replace("℃", "");
            String temp4 = jsonObject1.getString("temp4").replace("℃", "");
            String temp5 = jsonObject1.getString("temp5").replace("℃", "");
            String temp6 = jsonObject1.getString("temp6").replace("℃", "");
            String weather1 = jsonObject1.getString("weather1");
            String weather2 = jsonObject1.getString("weather2");
            String weather3 = jsonObject1.getString("weather3");
            String weather4 = jsonObject1.getString("weather4");
            String weather5 = jsonObject1.getString("weather5");
            String weather6 = jsonObject1.getString("weather6");
            weathers.add(temp1);
            weathers.add(temp2);
            weathers.add(temp3);
            weathers.add(temp4);
            weathers.add(temp5);
            weathers.add(temp6);

            weathers2.add(weather1);
            weathers2.add(weather2);
            weathers2.add(weather3);
            weathers2.add(weather4);
            weathers2.add(weather5);
            weathers2.add(weather6);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        shidu.setText("湿度 " + shiduString);
        work.setText(cleanCarString + "洗车");

        for (int i = 0; i < 6; i++) {
            if (i == 0) {
                temps[i].setText(weathers.get(i));
            } else
                temps[i].setText(weathers.get(i) + "℃");
        }

        for (int i = 0; i < 6; i++) {


            setWeatherImage(images[i], weathers2.get(i));

        }

    }


    private void setWeatherImage(ImageView weather_imageview, String str) {
        String[] strs = str.split("转");
        if (strs[0].contains("暴雨")) {
            weather_imageview.setImageResource(R.drawable.weather_baoyu);
        } else if (strs[0].contains("暴雪")) {
            weather_imageview.setImageResource(R.drawable.weather_baoxue);
        } else if (strs[0].contains("大暴雨")) {
            weather_imageview.setImageResource(R.drawable.weather_dabaoyu);
        } else if (strs[0].contains("大雪")) {
            weather_imageview.setImageResource(R.drawable.weather_daxue);
        } else if (strs[0].contains("阵雪")) {
            weather_imageview.setImageResource(R.drawable.weather_zhenxue);
        } else if (strs[0].contains("大雨")) {
            weather_imageview.setImageResource(R.drawable.weather_dayu);
        } else if (strs[0].contains("冻雨")) {
            weather_imageview.setImageResource(R.drawable.weather_dongyu);
        } else if (strs[0].contains("多云")) {
            weather_imageview.setImageResource(R.drawable.weather_duoyun);
        } else if (strs[0].contains("浮尘")) {
            weather_imageview.setImageResource(R.drawable.weather_fuchen);
        } else if (strs[0].contains("降雨伴有冰雹")) {
            weather_imageview.setImageResource(R.drawable.weather_zhenyubingbao);
        } else if (strs[0].contains("雷阵雨")) {
            weather_imageview.setImageResource(R.drawable.weather_leizhenyu);
        } else if (strs[0].contains("强沙尘暴")) {
            weather_imageview.setImageResource(R.drawable.weather_qiangshachenbao);
        } else if (strs[0].contains("晴")) {
            weather_imageview.setImageResource(R.drawable.weather_qing);
        } else if (strs[0].contains("沙尘暴")) {
            weather_imageview.setImageResource(R.drawable.weather_shachenbao);
        } else if (strs[0].contains("小雪")) {
            weather_imageview.setImageResource(R.drawable.weather_xiaoxue);
        } else if (strs[0].contains("小雨")) {
            weather_imageview.setImageResource(R.drawable.weather_xiaoyu);
        } else if (strs[0].contains("扬沙")) {
            weather_imageview.setImageResource(R.drawable.weather_yangsha);
        } else if (strs[0].contains("阴")) {
            weather_imageview.setImageResource(R.drawable.weather_yin);
        } else if (strs[0].contains("雨夹雪")) {
            weather_imageview.setImageResource(R.drawable.weather_yujiaxue);
        } else if (strs[0].contains("阵雨")) {
            weather_imageview.setImageResource(R.drawable.weather_zhenyu);
        } else if (strs[0].contains("中雪")) {
            weather_imageview.setImageResource(R.drawable.weather_zhongxue);
        } else if (strs[0].contains("中雨")) {
            weather_imageview.setImageResource(R.drawable.weather_zhongyu);
        } else if (strs[0].contains("雾")) {
            weather_imageview.setImageResource(R.drawable.weather_wu);
        } else if (strs[0].contains("霾")) {
            weather_imageview.setImageResource(R.drawable.weather_mai);
        }
    }

    private String nameToId(String name) {

        String cityNum = null;
        try {
            String columns[] = {"city_num"};
            Cursor cursor = dbManager.queryData("weather_citys", columns, "name='" + name + "'", null, null, null, null);
            while (cursor.moveToNext())
                cityNum = cursor.getString(0);
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return cityNum;
    }

    public static String getWeekOfDate(Date date) {

        String[] weekOfDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekOfDays[w];
    }

    private class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation BDLocation) {

            if (BDLocation == null)
                return;
            else {

                String strs[] = BDLocation.getCity().split("市");
                cityName = strs[0];
                address.setText("所在城市：\n" + cityName);
                cityNum = nameToId(cityName);
                RequestQueue queue = Volley.newRequestQueue(getContext());
                MyStringRequest stringRequest = new MyStringRequest(Request.Method.GET, NetConstants.WEATHER_URL + cityNum,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Log.e("--result:", response.toString());
                                parseJson(response);
                                PreferencesUtils.putString(getContext(), "cacheData", response);
                                PreferencesUtils.putString(getContext(), "cityName0", cityName);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                //Toast.makeText(getContext(), "网络异常，无法更新数据", Toast.LENGTH_LONG).show();
                            }
                        });
                queue.add(stringRequest);

            }

        }
    }

    @Override
    public void onDestroy() {
        if (mLocationClient != null && myLocationListener != null) {

            mLocationClient.stop();
            mLocationClient.unRegisterLocationListener(myLocationListener);
        }
        super.onDestroy();
    }
}
