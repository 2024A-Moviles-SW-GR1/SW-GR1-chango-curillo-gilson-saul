package com.example.deber2

import Celular
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AgregarCelular : AppCompatActivity() {

    private lateinit var phoneRepository: PhoneRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_agregar_celular)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.cl_agregar_celular)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        phoneRepository = PhoneRepository(this)

        val btnGuardarCelular = findViewById<Button>(R.id.bt_guardar_celular)
        btnGuardarCelular.setOnClickListener {
            guardarCelular()
        }
    }

    private fun guardarCelular() {
        val marca = findViewById<EditText>(R.id.et_marca).text.toString()
        val modelo = findViewById<EditText>(R.id.et_modelo).text.toString()
        val anioLanzamiento = findViewById<EditText>(R.id.et_anio_lanzamiento).text.toString().toIntOrNull()
        val precio = findViewById<EditText>(R.id.et_precio).text.toString().toDoubleOrNull()
        val es5G = findViewById<CheckBox>(R.id.cb_es_5g).isChecked
        val latitud = findViewById<EditText>(R.id.et_latitud).text.toString().toDoubleOrNull()
        val longitud = findViewById<EditText>(R.id.et_longitud).text.toString().toDoubleOrNull()

        if (marca.isNotBlank() && modelo.isNotBlank() && anioLanzamiento != null && precio != null && latitud != null && longitud != null) {
            val nuevoCelular = Celular(marca, modelo, anioLanzamiento, precio, es5G, latitud, longitud)
            phoneRepository.insertCelular(nuevoCelular)
            Toast.makeText(this, "Celular agregado con éxito", Toast.LENGTH_SHORT).show()
            finish() // Cierra la actividad y vuelve a la anterior
        } else {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
        }
    }
}

