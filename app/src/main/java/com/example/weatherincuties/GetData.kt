package com.example.weatherincuties

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.weatherincuties.data.WeatherModel
import org.json.JSONArray
import org.json.JSONObject

const val API_KEY = "8e5fd528f52b49e79d492437242301"
fun GetData(city: String, context: Context, daysList: MutableState<List<WeatherModel>>, currentDay: MutableState<WeatherModel>) {
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

fun getWeatherByDays(responce: String): List<WeatherModel>{
    if (responce.isEmpty()) return listOf() //если responce пустой то возвращаем пустой лист

    val list = ArrayList<WeatherModel>()
    val mainObject = JSONObject(responce)
    val city = mainObject.getJSONObject("location").getString("name")

    //берем из mainObject объект forecast, чтобы выдавать информацию о погоде по дням
    val days = mainObject.getJSONObject("forecast").getJSONArray("forecastday")
    //в days набор из объектов(массив)
    for (i in 0 until days.length()){
        val item = days[i] as JSONObject //выдает один день
        list.add( //добавляем в weatherModel данные с серверра
            WeatherModel(
                city,
                item.getString("date"),
                "",
                item.getJSONObject("day").getJSONObject("condition").getString("text"),
                item.getJSONObject("day").getJSONObject("condition").getString("icon"),
                item.getJSONObject("day").getString("maxtemp_c"),
                item.getJSONObject("day").getString("mintemp_c"),
                item.getJSONArray("hour").toString()
                )
        )

    }
    list[0] = list[0].copy( //изменяем первый день, т.к. currentTemp в первом дне есть, а в остальных нет, и формат времени другой
        time = mainObject.getJSONObject("current").getString("last_updated"),
        currentTemp = mainObject.getJSONObject("current").getString("temp_c")
    )
    return list

}

fun getWeatherByHours(hours: String): List<WeatherModel>{
    if (hours.isEmpty()) return listOf()

    val hoursArray = JSONArray(hours)
    val list = ArrayList<WeatherModel>()
    for (i in 0 until hoursArray.length()){
        val item = hoursArray[i] as JSONObject
        list.add(
            WeatherModel(
                "",
                item.getString("time"),
                item.getString("temp_c") + "°C",
                item.getJSONObject("condition").getString("text"),
                item.getJSONObject("condition").getString("icon"),
                "",
                "",
                ""
            )
        )
    }
    return list
}