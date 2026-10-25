package com.example.ArtesanApp.database

import com.example.ArtesanApp.model.Producto

// Productos de prueba (los mismos que carga el backend en DatosIniciales.java).
// Se insertan en Room la primera vez que se abre la app (ver ProductoViewModel).
val productosIniciales = listOf(
    Producto(1, "Cerámica", "DISPONIBLE", "Taza de greda de Pomaire",
        "Taza artesanal moldeada a mano, ideal para café o té.", "Greda",
        "Greda natural de Pomaire", "Cocción a 900 °C, 300 ml", 8990, 15),
    Producto(2, "Cerámica", "DISPONIBLE", "Fuente de cerámica esmaltada",
        "Fuente para servir con esmalte azul y bordes pintados a mano.", "Arcilla",
        "Arcilla blanca esmaltada", "32 cm de diámetro, apta para horno", 24990, 6),
    Producto(3, "Cerámica", "DISPONIBLE", "Chanchito de la suerte",
        "Clásico chanchito de greda de tres patas, símbolo de buena suerte.", "Greda",
        "Greda negra", "Altura 8 cm", 4990, 30),

    Producto(4, "Textil", "DISPONIBLE", "Manta de telar mapuche",
        "Manta tejida en telar tradicional con diseño geométrico.", "Lana",
        "Lana de oveja teñida con tintes naturales", "1,2 x 1,6 m", 59990, 4),
    Producto(5, "Textil", "DISPONIBLE", "Cojín de lana tejido",
        "Funda de cojín tejida a mano en tonos tierra.", "Lana",
        "Lana merino", "45 x 45 cm, incluye relleno", 18990, 10),
    Producto(6, "Textil", "AGOTADO", "Bufanda de alpaca",
        "Bufanda suave y abrigadora tejida a palillo.", "Alpaca",
        "Fibra de alpaca", "180 x 30 cm", 22990, 0),

    Producto(7, "Madera", "DISPONIBLE", "Tabla de raulí para picoteo",
        "Tabla rectangular tallada en una sola pieza.", "Madera",
        "Madera de raulí con aceite natural", "40 x 20 cm", 16990, 12),
    Producto(8, "Madera", "DISPONIBLE", "Set de cucharas de olivo",
        "Tres cucharas talladas a mano para cocina.", "Madera",
        "Madera de olivo", "Largo 25 cm cada una", 12990, 20),

    Producto(9, "Joyería", "DISPONIBLE", "Aros de lapislázuli",
        "Aros colgantes con piedra lapislázuli chilena.", "Plata",
        "Plata 950 y lapislázuli", "Largo 3 cm", 27990, 8),
    Producto(10, "Joyería", "DISPONIBLE", "Collar de cobre martillado",
        "Collar con colgante de cobre trabajado a mano.", "Cobre",
        "Cobre chileno", "Cadena de 45 cm", 19990, 9),

    Producto(11, "Cestería", "DISPONIBLE", "Panera de mimbre",
        "Panera ovalada tejida con mimbre de Chimbarongo.", "Mimbre",
        "Mimbre natural", "30 x 20 x 10 cm", 9990, 14),
    Producto(12, "Cestería", "DISPONIBLE", "Canasto de coirón",
        "Canasto con tapa tejido en fibra vegetal.", "Fibra vegetal",
        "Coirón y quilineja", "Diámetro 25 cm", 21990, 5)
)
