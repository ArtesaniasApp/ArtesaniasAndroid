package com.example.ArtesanApp.remote

import com.example.ArtesanApp.dto.EstadoRequest
import com.example.ArtesanApp.dto.ProductoRequest
import com.example.ArtesanApp.dto.StockRequest
import com.example.ArtesanApp.model.Clasificacion
import com.example.ArtesanApp.model.Estado
import com.example.ArtesanApp.model.Producto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProductoApi {

    @GET("api/productos")
    suspend fun listarCatalogo(): List<Producto>

    @GET("api/clasificaciones")
    suspend fun listarClasificaciones(): List<Clasificacion>

    @GET("api/estados-producto")
    suspend fun listarEstados(): List<Estado>

    @GET("api/admin/productos")
    suspend fun listarInventario(): List<Producto>

    @POST("api/admin/productos")
    suspend fun crear(@Body datos: ProductoRequest): Producto

    @PUT("api/admin/productos/{id}")
    suspend fun actualizar(@Path("id") id: Long, @Body datos: ProductoRequest): Producto

    @PATCH("api/admin/productos/{id}/stock")
    suspend fun cambiarStock(@Path("id") id: Long, @Body datos: StockRequest): Producto

    @PATCH("api/admin/productos/{id}/estado")
    suspend fun cambiarEstado(@Path("id") id: Long, @Body datos: EstadoRequest): Producto
}
