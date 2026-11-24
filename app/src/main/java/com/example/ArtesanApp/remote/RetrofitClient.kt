package com.example.ArtesanApp.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "http://10.0.2.2:8080/"

    private val cliente = OkHttpClient.Builder()
        .addInterceptor(TokenInterceptor())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(cliente)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val authApi: AuthApi by lazy { retrofit.create(AuthApi::class.java) }
    val productoApi: ProductoApi by lazy { retrofit.create(ProductoApi::class.java) }
    val pedidoApi: PedidoApi by lazy { retrofit.create(PedidoApi::class.java) }
}
