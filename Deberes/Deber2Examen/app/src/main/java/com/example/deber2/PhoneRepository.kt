package com.example.deber2

import Aplicativo
import Celular
import android.content.ContentValues
import android.content.Context

class PhoneRepository(context: Context) {

    private val baseDeDatos = BaseDeDatos(context)

    fun insertCelular(celular: Celular) {
        val db = baseDeDatos.writableDatabase
        val values = ContentValues().apply {
            put(BaseDeDatos.COLUMN_MARCA, celular.marca)
            put(BaseDeDatos.COLUMN_MODELO, celular.modelo)
            put(BaseDeDatos.COLUMN_ANIO_LANZAMIENTO, celular.anioLanzamiento)
            put(BaseDeDatos.COLUMN_PRECIO, celular.precio)
            put(BaseDeDatos.COLUMN_ES_5G, if (celular.es5G) 1 else 0)
            put("latitud", celular.latitud) // Nueva columna
            put("longitud", celular.longitud) // Nueva columna
        }
        db.insert(BaseDeDatos.TABLE_CELULAR, null, values)
        db.close()
    }

    fun insertAplicativo(aplicativo: Aplicativo) {
        val db = baseDeDatos.writableDatabase
        val values = ContentValues().apply {
            put(BaseDeDatos.COLUMN_NOMBRE, aplicativo.nombre)
            put(BaseDeDatos.COLUMN_VERSION, aplicativo.version)
            put(BaseDeDatos.COLUMN_FECHA_ACTUALIZACION, aplicativo.fechaActualizacion)
            put(BaseDeDatos.COLUMN_TAMANO_MB, aplicativo.tamanoMB)
            put(BaseDeDatos.COLUMN_ES_GRATUITO, if (aplicativo.esGratuito) 1 else 0)
        }
        db.insert(BaseDeDatos.TABLE_APLICATIVO, null, values)
        db.close()
    }
}

