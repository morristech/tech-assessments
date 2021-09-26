package design.morristech.openweather.core

enum class Temperature(private val displayedName: String) {
    CELSIUS("C°"),
    FAHRENHEIT("F°");

    override fun toString(): String = displayedName
}

enum class Wind(private val displayedName: String) {
    METERS_PER_SECOND("m/s"),
    KILOMETERS_PER_HOUR("km/h");

    override fun toString(): String = displayedName
}

enum class Pressure(private val displayedName: String) {
    MILLIMETERS_OF_MERCURY("mmHg"),
    HECTO_PASCALS("hPa");

    override fun toString(): String = displayedName
}

enum class Theme(private val displayedName: String) {
    LIGHT("Light"),
    NIGHT("Night");

    override fun toString(): String = displayedName
}
