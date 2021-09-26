package design.morristech.openweather.utils.extensions

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

fun Context.isNetworkConnected(): Boolean {
    val cm: ConnectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val capabilities: NetworkCapabilities =
            cm.getNetworkCapabilities(cm.activeNetwork) ?: return false
        when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                return true
            }
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                return true
            }
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                return true
            }
        }
    } else {
        return cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
    }
    return false
}
