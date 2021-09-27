package design.morristech.openweather.ui.settings

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItems
import dagger.hilt.android.AndroidEntryPoint
import design.morristech.openweather.BuildConfig
import design.morristech.openweather.R
import design.morristech.openweather.core.Theme
import design.morristech.openweather.core.Wind
import design.morristech.openweather.core.base.BaseFragment
import design.morristech.openweather.data.local.model.Pressure
import design.morristech.openweather.data.local.model.Temperature
import design.morristech.openweather.databinding.SettingsFragmentBinding
import design.morristech.openweather.ui.WeatherViewModel
import design.morristech.openweather.utils.extensions.openLink
import design.morristech.openweather.views.SettingsItem
import kotlinx.android.synthetic.main.settings_fragment.*

@AndroidEntryPoint
class SettingsFragment : BaseFragment() {
    override val layoutId = R.layout.settings_fragment
    private val viewModel: SettingsViewModel by viewModels()
    private val activityViewModel: WeatherViewModel by activityViewModels()

    private lateinit var binding: SettingsFragmentBinding

    companion object {
        const val TAG = "SettingsFragment"
        fun newInstance() = SettingsFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = SettingsFragmentBinding.bind(view)
        setUpAppearanceSection()
        setUpUnitsSection()
        setUpAboutSection()
    }

    private fun setUpAppearanceSection() {
        binding.themes.setValue(viewModel.theme)
        binding.themes.setOnClickListener {
            showDialog(
                titleId = R.string.theme_title,
                items = viewModel.availableThemes,
                valueToUpdate = binding.themes
            ) { item: Theme ->
                viewModel.updateTheme(item)
            }
        }
    }

    private fun setUpUnitsSection() {
        // Temperature
        binding.temperature.setValue(viewModel.temperature)
        binding.temperature.setOnClickListener {
            showDialog(
                titleId = R.string.temperature_title,
                items = viewModel.availableTemperatureUnits,
                valueToUpdate = binding.temperature
            ) { item: Temperature ->
                viewModel.updateTemperatureUnit(item)
            }
        }

        // Wind
        binding.wind.setValue(viewModel.wind)
        binding.wind.setOnClickListener {
            showDialog(
                titleId = R.string.wind_title,
                items = viewModel.availableWindUnits,
                valueToUpdate = binding.wind
            ) { item: Wind ->
                viewModel.updateWindUnit(item)
            }
        }

        // Pressure
        binding.pressure.setValue(viewModel.pressure)
        binding.pressure.setOnClickListener {
            showDialog(
                titleId = R.string.pressure_title,
                items = viewModel.availablePressureUnits,
                valueToUpdate = binding.pressure
            ) { item: Pressure ->
                viewModel.updatePressureUnit(item)
            }
        }
    }


    private fun setUpAboutSection() {
        github.setOnClickListener { openLink(R.string.settings_summary_github) }
        author.setOnClickListener { openLink("https://github.com/morristech") }
        binding.appVersion.setValue(BuildConfig.VERSION_NAME)
        binding.buildNumber.setValue(BuildConfig.VERSION_CODE.toString())
    }

    /**
     * Creates and shows material dialog based on list of elements.
     * @param titleId Title from string resources
     * @param items Array of all available elements to choose from
     * @param valueToUpdate SettingsItem view on which to update chosen value
     * @param onSelect high-order function with item (chosen element) as a parameter
     */
    private inline fun <T> showDialog(
        @StringRes titleId: Int,
        items: Array<T>,
        valueToUpdate: SettingsItem,
        crossinline onSelect: (item: T) -> Unit
    ) {
        MaterialDialog(requireContext()).show {
            title(titleId)
            listItems(items = items.map { it.toString() }) { _, index, text ->
                valueToUpdate.setValue(text.toString())
                onSelect(items[index])
                activityViewModel.applyNewUnits()
            }
        }
    }
}
