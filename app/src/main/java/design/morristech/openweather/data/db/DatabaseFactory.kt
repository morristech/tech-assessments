package design.morristech.openweather.data.db

import android.content.Context
import androidx.room.Room

object DatabaseFactory {
    fun create(context: Context): WeatherDataBase {
        return Room
            .databaseBuilder(context, WeatherDataBase::class.java, WeatherDataBase.DATABASE_NAME)
            .build()
    }
}
