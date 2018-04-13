package com.shangbeibei.zxweather.zxweather.init.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.shangbeibei.zxweather.R;
import com.shangbeibei.zxweather.common.db.DBHelper;
import com.shangbeibei.zxweather.common.parser.xml.XmlDataParser;
import com.shangbeibei.zxweather.common.utils.ListUtils;
import com.shangbeibei.zxweather.common.utils.PreferencesUtils;
import com.shangbeibei.zxweather.common.utils.StreamUtils;
import com.shangbeibei.zxweather.zxweather.base.BaseActivity;
import com.shangbeibei.zxweather.zxweather.init.logic.WelcomeLogic;
import com.shangbeibei.zxweather.zxweather.main.activity.MainActivity;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

public class WelcomeActivity extends BaseActivity implements Animation.AnimationListener{
        private ImageView welcomeImageView = null;
        private Animation alphaAnimation = null;
        private Intent intent;
        private XmlDataParser xmlDataParser =new XmlDataParser();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initListener();
    }

    @Override
    public void initView() {
        super.rootView =getLayoutInflater().inflate(R.layout.activity_welcome,null);
        setContentView(rootView);
        welcomeImageView = ((ImageView) rootView.findViewById(R.id.welcome_imageView));
    }

    @Override
    public void initData() {
        welcomeImageView.setImageResource(R.drawable.welcome_bg);
        welcomeImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        alphaAnimation = AnimationUtils.loadAnimation(this,R.anim.anim_welcome_activity_alpha2);
        alphaAnimation.setFillEnabled(true);//启动fill保持
        alphaAnimation.setFillAfter(true);// 设置动画的最后一帧是保持在VieW 上面
        welcomeImageView.setAnimation(alphaAnimation);
    }

    @Override
    public void initListener() {
        alphaAnimation.setAnimationListener(this);//  设置动画监听
    }

    @Override
    public void onAnimationStart(Animation animation) {
            intent =new Intent();
        if (!PreferencesUtils.getBoolean(this,"IS_HAVE_LOCAL_DB_TABLE",false)) {// 本地没有地区ID
            new DBTask().execute((Void) null);
            Log.e("--","IS_HAVE_LOCAL_DB_TABLE:false");
        }else {
            Log.e("--","IS_HAVE_LOCAL_DB_TABLE:true");
            try {
                if (dbManager.getDataCounts(DBHelper.WEATHER_PROVINCES_TABLE) <= 0
                        || dbManager.getDataCounts(DBHelper.WEATHER_CITYS_TABLE) <= 0) {
                    new DBTask().execute((Void) null);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        intent.setClass(WelcomeActivity.this, MainActivity.class);
    }

    @Override
    public void onAnimationEnd(Animation animation) {
            startActivity(intent);
        this.finish();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
    private class DBTask extends AsyncTask<Void,Void,String>{

        @Override
        protected String doInBackground(Void... params) {
            List<HashMap<String,Object>> listProvince =null;
            List<HashMap<String,Object>> listCity = null;

            InputStream inputStreamProvice =getResources().openRawResource(R.raw.weather_provinces);
            InputStream inputStreamCity =getResources().openRawResource(R.raw.weather_cities_without_dot);

            String provinceString = null;
            String cityString = null;

            try {
                provinceString = StreamUtils.inputStream2String(inputStreamProvice);
                cityString = StreamUtils.inputStream2String(inputStreamCity);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            if (provinceString != null) {
                xmlDataParser.putRawData(provinceString);
                listProvince =xmlDataParser.getFormatList("RECORDS");
            }
            if (cityString != null) {
                xmlDataParser.putRawData(cityString);
                listCity =xmlDataParser.getFormatList("RECORDS");
            }
            if (!ListUtils.isEmpty(listProvince) && !ListUtils.isEmpty(listCity)) {

                if(WelcomeLogic.insertWeatherProvincesTableData(dbManager, listProvince)
                        && WelcomeLogic.insertWeatherCitiesTableData(dbManager, listCity)) {
                    PreferencesUtils.putBoolean(WelcomeActivity.this, "IS_HAVE_LOCAL_DB_TABLE", true);
                } else {
                    PreferencesUtils.putBoolean(WelcomeActivity.this, "IS_HAVE_LOCAL_DB_TABLE", false);
                }
            }
            return "OK";
        }
    }
}
