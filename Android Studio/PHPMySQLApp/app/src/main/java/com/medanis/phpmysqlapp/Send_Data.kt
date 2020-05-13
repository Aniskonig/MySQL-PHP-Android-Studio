package com.medanis.phpmysqlapp

import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import java.util.*

internal class Send_Data(name: String, price: Double, description: String, listener: Response.Listener<String>) : StringRequest(Method.POST, SEND_DATA_URL, listener, null) {
    private val MapData: MutableMap<String, String>
    val param: Map<String, String>
        get() = MapData

    companion object {
        //private  static final String SEND_DATA_URL = "http://10.0.2.2/store/addValues.php";
        private const val SEND_DATA_URL = "http://192.168.1.19/store/innodev/add_cars.php"
    }

    init {
        MapData = HashMap()
        MapData["name"] = name
        MapData["price"] = price.toString()
        MapData["description"] = description
    }
}