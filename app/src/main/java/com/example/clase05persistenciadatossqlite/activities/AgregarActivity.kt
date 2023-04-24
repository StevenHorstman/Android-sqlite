package com.example.clase05persistenciadatossqlite.activities

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.example.clase05persistenciadatossqlite.R
import com.example.clase05persistenciadatossqlite.db.ManejadorBaseDatos
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class AgregarActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private  lateinit var fabAgregar: FloatingActionButton
    private  lateinit var etJuego: EditText
    private  lateinit var etPrecio: EditText
    private  lateinit var spConsola: Spinner
    private val roles = arrayOf("Top", "Mid", "Jungle", "Bot", "Support")
    private var rolSeleccionada: String = ""
    private  lateinit var tvJuego: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar)
        inicializarVistas()

        val adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item, roles)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spConsola.adapter = adapter
        spConsola.onItemSelectedListener = this
        fabAgregar.setOnClickListener{
            val nombre = etJuego.text.toString()
            val categoria = etPrecio.text.toString()
            insertarJuego(nombre, categoria, rolSeleccionada)
        }
    }

    val columnaNombreCampeon = "nombre"
    val columnaCategoria = "categoria"
    val columnaRol = "rol"
    var id: Int = 0
    private fun insertarJuego(nombreCampeon: String, categoria: String, rol: String){
       if(!TextUtils.isEmpty(rol)) {
           val baseDatos = ManejadorBaseDatos(this)
           //  val columnas = arrayOf(columnaID, columnaNombreJuego, columnaPrecio, columnaConsola)
           val contenido = ContentValues()
           contenido.put(columnaNombreCampeon, nombreCampeon)
           contenido.put(columnaCategoria, categoria)
           contenido.put(columnaRol, rol)
           //guardar imagen
            id = baseDatos.insertar(contenido).toInt()
           if (id > 0) {
               Toast.makeText(this, "Campeon" + nombreCampeon + " agregado", Toast.LENGTH_LONG).show()
               finish()
           } else
               Toast.makeText(this, "Ups no se pudo guardar el juego", Toast.LENGTH_LONG).show()
           baseDatos.cerrarConexion()
       }else{
           Snackbar.make(tvJuego,"Favor seleccionar una Rol", 0).show()
       }
    }

    private fun inicializarVistas(){
        etJuego = findViewById(R.id.etJuego)
        fabAgregar = findViewById(R.id.fabAgregar)
        etPrecio = findViewById(R.id.etPrecio)
        spConsola = findViewById(R.id.spConsola)
        tvJuego = findViewById(R.id.tvJuego)
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, id: Long) {
        rolSeleccionada = roles[position]
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }



}