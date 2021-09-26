package design.morristech.openweather.data.local.model

import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "current_forecast")
data class CurrentForecast(
    @PrimaryKey(autoGenerate = true)
    var currentForecastId: Int = 0,
    var temperature: Temperature,
    var feelsLike: Temperature,
    val description: String,
    var wind: Wind,
    var pressure: Pressure,
    val humidity: Int,
    @DrawableRes val imageId: Int
)
