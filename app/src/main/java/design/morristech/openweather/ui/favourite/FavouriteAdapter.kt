package design.morristech.openweather.ui.favourite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import design.morristech.openweather.R
import design.morristech.openweather.data.local.model.FavouriteForecast
import design.morristech.openweather.utils.extensions.withSign
import kotlinx.android.synthetic.main.item_favourite.view.*
import javax.inject.Inject

class FavouriteAdapter @Inject constructor(
    private val onLocationChange: (FavouriteForecast) -> Unit
) : RecyclerView.Adapter<FavouriteAdapter.MiniForecastHolder>() {
    private val data = ArrayList<FavouriteForecast>()

    fun updateData(data: List<FavouriteForecast>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    inner class MiniForecastHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(favouriteForecast: FavouriteForecast) = with(itemView) {
            temperature.text = favouriteForecast.temperature.value.withSign()
            description.text = favouriteForecast.description
            locationName.text = favouriteForecast.locationName
            weatherIcon.setImageResource(favouriteForecast.imageId)
            card.setOnClickListener {
                onLocationChange(favouriteForecast)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MiniForecastHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_favourite, parent, false)
        return MiniForecastHolder(view)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MiniForecastHolder, position: Int) {
        holder.bind(data[position])
    }
}
