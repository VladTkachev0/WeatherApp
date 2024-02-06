package com.example.weatherincuties.data

import org.json.JSONObject

fun getWeatherByDays(responce: String): List<WeatherModel>{
    if (responce.isEmpty()) return listOf() //если responce пустой то возвращаем пустой лист

    val list = ArrayList<WeatherModel>()
    val mainObject = JSONObject(responce)
    val city = mainObject.getJSONObject("location").getString("name")

    //берем из mainObject объект forecast, чтобы выдавать информацию о погоде по дням
    val days = mainObject.getJSONObject("forecast").getJSONArray("forecastday")
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