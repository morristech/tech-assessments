package design.morristech.openweather.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Hourly(
    @Json(name = "dt")
    val timestamp: Long,
    @Json(name = "temp")
    val temperature: Double,
    @Json(name = "feels_like")
    val feelsLike: Double,
    @Json(name = "pressure")
    val pressure: Int,
    @Json(name = "humidity")
    val humidity: Int,
    @Json(name = "dew_point")
    val dewPoint: Double,
    @Json(name = "clouds")
    val clouds: Int,
    @Json(name = "wind_speed")
    val windSpeed: Double,
    @Json(name = "wind_deg")
    val windDegree: Int,
    @Json(name = "weather")
    val weather: List<Weather>
)
