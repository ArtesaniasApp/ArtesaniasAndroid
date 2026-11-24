package com.example.ArtesanApp.remote

import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val peticion = chain.request().newBuilder()

        if (Sesion.token != "") {
            peticion.addHeader("Authorization", "Bearer ${Sesion.token}")
        }

        return chain.proceed(peticion.build())
    }
}
