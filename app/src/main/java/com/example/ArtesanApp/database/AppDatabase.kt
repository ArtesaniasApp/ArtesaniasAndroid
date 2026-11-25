package com.example.ArtesanApp.database

import android.content.Context
import androidx.room3.Database
import androidx.room3.Room
import androidx.room3.RoomDatabase
import com.example.ArtesanApp.dao.CarritoDao
import com.example.ArtesanApp.dao.FavoritoDao
import com.example.ArtesanApp.model.Favorito
import com.example.ArtesanApp.model.ItemCarrito

@Database(
    entities = [Favorito::class, ItemCarrito::class],
    version = 5,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun favoritoDao(): FavoritoDao
    abstract fun carritoDao(): CarritoDao

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
