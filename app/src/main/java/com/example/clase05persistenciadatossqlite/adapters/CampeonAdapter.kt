package com.example.clase05persistenciadatossqlite.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.clase05persistenciadatossqlite.R
import com.example.clase05persistenciadatossqlite.db.ManejadorBaseDatos
import com.example.clase05persistenciadatossqlite.interfaces.juegosInterface
import com.example.clase05persistenciadatossqlite.modelos.Campeon

class CampeonAdapter(campeones: ArrayList<Campeon>, context: Context, juegosInterface: juegosInterface):
    RecyclerView.Adapter<CampeonAdapter.ContenedorDeVista>() {
    var innerCampeon: ArrayList<Campeon> = campeones
    var innerContext: Context = context
    var juegoInterface: juegosInterface? = juegosInterface

    // Siempre tiene que tener de argumentro vista
    // Hereda lo de RecyclerView.ViewHolder
    inner class ContenedorDeVista(view: View): RecyclerView.ViewHolder(view){
        val tvNombreJuego: TextView
        val tvCategoria: TextView
        val img1: ImageView
        val img2: ImageView
        var id: Int
        var campeon: Campeon

        init {
            campeon = Campeon(100000, "", "", "")
            tvNombreJuego = view.findViewById(R.id.tvNombre)
            tvCategoria = view.findViewById(R.id.tvCategoria)
            img1 = view.findViewById(R.id.img01)
            img2 = view.findViewById(R.id.img02)
            id  = 0

            img1.setOnClickListener{
                juegoInterface?.editarJuego(campeon)
            }

            img2.setOnClickListener{
                //eliminar
                val baseDatos = ManejadorBaseDatos(innerContext)
                val argumentosWhere = arrayOf(id.toString())
                baseDatos.eliminar("id = ? ", argumentosWhere)
                juegoInterface?.juegoEliminado()
            }




        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContenedorDeVista {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_listado, parent, false)

        return  ContenedorDeVista(view)
    }

    override fun onBindViewHolder(holder: ContenedorDeVista, position: Int) {
        val campeon: Campeon = innerCampeon.get(position)
        holder.id = campeon.id
        holder.campeon = campeon
        holder.tvCategoria. text = campeon.categoria
        holder.tvNombreJuego.text  = campeon.nombre

    }


    override fun getItemCount(): Int {
        return innerCampeon.size //El size te la cantidad de elementos que hay en ele arraylist
    }
}