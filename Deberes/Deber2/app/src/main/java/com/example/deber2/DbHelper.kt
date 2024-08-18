package com.example.deber2

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "app_database.db"
        const val TABLE_CELULAR = "celular"
        const val TABLE_APLICATIVO = "aplicativo"

        private const val COLUMN_ID = "id"
        const val COLUMN_MARCA = "marca"
        const val COLUMN_MODELO = "modelo"
        const val COLUMN_ANIO_LANZAMIENTO = "anio_lanzamiento"
        const val COLUMN_PRECIO = "precio"
        const val COLUMN_ES_5G = "es_5g"
        const val COLUMN_NOMBRE = "nombre"
        const val COLUMN_VERSION = "version"
        const val COLUMN_FECHA_ACTUALIZACION = "fecha_actualizacion"
        const val COLUMN_TAMANO_MB = "tamano_mb"
        const val COLUMN_ES_GRATUITO = "es_gratuito"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableCelular = ("CREATE TABLE $TABLE_CELULAR (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_MARCA TEXT," +
                "$COLUMN_MODELO TEXT," +
                "$COLUMN_ANIO_LANZAMIENTO INTEGER," +
                "$COLUMN_PRECIO REAL," +
                "$COLUMN_ES_5G INTEGER)")

        val createTableAplicativo = ("CREATE TABLE $TABLE_APLICATIVO (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_NOMBRE TEXT," +
                "$COLUMN_VERSION TEXT," +
                "$COLUMN_FECHA_ACTUALIZACION INTEGER," +
                "$COLUMN_TAMANO_MB REAL," +
                "$COLUMN_ES_GRATUITO INTEGER)")

        db.execSQL(createTableCelular)
        db.execSQL(createTableAplicativo)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CELULAR")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_APLICATIVO")
        onCreate(db)
    }
}

