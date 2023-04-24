package com.example.clase05persistenciadatossqlite.interfaces

import com.example.clase05persistenciadatossqlite.modelos.Campeon
import com.example.clase05persistenciadatossqlite.modelos.Juego

public interface juegosInterface {
    fun juegoEliminado()
    fun editarJuego(campeon: Campeon)

}