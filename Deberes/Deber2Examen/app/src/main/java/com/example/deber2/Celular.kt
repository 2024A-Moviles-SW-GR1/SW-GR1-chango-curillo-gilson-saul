// Celular.kt
data class Celular(
    val marca: String,
    val modelo: String,
    val anioLanzamiento: Int,
    val precio: Double,
    val es5G: Boolean,
    val latitud: Double?, // Latitude of the location
    val longitud: Double? // Longitude of the location
) {
    override fun toString(): String {
        return "Celular(marca='$marca', modelo='$modelo', anioLanzamiento=$anioLanzamiento, precio=$precio, es5G=$es5G, latitud=$latitud, longitud=$longitud)"
    }
}

