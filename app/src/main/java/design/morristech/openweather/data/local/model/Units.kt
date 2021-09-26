package design.morristech.openweather.data.local.model

import design.morristech.openweather.core.Pressure
import design.morristech.openweather.core.Pressure.HECTO_PASCALS
import design.morristech.openweather.core.Pressure.MILLIMETERS_OF_MERCURY
import design.morristech.openweather.core.Temperature
import design.morristech.openweather.core.Temperature.CELSIUS
import design.morristech.openweather.core.Temperature.FAHRENHEIT
import design.morristech.openweather.core.Wind
import design.morristech.openweather.core.Wind.KILOMETERS_PER_HOUR
import design.morristech.openweather.core.Wind.METERS_PER_SECOND
import kotlin.math.roundToInt

/**
 * @param raw holds value in default units (temp - celsius, wind - m/s, pressure - hPa)
 */
abstract class Units(val raw: Int) {
    /**
     * Holds converted value in given units
     */
    abstract var value: Int

    override fun toString(): String = value.toString()
}

class Temperature(raw: Int) : Units(raw) {

    var unit: Temperature = CELSIUS

    override var value: Int = raw

    fun updateUnit(type: Temperature) {
        this.unit = type
        this.value = when (type) {
            CELSIUS -> raw
            FAHRENHEIT -> celsiusToFahrenheit(raw)
        }
    }

    override fun toString(): String = if (value > 0) "+$value" else "$value"

    /**
     * Converts celsius to fahrenheit
     */
    private fun celsiusToFahrenheit(celsius: Int): Int = (celsius * 1.8 + 32).roundToInt()
}

class Wind(raw: Int) : Units(raw) {

    var unit: Wind = METERS_PER_SECOND

    override var value: Int = raw

    fun updateUnit(type: Wind) {
        this.unit = type
        this.value = when (type) {
            METERS_PER_SECOND -> raw
            KILOMETERS_PER_HOUR -> metersPerSecToKilometersPerHour(raw)
        }
    }

    /**
     * Converts m/s into km/h
     */
    private fun metersPerSecToKilometersPerHour(metersPerSec: Int): Int =
        metersPerSec * 60 * 60 / 1000
}

class Pressure(raw: Int) : Units(raw) {

    var unit: Pressure = HECTO_PASCALS

    override var value: Int = raw

    fun updateUnit(type: Pressure) {
        this.unit = type
        this.value = when (type) {
            HECTO_PASCALS -> raw
            MILLIMETERS_OF_MERCURY -> hectoPascalsToMmHg(
                raw
            )
        }
    }

    /**
     * Converts hPa into mmHg
     */
    private fun hectoPascalsToMmHg(hectoPascals: Int): Int {
        val millimeterOfMercury = 133.3223684
        return (hectoPascals / millimeterOfMercury * 100).roundToInt()
    }
}
