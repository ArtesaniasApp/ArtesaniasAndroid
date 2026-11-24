package com.example.ArtesanApp.dto

data class PedidoRequest(
    val metodoPagoId: Long,
    val items: List<ItemPedidoRequest>
)
