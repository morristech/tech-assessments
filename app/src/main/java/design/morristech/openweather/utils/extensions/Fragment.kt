package design.morristech.openweather.utils.extensions

import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

val Fragment.actionBar: ActionBar?
    get() = (requireActivity() as AppCompatActivity).supportActionBar
