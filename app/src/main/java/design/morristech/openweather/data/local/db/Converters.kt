package design.morristech.openweather.data.local.db

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi.Builder
import com.squareup.moshi.Types
import design.morristech.openweather.data.local.model.*
import java.util.*

class Converters {
    private val moshi = Builder().build()

    @TypeConverter
    fun jsonToHourList(value: String): List<HourForecast>? {
        val listOfCardsType = Types.newParameterizedType(List::class.java, HourForecast::class.java)
        val jsonAdapter = moshi.adapter<List<HourForecast>>(listOfCardsType)
        return jsonAdapter.fromJson(value)
    }

    @TypeConverter
    fun jsonToDayList(value: String): List<DayForecast>? {
        val listOfCardsType = Types.newParameterizedType(List::class.java, DayForecast::class.java)
        val jsonAdapter = moshi.adapter<List<DayForecast>>(listOfCardsType)
        return jsonAdapter.fromJson(value)
    }

    @TypeConverter
    fun hourListToJson(hourForecastList: List<HourForecast>): String {
        val listOfCardsType = Types.newParameterizedType(List::class.java, HourForecast::class.java)
        val jsonAdapter = moshi.adapter<List<HourForecast>>(listOfCardsType)
        return jsonAdapter.toJson(hourForecastList)
    }

    @TypeConverter
    fun dayListToJson(dayForecastList: List<DayForecast>): String {
        val listOfCardsType = Types.newParameterizedType(List::class.java, DayForecast::class.java)
        val jsonAdapter = moshi.adapter<List<DayForecast>>(listOfCardsType)
        return jsonAdapter.toJson(dayForecastList)
    }

    @TypeConverter
    fun calendarToLong(calendar: Calendar): Long {
        return calendar.timeInMillis
    }

    @TypeConverter
    fun longToCalendar(value: Long): Calendar {
        return Calendar.getInstance().apply { timeInMillis = value }
    }

    @TypeConverter
    fun temperatureToInt(temperature: Temperature): Int {
        return temperature.raw
    }

    @TypeConverter
    fun windToInt(wind: Wind): Int {
        return wind.raw
    }

    @TypeConverter
    fun pressureToInt(pressure: Pressure): Int {
        return pressure.raw
    }

    @TypeConverter
    fun intToTemperature(value: Int): Temperature {
        return Temperature(value)
    }

    @TypeConverter
    fun intToWind(value: Int): Wind {
        return Wind(value)
    }

    @TypeConverter
    fun intToPressure(value: Int): Pressure {
        return Pressure(value)
    }
}
