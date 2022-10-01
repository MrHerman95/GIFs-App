package com.hermanbocharov.gifsapp.data.network.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hermanbocharov.gifsapp.data.network.database.dao.GifInfoDao
import com.hermanbocharov.gifsapp.data.network.database.entities.GifInfoEntity

@Database(
    entities = [
        GifInfoEntity::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        private var db: AppDatabase? = null
        private const val DB_NAME = "main.db"
        private val LOCK = Any()

        fun getInstance(context: Context): AppDatabase {
            db?.let { return it }

            synchronized(LOCK) {
                db?.let { return it }

                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    DB_NAME
                ).build()

                db = instance
                return instance
            }
        }
    }

    abstract fun gifInfoDao(): GifInfoDao
}
