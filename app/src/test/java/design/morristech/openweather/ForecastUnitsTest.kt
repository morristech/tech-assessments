package design.morristech.openweather

import design.morristech.openweather.core.Pressure
import design.morristech.openweather.core.Temperature
import design.morristech.openweather.core.Wind
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertFalse
import org.junit.Test
import java.util.*

class ForecastUnitsTest {
    @Test
    fun testDefaultUnits() {
        val actual = forecast

        // Setting up default units, nothing should be changed
        val expected = actual.apply {
            updateTemperatureUnit(Temperature.CELSIUS)
            updateWindUnit(Wind.METERS_PER_SECOND)
            updatePressureUnit(Pressure.HECTO_PASCALS)
        }

        assertEquals(actual, expected)
    }

    @Test
    fun testTemperatureUnits() {
        val updatedForecast = forecast

        // 1. Testing fahrenheit temperature units
        updatedForecast.updateTemperatureUnit(Temperature.FAHRENHEIT)

        // Current Forecast
        assertEquals(70, updatedForecast.currentForecast.temperature.value)
        assertEquals(64, updatedForecast.currentForecast.feelsLike.value)

        // Hourly Forecast
        assertEquals(70, updatedForecast.hourForecastList[0].temperature.value)
        assertEquals(66, updatedForecast.hourForecastList[1].temperature.value)
        assertEquals(61, updatedForecast.hourForecastList[2].temperature.value)
        assertEquals(59, updatedForecast.hourForecastList[3].temperature.value)

        // Daily Forecast
        assertEquals(70, updatedForecast.dayForecastList[0].highestTemp.value)
        assertEquals(66, updatedForecast.dayForecastList[0].lowestTemp.value)

        // 2. Changing temperature units back to celsius
        updatedForecast.updateTemperatureUnit(Temperature.CELSIUS)

        // Current Forecast
        assertEquals(21, updatedForecast.currentForecast.temperature.value)
        assertEquals(18, updatedForecast.currentForecast.feelsLike.value)

        // Hourly Forecast
        assertEquals(21, updatedForecast.hourForecastList[0].temperature.value)
        assertEquals(19, updatedForecast.hourForecastList[1].temperature.value)
        assertEquals(16, updatedForecast.hourForecastList[2].temperature.value)
        assertEquals(15, updatedForecast.hourForecastList[3].temperature.value)

        // Daily Forecast
        assertEquals(21, updatedForecast.dayForecastList[0].highestTemp.value)
        assertEquals(19, updatedForecast.dayForecastList[0].lowestTemp.value)
    }


    @Test
    fun testWindUnits() {
        val updatedForecast = forecast

        // 1. Testing km/h wind units
        updatedForecast.updateWindUnit(Wind.KILOMETERS_PER_HOUR)
        assertEquals(18, updatedForecast.currentForecast.wind.value)

        // 2. Changing wind units back to m/s
        updatedForecast.updateWindUnit(Wind.METERS_PER_SECOND)
        assertEquals(5, updatedForecast.currentForecast.wind.value)
    }

    @Test
    fun testPressureUnits() {
        val updatedForecast = forecast

        // 1. Testing mmHg pressure units
        updatedForecast.updatePressureUnit(Pressure.MILLIMETERS_OF_MERCURY)
        assertEquals(767, updatedForecast.currentForecast.pressure.value)

        // 2. Changing wind units back to hPa
        updatedForecast.updatePressureUnit(Pressure.HECTO_PASCALS)
        assertEquals(1022, updatedForecast.currentForecast.pressure.value)
    }

    @Test
    fun testForecastValidness() {
        val updatedForecast = forecast

        // New timestamp, forecast should be fresh and valid!
        assertFalse(updatedForecast.isOutdated)

        // Changing timestamp to an old one (06.08.2020)
        updatedForecast.timestamp.timeInMillis = Calendar.getInstance().apply {
            set(2020, 8, 6)
        }.timeInMillis

        // Since timestamp is old, forecast should be outdated
        assertFalse(updatedForecast.isOutdated)
    }
}
