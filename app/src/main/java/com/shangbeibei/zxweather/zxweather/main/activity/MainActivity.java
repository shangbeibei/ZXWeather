package com.shangbeibei.zxweather.zxweather.main.activity;

import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.shangbeibei.zxweather.R;
import com.shangbeibei.zxweather.common.systembartint.SystemBarTintManager;
import com.shangbeibei.zxweather.common.utils.PreferencesUtils;
import com.shangbeibei.zxweather.zxweather.base.BaseActivity;
import com.shangbeibei.zxweather.zxweather.main.adapter.WeatherPagerAdapter;
import com.shangbeibei.zxweather.zxweather.main.fragment.WeatherFragment;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity {

    private ImageView dot0,dot1,dot2;
    private ViewPager viewPager;
    private ArrayList<Fragment> fragmentList;
    private int currentIndex=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BaseActivity.activityList.add(this);
        initWindow();
        initViewPager();
    }
    private void initWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setNavigationBarTintEnabled(true);
            tintManager.setTintColor(Color.parseColor("#54464D"));
        }





    }

    private void initViewPager() {
        String city0 = PreferencesUtils.getString(getBaseContext(),"cityName0","北京");
        String city1 = PreferencesUtils.getString(getBaseContext(),"cityName1","上海");
        String city2 = PreferencesUtils.getString(getBaseContext(),"cityName2","广州");

        fragmentList = new ArrayList<>();
        dot0 = (ImageView) findViewById(R.id.dot0);
        dot1 = (ImageView) findViewById(R.id.dot1);
        dot2 = (ImageView) findViewById(R.id.dot2);
        viewPager = (ViewPager) findViewById(R.id.viewPage);
        viewPager.setOffscreenPageLimit(3);
        WeatherFragment weather0 = new WeatherFragment(city0,0);
        WeatherFragment weather1 = new WeatherFragment(city1,1);
        WeatherFragment weather2 = new WeatherFragment(city2,2);
        fragmentList.add(weather0);
        fragmentList.add(weather1);
        fragmentList.add(weather2);
        WeatherPagerAdapter adapter = new WeatherPagerAdapter(getSupportFragmentManager(),fragmentList);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                currentIndex=position;
                int i = currentIndex;
                cleanDots();
                switch (position) {
                    case 0:
                        dot0.setImageResource(android.R.drawable.presence_online);
                        break;
                    case 1:
                        dot1.setImageResource(android.R.drawable.presence_online);
                        break;
                    case 2:
                        dot2.setImageResource(android.R.drawable.presence_online);
                        break;
                    default:
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private void cleanDots(){

        dot0.setImageResource(android.R.drawable.presence_invisible);
        dot1.setImageResource(android.R.drawable.presence_invisible);
        dot2.setImageResource(android.R.drawable.presence_invisible);

    }
}
