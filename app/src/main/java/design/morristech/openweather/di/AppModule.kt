package design.morristech.openweather.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import design.morristech.openweather.data.AppSettings
import design.morristech.openweather.data.AppSettingsImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    internal fun provideAppSettings(@ApplicationContext context: Context): AppSettings {
        return AppSettingsImpl(context)
    }
}
