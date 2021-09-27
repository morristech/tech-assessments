package design.morristech.openweather.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.StringRes
import androidx.core.content.withStyledAttributes
import design.morristech.openweather.R
import design.morristech.openweather.databinding.ViewSettingsItemBinding
import design.morristech.openweather.utils.extensions.addRipple

class SettingsItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding = ViewSettingsItemBinding.inflate(LayoutInflater.from(context), this)

    init {
        context.withStyledAttributes(attrs, R.styleable.SettingsItem, defStyleAttr) {
            val title = getString(R.styleable.SettingsItem_siTitle)
            val value = getString(R.styleable.SettingsItem_siValue)
            val icon = getDrawable(R.styleable.SettingsItem_siIcon)

            binding.title.text = title
            binding.value.text = value
            binding.icon.setImageDrawable(icon)
        }
        isClickable = true
        isFocusable = true
        addRipple()
    }

    fun setTitle(@StringRes resId: Int) {
        binding.title.setText(resId)
    }

    fun setTitle(text: String) {
        binding.title.text = text
    }

    fun setValue(@StringRes resId: Int) {
        binding.value.setText(resId)
    }

    fun setValue(text: String) {
        binding.value.text = text
    }
}
