package org.example

import java.io.File
import java.nio.charset.Charset
import java.util.Date

class Celular(
    val marca: String,
    val modelo: String,
    val anioLanzamiento: Int,
    val precio: Double,
    val es5G: Boolean,
    val aplicativo: MutableList<Aplicativo> = mutableListOf()
) {
    companion object {
        fun guardarCelular(celular: Celular, filePath: String) {
            val datosCelular = "${celular.marca},${celular.modelo},${celular.anioLanzamiento},${celular.precio},${celular.es5G}\n"
            File(filePath).appendText(datosCelular, Charset.defaultCharset())
        }

        fun cargarCelulares(filePath: String): List<Celular> {
            val celularesData = File(filePath).readLines()
            val celulares = mutableListOf<Celular>()
            for (celularData in celularesData) {
                val parts = celularData.split(",")
                if (parts.size >= 5) {
                    val marca = parts[0]
                    val modelo = parts[1]
                    val anioLanzamiento = parts[2].toInt()
                    val precio = parts[3].toDouble()
                    val es5G = parts[4].toBoolean()
                    celulares.add(Celular(marca, modelo, anioLanzamiento, precio, es5G))
                } else {
                    println("Error: Formato de datos incorrecto en la línea: $celularData")
                }
            }
            return celulares
        }

        fun eliminarCelular(marca: String, modelo: String, filePath: String): Boolean {
            val celulares = cargarCelulares(filePath).toMutableList()
            val iterator = celulares.iterator()
            var eliminado = false
            while (iterator.hasNext()) {
                val celular = iterator.next()
                if (celular.marca == marca && celular.modelo == modelo) {
                    iterator.remove()
                    eliminado = true
                }
            }
            if (eliminado) {
                File(filePath).writeText("") // Borra el archivo actual
                celulares.forEach { guardarCelular(it, filePath) }
            }
            return eliminado
        }

        fun actualizarCelular(celularActualizado: Celular, filePath: String): Boolean {
            val celulares = cargarCelulares(filePath).toMutableList()
            var actualizado = false
            for (i in celulares.indices) {
                if (celulares[i].marca == celularActualizado.marca && celulares[i].modelo == celularActualizado.modelo) {
                    celulares[i] = celularActualizado
                    actualizado = true
                    break
                }
            }
            if (actualizado) {
                File(filePath).writeText("")
                celulares.forEach { guardarCelular(it, filePath) }
            }
            return actualizado
        }
    }
}