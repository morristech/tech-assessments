package design.morristech.openweather.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import design.morristech.openweather.R
import design.morristech.openweather.databinding.WeatherActivityBinding
import design.morristech.openweather.ui.ui.weather.WeatherFragment

@AndroidEntryPoint
class WeatherActivity: AppCompatActivity() {

    private lateinit var binding: WeatherActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = WeatherActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, WeatherFragment.newInstance())
                .commitNow()
        }
    }
}
