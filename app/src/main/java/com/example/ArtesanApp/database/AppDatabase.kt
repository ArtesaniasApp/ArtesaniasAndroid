package com.example.ArtesanApp.database

import android.content.Context
import androidx.room3.Database
import androidx.room3.Room
import androidx.room3.RoomDatabase
import com.example.ArtesanApp.dao.FavoritoDao
import com.example.ArtesanApp.dao.ProductoDao
import com.example.ArtesanApp.model.Favorito
import com.example.ArtesanApp.model.Producto
import com.example.ArtesanApp.model.TipoUsuario
import com.example.ArtesanApp.model.Usuario

@Database(
    entities = [Producto::class, Favorito::class, TipoUsuario::class, Usuario::class],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun productoDao(): ProductoDao
    abstract fun favoritoDao(): FavoritoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "db_artesanapp"
                )
                    .fallbackToDestructiveMigration(true)
                    .build()

                INSTANCE = instance

                instance
            }
        }
    }
}
