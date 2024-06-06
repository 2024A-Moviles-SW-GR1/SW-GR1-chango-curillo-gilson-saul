package org.example

import java.io.File
import java.nio.charset.Charset
import java.time.LocalDate
import java.util.*

class Aplicativo (
    val nombre: String,
    val version: String,
    val fechaActualizacion: LocalDate,
    val tamanoMB: Double,
    val esGratuito: Boolean
){
    companion object {
        fun guardarAplicativo(aplicativo: Aplicativo, filePath: String) {
            val datosAplicativo = "${aplicativo.nombre},${aplicativo.version},${aplicativo.fechaActualizacion},${aplicativo.tamanoMB},${aplicativo.esGratuito}\n"
            File(filePath).appendText(datosAplicativo, Charsets.UTF_8)
        }

        fun cargarAplicativos(filePath: String): List<Aplicativo> {
            val aplicativosData = File(filePath).readLines()
            val aplicativos = mutableListOf<Aplicativo>()
            for (aplicativoData in aplicativosData) {
                val parts = aplicativoData.split(",")
                if (parts.size >= 5) {
                    val nombre = parts[0]
                    val version = parts[1]
                    val fechaActualizacion = LocalDate.parse(parts[2])
                    val tamanoMB = parts[3].toDouble()
                    val esGratuito = parts[4].toBoolean()
                    aplicativos.add(Aplicativo(nombre, version, fechaActualizacion, tamanoMB, esGratuito))
                } else {
                    println("Error: Formato de datos incorrecto en la línea: $aplicativoData")
                }
            }
            return aplicativos
        }

        fun eliminarAplicativo(nombre: String, filePath: String): Boolean {
            val aplicativos = cargarAplicativos(filePath).toMutableList()
            val iterator = aplicativos.iterator()
            var eliminado = false
            while (iterator.hasNext()) {
                val aplicativo = iterator.next()
                if (aplicativo.nombre == nombre) {
                    iterator.remove()
                    eliminado = true
                }
            }
            if (eliminado) {
                File(filePath).writeText("") // Borra el archivo actual
                aplicativos.forEach { guardarAplicativo(it, filePath) }
            }
            return eliminado
        }

        fun actualizarAplicativo(nombre: String, version: String, fechaActualizacion: LocalDate, tamanoMB: Double, esGratuito: Boolean, filePath: String): Boolean {
            val aplicativos = cargarAplicativos(filePath).toMutableList()
            var actualizado = false
            for (i in aplicativos.indices) {
                val aplicativo = aplicativos[i]
                if (aplicativo.nombre == nombre) {
                    aplicativos[i] = Aplicativo(nombre, version, fechaActualizacion, tamanoMB, esGratuito)
                    actualizado = true
                    break
                }
            }
            if (actualizado) {
                File(filePath).writeText("") // Borra el archivo actual
                aplicativos.forEach { guardarAplicativo(it, filePath) }
            }
            return actualizado
        }
    }
}