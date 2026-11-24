package com.example.ArtesanApp.remote

import com.example.ArtesanApp.dto.EstadoRequest
import com.example.ArtesanApp.dto.PedidoRequest
import com.example.ArtesanApp.model.Estado
import com.example.ArtesanApp.model.MetodoPago
import com.example.ArtesanApp.model.Solicitud
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface PedidoApi {

    @GET("api/metodos-pago")
    suspend fun listarMetodosPago(): List<MetodoPago>

    @POST("api/pedidos")
    suspend fun crear(@Body datos: PedidoRequest): Solicitud

    @GET("api/pedidos/mis-pedidos")
    suspend fun listarMisPedidos(): List<Solicitud>

    @GET("api/estados-pedido")
    suspend fun listarEstados(): List<Estado>

    @GET("api/admin/pedidos")
    suspend fun listarTodos(): List<Solicitud>

    @PATCH("api/admin/pedidos/{id}/estado")
    suspend fun cambiarEstado(@Path("id") id: Long, @Body datos: EstadoRequest): Solicitud
}
