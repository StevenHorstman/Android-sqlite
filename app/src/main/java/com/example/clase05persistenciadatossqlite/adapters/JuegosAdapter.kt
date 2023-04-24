package com.example.clase05persistenciadatossqlite.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.clase05persistenciadatossqlite.R
import com.example.clase05persistenciadatossqlite.db.ManejadorBaseDatos
import com.example.clase05persistenciadatossqlite.interfaces.juegosInterface
import com.example.clase05persistenciadatossqlite.modelos.Campeon
import com.example.clase05persistenciadatossqlite.modelos.Juego


class JuegosAdapter(contexto: Context, var listadDejuegos: ArrayList<Campeon>, juegoInterface: juegosInterface) : BaseAdapter() {

    var contexto: Context? = contexto
    var juegoInterface: juegosInterface? = juegoInterface
    override fun getCount(): Int {
        return listadDejuegos.size
    }

    override fun getItem(p0: Int): Any {
        return listadDejuegos[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {

        //usar vista reciclado para eficientar
        var convertView: View?= p1
        if(convertView == null){
            convertView = View.inflate(contexto, R.layout.row_campeon, null)
        }

        val juego = listadDejuegos[p0]

        val miVista = convertView!!
        val tvNombre: TextView = miVista.findViewById(R.id.tvNombre)
        val tvCategoria: TextView = miVista.findViewById(R.id.tvCategoria)
        val img01: ImageView = miVista.findViewById(R.id.img01)
        val img02: ImageView = miVista.findViewById(R.id.img02)
        tvNombre.text = juego.nombre
        tvCategoria.text = juego.categoria
        //borrar
        img02.setOnClickListener(){
            //eliminar
            val baseDatos = ManejadorBaseDatos(this.contexto!!)
            val argumentosWhere = arrayOf(juego.id.toString())
            baseDatos.eliminar("id = ? ", argumentosWhere)
            juegoInterface?.juegoEliminado()
        }

        img01.setOnClickListener(){
            //Editar
            juegoInterface?.editarJuego(juego)
        }

        return miVista
    }

}
