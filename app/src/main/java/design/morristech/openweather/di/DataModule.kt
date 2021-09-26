package design.morristech.openweather.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import design.morristech.openweather.data.AppSettings
import design.morristech.openweather.data.WeatherDataSource
import design.morristech.openweather.data.WeatherRepository
import design.morristech.openweather.data.local.db.WeatherDataBase
import design.morristech.openweather.data.remote.RemoteWeatherDataSource
import design.morristech.openweather.data.remote.RemoteWeatherRepository
import design.morristech.openweather.data.remote.WeatherApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    internal fun provideWeatherDatabase(@ApplicationContext context: Context): WeatherDataBase {
        return Room
            .databaseBuilder(context, WeatherDataBase::class.java, WeatherDataBase.DATABASE_NAME)
            .build()
    }

    @Provides
    @Singleton
    internal fun provideWeatherDataSource(weatherApi: WeatherApi): WeatherDataSource {
        return RemoteWeatherDataSource(weatherApi)
    }

    @Provides
    @Singleton
    internal fun provideWeatherRepository(
        dataSource: WeatherDataSource,
        database: WeatherDataBase,
        settings: AppSettings
    ): WeatherRepository {
        return RemoteWeatherRepository(
            dataSource,
            database,
            settings
        )
    }
}
