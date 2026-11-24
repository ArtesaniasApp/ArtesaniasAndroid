package com.example.ArtesanApp.remote

import com.example.ArtesanApp.dto.AuthResponse
import com.example.ArtesanApp.dto.LoginRequest
import com.example.ArtesanApp.dto.RegistroRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("api/auth/login")
    suspend fun login(@Body datos: LoginRequest): AuthResponse

    @POST("api/auth/registro")
    suspend fun registrar(@Body datos: RegistroRequest): AuthResponse
}
