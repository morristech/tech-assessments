package design.morristech.openweather.ui.settings

import androidx.lifecycle.ViewModel

class SettingsViewModel(private val appSettings: AppSettings) : ViewModel() {
    val temperature: String = appSettings.temperature.displayedName
    val availableTemperatureUnits: Array<Temperature> = Temperature.values()

    val wind: String = appSettings.wind.displayedName
    val availableWindUnits: Array<Wind> = Wind.values()

    val pressure: String = appSettings.pressure.displayedName
    val availablePressureUnits: Array<Pressure> = Pressure.values()

    val theme: String = appSettings.theme.displayedName
    val availableThemes: Array<Theme> = Theme.values()

    fun updateTemperatureUnit(temperature: Temperature) {
        appSettings.temperature = temperature
    }

    fun updatePressureUnit(pressure: Pressure) {
        appSettings.pressure = pressure
    }

    fun updateWindUnit(wind: Wind) {
        appSettings.wind = wind
    }

    fun updateTheme(theme: Theme) {
        appSettings.theme = theme
    }
}
