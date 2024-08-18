package com.example.a2024aswgr1gsch

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ESqliteHelperEntrenador(
    contexto: Context
) :SQLiteOpenHelper(
    contexto,
    "moviles",
    null,
    1
){
    //Metodos que van se van a usar para guardar datos en la memoria del telefono
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaEntrenador =
            """
                CREATE TABLE ENTRENADOR(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre VARCHAR(50),
                descripcion VARCHAR(50)
                )
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaEntrenador)
    }

    override fun onUpgrade(
        db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    fun crearEntrenador(
        nombre: String,
        descripcion: String
    ):Boolean{
        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("descripcion", descripcion)
        val resultadoGuardar = basedatosEscritura
            .insert(
                "ENTRENADOR", //nombre de la tabla
                null,
                valoresAGuardar //Los datos que se guardaron
            )
        basedatosEscritura.close()
        return if(resultadoGuardar.toInt() == -1) false else true
    }

    fun eliminarEntrenadorFormulario(id:Int):Boolean{
        val conexionEscritura = writableDatabase
        //Consulta SQL
        val parametrosConsultaDelete = arrayOf(id.toString())
        val resultadoEliminacion = conexionEscritura
            .delete(
                "ENTRENADOR",
                "id=?",
                parametrosConsultaDelete
            )
        conexionEscritura.close()
        return if(resultadoEliminacion.toInt() == -1) false else true
    }


    fun actualizarEntrenadorFormulario(
        nombre:String, descripcion:String, id: Int
    ):Boolean{
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre", nombre)
        valoresAActualizar.put("descripcion", descripcion)
        ///Consulta SQL
        val parametrosConsultaActualizar  = arrayOf(id.toString())
        val resultadoActualizacion = conexionEscritura
            .update(
                "ENTRENADOR",
                valoresAActualizar,
                "id=?",
                parametrosConsultaActualizar
            )
        conexionEscritura.close()
        return if (resultadoActualizacion.toInt() == -1 ) false else true
    }

    fun consultarEntrenadorPorID(id:Int):BEntrenador?{
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
            SELECT * FROM ENTRENADOR WHERE ID = ?
            """.trimIndent()
        val arregloParametroConsultaLectura = arrayOf(
            id.toString()
        )
        val resultadoConsultaLectura = baseDatosLectura
            .rawQuery(
                scriptConsultaLectura,
                arregloParametroConsultaLectura
            )

        //logica busqueda
        //Recibimos un arreglo (puede ser nulo)
        //Llenar un arreglo de entrenadores
        //Si esta vacio, el arreglo no tiene elementos
        val exiseAlMenosUno = resultadoConsultaLectura
            .moveToFirst()
        val arregloRespuesta = arrayListOf<BEntrenador>()
        if(exiseAlMenosUno){
            do{
                val entrenador = BEntrenador(
                    resultadoConsultaLectura.getInt(0),
                    resultadoConsultaLectura.getString(1),
                    resultadoConsultaLectura.getString(2)
                )
                arregloRespuesta.add(entrenador)
            }while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        //return arregloRespuesta[0]
        return if (arregloRespuesta.size>0) arregloRespuesta[0] else null
    }

}