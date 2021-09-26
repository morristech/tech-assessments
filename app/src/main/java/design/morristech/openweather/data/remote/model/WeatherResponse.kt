package design.morristech.openweather.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherResponse(
    @Json(name = "lat")
    val lat: Double,
    @Json(name = "lon")
    val lon: Double,
    @Json(name = "timezone")
    val timezone: String,
    @Json(name = "timezone_offset")
    val timezone_offset: Int,
    @Json(name = "current")
    val current: Current,
    @Json(name = "hourly")
    val hourly: List<Hourly>,
    @Json(name = "daily")
    val daily: List<Daily>
)
