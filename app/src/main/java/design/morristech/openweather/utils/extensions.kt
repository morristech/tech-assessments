package design.morristech.openweather.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

var ActionBar.toolbarTitle: String?
    get() = title.toString()
    set(value) {
        title = value ?: title ?: ""
    }

val Fragment.actionBar: ActionBar?
    get() = (requireActivity() as AppCompatActivity).supportActionBar

var View.visible: Boolean
    get() = visibility == View.VISIBLE
    set(value) = if (value) visibility = View.VISIBLE else visibility = View.GONE

inline fun <reified T : Enum<T>> String.asEnumOrDefault(): T =
    enumValues<T>().first { it.name.equals(this, ignoreCase = true) }

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
