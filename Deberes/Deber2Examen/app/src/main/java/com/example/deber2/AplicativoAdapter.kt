package com.example.deber2

import Aplicativo
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class AplicativoAdapter(context: Context, private val items: List<Aplicativo>) :
    ArrayAdapter<Aplicativo>(context, 0, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val aplicativo = getItem(position)
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_aplicativo, parent, false)

        val nombreTextView = view.findViewById<TextView>(R.id.tv_nombre)
        val versionTextView = view.findViewById<TextView>(R.id.tv_version)
        val fechaTextView = view.findViewById<TextView>(R.id.tv_fecha)
        val tamanoTextView = view.findViewById<TextView>(R.id.tv_tamano)
        val gratuitoTextView = view.findViewById<TextView>(R.id.tv_gratuito)

        nombreTextView.text = aplicativo?.nombre
        versionTextView.text = aplicativo?.version
        fechaTextView.text = aplicativo?.fechaActualizacion.toString()
        tamanoTextView.text = aplicativo?.tamanoMB.toString()
        gratuitoTextView.text = if (aplicativo?.esGratuito == true) "Sí" else "No"

        return view
    }
}
