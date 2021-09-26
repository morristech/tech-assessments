package design.morristech.openweather.data.local.model

import androidx.annotation.Keep
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import design.morristech.openweather.core.Pressure
import design.morristech.openweather.core.Temperature
import design.morristech.openweather.core.Wind
import design.morristech.openweather.data.db.Converters
import java.util.*

@Keep
@Entity(tableName = "forecast")
data class Forecast(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var locationName: String,
    val longitude: Double,
    val latitude: Double,
    @TypeConverters(Converters::class)
    val timestamp: Calendar = Calendar.getInstance(),
    var wasOpenedLast: Boolean = true,
    var isFavourite: Boolean = false,
    @Embedded
    val currentForecast: CurrentForecast,
    @TypeConverters(Converters::class)
    val hourForecastList: List<HourForecast>,
    @TypeConverters(Converters::class)
    val dayForecastList: List<DayForecast>
) {
    /**
     * Checks if data is outdated or not.
     * Update interval: 2 hours (openweathermap for free forecast)
     * @see <a href="https://openweathermap.org/price">https://openweathermap.org/price</a>
     * @return Returns true if data is valid
     */
    val isOutdated: Boolean
        get() {
            val twoHoursInMillis = 2 * 60 * 60 * 1000 // 2 hours
            val currentTimeInMillis = Calendar.getInstance().timeInMillis
            return currentTimeInMillis - timestamp.timeInMillis >= twoHoursInMillis
        }

    /**
     * Updates all temperature units in [Forecast] according to new [unit]
     */
    fun updateTemperatureUnit(unit: Temperature) {
        currentForecast.temperature.updateUnit(unit)
        currentForecast.feelsLike.updateUnit(unit)
        hourForecastList.forEach {
            it.temperature.updateUnit(unit)
        }
        dayForecastList.forEach {
            it.highestTemp.updateUnit(unit)
            it.lowestTemp.updateUnit(unit)
        }
    }

    /**
     * Updates all pressure units in [Forecast] according to new [unit]
     */
    fun updatePressureUnit(unit: Pressure) {
        currentForecast.pressure.updateUnit(unit)
        dayForecastList.forEach {
            it.pressure.updateUnit(unit)
        }
    }

    /**
     * Updates all wind units in [Forecast] according to new [unit]
     */
    fun updateWindUnit(unit: Wind) {
        currentForecast.wind.updateUnit(unit)
        dayForecastList.forEach {
            it.wind.updateUnit(unit)
        }
    }
}
