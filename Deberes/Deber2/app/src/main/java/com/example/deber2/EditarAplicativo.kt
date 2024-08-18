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

class EditarAplicativo : AppCompatActivity() {

    private lateinit var nombreEditText: EditText
    private lateinit var versionEditText: EditText
    private lateinit var fechaActualizacionEditText: EditText
    private lateinit var tamanoMBEditText: EditText
    private lateinit var esGratuitoCheckBox: CheckBox
    private lateinit var guardarButton: Button

    private lateinit var originalNombre: String
    private lateinit var originalVersion: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_editar_aplicativo)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.cl_editar_aplicativo)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize views
        nombreEditText = findViewById(R.id.et_nombre)
        versionEditText = findViewById(R.id.et_version)
        fechaActualizacionEditText = findViewById(R.id.et_fecha_actualizacion)
        tamanoMBEditText = findViewById(R.id.et_tamano_mb)
        esGratuitoCheckBox = findViewById(R.id.cb_es_gratuito)
        guardarButton = findViewById(R.id.btn_guardar)

        // Get Aplicativo data from Intent
        val intent = intent
        originalNombre = intent.getStringExtra("nombre") ?: ""
        originalVersion = intent.getStringExtra("version") ?: ""
        val fechaActualizacion = intent.getIntExtra("fecha_actualizacion", 0)
        val tamanoMB = intent.getDoubleExtra("tamano_mb", 0.0)
        val esGratuito = intent.getBooleanExtra("es_gratuito", false)

        nombreEditText.setText(originalNombre)
        versionEditText.setText(originalVersion)
        fechaActualizacionEditText.setText(fechaActualizacion.toString())
        tamanoMBEditText.setText(tamanoMB.toString())
        esGratuitoCheckBox.isChecked = esGratuito

        guardarButton.setOnClickListener {
            val newNombre = nombreEditText.text.toString()
            val newVersion = versionEditText.text.toString()
            val newFechaActualizacion = fechaActualizacionEditText.text.toString().toIntOrNull() ?: 0
            val newTamanoMB = tamanoMBEditText.text.toString().toDoubleOrNull() ?: 0.0
            val newEsGratuito = esGratuitoCheckBox.isChecked

            val dbHelper = DbHelper(this)
            val db = dbHelper.writableDatabase
            val values = ContentValues().apply {
                put(DbHelper.COLUMN_NOMBRE, newNombre)
                put(DbHelper.COLUMN_VERSION, newVersion)
                put(DbHelper.COLUMN_FECHA_ACTUALIZACION, newFechaActualizacion)
                put(DbHelper.COLUMN_TAMANO_MB, newTamanoMB)
                put(DbHelper.COLUMN_ES_GRATUITO, if (newEsGratuito) 1 else 0)
            }
            val selection = "${DbHelper.COLUMN_NOMBRE} = ? AND ${DbHelper.COLUMN_VERSION} = ?"
            val selectionArgs = arrayOf(originalNombre, originalVersion)
            val rowsUpdated = db.update(DbHelper.TABLE_APLICATIVO, values, selection, selectionArgs)
            db.close()

            if (rowsUpdated > 0) {
                Toast.makeText(this, "Aplicativo actualizado", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Error al actualizar el aplicativo", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
