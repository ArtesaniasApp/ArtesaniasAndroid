package com.example.ArtesanApp.remote

import com.example.ArtesanApp.dto.ErrorResponse
import com.google.gson.Gson
import retrofit2.HttpException

fun mensajeError(e: Exception): String {
    if (e !is HttpException) {
        return "No se pudo conectar con el servidor"
    }

    val error = leerError(e)

    if (error?.errores != null) {
        return error.errores.values.joinToString("\n")
    }
    if (error?.mensaje != null) {
        return error.mensaje
    }

    return when (e.code()) {
        401 -> "Tu sesión expiró. Vuelve a iniciar sesión."
        403 -> "No tienes permiso para hacer esto"
        else -> "Error del servidor (${e.code()})"
    }
}

private fun leerError(e: HttpException): ErrorResponse? {
    return try {
        Gson().fromJson(e.response()?.errorBody()?.string(), ErrorResponse::class.java)
    } catch (error: Exception) {
        null
    }
}
