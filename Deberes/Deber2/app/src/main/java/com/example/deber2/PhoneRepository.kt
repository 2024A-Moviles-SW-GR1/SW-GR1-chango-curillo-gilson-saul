package com.example.deber2

import Aplicativo
import Celular
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase

class PhoneRepository(context: Context) {

    private val dbHelper = DbHelper(context)

    fun insertCelular(celular: Celular) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DbHelper.COLUMN_MARCA, celular.marca)
            put(DbHelper.COLUMN_MODELO, celular.modelo)
            put(DbHelper.COLUMN_ANIO_LANZAMIENTO, celular.anioLanzamiento)
            put(DbHelper.COLUMN_PRECIO, celular.precio)
            put(DbHelper.COLUMN_ES_5G, if (celular.es5G) 1 else 0)
        }
        db.insert(DbHelper.TABLE_CELULAR, null, values)
        db.close()
    }

    fun insertAplicativo(aplicativo: Aplicativo) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DbHelper.COLUMN_NOMBRE, aplicativo.nombre)
            put(DbHelper.COLUMN_VERSION, aplicativo.version)
            put(DbHelper.COLUMN_FECHA_ACTUALIZACION, aplicativo.fechaActualizacion)
            put(DbHelper.COLUMN_TAMANO_MB, aplicativo.tamanoMB)
            put(DbHelper.COLUMN_ES_GRATUITO, if (aplicativo.esGratuito) 1 else 0)
        }
        db.insert(DbHelper.TABLE_APLICATIVO, null, values)
        db.close()
    }
}
