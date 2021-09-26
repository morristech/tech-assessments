package design.morristech.openweather.data.local.model

import androidx.annotation.Keep

@Keep
data class Location(
    val locationName: String,
    val longitude: Double,
    val latitude: Double
)
