package com.example.weatherincuties

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.weatherincuties.data.WeatherModel
import com.example.weatherincuties.scrrens.MainCard
import com.example.weatherincuties.scrrens.TabLayout
import com.example.weatherincuties.ui.theme.WeatherInCutiesTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherInCutiesTheme {

                val daysList = remember {
                    mutableStateOf(listOf<WeatherModel>())
                }

                val currentDay = remember { //состояние для основной карточки (MainCard)
                    mutableStateOf(WeatherModel(
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                    ))
                }

                GetData("London", this, daysList, currentDay)
                Image(
                    painter = painterResource(id = R.drawable.fon),
                    contentDescription = "im1",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )
                Column {
                    MainCard(currentDay)
                    TabLayout(daysList, currentDay)
                }

            }
        }
    }
}


