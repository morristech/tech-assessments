package design.morristech.openweather.di

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import design.morristech.openweather.BuildConfig
import design.morristech.openweather.data.remote.WeatherApi
import design.morristech.openweather.data.remote.interceptors.ConnectivityInterceptor
import design.morristech.openweather.data.remote.interceptors.RequestInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    internal fun provideCache(@ApplicationContext context: Context): Cache {
        val cacheSize = 10 * 1024 * 1024.toLong() // 10 MB
        val httpCacheDirectory = File(context.cacheDir, "http-cache")
        return Cache(httpCacheDirectory, cacheSize)
    }

    @Provides
    @Singleton
    internal fun provideOkHttpClient(
        cache: Cache,
        connectivityInterceptor: ConnectivityInterceptor,
        requestInterceptor: RequestInterceptor,
        logger: HttpLoggingInterceptor
    ): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.cache(cache)
        httpClient.addInterceptor(logger)
        httpClient.addNetworkInterceptor(requestInterceptor)
        httpClient.addInterceptor(connectivityInterceptor)
        return httpClient.build()
    }

    @Provides
    @Singleton
    internal fun provideOkHttpLogger(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            if (BuildConfig.DEBUG) {
                level = HttpLoggingInterceptor.Level.BODY
            }
        }
    }

    @Provides
    @Singleton
    internal fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory()).build()
    }

    @Singleton
    @Provides
    internal fun provideWeatherService(moshi: Moshi, okHttpClient: OkHttpClient): WeatherApi {
        return Retrofit
            .Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
            .create(WeatherApi::class.java)
    }
}
