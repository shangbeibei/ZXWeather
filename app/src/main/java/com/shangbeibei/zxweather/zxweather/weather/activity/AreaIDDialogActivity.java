package com.shangbeibei.zxweather.zxweather.weather.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.shangbeibei.zxweather.R;
import com.shangbeibei.zxweather.common.db.DBHelper;
import com.shangbeibei.zxweather.common.db.DataBaseManager;
import com.shangbeibei.zxweather.common.utils.PreferencesUtils;
import com.shangbeibei.zxweather.zxweather.base.BaseActivity;
import com.shangbeibei.zxweather.zxweather.weather.adapter.CityAdapter;
import com.shangbeibei.zxweather.zxweather.weather.adapter.ProvinceAdapter;
import com.shangbeibei.zxweather.zxweather.weather.bean.WeatherIDCityBean;
import com.shangbeibei.zxweather.zxweather.weather.bean.WeatherIDProvinceBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AreaIDDialogActivity extends BaseActivity {

    private ImageView goback;
    private GridView gridView;
    private ListView listView;
    private EditText editText;
    private List<Map<String, Object>> provinceItemList;
    private List<Map<String, Object>> cityItemList;
    private ProvinceAdapter provinceAdapter;
    private CityAdapter cityAdapter;
    private int provinceId = -1;
    private int index;
    private Bundle bundle;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_iddialog);


        initView();
        initData();
        initListener();
    }

    @Override
    public void initView() {
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setLayout((int) (getWindowManager().getDefaultDisplay().getWidth() * 1.00), (int) (getWindowManager().getDefaultDisplay().getHeight() * 1.00));
        goback = (ImageView) findViewById(R.id.province_goback);
        gridView = (GridView) findViewById(R.id.province_gridview);
        listView = (ListView) findViewById(R.id.city_listview);
        editText = (EditText) findViewById(R.id.city_editText);


    }

    @Override
    public void initData() {

        index=getIntent().getBundleExtra("index").getInt("index");
        //Toast.makeText(getBaseContext(),index+"",Toast.LENGTH_SHORT).show();
        provinceItemList = new ArrayList<Map<String, Object>>();
        cityItemList = new ArrayList<Map<String, Object>>();


        try {

            provinceItemList = super.dbManager.query2ListMap("select * from " + DBHelper.WEATHER_PROVINCES_TABLE + ";", null, new WeatherIDProvinceBean());
            provinceAdapter = new ProvinceAdapter(getBaseContext(), provinceItemList);
            gridView.setAdapter(provinceAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initListener() {
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gridView.isShown()) {
                    finish();
                } else {
                    gridView.setVisibility(View.VISIBLE);
                    editText.setVisibility(View.GONE);
                    listView.setVisibility(View.GONE);
                }
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                provinceId = position;
                gridView.setVisibility(View.GONE);
                editText.setVisibility(View.VISIBLE);
                listView.setVisibility(View.VISIBLE);
                try {
                    cityItemList = dbManager.query2ListMap("select * from weather_citys where province_id='" + position + "';", null, new WeatherIDCityBean());
                    cityAdapter = new CityAdapter(getBaseContext(), cityItemList);
                    listView.setAdapter(cityAdapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String name = String.valueOf(charSequence);
                //Map<String,Object> map = new HashMap<String, Object>();
                try {
                    cityItemList = dbManager.query2ListMap("select * from weather_citys where province_id ='" + provinceId + "' and name like '%" + name + "%';", null, new WeatherIDCityBean());
                    if (cityItemList.size() == 0) {
                        HashMap<String, Object> map = new HashMap<String, Object>();
                        map.put("name", "no result");
                        cityItemList.add(map);
                    }
                    cityAdapter.setmList(cityItemList);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Map<String,Object> map = cityItemList.get(position);
                String cityName = map.get("name").toString();
                PreferencesUtils.putString(getBaseContext(),"cityName"+index,cityName);
                finish();

            }
        });

    }
}
