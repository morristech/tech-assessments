package design.morristech.openweather

import android.content.Context
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import design.morristech.openweather.data.AppSettings
import design.morristech.openweather.data.AppSettingsImpl
import design.morristech.openweather.data.WeatherDataSource
import design.morristech.openweather.data.local.db.WeatherDataBase
import design.morristech.openweather.data.remote.RemoteWeatherDataSource
import design.morristech.openweather.data.remote.RemoteWeatherRepository
import design.morristech.openweather.data.remote.WeatherApi
import design.morristech.openweather.ui.WeatherViewModel
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * [Context] object used for tests
 */
val context: Context
    get() = InstrumentationRegistry.getInstrumentation().targetContext

/**
 * [WeatherDataBase] object used for tests
 */
val weatherDataBase: WeatherDataBase
    get() = Room.inMemoryDatabaseBuilder(context, WeatherDataBase::class.java)
        .allowMainThreadQueries()
        .build()

val moshi: Moshi
    get() = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

/**
 * [WeatherApi] object used for tests
 */
val weatherApi: WeatherApi
    get() = Retrofit.Builder()
        .baseUrl(BuildConfig.API_BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
        .create(WeatherApi::class.java)

/**
 * [RemoteWeatherDataSource] object used for tests
 */
val weatherDataSource: WeatherDataSource
    get() = RemoteWeatherDataSource(weatherApi)

/**
 * [RemoteWeatherRepository] object used for tests
 */
val weatherRepository: RemoteWeatherRepository
    get() = RemoteWeatherRepository(weatherDataSource, weatherDataBase, appSettings)

/**
 * [AppSettings] object used for tests
 */
val appSettings: AppSettings
    get() = AppSettingsImpl(context)

/**
 * [WeatherViewModel] object used for tests
 */
val weatherViewModel: WeatherViewModel
    get() = WeatherViewModel(weatherRepository, appSettings)
