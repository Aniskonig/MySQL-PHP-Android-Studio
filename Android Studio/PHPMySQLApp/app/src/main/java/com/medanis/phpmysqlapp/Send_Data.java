package com.medanis.phpmysqlapp;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

class Send_Data extends StringRequest {
    private  static final String SEND_DATA_URL = "http://10.0.2.2/store/addValues.php";
    private Map<String,String> MapData;

    public Send_Data(String name, Double price,String description, Response.Listener<String> listener){
        super(Method.POST, SEND_DATA_URL, listener, null);
        MapData = new HashMap<>();
        MapData.put("name",name);
        MapData.put("price",price.toString());
        MapData.put("description", description);

    }

    public Map<String, String> getParam(){
        return  MapData;
    }
}
