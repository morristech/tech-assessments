package design.morristech.openweather.utils

import androidx.appcompat.app.ActionBar

var ActionBar.toolbarTitle: String?
    get() = title.toString()
    set(value) {
        title = value ?: title ?: ""
    }



