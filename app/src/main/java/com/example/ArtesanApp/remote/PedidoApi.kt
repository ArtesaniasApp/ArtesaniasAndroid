package com.example.ArtesanApp.remote

import com.example.ArtesanApp.dto.PedidoRequest
import com.example.ArtesanApp.model.MetodoPago
import com.example.ArtesanApp.model.Solicitud
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface PedidoApi {

    @GET("api/metodos-pago")
    suspend fun listarMetodosPago(): List<MetodoPago>

    @POST("api/pedidos")
    suspend fun crear(@Body datos: PedidoRequest): Solicitud

    @GET("api/pedidos/mis-pedidos")
    suspend fun listarMisPedidos(): List<Solicitud>
}
