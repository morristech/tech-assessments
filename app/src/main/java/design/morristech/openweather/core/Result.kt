package design.morristech.openweather.core

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()

    inline fun <R> fold(onFailure: (Exception) -> R, onSuccess: (T) -> R): R =
        when (this) {
            is Error -> onFailure(exception)
            is Success -> onSuccess(data)
        }

    inline fun onSuccess(block: (data: T) -> Unit): Result<T> {
        if (this is Success)
            block(data)
        return this
    }

    inline fun onFailure(block: (exception: Exception) -> Unit): Result<T> {
        if (this is Error)
            block(exception)
        return this
    }
}
