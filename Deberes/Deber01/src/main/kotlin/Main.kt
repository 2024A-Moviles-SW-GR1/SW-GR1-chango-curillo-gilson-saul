package org.example
import java.time.*
import java.time.format.DateTimeFormatter

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {

    val pathAplicativo = "C:\\Users\\gilso\\OneDrive\\Desktop\\Gilson\\Universidad\\Sexto Semestre\\Calidad de Software\\Moviles\\Deber\\src\\main\\resources\\aplicativo.txt"
    val pathCelular = "C:\\Users\\gilso\\OneDrive\\Desktop\\Gilson\\Universidad\\Sexto Semestre\\Calidad de Software\\Moviles\\Deber\\src\\main\\resources\\celular.txt"


    println("Bienvenido al programa de gestión de celulares")

    while (true) {
        println("\nSeleccione una opción:")
        println("1. Mostrar todos los celulares")
        println("2. Agregar un nuevo celular")
        println("3. Eliminar un celular")
        println("4. Actualizar un celular")
        println("5. Mostrar todos los aplicativos")
        println("6. Agregar un nuevo aplicativo")
        println("7. Eliminar un aplicativo")
        println("8. Actualizar un aplicativo")
        println("9. Salir")

        when (readLine()?.toIntOrNull()) {
            1 -> {
                val celulares = Celular.cargarCelulares(pathCelular)
                if (celulares.isNotEmpty()) {
                    println("Celulares registrados:")
                    celulares.forEachIndexed { index, celular ->
                        println("${index + 1}. Marca: ${celular.marca}, Modelo: ${celular.modelo}, Año: ${celular.anioLanzamiento}, Precio: ${celular.precio}, 5G: ${celular.es5G}")
                    }
                } else {
                    println("No hay celulares registrados.")
                }
            }
            2 -> {
                println("Ingrese la marca del celular:")
                val marca = readLine().toString()

                println("Ingrese el modelo del celular:")
                val modelo = readLine().toString()

                println("Ingrese el año de lanzamiento del celular:")
                val anioLanzamiento = readLine()?.toIntOrNull() ?: 0

                println("Ingrese el precio del celular:")
                val precio = readLine()?.toDoubleOrNull() ?: 0.0

                println("¿El celular es compatible con 5G? (true/false):")
                val es5G = readLine()?.toBoolean() ?: false

                val celular = Celular(marca, modelo, anioLanzamiento, precio, es5G)
                Celular.guardarCelular(celular, pathCelular)
                println("¡Celular agregado con éxito!")
            }
            3 -> {
                println("Ingrese la marca del celular a eliminar:")
                val marca = readLine().toString()

                println("Ingrese el modelo del celular a eliminar:")
                val modelo = readLine().toString()

                if (Celular.eliminarCelular(marca, modelo, pathCelular)) {
                    println("¡Celular eliminado con éxito!")
                } else {
                    println("No se encontró un celular con la marca y modelo especificados.")
                }
            }
            4 -> {
                println("Ingrese la marca del celular a actualizar:")
                val marca = readLine().toString()

                println("Ingrese el modelo del celular a actualizar:")
                val modelo = readLine().toString()

                println("Ingrese el nuevo año de lanzamiento del celular:")
                val anioLanzamiento = readLine()?.toIntOrNull() ?: 0

                println("Ingrese el nuevo precio del celular:")
                val precio = readLine()?.toDoubleOrNull() ?: 0.0

                println("¿El celular es compatible con 5G? (true/false):")
                val es5G = readLine()?.toBoolean() ?: false

                val celularActualizado = Celular(marca, modelo, anioLanzamiento, precio, es5G)
                if (Celular.actualizarCelular(celularActualizado, pathCelular)) {
                    println("¡Celular actualizado con éxito!")
                } else {
                    println("No se encontró un celular con la marca y modelo especificados.")
                }
            }
            5 -> {
                val aplicativos = Aplicativo.cargarAplicativos(pathAplicativo)
                if (aplicativos.isNotEmpty()) {
                    println("Aplicativos registrados:")
                    aplicativos.forEachIndexed { index, aplicativo ->
                        println("${index + 1}. Nombre: ${aplicativo.nombre}, Versión: ${aplicativo.version}, Fecha de Actualización: ${aplicativo.fechaActualizacion}, Tamaño: ${aplicativo.tamanoMB} MB, Gratuito: ${aplicativo.esGratuito}")
                    }
                } else {
                    println("No hay aplicativos registrados.")
                }
            }
            6 -> {
                println("Ingrese el nombre del aplicativo:")
                val nombre = readLine().toString()

                println("Ingrese la versión del aplicativo:")
                val version = readLine().toString()

                println("Ingrese la fecha de actualización del aplicativo (YYYY-MM-DD):")
                val fechaActualizacion = LocalDate.parse(readLine(), DateTimeFormatter.ISO_DATE)

                println("Ingrese el tamaño del aplicativo (en MB):")
                val tamanoMB = readLine()?.toDoubleOrNull() ?: 0.0

                println("¿El aplicativo es gratuito? (true/false):")
                val esGratuito = readLine()?.toBoolean() ?: false

                val aplicativo = Aplicativo(nombre, version, fechaActualizacion, tamanoMB, esGratuito)
                Aplicativo.guardarAplicativo(aplicativo, pathAplicativo)
                println("¡Aplicativo agregado con éxito!")
            }
            7 -> {
                println("Ingrese el nombre del aplicativo a eliminar:")
                val nombre = readLine().toString()

                if (Aplicativo.eliminarAplicativo(nombre, pathAplicativo)) {
                    println("¡Aplicativo eliminado con éxito!")
                } else {
                    println("No se encontró un aplicativo con el nombre especificado.")
                }
            }
            8 -> {
                println("Ingrese el nombre del aplicativo a actualizar:")
                val nombre = readLine().toString()

                println("Ingrese la nueva versión del aplicativo:")
                val version = readLine().toString()

                println("Ingrese la nueva fecha de actualización del aplicativo (YYYY-MM-DD):")
                val fechaActualizacion = LocalDate.parse(readLine(), DateTimeFormatter.ISO_DATE)

                println("Ingrese el nuevo tamaño del aplicativo (en MB):")
                val tamanoMB = readLine()?.toDoubleOrNull() ?: 0.0

                println("¿El aplicativo es gratuito? (true/false):")
                val esGratuito = readLine()?.toBoolean() ?: false

                if (Aplicativo.actualizarAplicativo(nombre, version, fechaActualizacion, tamanoMB, esGratuito, pathAplicativo)) {
                    println("¡Aplicativo actualizado con éxito!")
                } else {
                    println("No se encontró un aplicativo con el nombre especificado.")
                }
            }
            9 -> {
                println("¡Hasta luego!")
                return
            }
            else -> {
                println("Opción inválida. Por favor, seleccione una opción válida.")
            }
        }
    }
}