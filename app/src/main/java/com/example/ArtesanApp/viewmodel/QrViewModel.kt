package com.example.ArtesanApp.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

public class QrViewModel : ViewModel(){
    private val _contenidoQR = MutableStateFlow(value = "")
    val contenidoQR = _contenidoQR.asStateFlow()

    fun guardarQR(contenido : String){
        _contenidoQR.value = contenido
    }
}