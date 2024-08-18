package com.example.deber2

import android.content.ContentValues
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class EditarCelular : AppCompatActivity() {

    private lateinit var marcaEditText: EditText
    private lateinit var modeloEditText: EditText
    private lateinit var anioLanzamientoEditText: EditText
    private lateinit var precioEditText: EditText
    private lateinit var es5GCheckBox: CheckBox
    private lateinit var guardarButton: Button

    private lateinit var originalMarca: String
    private lateinit var originalModelo: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_editar_celular)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.cl_editar_celular)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize views
        marcaEditText = findViewById(R.id.et_marca)
        modeloEditText = findViewById(R.id.et_modelo)
        anioLanzamientoEditText = findViewById(R.id.et_anio_lanzamiento)
        precioEditText = findViewById(R.id.et_precio)
        es5GCheckBox = findViewById(R.id.cb_es_5g)
        guardarButton = findViewById(R.id.btn_guardar)

        // Get Celular data from Intent
        val intent = intent
        originalMarca = intent.getStringExtra("marca") ?: ""
        originalModelo = intent.getStringExtra("modelo") ?: ""
        val anioLanzamiento = intent.getIntExtra("anio_lanzamiento", 0)
        val precio = intent.getDoubleExtra("precio", 0.0)
        val es5G = intent.getBooleanExtra("es_5g", false)

        marcaEditText.setText(originalMarca)
        modeloEditText.setText(originalModelo)
        anioLanzamientoEditText.setText(anioLanzamiento.toString())
        precioEditText.setText(precio.toString())
        es5GCheckBox.isChecked = es5G

        guardarButton.setOnClickListener {
            val newMarca = marcaEditText.text.toString()
            val newModelo = modeloEditText.text.toString()
            val newAnioLanzamiento = anioLanzamientoEditText.text.toString().toIntOrNull() ?: 0
            val newPrecio = precioEditText.text.toString().toDoubleOrNull() ?: 0.0
            val newEs5G = es5GCheckBox.isChecked

            val baseDeDatos = BaseDeDatos(this)
            val db = baseDeDatos.writableDatabase
            val values = ContentValues().apply {
                put(BaseDeDatos.COLUMN_MARCA, newMarca)
                put(BaseDeDatos.COLUMN_MODELO, newModelo)
                put(BaseDeDatos.COLUMN_ANIO_LANZAMIENTO, newAnioLanzamiento)
                put(BaseDeDatos.COLUMN_PRECIO, newPrecio)
                put(BaseDeDatos.COLUMN_ES_5G, if (newEs5G) 1 else 0)
            }
            val selection = "${BaseDeDatos.COLUMN_MARCA} = ? AND ${BaseDeDatos.COLUMN_MODELO} = ?"
            val selectionArgs = arrayOf(originalMarca, originalModelo)
            val rowsUpdated = db.update(BaseDeDatos.TABLE_CELULAR, values, selection, selectionArgs)
            db.close()

            if (rowsUpdated > 0) {
                Toast.makeText(this, "Celular actualizado", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Error al actualizar el celular", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

