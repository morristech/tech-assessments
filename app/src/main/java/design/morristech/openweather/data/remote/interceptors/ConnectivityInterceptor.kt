package design.morristech.openweather.data.remote.interceptors

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import design.morristech.openweather.utils.isNetworkConnected
import okhttp3.Interceptor
import okhttp3.Response
import okio.IOException
import javax.inject.Inject

class ConnectivityInterceptor @Inject constructor(
    @ApplicationContext val context: Context
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!context.isNetworkConnected()) {
            throw IOException("Please check your internet connection")
        }
        return chain.proceed(chain.request())
    }
}
