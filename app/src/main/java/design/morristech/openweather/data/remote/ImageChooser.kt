package design.morristech.openweather.data.remote

import androidx.annotation.DrawableRes
import design.morristech.openweather.R

const val DAY = 'd'
const val NIGHT = 'n'

/**
 * Returns an image from [R.drawable] based on:
 * [code] (weather code, check the link)
 * @see <a href="https://openweathermap.org/weather-conditions">OpenWeatherMap Conditions</a>
 */
@DrawableRes fun getIconByCode(code: Int): Int = when (code) {
    in 200..531 -> R.drawable.rain
    800 -> R.drawable.clear
    in 801..804 -> R.drawable.partly_sunny
    else -> R.drawable.rain
}
