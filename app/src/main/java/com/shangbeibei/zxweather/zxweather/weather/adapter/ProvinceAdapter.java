package com.shangbeibei.zxweather.zxweather.weather.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.shangbeibei.zxweather.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProvinceAdapter extends BaseAdapter {

	private Context context;
	private LayoutInflater inflater;
	private List<Map<String, Object>> listItemProvince;
	
	public ProvinceAdapter(Context context, List<Map<String, Object>> listItemProvince){
		this.context = context;
		inflater = LayoutInflater.from(context);
		setmList(listItemProvince);
	}
	
	public void setmList(List<Map<String, Object>> listItemProvince) {
		if(listItemProvince != null){
			this.listItemProvince = listItemProvince;
		}
		else{
			this.listItemProvince = new ArrayList<Map<String, Object>>();
		}
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return listItemProvince.size();
	}

	@Override
	public Map<String, Object> getItem(int position) {
		return listItemProvince.get(position);
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
			convertView = inflater.inflate(R.layout.item_activity_adapter, null);
			holder.tvProvince = (TextView) convertView.findViewById(R.id.adapter_item_textview);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.tvProvince.setText(listItemProvince.get(position).get("name").toString());
		
		return convertView;
	}
	
	class ViewHolder{
		TextView tvProvince;
	}

}
