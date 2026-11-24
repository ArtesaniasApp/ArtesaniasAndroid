package com.example.ArtesanApp.repository

import com.example.ArtesanApp.dto.PedidoRequest
import com.example.ArtesanApp.model.MetodoPago
import com.example.ArtesanApp.model.Solicitud
import com.example.ArtesanApp.remote.PedidoApi
import com.example.ArtesanApp.remote.RetrofitClient

class SolicitudRepository(
    private val api: PedidoApi = RetrofitClient.pedidoApi
) {
    suspend fun listarMetodosPago(): List<MetodoPago> {
        return api.listarMetodosPago()
    }

    suspend fun crear(datos: PedidoRequest): Solicitud {
        return api.crear(datos)
    }

    suspend fun listarMisPedidos(): List<Solicitud> {
        return api.listarMisPedidos()
    }
}
