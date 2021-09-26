package design.morristech.openweather.utils.extensions

import android.view.View

var View.visible: Boolean
    get() = visibility == View.VISIBLE
    set(value) = if (value) visibility = View.VISIBLE else visibility = View.GONE
