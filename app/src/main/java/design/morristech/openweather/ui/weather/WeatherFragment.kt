package design.morristech.openweather.ui.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import design.morristech.openweather.ui.R
import design.morristech.openweather.ui.WeatherViewModel

@AndroidEntryPoint
class WeatherFragment : Fragment() {

    companion object {
        fun newInstance() = WeatherFragment()
    }

    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        with(viewModel) {
            observe(forecast, ::renderForecast)
            observe(forecastFailure, ::handleFailure)
            observe(isLoading, ::onLoading)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        // Set up SwipeRefreshLayout
        swipeRefreshLayout.run {
            setProgressViewOffset(true, 0, 55.dp)
            setOnRefreshListener { viewModel.updateForecast() }
        }

        // Set up BottomAppBar
        (activity as WeatherActivity).setSupportActionBar(bottomAppBar)
        bottomAppBar.run {
            setNavigationOnClickListener {
                FavouriteFragment
                    .newInstance()
                    .show(parentFragmentManager, FavouriteFragment.TAG)
            }
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu_search_action -> {
                        parentFragmentManager.commit {
                            replace(R.id.mainContainer, SearchFragment.newInstance())
                            addToBackStack(SearchFragment.TAG)
                        }
                        true
                    }
                    else -> false
                }
            }
        }

        // List configurations
        hourList.run {
            adapter = hourlyForecastAdapter
            layoutManager = LinearLayoutManager(requireContext(), HORIZONTAL, false)
            isNestedScrollingEnabled = false
        }
        dayList.run {
            adapter = dailyForecastAdapter
            layoutManager = LinearLayoutManager(requireContext(), VERTICAL, false)
            isNestedScrollingEnabled = false
        }

        // Add to favourite button (star)
        favouriteToggle.setOnClickListener {
            viewModel.changeForecastFavouriteStatus(favouriteToggle.isChecked)
        }
    }

    /**
     * Handles with [forecast] data
     */
    private fun renderForecast(forecast: Forecast) {
        renderCurrentForecast(forecast.currentForecast)
        hourlyForecastAdapter.submitList(forecast.hourForecastList)
        dailyForecastAdapter.submitList(forecast.dayForecastList)
        locationName.text = forecast.locationName
        favouriteToggle.isChecked = forecast.isFavourite
        if (mainContentLinearLayout.isInvisible) {
            mainContentLinearLayout.fadeIn()
        }
    }

    /**
     * Displays [currentForecast] data
     */
    private fun renderCurrentForecast(currentForecast: CurrentForecast) = with(currentForecast) {
        // Forecast description
        currentDescription.text = description
        currentDescriptionImage.animateScaleFadeChange(imageId)

        // Temperature
        currentTemperature.animateNumberChange(newValue = temperature.value, isSignShown = true)
        currentFeelsLike.animateNumberChange(newValue = feelsLike.value, isSignShown = true)

        // Humidity
        humidityCard.value.animateNumberChange(newValue = humidity)

        // Wind
        windCard.value.animateNumberChange(newValue = wind.value)
        when (currentForecast.wind.unit) {
            METERS_PER_SECOND ->
                windCard.unit.setText(R.string.wind_value_meters_per_second)
            KILOMETERS_PER_HOUR ->
                windCard.unit.setText(R.string.wind_value_kilometers_per_hour)
        }

        // Pressure
        pressureCard.value.animateNumberChange(newValue = pressure.value)
        when (currentForecast.pressure.unit) {
            MILLIMETERS_OF_MERCURY ->
                pressureCard.unit.setText(R.string.pressure_value_millimeters)
            HECTO_PASCALS ->
                pressureCard.unit.setText(R.string.pressure_value_pascals)
        }
    }

    /**
     * Handles with failures that may occur while loading data.
     *
     * Notifies user via SnackBar about them.
     */
    private fun handleFailure(failure: Exception?) {
        when (failure) {
            is NoConnection -> {
                snackbarAction(
                    coordinatorLayout,
                    bottomAppBar,
                    R.string.failure_connection_error,
                    R.string.button_try_again
                ) {
                    viewModel.updateForecast()
                }
            }
            is BadServerResponse -> {
                snackbarAction(
                    coordinatorLayout,
                    bottomAppBar,
                    R.string.failure_server_error,
                    R.string.button_try_again
                ) {
                    viewModel.updateForecast()
                }
            }
            else -> {
                // TODO: Handle other failures?
            }
        }
    }

    /**
     * Changes [swipeRefreshLayout] loading status when loading data
     */
    private fun onLoading(isLoading: Boolean) {
        swipeRefreshLayout.isRefreshing = isLoading
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }
}
