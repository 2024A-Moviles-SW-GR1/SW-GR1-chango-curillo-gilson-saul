package com.example.deber2

import Celular
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.ContextMenu.ContextMenuInfo
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ListaCelular : AppCompatActivity() {
    private lateinit var baseDeDatos: BaseDeDatos
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

        baseDeDatos = BaseDeDatos(this)
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
        val db = baseDeDatos.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM ${BaseDeDatos.TABLE_CELULAR}", null)

        val celulares = mutableListOf<Celular>()
        with(cursor) {
            while (moveToNext()) {
                val marca = getString(getColumnIndexOrThrow(BaseDeDatos.COLUMN_MARCA))
                val modelo = getString(getColumnIndexOrThrow(BaseDeDatos.COLUMN_MODELO))
                val anioLanzamiento = getInt(getColumnIndexOrThrow(BaseDeDatos.COLUMN_ANIO_LANZAMIENTO))
                val precio = getDouble(getColumnIndexOrThrow(BaseDeDatos.COLUMN_PRECIO))
                val es5G = getInt(getColumnIndexOrThrow(BaseDeDatos.COLUMN_ES_5G)) > 0
                val latitud = getDouble(getColumnIndexOrThrow("latitud"))
                val longitud = getDouble(getColumnIndexOrThrow("longitud"))
                celulares.add(Celular(marca, modelo, anioLanzamiento, precio, es5G, latitud, longitud))
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
                        putExtra("latitud", it.latitud)
                        putExtra("longitud", it.longitud)
                    }
                    startActivity(intent)
                }
                true
            }
            R.id.mi_eliminar -> {
                celular?.let { deleteCelular(it) }
                loadCelulares() // Reload list
                true
            }
            R.id.mi_aplicativos -> {
                irActividad(ListaAplicativo::class.java)
                true
            }
            R.id.mi_ver_tienda -> { // Asegúrate de que este ID coincida con el del XML
                // Inicia la actividad de mapas con coordenadas específicas
                val intent = Intent(this, GGooleMapsInicio::class.java).apply {
                    putExtra("latitud", -0.25103080991177307)
                    putExtra("longitud", -78.50456671224644)
                }
                startActivity(intent)
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun deleteCelular(celular: Celular) {
        val db = baseDeDatos.writableDatabase
        val selection = "${BaseDeDatos.COLUMN_MARCA} = ? AND ${BaseDeDatos.COLUMN_MODELO} = ?"
        val selectionArgs = arrayOf(celular.marca, celular.modelo)
        db.delete(BaseDeDatos.TABLE_CELULAR, selection, selectionArgs)
        db.close()
    }

    private fun irActividad(clase: Class<*>) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}


