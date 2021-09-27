package design.morristech.openweather.data

import androidx.test.ext.junit.runners.AndroidJUnit4
import design.morristech.openweather.*
import junit.framework.Assert.*
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ForecastDaoTest {
    private val database = weatherDataBase
    private val forecastDao = database.getForecastDao()

    @Before
    fun createDb() = runBlocking {
        for (forecast in forecasts)
            forecastDao.insertForecast(forecast)
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test fun testAddedForecasts() = runBlocking {
        assertFalse(forecastDao.getForecasts().isEmpty())
        assertEquals(3, forecastDao.getForecasts().size)
    }

    @Test fun testFavouriteForecasts() = runBlocking {
        val savedForecasts = forecastDao.getFavouriteForecasts()

        // Size of favourite forecasts pre-defined: 2
        assertEquals(2, savedForecasts.size)

        // Checking each value of isFavourite
        assertTrue(savedForecasts[0].isFavourite)
        assertTrue(savedForecasts[1].isFavourite)
    }

    @Test fun testLastOpenedForecast() = runBlocking {
        // Checking that Johannesburg forecast is the current forecast (wasOpenedLast=true)
        assertEquals(joburgForecast.locationName, forecastDao.getCurrentForecast()!!.locationName)
    }

    @Test fun testUpdateLastOpenedForecast() = runBlocking {
        // Changing current forecast to Amsterdam forecast
        forecastDao.updateCurrentForecast(amsterdamForecast.locationName)

        // Getting Johannesburg forecast from Dao
        val joburg = forecastDao.getForecasts().find {
            it.locationName == joburgForecast.locationName
        }

        // Checking wasOpenedLast of Joburg, since Amsterdam is current now (wasOpenedLast=true)
        assertFalse(joburg!!.wasOpenedLast)

        // Making sure Amsterdam is the current forecast
        assertEquals(amsterdamForecast.locationName, forecastDao.getCurrentForecast()!!.locationName)
    }

    @Test
    fun testChangeFavouriteForecast() = runBlocking {
        // Changing favourite status of Cape Town to true
        forecastDao.setForecastFavouriteStatus(capeTownForecast.locationName, true)

        // Getting Cape Town forecast from Dao
        val capeTown = forecastDao.getForecasts().find {
            it.locationName == capeTownForecast.locationName
        }

        // Checking that isFavourite value changed
        assertTrue(capeTown!!.isFavourite)

        // Changing favourite status of Amsterdam to false
        forecastDao.setForecastFavouriteStatus(amsterdamForecast.locationName, false)

        // Getting Amsterdam forecast from Dao
        val amsterdam = forecastDao.getForecasts().find {
            it.locationName == amsterdamForecast.locationName
        }

        // Checking that isFavourite value changed
        assertFalse(amsterdam!!.isFavourite)
    }
}
