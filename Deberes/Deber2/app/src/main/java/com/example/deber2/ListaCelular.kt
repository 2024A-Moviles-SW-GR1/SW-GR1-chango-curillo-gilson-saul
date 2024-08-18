package com.example.deber2

import Celular
import android.content.Context
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

class ListaCelular : AppCompatActivity() {
    private lateinit var dbHelper: DbHelper
    private lateinit var celularListView: ListView
    private lateinit var agregarButton: Button
    private lateinit var selectedCelular: Celular

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lista_celular)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.cl_lista_celular)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dbHelper = DbHelper(this)
        celularListView = findViewById(R.id.lv_lista_celular)
        agregarButton = findViewById(R.id.bt_agregar_celular)

        loadCelulares()

        registerForContextMenu(celularListView) // Register for context menu

        agregarButton.setOnClickListener {
            irActividad(AgregarCelular::class.java)
        }

        val botonActualizarLista = findViewById<Button>(R.id.bt_actualizar_celulares)
        botonActualizarLista
            .setOnClickListener{
                loadCelulares()
            }

    }

    private fun loadCelulares() {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM celular", null)

        val celulares = mutableListOf<Celular>()
        with(cursor) {
            while (moveToNext()) {
                val marca = getString(getColumnIndexOrThrow("marca"))
                val modelo = getString(getColumnIndexOrThrow("modelo"))
                val anioLanzamiento = getInt(getColumnIndexOrThrow("anio_lanzamiento"))
                val precio = getDouble(getColumnIndexOrThrow("precio"))
                val es5G = getInt(getColumnIndexOrThrow("es_5g")) > 0
                celulares.add(Celular(marca, modelo, anioLanzamiento, precio, es5G))
            }
            close()
        }
        celularListView.adapter = CelularAdapter(this, celulares)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.menu_celular, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = (item.menuInfo as AdapterView.AdapterContextMenuInfo)
        val celular = (celularListView.adapter as CelularAdapter).getItem(info.position)

        return when (item.itemId) {
            R.id.mi_editar -> {
                celular?.let {
                    val intent = Intent(this, EditarCelular::class.java).apply {
                        putExtra("marca", it.marca)
                        putExtra("modelo", it.modelo)
                        putExtra("anio_lanzamiento", it.anioLanzamiento)
                        putExtra("precio", it.precio)
                        putExtra("es_5g", it.es5G)
                    }
                    startActivity(intent)
                }
                true
            }
            R.id.mi_eliminar -> {
                // Handle Delete action
                celular?.let { deleteCelular(it) }
                loadCelulares() // Reload list
                true
            }
            R.id.mi_aplicativos -> {
                // Handle Aplicativos action
                irActividad(ListaAplicativo::class.java)
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun deleteCelular(celular: Celular) {
        val db = dbHelper.writableDatabase
        val selection = "${DbHelper.COLUMN_MARCA} = ? AND ${DbHelper.COLUMN_MODELO} = ?"
        val selectionArgs = arrayOf(celular.marca, celular.modelo)
        db.delete(DbHelper.TABLE_CELULAR, selection, selectionArgs)
        db.close()
    }

    private fun irActividad(clase: Class<*>) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}

