// Celular.kt
data class Celular(
    val marca: String,
    val modelo: String,
    val anioLanzamiento: Int,
    val precio: Double,
    val es5G: Boolean
) {
    override fun toString(): String {
        return "Celular(marca='$marca', modelo='$modelo', anioLanzamiento=$anioLanzamiento, precio=$precio, es5G=$es5G)"
    }
}
