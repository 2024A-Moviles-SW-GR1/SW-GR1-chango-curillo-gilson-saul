package com.example.deber2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.cl_lista_celular)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //Me voy a mi pantalla de lista de celulares
        val botonIngresar = findViewById<Button>(R.id.bt_ingresar)
        botonIngresar
            .setOnClickListener{
                irActividad(ListaCelular::class.java)
            }


    }
    fun irActividad(
        clase: Class <*>
    ){
        val intent = Intent (this, clase)
        startActivity(intent)
    }
}