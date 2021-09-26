package design.morristech.openweather.core

import design.morristech.openweather.core.exceptions.BadServerResponse
import design.morristech.openweather.core.exceptions.NoConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException

interface NetworkDataSource {
    suspend fun <T, R, P> safeApiCall(
        call: suspend () -> Response<T>,
        params: P,
        transform: (T, P) -> R
    ): Result<R> {
        try {
            val response = withContext(Dispatchers.IO) { call.invoke() }
            return if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    withContext(Dispatchers.Default) {
                        Result.Success(transform(body, params))
                    }
                } else {
                    Result.Error(BadServerResponse)
                }
            } else {
                Result.Error(BadServerResponse)
            }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return Result.Error(NoConnection)
        } catch (unknown: Exception) {
            unknown.printStackTrace()
            return Result.Error(BadServerResponse)
        }
    }
}
