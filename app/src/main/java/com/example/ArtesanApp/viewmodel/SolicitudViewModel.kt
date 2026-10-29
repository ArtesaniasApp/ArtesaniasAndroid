package com.example.ArtesanApp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.ArtesanApp.model.DetalleSolicitud
import com.example.ArtesanApp.model.EstadoPedido
import com.example.ArtesanApp.model.MetodoPago
import com.example.ArtesanApp.model.Solicitud
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class SolicitudViewModel : ViewModel() {

    private val _solicitudes = MutableStateFlow<List<Solicitud>>(emptyList())
    val solicitudes: StateFlow<List<Solicitud>> = _solicitudes

    fun agregarSolicitud(detalles: List<DetalleSolicitud>, total: Int, metodoPago: MetodoPago): Int {
        val numero = _solicitudes.value.size + 1
        val fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))

        val nueva = Solicitud(
            numero = numero,
            fecha = fecha,
            estado = EstadoPedido.PENDIENTE,
            metodoPago = metodoPago,
            total = total,
            detalles = detalles
        )

        _solicitudes.value = _solicitudes.value + nueva

        return numero
    }
}
