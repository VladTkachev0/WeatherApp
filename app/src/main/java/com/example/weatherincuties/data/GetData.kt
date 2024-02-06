package com.example.weatherincuties.data

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject

const val API_KEY = "8e5fd528f52b49e79d492437242301"
fun getData(city: String, context: Context, daysList: MutableState<List<WeatherModel>>, currentDay: MutableState<WeatherModel>) {
    val url = "https://api.weatherapi.com/v1/forecast.json?" +
            "key=$API_KEY"+
            "&q=$city"+
            "&days=3&aqi=no&alerts=no"
    val queue = Volley.newRequestQueue(context)// создаем очередь (чтобы передать туда запрос, бибилиотека Volley)
    val sRequest = StringRequest( //формируем запрос
        Request.Method.GET,
        url,
        {
            responce->
            val list = getWeatherByDays(responce)
            currentDay.value = list[0]
            daysList.value = list
        },
        {
            Log.d("MyLog", "VolleyError: $it")
        }
    )
    queue.add(sRequest)//добавляем запрос в очередь
}