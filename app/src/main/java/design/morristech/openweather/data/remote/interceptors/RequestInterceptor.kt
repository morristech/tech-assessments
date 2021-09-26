package design.morristech.openweather.data.remote.interceptors

import design.morristech.openweather.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class RequestInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()
        val url = request.url().newBuilder()
            .addQueryParameter(APP_ID, BuildConfig.API_KEY_OPEN_WEATHER_MAP)
            .build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }

    companion object {
        const val APP_ID: String = "appid"
    }
}
