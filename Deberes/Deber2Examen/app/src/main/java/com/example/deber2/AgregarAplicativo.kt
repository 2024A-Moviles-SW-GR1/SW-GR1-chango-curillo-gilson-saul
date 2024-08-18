package com.example.deber2

import Aplicativo
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AgregarAplicativo : AppCompatActivity() {

    private lateinit var phoneRepository: PhoneRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_agregar_aplicativo)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.cl_agregar_aplicativo)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        phoneRepository = PhoneRepository(this)

        val btnGuardarAplicativo = findViewById<Button>(R.id.bt_guardar_aplicativo)
        btnGuardarAplicativo.setOnClickListener {
            guardarAplicativo()
        }
    }

    private fun guardarAplicativo() {
        val nombre = findViewById<EditText>(R.id.et_nombre).text.toString()
        val version = findViewById<EditText>(R.id.et_version).text.toString()
        val fechaActualizacion = findViewById<EditText>(R.id.et_fecha_actualizacion).text.toString().toIntOrNull()
        val tamanoMB = findViewById<EditText>(R.id.et_tamano).text.toString().toDoubleOrNull()
        val esGratuito = findViewById<CheckBox>(R.id.cb_es_gratuito).isChecked

        if (nombre.isNotBlank() && version.isNotBlank() && fechaActualizacion != null && tamanoMB != null) {
            val nuevoAplicativo = Aplicativo(nombre, version, fechaActualizacion, tamanoMB, esGratuito)
            phoneRepository.insertAplicativo(nuevoAplicativo)
            Toast.makeText(this, "Aplicativo agregado con éxito", Toast.LENGTH_SHORT).show()
            finish() // Cierra la actividad y vuelve a la anterior
        } else {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
        }
    }

}
