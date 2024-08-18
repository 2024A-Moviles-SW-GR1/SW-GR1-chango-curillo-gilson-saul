// Aplicativo.kt
data class Aplicativo(
    val nombre: String,
    val version: String,
    val fechaActualizacion: Int, // Puedes cambiar el tipo si la fecha tiene otro formato
    val tamanoMB: Double,
    val esGratuito: Boolean
) {
    override fun toString(): String {
        return "Aplicativo(nombre='$nombre', version='$version', fechaActualizacion=$fechaActualizacion, tamanoMB=$tamanoMB, esGratuito=$esGratuito)"
    }
}
