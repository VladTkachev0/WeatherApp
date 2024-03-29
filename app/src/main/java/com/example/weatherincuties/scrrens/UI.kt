package com.example.weatherincuties.scrrens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weatherincuties.data.WeatherModel
import com.example.weatherincuties.ui.theme.BlueLite

@Composable
fun ListItem(item: WeatherModel, currentDay: MutableState<WeatherModel>){ //лист который выдает прогноз по часам иди по дням

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp)
            .clickable {
                if (item.hours.isEmpty()) {
                    return@clickable
                } else currentDay.value = item // при нажатии на день, основная карточка меняется и меняется прогноз по часам на тот день, который был выбран
            },
        colors = CardDefaults.cardColors(
            containerColor = BlueLite
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        ),
        shape = RoundedCornerShape(5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .padding(
                        start = 8.dp,
                        top = 5.dp,
                        bottom = 5.dp)
            ) {
                Text(
                    text = item.time,
                    color = Color.White)
                Text(
                    text = item.condition,
                    color = Color.White
                )
            }
            Text(
                text = if (item.currentTemp.isNotEmpty()) {
                    item.currentTemp

                } else {
                    "${item.max_temp}°C / ${item.min_temp}°C"
                },
                color = Color.White,
                fontSize = 25.sp
            )

            AsyncImage(
                model = "https:${item.icon}",
                contentDescription = "im2",
                modifier = Modifier
                    .size(35.dp)
                    .padding(top = 1.dp, end = 5.dp)
            )

        }
    }
}

@Composable
fun MainList(list: List<WeatherModel>, currentDay: MutableState<WeatherModel>) {

    LazyColumn(modifier = Modifier
        .fillMaxWidth()
    ){
        itemsIndexed(
            list
        ) {
                _, item ->  ListItem(item, currentDay)

        }
    }
}
