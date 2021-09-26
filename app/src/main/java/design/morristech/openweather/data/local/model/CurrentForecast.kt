package design.morristech.openweather.data.local.model

import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import design.morristech.openweather.data.db.Converters

@Entity(tableName = "current_forecast")
data class CurrentForecast(
    @PrimaryKey(autoGenerate = true)
    var currentForecastId: Int = 0,
    @TypeConverters(Converters::class)
    var temperature: Temperature,
    @TypeConverters(Converters::class)
    var feelsLike: Temperature,
    val description: String,
    @TypeConverters(Converters::class)
    var wind: Wind,
    @TypeConverters(Converters::class)
    var pressure: Pressure,
    val humidity: Int,
    @DrawableRes val imageId: Int
)
