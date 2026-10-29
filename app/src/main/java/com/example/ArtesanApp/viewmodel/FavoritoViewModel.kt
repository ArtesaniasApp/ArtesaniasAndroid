package com.example.ArtesanApp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ArtesanApp.database.AppDatabase
import com.example.ArtesanApp.model.Favorito
import com.example.ArtesanApp.model.Producto
import com.example.ArtesanApp.repository.FavoritoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoritoViewModel(application: Application)
    : AndroidViewModel(application) {
    private val repository: FavoritoRepository

    private val usuarioId: Long = 1

    val favoritos: StateFlow<List<Producto>>

    init {

        val favoritoDao = AppDatabase
            .getDatabase(application)
            .favoritoDao()

        repository = FavoritoRepository(favoritoDao)

        favoritos = repository.favoritosListar(usuarioId).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
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
