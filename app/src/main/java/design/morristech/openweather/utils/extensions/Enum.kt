package design.morristech.openweather.utils.extensions

inline fun <reified T : Enum<T>> String.asEnumOrDefault(): T =
    enumValues<T>().first { it.name.equals(this, ignoreCase = true) }
