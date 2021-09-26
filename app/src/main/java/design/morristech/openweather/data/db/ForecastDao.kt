package design.morristech.openweather.data.db

import androidx.room.*
import design.morristech.openweather.data.local.model.Forecast

@Dao
abstract class ForecastDao {
    @Query("SELECT * FROM forecast")
    abstract suspend fun getForecasts(): List<Forecast>

    @Query("SELECT * FROM forecast WHERE isFavourite = 1")
    abstract suspend fun getFavouriteForecasts(): List<Forecast>

    @Query("SELECT * FROM forecast WHERE wasOpenedLast = 1 LIMIT 1")
    abstract suspend fun getCurrentForecast(): Forecast?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertForecast(forecast: Forecast)

    @Update
    abstract suspend fun updateForecast(forecast: Forecast)

    @Query("UPDATE forecast SET wasOpenedLast = CASE WHEN locationName = :locationName THEN 1 ELSE 0 END")
    abstract suspend fun updateCurrentForecast(locationName: String)

    @Query("UPDATE forecast SET isFavourite = :isFavourite WHERE locationName = :locationName")
    abstract suspend fun setForecastFavouriteStatus(
        locationName: String,
        isFavourite: Boolean
    )

    @Delete
    abstract suspend fun deleteForecast(forecast: Forecast)
}
