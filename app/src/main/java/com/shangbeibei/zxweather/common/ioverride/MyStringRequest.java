package com.shangbeibei.zxweather.common.ioverride;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.shangbeibei.zxweather.common.constants.Constants;
import com.shangbeibei.zxweather.common.constants.NetConstants;

/**
 * Created by Administrator on 2016/1/21.
 */
public class MyStringRequest extends StringRequest {
    public MyStringRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    public MyStringRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {

        try {
            String type = response.headers.get("content-type");
            if (type == null) {
                type = "charset=UTF-8";
                response.headers.put("content-type", type);

            }else if(!type.contains("UTF-8")){
                type+=";"+"charset=UTF-8";
                response.headers.put("content-type", type);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return super.parseNetworkResponse(response);
    }
}
