package design.morristech.openweather.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Current(
    @Json(name = "dt")
    val timestamp: Long,
    @Json(name = "sunrise")
    val sunrise: Int,
    @Json(name = "sunset")
    val sunset: Int,
    @Json(name = "temp")
    val temp: Double,
    @Json(name = "feels_like")
    val feelsLike: Double,
    @Json(name = "pressure")
    val pressure: Int,
    @Json(name = "humidity")
    val humidity: Int,
    @Json(name = "dew_point")
    val dewPoint: Double,
    @Json(name = "uvi")
    val uvi: Double,
    @Json(name = "clouds")
    val clouds: Int,
    @Json(name = "visibility")
    val visibility: Int,
    @Json(name = "wind_speed")
    val windSpeed: Double,
    @Json(name = "wind_deg")
    val windDeg: Int,
    @Json(name = "weather")
    val weather: List<Weather>
)
