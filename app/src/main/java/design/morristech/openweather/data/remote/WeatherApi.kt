package design.morristech.openweather.data.remote

import design.morristech.openweather.data.remote.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("/data/2.5/onecall")
    suspend fun getForecast(
        @Query("lon") longitude: Double,
        @Query("lat") latitude: Double,
        @Query("lang") language: String,
        @Query("units") units: String
    ): Response<WeatherResponse>
}
