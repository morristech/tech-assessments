package design.morristech.openweather.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import design.morristech.openweather.data.local.model.CurrentForecast
import design.morristech.openweather.data.local.model.Forecast

@Database(entities = [CurrentForecast::class, Forecast::class], version = 1)
@TypeConverters(Converters::class)
abstract class WeatherDataBase: RoomDatabase() {
    abstract fun getForecastDao(): ForecastDao

    companion object {
        const val DATABASE_NAME = "weather-db"
    }
}
