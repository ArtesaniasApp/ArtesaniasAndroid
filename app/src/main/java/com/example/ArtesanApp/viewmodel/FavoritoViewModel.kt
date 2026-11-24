package com.example.ArtesanApp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ArtesanApp.database.AppDatabase
import com.example.ArtesanApp.model.Favorito
import com.example.ArtesanApp.model.Producto
import com.example.ArtesanApp.repository.FavoritoRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavoritoViewModel(application: Application)
    : AndroidViewModel(application) {
    private val repository: FavoritoRepository

    private var usuarioId: Long = 0

    private var lectura: Job? = null

    private val _favoritos = MutableStateFlow<List<Favorito>>(emptyList())
    val favoritos: StateFlow<List<Favorito>> = _favoritos

    init {

        val favoritoDao = AppDatabase
            .getDatabase(application)
            .favoritoDao()

        repository = FavoritoRepository(favoritoDao)
    }

    fun cargarFavoritos(idUsuario: Long) {

        usuarioId = idUsuario

        lectura?.cancel()
        lectura = viewModelScope.launch {
            repository.favoritosListar(idUsuario).collect { lista ->
                _favoritos.value = lista
            }
        }
    }

    fun cambiarFavorito(producto: Producto, esFavorito: Boolean) {

        val favorito = Favorito(
            usuarioId = usuarioId,
            productoId = producto.id
        )

        viewModelScope.launch {
            if (esFavorito) {
                repository.favoritoEliminar(favorito)
            } else {
                repository.favoritoAlmacenar(favorito)
            }
        }
    }
}
