package design.morristech.openweather.utils.extensions

import android.content.Intent
import android.net.Uri
import androidx.annotation.StringRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

val Fragment.actionBar: ActionBar?
    get() = (requireActivity() as AppCompatActivity).supportActionBar

fun Fragment.openLink(@StringRes resId: Int) {
    val link = Intent(Intent.ACTION_VIEW, Uri.parse(getString(resId)))
    startActivity(link)
}

fun Fragment.openLink(url: String) {
    val link = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    startActivity(link)
}
