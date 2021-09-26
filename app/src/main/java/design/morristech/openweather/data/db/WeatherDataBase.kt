package design.morristech.openweather.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import design.morristech.openweather.data.local.model.CurrentForecast
import design.morristech.openweather.data.local.model.Forecast

@Database(entities = [CurrentForecast::class, Forecast::class], version = 1)
abstract class WeatherDataBase: RoomDatabase() {
    abstract fun getForecastDao(): ForecastDao

    companion object {
        const val DATABASE_NAME = "weather-db"
    }
}
