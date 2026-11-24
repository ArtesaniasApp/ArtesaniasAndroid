package com.example.ArtesanApp.repository

import com.example.ArtesanApp.dto.AuthResponse
import com.example.ArtesanApp.dto.LoginRequest
import com.example.ArtesanApp.dto.RegistroRequest
import com.example.ArtesanApp.remote.AuthApi
import com.example.ArtesanApp.remote.RetrofitClient

class AuthRepository(
    private val api: AuthApi = RetrofitClient.authApi
) {
    suspend fun login(datos: LoginRequest): AuthResponse {
        return api.login(datos)
    }

    suspend fun registrar(datos: RegistroRequest): AuthResponse {
        return api.registrar(datos)
    }
}
