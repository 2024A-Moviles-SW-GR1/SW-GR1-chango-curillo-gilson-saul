package com.example.deber2

import Aplicativo
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.ContextMenu
import android.view.ContextMenu.ContextMenuInfo
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ListaAplicativo : AppCompatActivity() {
    private lateinit var dbHelper: DbHelper
    private lateinit var aplicativoListView: ListView
    private lateinit var agregarButton: Button
    private lateinit var selectedAplicativo: Aplicativo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lista_aplicativo)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.cl_lista_aplicativo)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dbHelper = DbHelper(this)
        aplicativoListView = findViewById(R.id.lv_lista_aplicativo)
        agregarButton = findViewById(R.id.bt_agregar_aplicativo)

        loadAplicativos()

        registerForContextMenu(aplicativoListView) // Register for context menu

        agregarButton.setOnClickListener {
            irActividad(AgregarAplicativo::class.java)
        }

        val botonActulizarListaAplicativo = findViewById<Button>(R.id.bt_actualizar_lista_aplicativo)
        botonActulizarListaAplicativo
            .setOnClickListener{
                loadAplicativos()
            }
    }

    public fun  loadAplicativos() {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM aplicativo", null)

        val aplicativos = mutableListOf<Aplicativo>()
        with(cursor) {
            while (moveToNext()) {
                val nombre = getString(getColumnIndexOrThrow("nombre"))
                val version = getString(getColumnIndexOrThrow("version"))
                val fechaActualizacion = getInt(getColumnIndexOrThrow("fecha_actualizacion"))
                val tamanoMB = getDouble(getColumnIndexOrThrow("tamano_mb"))
                val esGratuito = getInt(getColumnIndexOrThrow("es_gratuito")) > 0
                aplicativos.add(Aplicativo(nombre, version, fechaActualizacion, tamanoMB, esGratuito))
            }
            close()
        }
        aplicativoListView.adapter = AplicativoAdapter(this, aplicativos)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.menu_aplicativo, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = (item.menuInfo as AdapterView.AdapterContextMenuInfo)
        val aplicativo = (aplicativoListView.adapter as AplicativoAdapter).getItem(info.position)

        return when (item.itemId) {
            R.id.mi_editar_aplicativo -> {
                val intent = Intent(this, EditarAplicativo::class.java)
                intent.putExtra("nombre", aplicativo?.nombre)
                intent.putExtra("version", aplicativo?.version)
                intent.putExtra("fecha_actualizacion", aplicativo?.fechaActualizacion)
                intent.putExtra("tamano_mb", aplicativo?.tamanoMB)
                intent.putExtra("es_gratuito", aplicativo?.esGratuito)
                startActivity(intent)
                true
            }
            R.id.mi_eliminar_aplicativo -> {
                // Handle Delete action
                aplicativo?.let { deleteAplicativo(it) }
                loadAplicativos() // Reload list
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun deleteAplicativo(aplicativo: Aplicativo) {
        val db = dbHelper.writableDatabase
        val selection = "${DbHelper.COLUMN_NOMBRE} = ? AND ${DbHelper.COLUMN_VERSION} = ?"
        val selectionArgs = arrayOf(aplicativo.nombre, aplicativo.version)
        db.delete(DbHelper.TABLE_APLICATIVO, selection, selectionArgs)
        db.close()
    }

    private fun irActividad(clase: Class<*>) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}

