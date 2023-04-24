package com.example.clase05persistenciadatossqlite.activities

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.widget.ListView
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.clase05persistenciadatossqlite.R
import com.example.clase05persistenciadatossqlite.adapters.CampeonAdapter
import com.example.clase05persistenciadatossqlite.adapters.JuegosAdapter
import com.example.clase05persistenciadatossqlite.db.ManejadorBaseDatos
import com.example.clase05persistenciadatossqlite.interfaces.juegosInterface
import com.example.clase05persistenciadatossqlite.modelos.Campeon
import com.example.clase05persistenciadatossqlite.modelos.Juego
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListadoActivity : AppCompatActivity(), juegosInterface {

    private lateinit var recycler: RecyclerView
    private var listaCampeones = ArrayList<Campeon>()
    private lateinit var fab: FloatingActionButton
    private val ORDENAR_POR_NOMBRE : String  = "nombre"
    val columnas = arrayOf("id", "nombre", "categoria", "rol" )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listado)
        inicializarVistas()
        asignarEventos()
    }
    override fun onResume() {
        super.onResume()
        traerMisJuegos()
    }
    private fun inicializarVistas(){
        recycler = findViewById(R.id.recycler)
        fab = findViewById(R.id.fab)
    }

    private fun asignarEventos(){
        fab.setOnClickListener{
            val intent = Intent(this, AgregarActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_listado, menu)
        val searchView = menu!!.findItem(R.id.app_bar_search).actionView as SearchView
        val manejador = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(manejador.getSearchableInfo(componentName))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                buscarJuego("%" + p0 + "%")
                Toast.makeText(applicationContext, p0, Toast.LENGTH_SHORT).show()
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if(TextUtils.isEmpty(p0)){
                    this.onQueryTextSubmit("");
                }
                return false
            }


        })
        return super.onCreateOptionsMenu(menu)
    }


    private fun traerMisJuegos() {
        val baseDatos = ManejadorBaseDatos(this)
        val cursor = baseDatos.traerTodos(columnas, ORDENAR_POR_NOMBRE)
        recorrerResultados( cursor)
        baseDatos.cerrarConexion()
    }

    @SuppressLint("Range")
    private fun buscarJuego(nombre: String) {
        val baseDatos = ManejadorBaseDatos(this)
        val camposATraer = arrayOf(nombre)
        val cursor = baseDatos.seleccionar(columnas,"nombre like ?", camposATraer, ORDENAR_POR_NOMBRE)
        recorrerResultados( cursor)
        baseDatos.cerrarConexion()
    }

    @SuppressLint("Range")
    fun recorrerResultados(cursor : Cursor){
        if(listaCampeones.size > 0)
            listaCampeones.clear()

        if(cursor.moveToFirst()){
            do{
                val juego_id = cursor.getInt(cursor.getColumnIndex("id"))
                val nombre = cursor.getString(cursor.getColumnIndex("nombre"))
                val precio = cursor.getString(cursor.getColumnIndex("categoria"))
                val consola = cursor.getString(cursor.getColumnIndex("rol"))
                val campeon: Campeon
                campeon = Campeon(juego_id, nombre, precio, consola)
                listaCampeones.add(campeon)
            }while(cursor.moveToNext())
        }
        val adapter  = CampeonAdapter( listaCampeones,this, this)
       val manager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recycler.adapter = adapter
        recycler.layoutManager = manager

    }

    override fun juegoEliminado() {
        Log.d("PRUEBAS", "juegoEliminado")
        traerMisJuegos()
    }

    override fun editarJuego(campeon: Campeon) {
        Log.d("PRUEBAS", "editar Juego "+ campeon.id)
        val intent = Intent(this, EditarActivity::class.java)
        intent.putExtra("id",campeon.id)
        intent.putExtra("nombre", campeon.nombre)
        intent.putExtra("rol", campeon.rol)
        startActivity(intent)
    }


}