package com.example.deber2

import Celular
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class CelularAdapter(context: Context, private val items: List<Celular>) :
    ArrayAdapter<Celular>(context, 0, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val celular = getItem(position)
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_celular, parent, false)

        val marcaTextView = view.findViewById<TextView>(R.id.tv_marca)
        val modeloTextView = view.findViewById<TextView>(R.id.tv_modelo)
        val anioTextView = view.findViewById<TextView>(R.id.tv_anio_lanzamiento)
        val precioTextView = view.findViewById<TextView>(R.id.tv_precio)
        val es5GTextView = view.findViewById<TextView>(R.id.tv_es_5g)

        marcaTextView.text = celular?.marca
        modeloTextView.text = celular?.modelo
        anioTextView.text = celular?.anioLanzamiento.toString()
        precioTextView.text = celular?.precio.toString()
        es5GTextView.text = if (celular?.es5G == true) "Sí" else "No"

        return view
    }
}
