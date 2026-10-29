package com.example.ArtesanApp.dao

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import androidx.room3.Update
import com.example.ArtesanApp.model.Producto
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductoDao {
    @Insert
    suspend fun productoAlmacenar(producto: Producto)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun productosAlmacenarTodos(productos: List<Producto>)

    @Update
    suspend fun productoActualizar(producto: Producto)

    @Query("SELECT * FROM producto ORDER BY id ASC")
    fun productosListar(): Flow<List<Producto>>

    @Query("SELECT COUNT(*) FROM producto")
    suspend fun productosContar(): Int
}
