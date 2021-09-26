package design.morristech.openweather.data

import design.morristech.openweather.core.Pressure
import design.morristech.openweather.core.Temperature
import design.morristech.openweather.core.Theme
import design.morristech.openweather.core.Wind
import java.util.*

interface AppSettings {
    var temperature: Temperature
    var wind: Wind
    var pressure: Pressure
    var theme: Theme
    var locale: Locale
}
