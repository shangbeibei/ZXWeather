package com.shangbeibei.zxweather.zxweather.weather.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.shangbeibei.zxweather.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CityAdapter extends BaseAdapter {

	private Context context;
	private LayoutInflater inflater;
	private List<Map<String, Object>> listItemCity;
	
	public CityAdapter(Context context, List<Map<String, Object>> listItemCity){
		this.context = context;
		inflater = LayoutInflater.from(context);
		setmList(listItemCity);
	}
	
	public void setmList(List<Map<String, Object>> listItemCity) {
		if(listItemCity != null){
			this.listItemCity = listItemCity;
		}
		else{
			this.listItemCity = new ArrayList<Map<String, Object>>();
		}
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return listItemCity.size();
	}

	@Override
	public Map<String, Object> getItem(int position) {
		return listItemCity.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if(convertView == null){
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.db_id_second_level_layout_item, null);
			holder.tvCity = (TextView) convertView.findViewById(R.id.db_id_second_level_layout_item_text);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		Map<String, Object> map = getItem(position);
		if(map.get("name").toString().equals("没有找到该地区...")){
			holder.tvCity.setTextColor(Color.parseColor("#b1b1b1"));
		}else{
			holder.tvCity.setTextColor(Color.parseColor("#444444"));
		}
		holder.tvCity.setText(map.get("name").toString());
		
		return convertView;
	}
	
	class ViewHolder{
		TextView tvCity;
	}

}
