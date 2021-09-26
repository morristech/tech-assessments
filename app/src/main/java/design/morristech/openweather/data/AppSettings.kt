package design.morristech.openweather.data

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import design.morristech.openweather.core.Pressure
import design.morristech.openweather.core.Temperature
import design.morristech.openweather.core.Wind
import java.util.*
import javax.inject.Inject

/**
 * Storage for app and user preferences.
 */
interface AppSettings {
    var temperature: Temperature
    var wind: Wind
    var pressure: Pressure
    var locale: Locale
}

class AppSettingsImpl @Inject constructor(
    @ApplicationContext context: Context
) : AppSettings {
    companion object {
        const val PREF_NAME = "pref_settings"
        const val PREF_DARK_MODE_ENABLED = "pref_dark_mode"
        const val PREF_TEMPERATURE_UNIT = "pref_temperature"
        const val PREF_WIND_UNIT = "pref_wind"
        const val PREF_PRESSURE_UNIT = "pref_pressure"
        const val PREF_LANGUAGE = "pref_lang"
    }

    private val prefs: Lazy<SharedPreferences> = lazy {
        context.applicationContext.getSharedPreferences(PREF_NAME, MODE_PRIVATE)
    }

    /**
     * Unit of measurement of temperature.
     * Default unit - celsius
     */
    override var temperature: Temperature = Temperature.CELSIUS
        get() = Temperature.valueOf(getString(PREF_TEMPERATURE_UNIT, Temperature.CELSIUS.name))
        set(value) {
            field = value
            saveString(PREF_TEMPERATURE_UNIT, value.name)
        }

    /**
     * Unit of measurement of wind
     * Default unit - meters per second (m/s)
     */
    override var wind: Wind = Wind.METERS_PER_SECOND
        get() = Wind.valueOf(getString(PREF_WIND_UNIT, Wind.METERS_PER_SECOND.name))
        set(value) {
            field = value
            saveString(PREF_WIND_UNIT, value.name)
        }

    /**
     * Unit of measurement of pressure
     * Default unit - mmHg
     */
    override var pressure: Pressure = Pressure.MILLIMETERS_OF_MERCURY
        get() = Pressure.valueOf(
            getString(
                PREF_PRESSURE_UNIT,
                Pressure.MILLIMETERS_OF_MERCURY.name
            )
        )
        set(value) {
            field = value
            saveString(PREF_PRESSURE_UNIT, value.name)
        }

    /**
     * App Locale
     * Default - English (en)
     */
    override var locale: Locale = Locale.ENGLISH
        get() {
            val savedLang = getNullableString(PREF_LANGUAGE)
            return if (savedLang != null) {
                Locale.forLanguageTag(savedLang)
            } else {
                val fallbackLang = Locale.ENGLISH
                when (val systemLang = Locale.getDefault().language) {
                    "en" -> Locale(systemLang)
                    else -> fallbackLang
                }
            }
        }
        set(value) {
            field = value
            saveString(PREF_LANGUAGE, value.language)
        }

    private fun getString(key: String, def: String): String {
        return prefs.value.getString(key, def) ?: def
    }

    private fun getNullableString(key: String): String? {
        return prefs.value.getString(key, null)
    }

    private fun saveString(key: String, value: String) {
        prefs.value.edit {
            putString(key, value)
            apply()
        }
    }
}
